package com.metacontent.cobblenav.util

import com.cobblemon.mod.common.api.pokemon.feature.GlobalSpeciesFeatures
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeatureProvider
import com.cobblemon.mod.common.api.pokemon.stats.Stat
import com.cobblemon.mod.common.entity.fishing.PokeRodFishingBobberEntity
import com.cobblemon.mod.common.pokemon.FormData
import com.metacontent.cobblenav.duck.GlobalSpeciesFeaturesDuck
import com.metacontent.cobblenav.duck.PokeRodFishingBobberEntityDuck
import com.metacontent.cobblenav.mixin.accessor.FormDataMixin
import com.metacontent.cobblenav.mixin.accessor.GrowingPlantBlockMixin
import net.minecraft.world.level.block.GrowingPlantBlock
import net.minecraft.world.level.block.GrowingPlantHeadBlock

fun PokeRodFishingBobberEntity.isTraveling(): Boolean = (this as PokeRodFishingBobberEntityDuck).`cobblenav$isTraveling`()

fun GrowingPlantBlock.getHeadBlock(): GrowingPlantHeadBlock = (this as GrowingPlantBlockMixin).invokeGetHeadBlock()

fun GlobalSpeciesFeatures.registerDirectly(name: String, provider: SpeciesFeatureProvider<*>) = (this as GlobalSpeciesFeaturesDuck).`cobblenav$registerDirectly`(name, provider)

fun FormData.setEvYield(evYield: MutableMap<Stat, Int>?) = (this as FormDataMixin).`cobblenav$setEvYield`(evYield)

fun FormData.getEvYield(): Map<Stat, Int>? = (this as FormDataMixin).`cobblenav$getEvYield`()
