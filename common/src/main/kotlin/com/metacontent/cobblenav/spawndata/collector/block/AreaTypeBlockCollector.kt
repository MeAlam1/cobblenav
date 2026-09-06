package com.metacontent.cobblenav.spawndata.collector.block

import com.cobblemon.mod.common.api.spawning.condition.AreaTypeSpawningCondition
import com.metacontent.cobblenav.spawndata.collector.BlockConditionCollector
import net.minecraft.resources.ResourceLocation

class AreaTypeBlockCollector : BlockConditionCollector<AreaTypeSpawningCondition<*>> {
	companion object {
		const val NAME = "area_type_block"
	}

	override val name = NAME
	override val conditionClass = AreaTypeSpawningCondition::class.java

	override fun collect(condition: AreaTypeSpawningCondition<*>): Set<ResourceLocation> = condition.neededNearbyBlocks?.toBlockSet() ?: emptySet()
}
