package com.metacontent.cobblenav.util

import com.cobblemon.mod.common.util.asTranslated
import com.metacontent.cobblenav.Cobblenav
import net.minecraft.network.chat.MutableComponent

fun lang(
	subKey: String,
	vararg objects: Any,
): MutableComponent = "${Cobblenav.ID}.$subKey".asTranslated(*objects)

fun guiLang(subKey: String, vararg objects: Any): MutableComponent = lang("gui.$subKey", *objects)
fun creativeTabLang(subKey: String, vararg objects: Any): MutableComponent = lang("itemGroup.$subKey", *objects)
