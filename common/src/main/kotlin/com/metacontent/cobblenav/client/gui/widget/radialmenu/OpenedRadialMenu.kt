package com.metacontent.cobblenav.client.gui.widget.radialmenu

import com.metacontent.cobblenav.client.gui.pokenav.ContactsScreen
import com.metacontent.cobblenav.client.gui.pokenav.LocationScreen
import com.metacontent.cobblenav.client.gui.pokenav.MapScreen
import com.metacontent.cobblenav.client.gui.widget.button.IconButton
import com.metacontent.cobblenav.os.PokenavOS
import com.metacontent.cobblenav.utils.I18nUtil.label
import net.minecraft.client.gui.GuiGraphics
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class OpenedRadialMenu(
	os: PokenavOS,
	statefulWidget: RadialPopupMenu,
	pX: Int,
	pY: Int,
) : RadialMenuState(os, statefulWidget, pX, pY, DIAMETER, DIAMETER, label("opened_radial_menu")) {
	companion object {
		const val DIAMETER: Int = 100
	}

	private val closeButton =
		IconButton(
			pX = x,
			pY = y - MENU_DIAMETER / 2,
			pWidth = MENU_DIAMETER,
			pHeight = MENU_DIAMETER,
			texture = RADIAL_MENU,
			textureWidth = ANIMATION_SHEET_WIDTH,
			uOffset = ANIMATION_SHEET_WIDTH - MENU_DIAMETER,
			action = { statefulWidget.changeState(ClosedRadialMenu(os, statefulWidget, x, y)) },
		).also { addWidget(it) }

	private val buttons =
		listOf(
			IconButton(
				pWidth = 16,
				pHeight = 16,
				action = { statefulWidget.pokenavScreen.changeScreen(MapScreen(os)) },
				texture = MAP,
				disabled = true,
			),
			IconButton(
				pWidth = 16,
				pHeight = 16,
				action = { statefulWidget.pokenavScreen.changeScreen(LocationScreen(os)) },
				texture = LOCATION,
			),
			IconButton(
				pWidth = 16,
				pHeight = 16,
				action = { statefulWidget.pokenavScreen.changeScreen(ContactsScreen(os)) },
				texture = CONTACTS,
				disabled = true,
			),
			IconButton(
				pWidth = 16,
				pHeight = 16,
				action = { statefulWidget.parentScreen?.onClose() },
				texture = SWITCH_OFF,
			),
		).also {
			val iterator = it.iterator()
			var buttonIndex = 0.5
			while (iterator.hasNext()) {
				val button = iterator.next()
				val angle = buttonIndex / it.size.toFloat() * PI - PI / 2
				// plus 1 to make it more symmetrical
				button.x = (x + 1 - 40 * sin(angle)).toInt()
				button.y = (y - MENU_DIAMETER / 2 - 40 * cos(angle)).toInt()
				addWidget(button)
				buttonIndex++
			}
		}

	override fun renderWidget(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
		closeButton.render(guiGraphics, i, j, f)
		buttons.forEach { it.render(guiGraphics, i, j, f) }
	}

	override val blockScreenWidgets: Boolean = true

	override fun mouseClicked(pMouseX: Double, pMouseY: Double, pButton: Int): Boolean {
		val clicked = super.mouseClicked(pMouseX, pMouseY, pButton)
		if (!clicked) {
			statefulWidget.changeState(ClosedRadialMenu(os, statefulWidget as RadialPopupMenu, x, y))
		}
		return clicked
	}
}
