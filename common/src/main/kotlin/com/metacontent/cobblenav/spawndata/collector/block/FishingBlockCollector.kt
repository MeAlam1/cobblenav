package com.metacontent.cobblenav.spawndata.collector.block

import com.cobblemon.mod.common.api.spawning.condition.FishingSpawningCondition
import com.metacontent.cobblenav.spawndata.collector.BlockConditionCollector
import net.minecraft.resources.ResourceLocation

class FishingBlockCollector : BlockConditionCollector<FishingSpawningCondition> {
	companion object {
		const val NAME = "fishing_block"
	}

	override val name = NAME
	override val conditionClass = FishingSpawningCondition::class.java

	override fun collect(condition: FishingSpawningCondition): Set<ResourceLocation> = condition.neededNearbyBlocks?.toBlockSet() ?: emptySet()
}
