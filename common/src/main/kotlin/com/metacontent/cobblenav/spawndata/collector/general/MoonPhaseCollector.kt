package com.metacontent.cobblenav.spawndata.collector.general

import com.cobblemon.mod.common.api.spawning.condition.MoonPhase
import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.metacontent.cobblenav.utils.I18nUtil.moon
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

class MoonPhaseCollector : GeneralConditionCollector() {
	companion object {
		const val NAME = "moon_phase"
	}

	override val name = NAME
	override val color = 0x708090

	override fun collectValues(
		detail: SpawnDetail,
		condition: SpawningCondition<*>,
		player: ServerPlayer,
	): List<MutableComponent>? =
		condition.moonPhase?.ranges?.flatMap { range ->
			range.mapNotNull { phase ->
				moon("${MoonPhase.entries.getOrNull(phase)?.name?.lowercase()}")
			}
		}?.distinct()
}
