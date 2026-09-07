package com.metacontent.cobblenav.spawndata.collector.general

import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

class YHeightCollector : GeneralConditionCollector() {
	companion object {
		const val NAME = "y_height"
	}

	override val name = NAME
	override val color = 0x4B0082

	override fun collectValues(detail: SpawnDetail, condition: SpawningCondition<*>, player: ServerPlayer): List<MutableComponent>? =
		formatValueRange(condition.minY, condition.maxY)?.let {
			listOf(Component.literal(it))
		}
}
