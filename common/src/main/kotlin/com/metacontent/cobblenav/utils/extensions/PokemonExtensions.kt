package com.metacontent.cobblenav.utils.extensions

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.cobblemon.mod.common.pokemon.feature.SeasonFeatureHandler
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel

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

fun PokemonProperties.createAndGetAsRenderable(level: ServerLevel? = null, pos: BlockPos? = null): RenderablePokemon {
	val pokemon = Pokemon()
	this.apply(pokemon)
	if (level != null && pos != null) {
		SeasonFeatureHandler.updateSeason(pokemon, level, pos)
	}
	return pokemon.asRenderablePokemon()
}
