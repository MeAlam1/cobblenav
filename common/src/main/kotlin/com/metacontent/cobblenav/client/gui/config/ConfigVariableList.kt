package com.metacontent.cobblenav.client.gui.config

import com.metacontent.cobblenav.config.ConfigOption
import com.metacontent.cobblenav.util.I18nUtil.label
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.ContainerObjectSelectionList
import net.minecraft.client.gui.components.CycleButton
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.network.chat.Component
import kotlin.math.min

class ConfigVariableList(private val idPrefix: String, y: Int, height: Int, screenWidth: Int) :
	ContainerObjectSelectionList<ConfigVariableList.Entry>(
		Minecraft.getInstance(),
		screenWidth,
		height,
		y,
		ConfigScreen.SLOT_HEIGHT,
	) {

	private val allEntries = mutableListOf<Entry>()

	init {
		setRenderHeader(false, 0)
	}

	fun setOptions(options: List<ConfigOption<*>>) {
		allEntries.clear()
		allEntries += options.map { Entry(it, idPrefix) }
	}

	fun filter(search: String) {
		val query = search.trim().lowercase()

		clearEntries()
		allEntries
			.filter { query.isEmpty() || it.matches(query) }
			.forEach(::addEntry)

		scrollAmount = min(maxScroll.toDouble(), scrollAmount)
	}

	override fun getRowWidth(): Int = ConfigScreen.SLOT_WIDTH
	override fun getScrollbarPosition(): Int = width / 2 + ConfigScreen.SLOT_WIDTH / 2 + ConfigScreen.PADDING

	class Entry(private val option: ConfigOption<*>, idPrefix: String) : ContainerObjectSelectionList.Entry<Entry>() {

		private val label: Component = label("$idPrefix.option.${option.name}")
		private val labelQuery: String = label.string.lowercase()

		private val widget: AbstractWidget = buildWidget()
		private val resetButton: Button = buildResetButton()
		private val children = listOf(widget, resetButton)

		init {
			updateResetState()
		}

		fun matches(query: String): Boolean = labelQuery.contains(query)

		private fun buildWidget(): AbstractWidget = when (option) {
			is ConfigOption.BooleanOption -> buildBoolean(option)
			is ConfigOption.EnumOption<*> -> buildEnum(option)
			is ConfigOption.IntOption -> buildText(option, String::toIntOrNull)
			is ConfigOption.LongOption -> buildText(option, String::toLongOrNull)
			is ConfigOption.FloatOption -> buildText(option, String::toFloatOrNull)
			is ConfigOption.DoubleOption -> buildText(option, String::toDoubleOrNull)
			is ConfigOption.StringOption -> buildText(option) { it }
		}

		private fun buildBoolean(option: ConfigOption.BooleanOption): AbstractWidget = CycleButton.onOffBuilder(option.get())
			.displayOnlyValue()
			.create(0, 0, ConfigScreen.WIDGET_WIDTH, ConfigScreen.WIDGET_HEIGHT, Component.empty()) { _, value ->
				option.set(value)
				updateResetState()
			}

		private fun buildEnum(option: ConfigOption.EnumOption<*>): AbstractWidget = CycleButton.builder<Enum<*>> { value ->
			Component.literal(value.name)
		}
			.withValues(option.values)
			.withInitialValue(option.get())
			.displayOnlyValue()
			.create(0, 0, ConfigScreen.WIDGET_WIDTH, ConfigScreen.WIDGET_HEIGHT, Component.empty()) { _, value ->
				option.setUnchecked(value)
				updateResetState()
			}

		private fun <V : Any> buildText(option: ConfigOption<V>, parse: (String) -> V?): EditBox = EditBox(
			Minecraft.getInstance().font,
			0,
			0,
			ConfigScreen.WIDGET_WIDTH,
			ConfigScreen.WIDGET_HEIGHT,
			Component.empty(),
		).also { editBox ->
			editBox.value = option.get().toString()

			editBox.setFilter { text ->
				text.isEmpty() ||
					text == "-" ||
					text == "." ||
					text == "-." ||
					parse(text) != null
			}

			editBox.setResponder { text ->
				parse(text)?.let(option::set)
				if (parse(text) != null) updateResetState()
			}
		}

		private fun buildResetButton(): Button = Button.builder(label("reset")) {
			option.reset()
			refreshWidgetValue()
			updateResetState()
		}
			.bounds(0, 0, ConfigScreen.RESET_SLOT_WIDTH, ConfigScreen.WIDGET_HEIGHT)
			.build()

		@Suppress("UNCHECKED_CAST")
		private fun refreshWidgetValue() {
			when (widget) {
				is CycleButton<*> -> (widget as CycleButton<Any>).value = option.get()
				is EditBox -> widget.value = option.get().toString()
			}
		}

		private fun updateResetState() {
			resetButton.active = !option.isAtBaseValue()
		}

		override fun render(
			graphics: GuiGraphics,
			index: Int,
			top: Int,
			left: Int,
			width: Int,
			height: Int,
			mouseX: Int,
			mouseY: Int,
			hovering: Boolean,
			partialTick: Float,
		) {
			widget.x = left + width - ConfigScreen.RESET_SLOT_WIDTH - ConfigScreen.WIDGET_WIDTH - ConfigScreen.PADDING
			widget.y = top

			resetButton.x = left + width - ConfigScreen.RESET_SLOT_WIDTH
			resetButton.y = top

			graphics.drawString(
				Minecraft.getInstance().font,
				label,
				left + ConfigScreen.PADDING,
				top + ConfigScreen.LABEL_Y_OFFSET,
				0xA0A0A0,
				false,
			)

			widget.render(graphics, mouseX, mouseY, partialTick)
			resetButton.render(graphics, mouseX, mouseY, partialTick)
		}

		override fun children(): MutableList<out GuiEventListener> = children.toMutableList()

		override fun narratables(): MutableList<out NarratableEntry> = children.filterIsInstance<NarratableEntry>().toMutableList()
	}
}
