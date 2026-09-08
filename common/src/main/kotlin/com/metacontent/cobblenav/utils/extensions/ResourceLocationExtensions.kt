package com.metacontent.cobblenav.utils.extensions

import com.cobblemon.mod.common.util.asIdentifierDefaultingNamespace
import com.metacontent.cobblenav.CobbleNav
import net.minecraft.resources.ResourceLocation

fun String.asIdentifier(namespace: String = CobbleNav.MOD_ID): ResourceLocation = this.asIdentifierDefaultingNamespace(namespace)
