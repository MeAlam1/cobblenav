package com.metacontent.cobblenav.util

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.Pokemon

fun PokemonProperties.matchesOnClient(pokemon: Pokemon): Boolean {
	val customPropsBackup = this.customProperties
	val standardMatches = try {
		this.customProperties = mutableListOf()
		this.matches(pokemon)
	} finally {
		this.customProperties = customPropsBackup
	}

	return standardMatches && customPropsBackup.all { customProp ->
		if (customProp.matches(pokemon)) return@all true

		val impliedAspects = PokemonProperties().apply {
			customProperties = mutableListOf(customProp)
			updateAspects()
		}.aspects

		val aspectFallbackMatches = impliedAspects.isNotEmpty() && impliedAspects.all { it in pokemon.aspects }

		aspectFallbackMatches
	}
}
