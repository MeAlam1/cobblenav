package com.metacontent.cobblenav.client.gui.screen

import com.metacontent.cobblenav.config.Config
import com.metacontent.cobblenav.config.ConfigOption
import com.metacontent.cobblenav.util.guiLang
import com.metacontent.cobblenav.util.lang
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.*
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout
import net.minecraft.client.gui.layouts.LinearLayout
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class ConfigScreen<T : Config<T>>(
	private val config: T,
	private val parent: Screen?,
) : Screen(
	lang("edit.context", "${idPrefix(config)}.title"),
) {

	companion object {
		private const val HEADER_HEIGHT = 45
		private const val FOOTER_HEIGHT = 33

		private const val PADDING = 4

		private const val SLOT_WIDTH = 340
		private const val SLOT_HEIGHT = 22

		private const val RESET_SLOT_WIDTH = 40

		private const val WIDGET_WIDTH = 110
		private const val WIDGET_HEIGHT = SLOT_HEIGHT - PADDING

		private const val LABEL_Y_OFFSET = 4

		private const val SCROLLBAR_WIDTH = 6
		private const val SCROLLBAR_GAP = 8
		private const val MIN_THUMB_HEIGHT = 32

		private fun idPrefix(config: Config<*>): String = config.fileName
			.removeSuffix(".json")
			.replace('-', '_')

		private fun textPrefix(config: Config<*>): String = idPrefix(config)
			.replace('_', ' ')
	}

	private class Row(
		val option: ConfigOption<*>,
		val label: Component,
		val widget: AbstractWidget,
		val resetButton: Button,
		val isAtBaseValue: () -> Boolean,
		val refreshWidget: () -> Unit,
	) {
		var visible: Boolean = true
	}

	private lateinit var layout: HeaderAndFooterLayout
	private lateinit var searchEdit: EditBox
	private lateinit var doneButton: Button

	private val rows = mutableListOf<Row>()

	private var searchString = ""
	private var scrollAmount = 0.0
	private var maxScroll = 0.0

	private var isDraggingScrollbar = false

	private val boxLeft: Int
		get() = (width - SLOT_WIDTH) / 2

	private val boxRight: Int
		get() = boxLeft + SLOT_WIDTH

	private val scrollbarX: Int
		get() = boxRight + SCROLLBAR_GAP

	override fun init() {
		super.init()

		rows.clear()
		scrollAmount = 0.0

		setupLayout()
		setupSearch()
		setupRows()
		setupDoneButton()

		registerWidgets()
		arrangeLayoutElements()

		filterRows(searchString)
	}

	private fun setupLayout() {
		layout = HeaderAndFooterLayout(
			this,
			HEADER_HEIGHT,
			FOOTER_HEIGHT,
		)
	}

	private fun setupSearch() {
		val searchLayout = layout.addToHeader(
			LinearLayout.vertical(),
		)

		searchLayout.addChild(
			StringWidget(
				lang("search.context", textPrefix(config)),
				font,
			),
			searchLayout.newCellSettings().alignHorizontallyCenter(),
		)

		searchEdit = EditBox(
			font,
			0,
			0,
			SLOT_WIDTH / 2,
			SLOT_HEIGHT,
			lang("search.context", textPrefix(config)),
		).also { editBox ->
			editBox.height = WIDGET_HEIGHT
			editBox.setMaxLength(250)
			editBox.value = searchString

			editBox.setResponder { text ->
				searchString = text
				filterRows(text)
			}
		}

		searchLayout.addChild(searchEdit)
	}

	private fun setupRows() {
		for (option in config.options()) {
			rows += buildRows(option)
		}
	}

	private fun setupDoneButton() {
		doneButton = Button.builder(CommonComponents.GUI_DONE) {
			onDone()
		}
			.pos(
				width / 2 - SLOT_WIDTH / 4,
				height - FOOTER_HEIGHT + PADDING,
			)
			.width(SLOT_WIDTH / 2)
			.build()

		layout.addToFooter(doneButton)
	}

	private fun registerWidgets() {
		layout.visitWidgets { widget ->
			addRenderableWidget(widget)
		}

		rows.forEach { row ->
			addWidget(row.widget)
			addWidget(row.resetButton)
		}
	}

	private fun arrangeLayoutElements() {
		layout.arrangeElements()
		layoutWidgets()
	}

	private fun buildRows(option: ConfigOption<*>): List<Row> = when (option) {
		is ConfigOption.BooleanOption ->
			listOf(
				createScalarRow(
					option = option,
					label = guiLang("${idPrefix(config)}.option.${option.name}"),
					widgetBuilder = { onChanged ->
						buildBooleanWidget(option, onChanged)
					},
				),
			)

		is ConfigOption.EnumOption<*> ->
			listOf(
				createScalarRow(
					option = option,
					label = guiLang("${idPrefix(config)}.option.${option.name}"),
					widgetBuilder = { onChanged ->
						buildEnumWidget(option, onChanged)
					},
				),
			)

		is ConfigOption.IntOption -> listOf(buildParsedTextRow(option, String::toIntOrNull))

		is ConfigOption.LongOption -> listOf(buildParsedTextRow(option, String::toLongOrNull))

		is ConfigOption.FloatOption -> listOf(buildParsedTextRow(option, String::toFloatOrNull))

		is ConfigOption.DoubleOption -> listOf(buildParsedTextRow(option, String::toDoubleOrNull))

		is ConfigOption.StringOption ->
			listOf(
				createTextRow(
					option = option,
					label = guiLang("${idPrefix(config)}.option.${option.name}"),
					initialValue = option.get(),
					isValid = { true },
					parseAndSet = { text ->
						option.set(text)
					},
					refresh = { editBox ->
						editBox.value = option.get()
					},
				),
			)
	}

	private fun <T : Any> buildParsedTextRow(
		option: ConfigOption<T>,
		parse: (String) -> T?,
	): Row = createTextRow(
		option = option,
		label = guiLang("${idPrefix(config)}.option.${option.name}"),
		initialValue = option.get().toString(),
		isValid = { parse(it) != null },
		parseAndSet = { text ->
			parse(text)?.let(option::set)
		},
		refresh = { editBox ->
			editBox.value = option.get().toString()
		},
	)

	private fun createScalarRow(
		option: ConfigOption<*>,
		label: Component,
		widgetBuilder: (onChanged: () -> Unit) -> AbstractWidget,
	): Row {
		lateinit var row: Row

		val widget = widgetBuilder {
			updateResetState(row)
		}

		val resetButton = createResetButton {
			option.reset()
			row.refreshWidget()
			updateResetState(row)
		}

		row = Row(
			option = option,
			label = label,
			widget = widget,
			resetButton = resetButton,
			isAtBaseValue = {
				option.isAtBaseValue()
			},
			refreshWidget = {
				refreshWidget(widget, option)
			},
		)

		updateResetState(row)

		return row
	}

	private fun <V : Any> createTextRow(
		option: ConfigOption<V>,
		label: Component,
		initialValue: String,
		isValid: (String) -> Boolean,
		parseAndSet: (String) -> Unit,
		refresh: (EditBox) -> Unit,
	): Row {
		lateinit var row: Row

		val editBox = buildTextWidget(
			initialText = initialValue,
			isValid = isValid,
			apply = { text ->
				parseAndSet(text)

				if (isValid(text)) {
					updateResetState(row)
				}
			},
		)

		val resetButton = createResetButton {
			option.reset()
			refresh(editBox)
			updateResetState(row)
		}

		row = Row(
			option = option,
			label = label,
			widget = editBox,
			resetButton = resetButton,
			isAtBaseValue = {
				option.isAtBaseValue()
			},
			refreshWidget = {
				refresh(editBox)
			},
		)

		updateResetState(row)

		return row
	}

	private fun createResetButton(
		onReset: () -> Unit,
	): Button = Button.builder(
		lang(
			"reset",
		),
	) {
		onReset()
	}
		.bounds(
			0,
			0,
			RESET_SLOT_WIDTH,
			WIDGET_HEIGHT,
		)
		.build()

	private fun updateResetState(row: Row) {
		row.resetButton.active = !row.isAtBaseValue()
	}

	private fun buildBooleanWidget(
		option: ConfigOption.BooleanOption,
		onChanged: () -> Unit,
	): AbstractWidget = CycleButton.onOffBuilder(option.get())
		.displayOnlyValue()
		.create(
			0,
			0,
			WIDGET_WIDTH,
			WIDGET_HEIGHT,
			Component.empty(),
		) { _, value ->
			option.set(value)
			onChanged()
		}

	private fun buildEnumWidget(
		option: ConfigOption.EnumOption<*>,
		onChanged: () -> Unit,
	): AbstractWidget {
		val values = option.values
		val initialValue = option.get()

		return CycleButton.builder<Enum<*>> { value ->
			Component.literal(value.name)
		}
			.withValues(values)
			.withInitialValue(initialValue)
			.displayOnlyValue()
			.create(
				0,
				0,
				WIDGET_WIDTH,
				WIDGET_HEIGHT,
				Component.empty(),
			) { _, value ->
				option.setUnchecked(value)
				onChanged()
			}
	}

	private fun buildTextWidget(
		initialText: String,
		isValid: (String) -> Boolean,
		apply: (String) -> Unit,
	): EditBox = EditBox(
		font,
		0,
		0,
		WIDGET_WIDTH,
		WIDGET_HEIGHT,
		Component.empty(),
	).also { editBox ->

		editBox.value = initialText

		editBox.setFilter { text ->
			text.isEmpty() ||
				text == "-" ||
				text == "." ||
				text == "-." ||
				isValid(text)
		}

		editBox.setResponder { text ->
			apply(text)
		}
	}

	private fun refreshWidget(
		widget: AbstractWidget,
		option: ConfigOption<*>,
	) {
		when (widget) {
			is CycleButton<*> -> {
				refreshCycleButton(widget, option.get())
			}

			is EditBox -> {
				widget.value = option.get().toString()
			}
		}
	}

	@Suppress("UNCHECKED_CAST")
	private fun refreshCycleButton(
		widget: CycleButton<*>,
		value: Any,
	) {
		(widget as CycleButton<Any>).value = value
	}

	private fun filterRows(search: String) {
		val query = search
			.trim()
			.lowercase()

		rows.forEach { row ->
			val label = row.label.string.lowercase()

			row.visible = query.isEmpty() ||
				label.contains(query)
		}

		scrollAmount = 0.0
		layoutWidgets()
	}

	private fun layoutWidgets() {
		val visibleRows = rows.filter { it.visible }

		val listTop = listTop()
		val listBottom = listBottom()

		val availableHeight =
			(listBottom - listTop).coerceAtLeast(0)

		val contentHeight =
			visibleRows.size * SLOT_HEIGHT

		maxScroll = (
			contentHeight - availableHeight
			).coerceAtLeast(0).toDouble()

		scrollAmount = scrollAmount.coerceIn(
			0.0,
			maxScroll,
		)

		val widgetX = boxRight -
			RESET_SLOT_WIDTH -
			WIDGET_WIDTH -
			PADDING

		val resetX = boxRight -
			RESET_SLOT_WIDTH

		visibleRows.forEachIndexed { index, row ->
			val y =
				listTop +
					index * SLOT_HEIGHT -
					scrollAmount.toInt()

			row.widget.x = widgetX
			row.widget.y = y

			row.resetButton.x = resetX
			row.resetButton.y = y

			val visible =
				y + SLOT_HEIGHT >= listTop &&
					y <= listBottom

			row.widget.visible = visible
			row.resetButton.visible = visible
		}

		rows
			.filter { !it.visible }
			.forEach { row ->
				row.widget.visible = false
				row.resetButton.visible = false
			}
	}

	private fun listTop(): Int = HEADER_HEIGHT + PADDING

	private fun listBottom(): Int = height - FOOTER_HEIGHT - PADDING

	override fun mouseScrolled(
		mouseX: Double,
		mouseY: Double,
		scrollX: Double,
		scrollY: Double,
	): Boolean {
		if (maxScroll <= 0) {
			return super.mouseScrolled(
				mouseX,
				mouseY,
				scrollX,
				scrollY,
			)
		}

		scrollAmount = (
			scrollAmount -
				scrollY * SLOT_HEIGHT
			).coerceIn(
			0.0,
			maxScroll,
		)

		layoutWidgets()

		return true
	}

	override fun mouseClicked(
		mouseX: Double,
		mouseY: Double,
		button: Int,
	): Boolean {
		if (
			maxScroll > 0 &&
			isMouseOverScrollbar(mouseX, mouseY)
		) {
			isDraggingScrollbar = true
			updateScrollFromMouse(mouseY)
			return true
		}

		return super.mouseClicked(
			mouseX,
			mouseY,
			button,
		)
	}

	override fun mouseDragged(
		mouseX: Double,
		mouseY: Double,
		button: Int,
		dragX: Double,
		dragY: Double,
	): Boolean {
		if (isDraggingScrollbar) {
			updateScrollFromMouse(mouseY)
			return true
		}

		return super.mouseDragged(
			mouseX,
			mouseY,
			button,
			dragX,
			dragY,
		)
	}

	override fun mouseReleased(
		mouseX: Double,
		mouseY: Double,
		button: Int,
	): Boolean {
		if (isDraggingScrollbar) {
			isDraggingScrollbar = false
			return true
		}

		return super.mouseReleased(
			mouseX,
			mouseY,
			button,
		)
	}

	private fun isMouseOverScrollbar(
		mouseX: Double,
		mouseY: Double,
	): Boolean {
		val listTop = listTop()
		val listBottom = listBottom()

		return mouseX >= scrollbarX &&
			mouseX <= scrollbarX + SCROLLBAR_WIDTH &&
			mouseY >= listTop &&
			mouseY <= listBottom
	}

	private fun updateScrollFromMouse(mouseY: Double) {
		val listTop = listTop()
		val listBottom = listBottom()

		val trackHeight =
			listBottom - listTop

		val contentHeight =
			rows.count { it.visible } *
				SLOT_HEIGHT

		if (contentHeight <= trackHeight) {
			return
		}

		val thumbHeight =
			(
				trackHeight.toDouble() *
					trackHeight.toDouble() /
					contentHeight.toDouble()
				)
				.coerceAtLeast(
					MIN_THUMB_HEIGHT.toDouble(),
				)

		val maxThumbY =
			trackHeight - thumbHeight

		if (maxThumbY <= 0) {
			return
		}

		val relativeY = (
			mouseY -
				listTop -
				thumbHeight / 2
			).coerceIn(
			0.0,
			maxThumbY,
		)

		scrollAmount =
			(relativeY / maxThumbY) *
			maxScroll

		layoutWidgets()
	}

	override fun render(
		graphics: GuiGraphics,
		mouseX: Int,
		mouseY: Int,
		partialTick: Float,
	) {
		super.render(
			graphics,
			mouseX,
			mouseY,
			partialTick,
		)

		val listTop = listTop()
		val listBottom = listBottom()

		graphics.enableScissor(
			boxLeft,
			listTop,
			boxRight,
			listBottom,
		)

		renderRows(
			graphics,
			mouseX,
			mouseY,
			partialTick,
			listTop,
			listBottom,
		)

		graphics.disableScissor()

		if (maxScroll > 0) {
			drawScrollbar(graphics)
		}
	}

	private fun renderRows(
		graphics: GuiGraphics,
		mouseX: Int,
		mouseY: Int,
		partialTick: Float,
		listTop: Int,
		listBottom: Int,
	) {
		val visibleRows =
			rows.filter { it.visible }

		visibleRows.forEachIndexed { index, row ->
			val y =
				listTop +
					index * SLOT_HEIGHT -
					scrollAmount.toInt()

			if (
				y + SLOT_HEIGHT < listTop ||
				y > listBottom
			) {
				return@forEachIndexed
			}

			graphics.drawString(
				font,
				row.label,
				boxLeft + PADDING,
				y + LABEL_Y_OFFSET,
				0xA0A0A0,
				false,
			)

			row.widget.render(
				graphics,
				mouseX,
				mouseY,
				partialTick,
			)

			row.resetButton.render(
				graphics,
				mouseX,
				mouseY,
				partialTick,
			)
		}
	}

	private fun drawScrollbar(
		graphics: GuiGraphics,
	) {
		val listTop = listTop()
		val listBottom = listBottom()

		val trackHeight =
			listBottom - listTop

		val contentHeight =
			rows.count { it.visible } *
				SLOT_HEIGHT

		if (contentHeight <= trackHeight) {
			return
		}

		val scrollbarRight =
			scrollbarX + SCROLLBAR_WIDTH

		graphics.fill(
			scrollbarX,
			listTop,
			scrollbarRight,
			listBottom,
			0xFF000000.toInt(),
		)

		val thumbHeight =
			(
				trackHeight.toDouble() *
					trackHeight.toDouble() /
					contentHeight.toDouble()
				)
				.coerceAtLeast(
					MIN_THUMB_HEIGHT.toDouble(),
				)

		val maxThumbOffset =
			trackHeight - thumbHeight

		val thumbOffset =
			if (maxScroll > 0) {
				(scrollAmount / maxScroll) *
					maxThumbOffset
			} else {
				0.0
			}

		val thumbY =
			listTop + thumbOffset

		graphics.fill(
			scrollbarX,
			thumbY.toInt(),
			scrollbarRight,
			(thumbY + thumbHeight).toInt(),
			-8355712,
		)

		graphics.fill(
			scrollbarX,
			thumbY.toInt(),
			scrollbarRight - 1,
			(thumbY + thumbHeight).toInt(),
			-4144960,
		)
	}

	private fun onDone() {
		config.save()
		minecraft?.setScreen(parent)
	}

	override fun onClose() {
		config.save()
		minecraft?.setScreen(parent)
	}
}
