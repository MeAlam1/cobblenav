package com.metacontent.cobblenav.spawndata.collector

import com.cobblemon.mod.common.api.conditional.RegistryLikeCondition
import com.cobblemon.mod.common.api.conditional.RegistryLikeIdentifierCondition
import com.cobblemon.mod.common.api.conditional.RegistryLikeTagCondition
import com.cobblemon.mod.common.api.spawning.condition.SpawningCondition
import com.metacontent.cobblenav.Cobblenav
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block

interface BlockConditionCollector<T : SpawningCondition<*>> : Collector<T> {
    override val color: Int
        get() = 0

    fun collect(condition: T): Set<ResourceLocation>

    fun Collection<RegistryLikeCondition<Block>>.toBlockSet(): Set<ResourceLocation> {
        return this.flatMap {
            if (it is RegistryLikeIdentifierCondition<Block>) {
                return@flatMap listOf(it.identifier)
            }
            if (it is RegistryLikeTagCondition<Block>) {
                if (it.tag.location.path == "natural" && Cobblenav.config.hideNaturalBlockConditions) return@flatMap emptyList()
                val optional = BuiltInRegistries.BLOCK.getTag(it.tag)
                if (optional.isPresent) {
                    return@flatMap optional.get().map { blockHolder -> BuiltInRegistries.BLOCK.getKey(blockHolder.value()) }
                }
            }
            return@flatMap emptyList()
        }.toSet()
    }
}