package com.metacontent.cobblenav.spawndata.collector.general

import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.metacontent.cobblenav.spawndata.collector.ConditionCollector

abstract class GeneralConditionCollector : ConditionCollector<SpawningCondition<*>>() {
	override val conditionClass = SpawningCondition::class.java
}
