package com.metacontent.cobblenav.client.settings.pokefinder.type

import com.metacontent.cobblenav.client.gui.util.gui
import com.metacontent.cobblenav.client.settings.pokefinder.filter.PokemonPropertiesFilter
import com.metacontent.cobblenav.util.I18nUtil.label

object PokemonPropertiesFilterType : EditableTextFilterType<PokemonPropertiesFilter>() {
	override val filterClass = PokemonPropertiesFilter::class.java

	override val typeIcon = gui("pokefinder/pokemon_properties")

	override val displayedName = label("pokefinder.filter.properties")

	override fun createFilter(): PokemonPropertiesFilter = PokemonPropertiesFilter()
}
