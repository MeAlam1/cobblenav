package com.metacontent.cobblenav.client.gui.widget.radialmenu

import com.cobblemon.mod.common.api.gui.blitk
import com.metacontent.cobblenav.client.gui.Timer
import com.metacontent.cobblenav.os.PokenavOS
import com.metacontent.cobblenav.utils.I18nUtil.label
import net.minecraft.client.gui.GuiGraphics

class ClosedRadialMenu(
	os: PokenavOS,
	statefulWidget: RadialPopupMenu,
	pX: Int,
	pY: Int,
) : RadialMenuState(os, statefulWidget, pX, pY, MENU_DIAMETER, MENU_DIAMETER, label("closed_radial_menu")) {
	companion object {
		const val ANIMATION_DURATION: Float = 0.5f
	}

	private val timer = Timer(ANIMATION_DURATION)

	override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
		if ((statefulWidget as RadialPopupMenu).pokenavScreen.blockWidgets) return

		var rgb = 1f
		var alpha = 0.6f
		if (ishHovered(mouseX, mouseY)) {
			timer.tick(delta)
			rgb = 1.1f
			alpha = 1f
		} else if (timer.getProgress() != 0f) {
			timer.reset()
		}

		blitk(
			guiGraphics.pose(),
			RADIAL_MENU,
			x,
			y - 2 * timer.getProgress(),
			width = MENU_DIAMETER,
			height = MENU_DIAMETER,
			textureWidth = ANIMATION_SHEET_WIDTH,
			red = rgb,
			green = rgb,
			blue = rgb,
			alpha = alpha,
		)
	}

	override val blockScreenWidgets: Boolean = false

	override fun mouseClicked(pMouseX: Double, pMouseY: Double, pButton: Int): Boolean {
		if (clicked(
				pMouseX,
				pMouseY,
			) && isValidClickButton(pButton) && !(statefulWidget as RadialPopupMenu).pokenavScreen.blockWidgets
		) {
			statefulWidget.changeState(OpeningRadialMenu(os, statefulWidget, x, y))
			return true
		}
		return false
	}
}
