package com.metacontent.cobblenav.client.gui.widget.location

import com.cobblemon.mod.common.api.text.onHover
import com.cobblemon.mod.common.api.text.red
import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget
import com.cobblemon.mod.common.client.render.drawScaledText
import com.metacontent.cobblenav.client.gui.pokenav.LocationScreen
import com.metacontent.cobblenav.client.gui.widget.button.IconButton
import com.metacontent.cobblenav.utils.I18nUtil.bucket
import com.metacontent.cobblenav.utils.I18nUtil.label
import com.metacontent.cobblenav.utils.extensions.gui
import net.minecraft.client.gui.GuiGraphics

class BucketSelectorWidget(
	x: Int,
	y: Int,
	private val parent: LocationScreen,
) : SoundlessWidget(x, y, WIDTH, HEIGHT, label("bucket_selector")) {
	companion object {
		const val WIDTH: Int = 80
		const val HEIGHT: Int = 16
		const val BUTTON_WIDTH: Int = 12
		const val BUTTON_HEIGHT: Int = 10
		const val SPACE: Int = 1
		val NEXT = gui("button/next_button")
		val PREV = gui("button/prev_button")
	}

	private val prevButton =
		IconButton(
			pX = x + 2,
			pY = y + (height - BUTTON_HEIGHT) / 2,
			pWidth = BUTTON_WIDTH,
			pHeight = BUTTON_HEIGHT,
			disabled = parent.bucketIndex <= 0,
			action = { parent.bucketIndex-- },
			texture = PREV,
		).also { addWidget(it) }
	private val nextButton =
		IconButton(
			pX = x + WIDTH - BUTTON_WIDTH,
			pY = y + (height - BUTTON_HEIGHT) / 2,
			pWidth = BUTTON_WIDTH,
			pHeight = BUTTON_HEIGHT,
			disabled = parent.bucketIndex >= parent.buckets.size - 1,
			action = { parent.bucketIndex++ },
			texture = NEXT,
		).also { addWidget(it) }

	override fun renderWidget(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
		prevButton.disabled = parent.bucketIndex <= 0
		nextButton.disabled = parent.bucketIndex >= parent.buckets.size - 1
		prevButton.render(guiGraphics, i, j, f)
		val bucketName = parent.currentBucket
		val text = bucket(bucketName)
		text.onHover(text).red()

		drawScaledText(
			context = guiGraphics,
			text = text,
			x = x + 1.5 + WIDTH / 2,
			y = y + 4,
			centered = true,
			maxCharacterWidth = WIDTH - 2 * (BUTTON_WIDTH + SPACE) - 2,
			pMouseX = i,
			pMouseY = j,
		)
		nextButton.render(guiGraphics, i, j, f)
	}
}
