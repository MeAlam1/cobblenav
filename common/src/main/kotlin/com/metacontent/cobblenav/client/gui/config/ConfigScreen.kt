package com.metacontent.cobblenav.client.gui.config

import com.metacontent.cobblenav.config.Config
import com.metacontent.cobblenav.utils.I18nUtil.label
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout
import net.minecraft.client.gui.layouts.LinearLayout
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents

class ConfigScreen<T : Config<T>>(private val config: T, private val parent: Screen?) :
	Screen(
		label("edit.context", "Config"), // TODO
	) {

	companion object {
		const val HEADER_HEIGHT = 45
		const val FOOTER_HEIGHT = 33

		const val PADDING = 4

		const val SLOT_WIDTH = 340
		const val SLOT_HEIGHT = 22

		const val RESET_SLOT_WIDTH = 40

		const val WIDGET_WIDTH = 110
		const val WIDGET_HEIGHT = SLOT_HEIGHT - PADDING

		const val LABEL_Y_OFFSET = 4
	}

	private val idPrefix: String = config.fileName
		.removeSuffix(".json")
		.replace('-', '_')
	private val textPrefix: String = idPrefix.replace('_', ' ')

	private lateinit var layout: HeaderAndFooterLayout
	private lateinit var searchEdit: EditBox
	private lateinit var variableList: ConfigVariableList
	private lateinit var doneButton: Button

	private var searchString = ""

	override fun init() {
		super.init()

		layout = HeaderAndFooterLayout(this, HEADER_HEIGHT, FOOTER_HEIGHT)

		val listTop = HEADER_HEIGHT + PADDING
		val listHeight = (height - FOOTER_HEIGHT - PADDING) - listTop

		variableList = ConfigVariableList(idPrefix, listTop, listHeight, width)
		variableList.setOptions(config.options())
		addRenderableWidget(variableList)

		//region Search Bar
		val searchLayout = layout.addToHeader(LinearLayout.vertical())

		searchLayout.addChild(
			StringWidget(label("search.context", textPrefix), font),
			searchLayout.newCellSettings().alignHorizontallyCenter(),
		)

		searchEdit = EditBox(
			font,
			0,
			0,
			SLOT_WIDTH / 2,
			SLOT_HEIGHT,
			label("search.context", textPrefix),
		).also { editBox ->
			editBox.height = WIDGET_HEIGHT
			editBox.setMaxLength(250)
			editBox.value = searchString

			editBox.setResponder { text ->
				searchString = text
				variableList.filter(text)
			}
		}

		searchLayout.addChild(searchEdit)
		//endregion
		//region Done Button
		doneButton = Button.builder(CommonComponents.GUI_DONE) {
			onClose()
		}
			.pos(
				width / 2 - SLOT_WIDTH / 4,
				height - FOOTER_HEIGHT + PADDING,
			)
			.width(SLOT_WIDTH / 2)
			.build()

		layout.addToFooter(doneButton)
		//endregion
		layout.visitWidgets { widget -> addRenderableWidget(widget) }
		layout.arrangeElements()

		variableList.filter(searchString)
	}

	override fun onClose() {
		config.save()
		minecraft?.setScreen(parent)
	}
}
