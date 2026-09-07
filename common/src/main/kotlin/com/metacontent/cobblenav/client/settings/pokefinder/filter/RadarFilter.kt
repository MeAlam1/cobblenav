package com.metacontent.cobblenav.client.settings.pokefinder.filter

import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.resources.ResourceLocation

interface RadarFilter {
	val type: ResourceLocation

	fun test(pokemon: Pokemon): Boolean
}
