package com.metacontent.cobblenav.client.gui.config

import com.metacontent.cobblenav.config.ClientCobblenavConfig
import com.metacontent.cobblenav.config.CobblenavConfig
import com.metacontent.cobblenav.config.Config
import com.metacontent.cobblenav.util.I18nUtil.label
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

class ConfigListScreen(
	private val parent: Screen?,
	private val entries: List<Entry>,
) : Screen(label("config_list.title")) {
	class Entry(
		val label: Component,
		val open: (parent: Screen) -> Screen,
	)

	companion object {
		private const val BUTTON_WIDTH = 200
		private const val BUTTON_HEIGHT = 20
		private const val BUTTON_SPACING = 24

		fun defaults(parent: Screen?): ConfigListScreen = ConfigListScreen(
			parent,
			listOf(
				Entry(
					Component.translatableWithFallback("cobblenav.config_list.client", "Edit Client Config"),
				) { screenParent ->
					ConfigScreen(Config.load(ClientCobblenavConfig::class.java), screenParent)
				},
				Entry(
					Component.translatableWithFallback("cobblenav.config_list.server", "Edit Server Config"),
				) { screenParent ->
					ConfigScreen(Config.load(CobblenavConfig::class.java), screenParent)
				},
			),
		)
	}

	override fun init() {
		val totalHeight = entries.size * BUTTON_SPACING
		val startY = height / 2 - totalHeight / 2

		entries.forEachIndexed { index, entry ->
			addRenderableWidget(
				Button
					.builder(entry.label) { minecraft?.setScreen(entry.open(this)) }
					.bounds(
						width / 2 - BUTTON_WIDTH / 2,
						startY + index * BUTTON_SPACING,
						BUTTON_WIDTH,
						BUTTON_HEIGHT,
					).build(),
			)
		}

		addRenderableWidget(
			Button
				.builder(Component.translatable("gui.back")) { onClose() }
				.bounds(width / 2 - BUTTON_WIDTH / 2, height - 28, BUTTON_WIDTH, BUTTON_HEIGHT)
				.build(),
		)
	}

	override fun render(
		graphics: GuiGraphics,
		mouseX: Int,
		mouseY: Int,
		partialTick: Float,
	) {
		renderBackground(graphics, mouseX, mouseY, partialTick)
		graphics.drawCenteredString(font, title, width / 2, 12, 0xFFFFFF)
		super.render(graphics, mouseX, mouseY, partialTick)
	}

	override fun onClose() {
		minecraft?.setScreen(parent)
	}

	override fun isPauseScreen(): Boolean = false
}
