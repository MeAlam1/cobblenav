package com.metacontent.cobblenav.spawndata.collector.general

import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.metacontent.cobblenav.utils.I18nUtil.weather
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

class WeatherCollector : GeneralConditionCollector() {
	companion object {
		const val NAME = "weather"
	}

	override val name = NAME
	override val color = 0x4682B4

	override fun collectValues(
		detail: SpawnDetail,
		condition: SpawningCondition<*>,
		player: ServerPlayer,
	): List<MutableComponent>? {
		val values = mutableListOf<MutableComponent>()
		if (condition.isThundering == true) values.add(weather("thunder"))
		if (condition.isRaining == true) values.add(weather("rain"))
		if (condition.isRaining == false) values.add(weather("clear"))
		return values.ifEmpty { null }
	}
}
