package com.metacontent.cobblenav.client.settings.pokefinder.filter

import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.cobblenav.utils.cobblenavResource
import net.minecraft.resources.ResourceLocation

// TODO: Unused
class ShinyFilter : RadarFilter {
	companion object {
		val TYPE: ResourceLocation = cobblenavResource("shiny")
	}

	override val type = TYPE

	override fun test(pokemon: Pokemon): Boolean = pokemon.shiny
}
