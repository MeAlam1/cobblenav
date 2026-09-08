package com.metacontent.cobblenav.utils.extensions

import net.minecraft.world.item.ItemDisplayContext

fun ItemDisplayContext.isGui() = this == ItemDisplayContext.GUI ||
//        this == ItemDisplayContext.GROUND ||
	this == ItemDisplayContext.FIXED
