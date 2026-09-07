package com.metacontent.cobblenav.client.settings.pokefinder.filter

import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.cobblenav.utils.cobblenavResource
import net.minecraft.resources.ResourceLocation

class UncaughtFilter : RadarFilter {
	companion object {
		val TYPE: ResourceLocation = cobblenavResource("uncaught")
	}

	override val type = TYPE

	override fun test(pokemon: Pokemon): Boolean {
		val speciesRecord = CobblemonClient.clientPokedexData.getSpeciesRecord(pokemon.species.resourceIdentifier)
		val knowledge = speciesRecord?.getFormRecord(pokemon.form.name)?.knowledge ?: return true
		return knowledge != PokedexEntryProgress.OWNED
	}
}
