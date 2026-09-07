package com.metacontent.cobblenav.client.settings.pokefinder

import com.metacontent.cobblenav.client.settings.pokefinder.filter.*
import com.metacontent.cobblenav.client.settings.pokefinder.type.*
import net.minecraft.resources.ResourceLocation

object RadarFilterTypeRegistry {
	private val types = mutableMapOf<ResourceLocation, RadarFilterType<out RadarFilter>>()

	fun register(type: ResourceLocation, filterType: RadarFilterType<out RadarFilter>) {
		types[type] = filterType
	}

	fun get(type: ResourceLocation): RadarFilterType<out RadarFilter>? = types[type]

	fun types(): Iterable<RadarFilterType<out RadarFilter>> = types.values

	init {
		register(TranslatedNameFilter.TYPE, TranslatedNameFilterType)
		register(PokemonPropertiesFilter.TYPE, PokemonPropertiesFilterType)
		register(LabelFilter.TYPE, LabelFilterType)
		register(EvYieldFilter.TYPE, EvYieldFilterType)
		register(UncaughtFilter.TYPE, UncaughtFilterType)
	}
}
