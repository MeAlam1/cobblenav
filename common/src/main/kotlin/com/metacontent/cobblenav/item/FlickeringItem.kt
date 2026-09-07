package com.metacontent.cobblenav.item

import com.metacontent.cobblenav.utils.extensions.isGui
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

interface FlickeringItem {
	val flickeringInventoryModel: ResourceLocation
	val flickeringInHandModel: ResourceLocation

	fun getFlickeringModel(stack: ItemStack, displayContext: ItemDisplayContext): ResourceLocation? = if (displayContext.isGui()) {
		flickeringInventoryModel
	} else if (displayContext.firstPerson()) {
		flickeringInHandModel
	} else {
		null
	}
}
