package com.metacontent.cobblenav.client.settings

import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.cobblenav.client.settings.pokefinder.filter.RadarFilter

class PokefinderSettings : Settings<PokefinderSettings>() {
	companion object {
		const val NAME = "pokefinder"
	}

	@Transient
	override val name = NAME

	private val filters = mutableListOf<RadarFilter>()

    fun check(pokemon: Pokemon): Boolean {
        val lowercaseSpecies = species.map(String::lowercase)
        return if (species.isNotEmpty() && !lowercaseSpecies.contains(pokemon.species.name.lowercase()) && !lowercaseSpecies.contains(pokemon.species.translatedName.string.lowercase())) {        
          else if (strictAspectCheck && !pokemon.aspects.containsAll(aspects.map(String::lowercase))) {
            false
        }
        else if (!strictAspectCheck && aspects.isNotEmpty() && !aspects.any { pokemon.aspects.contains(it.lowercase()) }) {
            false
        }
        else if (strictLabelCheck && !pokemon.form.labels.containsAll(labels.map(String::lowercase))) {
            false
        }
        else if (!strictLabelCheck && labels.isNotEmpty() && !labels.any { pokemon.form.labels.contains(it.lowercase()) }) {
            false
        }
        else if (shinyOnly && !pokemon.shiny) {
            false
        }
        else {
            true
        }
    }
}
