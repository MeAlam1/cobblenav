package com.metacontent.cobblenav.client.settings.pokefinder.type

import com.metacontent.cobblenav.client.gui.util.gui
import com.metacontent.cobblenav.client.gui.widget.pokefinder.EvYieldFilterWidget
import com.metacontent.cobblenav.client.settings.pokefinder.filter.EvYieldFilter
import com.metacontent.cobblenav.utils.I18nUtil.label
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.network.chat.MutableComponent

object EvYieldFilterType : RadarFilterType<EvYieldFilter> {
	override val filterClass = EvYieldFilter::class.java

	override val typeIcon = gui("pokefinder/ev_yield")

	override val displayedName: MutableComponent = label("pokefinder.filter.ev_yield")

	override fun createFilter(): EvYieldFilter = EvYieldFilter()

	override fun createWidget(filter: EvYieldFilter): AbstractWidget = EvYieldFilterWidget(filter)
}
