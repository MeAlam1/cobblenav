package com.metacontent.cobblenav.utils

import com.cobblemon.mod.common.api.conditional.RegistryLikeCondition
import com.cobblemon.mod.common.api.conditional.RegistryLikeIdentifierCondition
import com.cobblemon.mod.common.api.conditional.RegistryLikeTagCondition
import com.metacontent.cobblenav.CobbleNav
import net.minecraft.resources.ResourceLocation

fun cobblenavResource(name: String, namespace: String = CobbleNav.MOD_ID): ResourceLocation =
	ResourceLocation.fromNamespaceAndPath(namespace, name)

fun RegistryLikeCondition<*>.toResourceLocation(): ResourceLocation? {
	if (this is RegistryLikeIdentifierCondition) {
		return this.identifier
	}
	if (this is RegistryLikeTagCondition) {
		return this.tag.location
	}
	return null
}
