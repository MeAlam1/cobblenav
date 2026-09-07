package com.metacontent.cobblenav.spawndata.collector.general

import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.metacontent.cobblenav.util.I18nUtil.label
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

class SlimeChunkCollector : GeneralConditionCollector() {
	companion object {
		const val NAME = "slime_chunk"
	}

	override val name = NAME
	override val color = 0x32CD32

	override fun collectValues(
		detail: SpawnDetail,
		condition: SpawningCondition<*>,
		player: ServerPlayer,
	): List<MutableComponent>? = condition.isSlimeChunk?.let {
		listOf(label(it.toString()))
	}
}
