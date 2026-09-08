package com.metacontent.cobblenav.client.settings.pokefinder.filter

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.cobblenav.client.CobblenavClient
import com.metacontent.cobblenav.utils.cobblenavResource
import com.metacontent.cobblenav.utils.extensions.matchesOnClient
import net.minecraft.resources.ResourceLocation

class PokemonPropertiesFilter(private var properties: PokemonProperties = PokemonProperties()) : EditableTextFilter() {
	companion object {
		val TYPE: ResourceLocation = cobblenavResource("properties")
	}

	override val type = TYPE

	override fun test(pokemon: Pokemon): Boolean {
		val result = properties.matchesOnClient(pokemon)
		return result
	}

	override fun update(value: String) {
		try {
			properties = if (value.isBlank()) {
				PokemonProperties()
			} else {
				PokemonProperties.parse(value)
			}
		} catch (_: Exception) {
		}

		CobblenavClient.pokefinderSettings?.changed = true
	}

	override fun asString(): String = properties.originalString
}
