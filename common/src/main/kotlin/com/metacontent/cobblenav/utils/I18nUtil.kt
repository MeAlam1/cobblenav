package com.metacontent.cobblenav.utils

import com.metacontent.cobblenav.CobbleNav
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Component.translatable
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

private typealias RL = ResourceLocation
private typealias MC = MutableComponent
private typealias C = Component

object I18nUtil {
	private fun prefixed(key: String, prefix: String, vararg args: Any) =
		translatable(cobblenavResource(key).toLanguageKey(prefix), *args)

	fun withPrefix(prefix: String, key: String, vararg args: Any): MC =
		translatable("$prefix.${CobbleNav.MOD_ID}.$key", *args)

	fun tag(key: String, vararg args: Any): MutableComponent = prefixed(key, "tag", *args)

	fun item(key: String, vararg args: Any): MutableComponent = prefixed(key, "item", *args)

	fun gui(key: String, vararg args: Any): MutableComponent = prefixed(key, "gui", *args)

	fun itemGroup(key: String, vararg args: Any): MutableComponent = prefixed(key, "itemGroup", *args)

	fun label(key: String, vararg args: Any): MutableComponent = prefixed(key, "gui", *args)

	fun bucket(key: String, vararg args: Any): MutableComponent = prefixed(key, "bucket", *args)

	fun biome(key: String, vararg args: Any): MutableComponent = prefixed(key, "biome", *args)

	fun structure(key: String, vararg args: Any): MutableComponent = prefixed(key, "structure", *args)

	fun weather(key: String, vararg args: Any): MutableComponent = prefixed(key, "weather", *args)

	fun moon(key: String, vararg args: Any): MutableComponent = prefixed(key, "moon", *args)
}
