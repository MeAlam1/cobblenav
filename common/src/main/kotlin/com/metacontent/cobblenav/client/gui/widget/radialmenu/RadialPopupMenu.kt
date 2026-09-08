package com.metacontent.cobblenav.client.gui.widget.radialmenu

import com.metacontent.cobblenav.client.gui.pokenav.PokenavScreen
import com.metacontent.cobblenav.client.gui.widget.stateful.StatefulWidget
import com.metacontent.cobblenav.client.gui.widget.stateful.WidgetState
import com.metacontent.cobblenav.utils.I18nUtil.label

class RadialPopupMenu(val pokenavScreen: PokenavScreen, pX: Int, pY: Int) :
	StatefulWidget(
		pokenavScreen,
		pX,
		pY,
		RadialMenuState.MENU_DIAMETER,
		RadialMenuState.MENU_DIAMETER,
		label("radial_popup_menu"),
	) {
	val os = pokenavScreen.os

	override var state = initState(ClosedRadialMenu(pokenavScreen.os, this, pX, pY))

	override fun initState(state: WidgetState<*>): WidgetState<*> {
		(state as? RadialMenuState)?.let { pokenavScreen.blockWidgets = it.blockScreenWidgets }
		return super.initState(state)
	}

	override fun changeState(state: WidgetState<*>) {
		super.changeState(state)
		wrap(state)
	}

	private fun wrap(state: WidgetState<*>) {
		x += (width - state.width) / 2
		y += (height - state.height) / 2
		state.x = x
		state.y = y
		width = state.width
		height = state.height
	}
}
