package com.metacontent.cobblenav.spawndata.collector.special

import com.cobblemon.mod.common.api.spawning.condition.SubmergedTypeSpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.metacontent.cobblenav.client.gui.util.translate
import com.metacontent.cobblenav.spawndata.collector.ConditionCollector
import com.metacontent.cobblenav.toResourceLocation
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

class FluidSubmergedCollector : ConditionCollector<SubmergedTypeSpawningCondition<*>>() {
	companion object {
		const val NAME = "fluid_submerged"
	}

	override val name = NAME
	override val color = 0x20B2AA
	override val conditionClass = SubmergedTypeSpawningCondition::class.java

	override fun collectValues(detail: SpawnDetail, condition: SubmergedTypeSpawningCondition<*>, player: ServerPlayer): List<MutableComponent>? = condition.fluid?.toResourceLocation()?.let {
		listOf(translate("tag.fluid.c.${it.path}"))
	}
}
