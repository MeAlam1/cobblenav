package com.metacontent.cobblenav.client.gui.widget.party

import com.metacontent.cobblenav.utils.I18nUtil.label
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

// TODO: Unused
class PartyWidget(
	playerX: Int,
	playerY: Int,
) : AbstractWidget(playerX, playerY, 0, 0, label("party_widget")) {
	override fun renderWidget(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
	}

	override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {
	}
}
