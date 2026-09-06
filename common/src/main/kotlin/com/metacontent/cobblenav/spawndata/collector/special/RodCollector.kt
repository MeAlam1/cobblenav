package com.metacontent.cobblenav.spawndata.collector.special

import com.cobblemon.mod.common.api.spawning.condition.FishingSpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.metacontent.cobblenav.client.gui.util.translate
import com.metacontent.cobblenav.spawndata.collector.ConditionCollector
import com.metacontent.cobblenav.util.toResourceLocation
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

class RodCollector : ConditionCollector<FishingSpawningCondition>() {
	companion object {
		const val NAME = "rod"
	}

	override val name = NAME
	override val color = 0xA0522D
	override val conditionClass = FishingSpawningCondition::class.java

	override fun collectValues(detail: SpawnDetail, condition: FishingSpawningCondition, player: ServerPlayer): List<MutableComponent>? = condition.rod?.toResourceLocation()?.let {
		listOf(translate(it, "item"))
	}
}
