package com.metacontent.cobblenav.client.gui.widget.spawndata

import com.metacontent.cobblenav.client.gui.widget.stateful.WidgetState
import com.metacontent.cobblenav.utils.I18nUtil.label
import net.minecraft.client.gui.GuiGraphics

class ClosedSpawnDataDetail(statefulWidget: SpawnDataDetailWidget, x: Int, y: Int) :
	WidgetState<SpawnDataDetailWidget>(
		statefulWidget,
		x,
		y,
		SpawnDataDetailWidget.WIDTH,
		SpawnDataDetailWidget.HEIGHT,
		label("closed_spawn_data_details"),
	) {
	init {
		statefulWidget.pokenavScreen.blockWidgets = false
	}

	override fun renderWidget(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
		if (statefulWidget.displayer.isDataSelected()) {
			statefulWidget.changeState(OpeningSpawnDataDetail(statefulWidget, x, y))
		}
	}

	override fun mouseClicked(pMouseX: Double, pMouseY: Double, pButton: Int): Boolean = false
}
