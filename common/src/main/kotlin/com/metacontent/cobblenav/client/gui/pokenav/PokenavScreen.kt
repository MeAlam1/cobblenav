package com.metacontent.cobblenav.client.gui.pokenav

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.client.gui.CobblemonRenderable
import com.metacontent.cobblenav.client.CobblenavClient
import com.metacontent.cobblenav.client.gui.ScreenElementManager
import com.metacontent.cobblenav.client.gui.widget.NotificationWidget
import com.metacontent.cobblenav.client.gui.widget.StatusBarWidget
import com.metacontent.cobblenav.client.gui.widget.button.IconButton
import com.metacontent.cobblenav.client.gui.widget.button.PokenavButton
import com.metacontent.cobblenav.client.gui.widget.radialmenu.RadialMenuState
import com.metacontent.cobblenav.client.gui.widget.radialmenu.RadialPopupMenu
import com.metacontent.cobblenav.os.PokenavOS
import com.metacontent.cobblenav.utils.extensions.cobblenavScissor
import com.metacontent.cobblenav.utils.extensions.drawBlurredArea
import com.metacontent.cobblenav.utils.extensions.gui
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.FastColor

abstract class PokenavScreen(
	val os: PokenavOS,
	makeOpeningSound: Boolean,
	animateOpening: Boolean,
	component: Component,
) : Screen(component),
	CobblemonRenderable {

	companion object {
		const val WIDTH = 350
		const val HEIGHT = 250
		const val VERTICAL_BORDER_DEPTH = 21
		const val HORIZONTAL_BORDER_DEPTH = 16
		const val SCREEN_WIDTH = WIDTH - 2 * VERTICAL_BORDER_DEPTH
		const val SCREEN_HEIGHT = HEIGHT - 2 * HORIZONTAL_BORDER_DEPTH
		const val ANIMATION_SPEED = 20f
		const val ANIMATION_OFFSET = 20f
		const val BACK_BUTTON_SIZE = 14
		val DETAILS = gui("pokenav_details")
		val SCREEN_GLOW = gui("pokenav_screen_glow")
		val BORDERS = gui("pokenav_borders")
		val SCREEN = gui("pokenav_screen")
		val BACK_BUTTON = gui("button/back")
		val SUPPORT = gui("button/support_button")
	}

	val scale = CobblenavClient.config.screenScale
	var screenX = 0
	var screenY = 0
	abstract val color: Int
	val player: LocalPlayer? = Minecraft.getInstance().player

	private var animationOffset: Float = if (animateOpening) ANIMATION_OFFSET else 0f
	var blockWidgets: Boolean = false

	protected val widgets = ScreenElementManager(scale)

	lateinit var notifications: NotificationWidget
	var previousScreen: PokenavScreen? = null

	init {
		if (makeOpeningSound) {
			player?.playSound(CobblemonSounds.PC_ON, 0.1f, 1.25f)
		}
	}

	override fun init() {
		blockWidgets = false
		widgets.clear()

		width = (width / scale).toInt()
		height = (height / scale).toInt()

		screenX = (width - WIDTH) / 2
		screenY = (height - HEIGHT) / 2

		notifications = NotificationWidget(
			screenX + VERTICAL_BORDER_DEPTH,
			screenY + HORIZONTAL_BORDER_DEPTH,
		).also { widgets.addUnblockable(it) }

		initScreen()
	}

	abstract fun initScreen()

	override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
		guiGraphics.pose().pushPose()
		guiGraphics.pose().scale(scale, scale, 1f)

		val scaledMouseX = (mouseX / scale).toInt()
		val scaledMouseY = (mouseY / scale).toInt()

		renderScreenContent(guiGraphics, scaledMouseX, scaledMouseY, delta)

		guiGraphics.pose().popPose()

		if (animationOffset > 0f) {
			animationOffset -= ANIMATION_SPEED * delta
			if (animationOffset < 0f) animationOffset = 0f
		}
	}

	private fun renderScreenContent(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
		renderBaseElement(guiGraphics, BORDERS)

		renderScreenBackground(guiGraphics, SCREEN, color)

		guiGraphics.cobblenavScissor(
			screenX + VERTICAL_BORDER_DEPTH,
			screenY + HORIZONTAL_BORDER_DEPTH - 1,
			screenX + VERTICAL_BORDER_DEPTH + SCREEN_WIDTH,
			screenY + HORIZONTAL_BORDER_DEPTH + SCREEN_HEIGHT + 1,
		)

		try {
			renderOnBackLayer(guiGraphics, mouseX, mouseY, delta)
			widgets.renderBlockable(guiGraphics, mouseX, mouseY, delta, blockWidgets)
			renderOnFrontLayer(guiGraphics, mouseX, mouseY, delta)

			if (blockWidgets) {
				guiGraphics.fill(
					screenX + VERTICAL_BORDER_DEPTH,
					screenY + HORIZONTAL_BORDER_DEPTH,
					screenX + VERTICAL_BORDER_DEPTH + SCREEN_WIDTH,
					screenY + HORIZONTAL_BORDER_DEPTH + SCREEN_HEIGHT,
					FastColor.ARGB32.color(70, 0, 0, 0),
				)
				guiGraphics.drawBlurredArea(
					x1 = screenX + VERTICAL_BORDER_DEPTH + 1,
					y1 = screenY + HORIZONTAL_BORDER_DEPTH - 1,
					x2 = screenX + VERTICAL_BORDER_DEPTH + SCREEN_WIDTH - 1,
					y2 = screenY + HORIZONTAL_BORDER_DEPTH + SCREEN_HEIGHT + 1,
					blur = 3f,
					delta = delta,
				)
			}

			widgets.renderUnblockable(guiGraphics, mouseX, mouseY, delta)
		} finally {
			guiGraphics.disableScissor()
		}

		renderOverlay(guiGraphics, mouseX, mouseY, delta)
	}

	protected open fun renderOnBackLayer(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {}
	protected open fun renderOnFrontLayer(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {}
	protected open fun renderOverlay(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
		blitk(
			guiGraphics.pose(),
			texture = SCREEN_GLOW,
			x = screenX,
			y = screenY,
			width = WIDTH,
			height = HEIGHT,
			red = FastColor.ARGB32.red(color) / 128f,
			green = FastColor.ARGB32.green(color) / 128f,
			blue = FastColor.ARGB32.blue(color) / 128f,
		)
		blitk(
			guiGraphics.pose(),
			texture = DETAILS,
			x = screenX,
			y = screenY,
			width = WIDTH,
			height = HEIGHT,
		)
	}

	private fun renderBaseElement(guiGraphics: GuiGraphics, texture: ResourceLocation) {
		blitk(
			guiGraphics.pose(),
			texture = texture,
			x = screenX,
			y = screenY,
			width = WIDTH,
			height = HEIGHT,
		)
	}

	private fun renderScreenBackground(guiGraphics: GuiGraphics, texture: ResourceLocation?, color: Int) {
		guiGraphics.fill(
			screenX + VERTICAL_BORDER_DEPTH,
			screenY + HORIZONTAL_BORDER_DEPTH - 1,
			screenX + WIDTH - VERTICAL_BORDER_DEPTH,
			screenY + HEIGHT - HORIZONTAL_BORDER_DEPTH + 1,
			color,
		)
	}

	protected fun addBlockableWidget(widget: AbstractWidget) = widgets.addBlockable(widget)
	protected fun removeBlockableWidget(widget: AbstractWidget) = widgets.removeBlockable(widget)
	protected fun clearBlockableWidgets() = widgets.clearBlockable()
	protected fun addUnblockableWidget(widget: AbstractWidget) = widgets.addUnblockable(widget)
	protected fun removeUnblockableWidget(widget: AbstractWidget) = widgets.removeUnblockable(widget)
	protected fun clearUnblockableWidget() = widgets.clearUnblockable()

	protected fun addDefaultBottomWidgets(
		includeRadialMenu: Boolean = true,
		includeStatusBar: Boolean = true,
		includeBackButton: Boolean = false,
		backAction: ((PokenavButton) -> Unit)? = null,
	) {
		if (includeRadialMenu) {
			RadialPopupMenu(
				this,
				screenX + (WIDTH - RadialMenuState.MENU_DIAMETER) / 2,
				screenY + HEIGHT - HORIZONTAL_BORDER_DEPTH - RadialMenuState.MENU_DIAMETER / 2,
			).also { addUnblockableWidget(it) }
		}
		if (includeStatusBar) {
			StatusBarWidget(
				screenX + WIDTH - VERTICAL_BORDER_DEPTH - StatusBarWidget.WIDTH - 2,
				screenY + HEIGHT - HORIZONTAL_BORDER_DEPTH - StatusBarWidget.HEIGHT,
			).also { addUnblockableWidget(it) }
		}
		if (includeBackButton && backAction != null) {
			IconButton(
				pX = screenX + VERTICAL_BORDER_DEPTH,
				pY = screenY + HEIGHT - HORIZONTAL_BORDER_DEPTH - BACK_BUTTON_SIZE,
				pWidth = BACK_BUTTON_SIZE,
				pHeight = BACK_BUTTON_SIZE,
				texture = BACK_BUTTON,
				action = backAction,
			).let { addBlockableWidget(it) }
		}
	}

	override fun mouseClicked(d: Double, e: Double, i: Int): Boolean =
		widgets.mouseClicked(d, e, i, blockWidgets)

	override fun mouseScrolled(d: Double, e: Double, f: Double, g: Double): Boolean =
		widgets.mouseScrolled(d, e, f, g, blockWidgets)

	override fun mouseDragged(d: Double, e: Double, i: Int, f: Double, g: Double): Boolean {
		widgets.mouseDragged(d, e, i, f, g, blockWidgets)
		return true
	}

	override fun mouseReleased(d: Double, e: Double, i: Int): Boolean {
		widgets.mouseReleased(d, e, i, blockWidgets)
		return true
	}

	fun changeScreen(screen: PokenavScreen, savePrevious: Boolean = false) {
		onScreenChange()
		if (savePrevious) screen.previousScreen = this
		minecraft?.setScreen(screen)
	}

	fun toPreviousScreen() {
		minecraft?.screen = previousScreen
	}

	override fun onClose() {
		onScreenChange()
		super.onClose()
	}

	open fun onScreenChange() {}

	override fun isPauseScreen(): Boolean = false
}
