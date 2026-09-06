package com.metacontent.cobblenav.spawndata.collector

import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition

interface Collector<T : SpawningCondition<*>> {
	val name: String
	val color: Int
	val conditionClass: Class<T>

	fun supports(condition: SpawningCondition<*>): Boolean = conditionClass.isInstance(condition)
}
