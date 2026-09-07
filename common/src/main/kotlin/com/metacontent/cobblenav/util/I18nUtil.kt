package com.metacontent.cobblenav.util

import com.metacontent.cobblenav.Cobblenav
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Component.translatable
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

private typealias RL = ResourceLocation
private typealias MC = MutableComponent
private typealias C = Component

object I18nUtil {
	private fun prefixed(
		key: String,
		prefix: String,
		vararg args: Any,
	) = translatable(cobblenavResource(key).toLanguageKey(prefix), *args)

	fun withPrefix(
		prefix: String,
		key: String,
		vararg args: Any,
	): MC = translatable("$prefix.${Cobblenav.ID}.$key", *args)

	fun tag(
		key: String,
		vararg args: Any,
	): MutableComponent = prefixed(key, "tag")

	fun item(
		key: String,
		vararg args: Any,
	): MutableComponent = prefixed(key, "item")

	fun gui(
		key: String,
		vararg args: Any,
	): MutableComponent = prefixed(key, "gui")

	fun itemGroup(
		key: String,
		vararg args: Any,
	): MutableComponent = prefixed(key, "itemGroup")

	fun label(
		key: String,
		vararg args: Any,
	): MutableComponent = prefixed(key, "gui", *args)

	fun bucket(
		key: String,
		vararg args: Any,
	): MutableComponent = prefixed(key, "bucket", *args)
}
