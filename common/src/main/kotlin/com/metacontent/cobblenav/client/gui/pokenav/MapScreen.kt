package com.metacontent.cobblenav.client.gui.pokenav

import com.metacontent.cobblenav.os.PokenavOS
import com.metacontent.cobblenav.utils.I18nUtil.label
import java.awt.Color

class MapScreen(
	os: PokenavOS,
	makeOpeningSound: Boolean = false,
	animateOpening: Boolean = false,
) : PokenavScreen(os, makeOpeningSound, animateOpening, label("map")) {

	override val color = Color.decode("#000000").rgb

	override fun initScreen() {
		addDefaultBottomWidgets(
			includeRadialMenu = false,
			includeStatusBar = false,
			includeBackButton = true,
			backAction = { changeScreen(MainScreen(os)) },
		)
	}
}
