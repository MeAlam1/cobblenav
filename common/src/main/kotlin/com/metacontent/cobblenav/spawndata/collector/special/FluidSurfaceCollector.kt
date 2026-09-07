package com.metacontent.cobblenav.spawndata.collector.special

import com.cobblemon.mod.common.api.spawning.condition.SurfaceTypeSpawningCondition
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.metacontent.cobblenav.spawndata.collector.ConditionCollector
import com.metacontent.cobblenav.utils.I18nUtil.tag
import com.metacontent.cobblenav.utils.toResourceLocation
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

class FluidSurfaceCollector : ConditionCollector<SurfaceTypeSpawningCondition<*>>() {
	companion object {
		const val NAME = "fluid_surface"
	}

	override val name = NAME
	override val color = 0x5F9EA0
	override val conditionClass = SurfaceTypeSpawningCondition::class.java

	override fun collectValues(
		detail: SpawnDetail,
		condition: SurfaceTypeSpawningCondition<*>,
		player: ServerPlayer,
	): List<MutableComponent>? = condition.fluid?.toResourceLocation()?.let {
		listOf(tag("fluid.c.${it.path}"))
	}
}
