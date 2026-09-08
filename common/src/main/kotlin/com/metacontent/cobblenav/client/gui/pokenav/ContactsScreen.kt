package com.metacontent.cobblenav.client.gui.pokenav

import com.metacontent.cobblenav.os.PokenavOS
import com.metacontent.cobblenav.utils.I18nUtil.label
import java.awt.Color

class ContactsScreen(
	os: PokenavOS,
	makeOpeningSound: Boolean = false,
	animateOpening: Boolean = false,
) : PokenavScreen(os, makeOpeningSound, animateOpening, label("contacts")) {

	override val color = Color.decode("#C3BEA6").rgb

	override fun initScreen() {
		addDefaultBottomWidgets(
			includeBackButton = true,
			backAction = { changeScreen(MainScreen(os)) },
		)
	}
}
