package com.metacontent.cobblenav.client.gui

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget

class ScreenElementManager(private val scale: Float) {

	private val blockable = mutableListOf<AbstractWidget>()
	private val unblockable = mutableListOf<AbstractWidget>()

	fun addBlockable(widget: AbstractWidget) {
		blockable.add(widget)
	}

	fun removeBlockable(widget: AbstractWidget) {
		blockable.remove(widget)
	}

	fun clearBlockable() {
		blockable.clear()
	}

	fun addUnblockable(widget: AbstractWidget) {
		unblockable.add(widget)
	}

	fun removeUnblockable(widget: AbstractWidget) {
		unblockable.remove(widget)
	}

	fun clearUnblockable() {
		unblockable.clear()
	}

	fun clear() {
		blockable.clear()
		unblockable.clear()
	}

	fun renderBlockable(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float, blockWidgets: Boolean) {
		if (!blockWidgets) {
			blockable.forEach { it.render(guiGraphics, mouseX, mouseY, delta) }
		}
	}

	fun renderUnblockable(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
		unblockable.forEach { it.render(guiGraphics, mouseX, mouseY, delta) }
	}

	fun mouseClicked(d: Double, e: Double, i: Int, blockWidgets: Boolean): Boolean {
		if (!blockWidgets) {
			if (blockable.any { it.mouseClicked(d / scale, e / scale, i) }) return true
		}
		return unblockable.any { it.mouseClicked(d / scale, e / scale, i) }
	}

	fun mouseScrolled(d: Double, e: Double, f: Double, g: Double, blockWidgets: Boolean): Boolean {
		if (!blockWidgets) {
			if (blockable.any { it.mouseScrolled(d / scale, e / scale, f / scale, g / scale) }) return true
		}
		return unblockable.any { it.mouseScrolled(d / scale, e / scale, f / scale, g / scale) }
	}

	fun mouseDragged(d: Double, e: Double, i: Int, f: Double, g: Double, blockWidgets: Boolean) {
		if (!blockWidgets) {
			blockable.forEach { it.mouseDragged(d / scale, e / scale, i, f / scale, g / scale) }
		}
		unblockable.forEach { it.mouseDragged(d / scale, e / scale, i, f / scale, g / scale) }
	}

	fun mouseReleased(d: Double, e: Double, i: Int, blockWidgets: Boolean) {
		if (!blockWidgets) {
			blockable.forEach { it.mouseReleased(d / scale, e / scale, i) }
		}
		unblockable.forEach { it.mouseReleased(d / scale, e / scale, i) }
	}
}
