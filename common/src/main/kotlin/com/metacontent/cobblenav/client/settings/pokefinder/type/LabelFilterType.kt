package com.metacontent.cobblenav.client.settings.pokefinder.type

import com.metacontent.cobblenav.client.gui.util.gui
import com.metacontent.cobblenav.client.settings.pokefinder.filter.LabelFilter
import com.metacontent.cobblenav.utils.I18nUtil.label

object LabelFilterType : EditableTextFilterType<LabelFilter>() {
	override val filterClass = LabelFilter::class.java

	override val typeIcon = gui("pokefinder/label")

	override val displayedName = label("pokefinder.filter.label")

	override fun createFilter(): LabelFilter = LabelFilter()
}
