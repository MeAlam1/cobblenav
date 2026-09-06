package com.metacontent.cobblenav.spawndata.collector.block

import com.cobblemon.mod.common.api.spawning.condition.GroundedTypeSpawningCondition
import com.metacontent.cobblenav.spawndata.collector.BlockConditionCollector
import net.minecraft.resources.ResourceLocation

class GroundedTypeBlockCollector : BlockConditionCollector<GroundedTypeSpawningCondition<*>> {
	companion object {
		const val NAME = "grounded_type_block"
	}

	override val name = NAME
	override val conditionClass = GroundedTypeSpawningCondition::class.java

	override fun collect(condition: GroundedTypeSpawningCondition<*>): Set<ResourceLocation> = condition.neededBaseBlocks?.toBlockSet() ?: emptySet()
}
