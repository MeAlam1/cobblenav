package com.metacontent.cobblenav.spawndata.collector.block

import com.cobblemon.mod.common.api.spawning.condition.SeafloorTypeSpawningCondition
import com.metacontent.cobblenav.spawndata.collector.BlockConditionCollector
import net.minecraft.resources.ResourceLocation

class SeafloorTypeBlockCollector : BlockConditionCollector<SeafloorTypeSpawningCondition<*>> {
	companion object {
		const val NAME = "seafloor_type_block"
	}

	override val name = NAME
	override val conditionClass = SeafloorTypeSpawningCondition::class.java

	override fun collect(condition: SeafloorTypeSpawningCondition<*>): Set<ResourceLocation> = condition.neededBaseBlocks?.toBlockSet() ?: emptySet()
}
