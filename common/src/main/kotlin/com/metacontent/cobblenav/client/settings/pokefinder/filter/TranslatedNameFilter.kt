package com.metacontent.cobblenav.client.settings.pokefinder.filter

import com.cobblemon.mod.common.pokemon.Pokemon
import com.metacontent.cobblenav.client.CobblenavClient
import com.metacontent.cobblenav.utils.cobblenavResource
import net.minecraft.resources.ResourceLocation

class TranslatedNameFilter(private var names: List<String> = emptyList()) : EditableTextFilter() {
	companion object {
		val TYPE: ResourceLocation = cobblenavResource("name")
	}

	override val type = TYPE

	override fun test(pokemon: Pokemon): Boolean = names.isEmpty() || names.any {
		it.equals(pokemon.getDisplayName().string, true)
	}

	override fun update(value: String) {
		names = value
			.split(",")
			.map(String::trim)
			.filter(String::isNotEmpty)

		CobblenavClient.pokefinderSettings?.changed = true
	}

	override fun asString(): String = names.joinToString()
}
