package com.metacontent.cobblenav.client.gui

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractWidget

class WidgetLayerDispatcher(private val scale: Float) {

	private data class Layer(
		val name: String,
		val blockable: Boolean,
		val widgets: MutableList<AbstractWidget> = mutableListOf(),
	)

	private val layers = LinkedHashMap<String, Layer>()

	fun registerLayer(name: String, blockable: Boolean) {
		layers.putIfAbsent(name, Layer(name, blockable))
	}

	fun addWidget(layer: String, widget: AbstractWidget) {
		layerOrThrow(layer).widgets.add(widget)
	}

	fun removeWidget(layer: String, widget: AbstractWidget) {
		layerOrThrow(layer).widgets.remove(widget)
	}

	fun clearLayer(layer: String) {
		layerOrThrow(layer).widgets.clear()
	}

	fun clearAll() {
		layers.values.forEach { it.widgets.clear() }
	}

	fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float, blocked: Boolean) {
		for ((_, blockable, widgets) in layers.values) {
			if (blockable && blocked) continue
			widgets.forEach { it.render(guiGraphics, mouseX, mouseY, delta) }
		}
	}

	fun renderLayer(name: String, guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float, blocked: Boolean) {
		val layer = layerOrThrow(name)
		if (layer.blockable && blocked) return
		layer.widgets.forEach { it.render(guiGraphics, mouseX, mouseY, delta) }
	}

	fun mouseClicked(x: Double, y: Double, button: Int, blocked: Boolean): Boolean {
		for ((_, blockable, widgets) in layers.values) {
			if (blockable && blocked) continue
			if (widgets.any { it.mouseClicked(x / scale, y / scale, button) }) return true
		}
		return false
	}

	fun mouseScrolled(x: Double, y: Double, scrollX: Double, scrollY: Double, blocked: Boolean): Boolean {
		for ((_, blockable, widgets) in layers.values) {
			if (blockable && blocked) continue
			if (widgets.any { it.mouseScrolled(x / scale, y / scale, scrollX / scale, scrollY / scale) }) return true
		}
		return false
	}

	fun mouseDragged(x: Double, y: Double, button: Int, dragX: Double, dragY: Double, blocked: Boolean) {
		for ((_, blockable, widgets) in layers.values) {
			if (blockable && blocked) continue
			widgets.forEach { it.mouseDragged(x / scale, y / scale, button, dragX / scale, dragY / scale) }
		}
	}

	fun mouseReleased(x: Double, y: Double, button: Int, blocked: Boolean) {
		for ((_, blockable, widgets) in layers.values) {
			if (blockable && blocked) continue
			widgets.forEach { it.mouseReleased(x / scale, y / scale, button) }
		}
	}

	private fun layerOrThrow(name: String): Layer =
		layers[name] ?: throw IllegalArgumentException("Layer '$name' has not been registered")
}
