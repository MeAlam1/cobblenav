package com.metacontent.cobblenav.util

import com.metacontent.cobblenav.Cobblenav
import net.minecraft.network.chat.Component

object TranslatableUtils {
	fun translatable(key: String, vararg args: Any): Component = Component.translatable(Cobblenav.ID + "." + key, *args)

	fun gui(key: String, vararg args: Any): Component = Component.translatable(Cobblenav.ID + ".gui." + key, *args)
}
