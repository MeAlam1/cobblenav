package com.metacontent.cobblenav.client.gui.pokenav

import com.metacontent.cobblenav.os.PokenavOS
import com.metacontent.cobblenav.utils.I18nUtil.label
import net.minecraft.util.FastColor

class MainScreen(
	os: PokenavOS,
	makeOpeningSound: Boolean = false,
	animateOpening: Boolean = false,
) : PokenavScreen(os, makeOpeningSound, animateOpening, label("main")) {

	override val color = FastColor.ARGB32.color(255, 79, 189, 201)

	override fun initScreen() {
		addDefaultBottomWidgets()
	}
}
