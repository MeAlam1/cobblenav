package com.metacontent.cobblenav.client.settings.pokefinder.type

import com.metacontent.cobblenav.client.settings.pokefinder.filter.PokemonPropertiesFilter
import com.metacontent.cobblenav.utils.I18nUtil.label
import com.metacontent.cobblenav.utils.extensions.gui

object PokemonPropertiesFilterType : EditableTextFilterType<PokemonPropertiesFilter>() {
	override val filterClass = PokemonPropertiesFilter::class.java

	override val typeIcon = gui("pokefinder/pokemon_properties")

	override val displayedName = label("pokefinder.filter.properties")

	override fun createFilter(): PokemonPropertiesFilter = PokemonPropertiesFilter()
}
