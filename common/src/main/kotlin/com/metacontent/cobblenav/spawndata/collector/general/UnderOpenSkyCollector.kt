package com.metacontent.cobblenav.spawndata.collector.general

import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.metacontent.cobblenav.util.I18nUtil.label
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

class UnderOpenSkyCollector : GeneralConditionCollector() {
	companion object {
		const val NAME = "under_open_sky"
	}

	override val name = NAME
	override val color = 0x1E90FF

	override fun collectValues(detail: SpawnDetail, condition: SpawningCondition<*>, player: ServerPlayer): List<MutableComponent>? {
		return condition.canSeeSky?.let {
			return listOf(label(it.toString()))
		}
	}
}
