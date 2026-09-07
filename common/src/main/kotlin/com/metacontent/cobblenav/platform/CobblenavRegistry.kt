package com.metacontent.cobblenav.platform

import com.cobblemon.mod.common.platform.PlatformRegistry
import com.metacontent.cobblenav.utils.cobblenavResource
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey

abstract class CobblenavRegistry<R : Registry<T>, K : ResourceKey<R>, T> : PlatformRegistry<R, K, T>() {

	override fun <E : T> create(name: String, entry: E): E {
		val resourceLocation = cobblenavResource(name)
		return this.create(resourceLocation, entry)
	}
}
