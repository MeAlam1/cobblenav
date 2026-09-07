package com.metacontent.cobblenav.util

import com.cobblemon.mod.common.util.asIdentifierDefaultingNamespace
import com.metacontent.cobblenav.Cobblenav
import net.minecraft.resources.ResourceLocation

fun String.asIdentifier(namespace: String = Cobblenav.ID): ResourceLocation = this.asIdentifierDefaultingNamespace(namespace)
