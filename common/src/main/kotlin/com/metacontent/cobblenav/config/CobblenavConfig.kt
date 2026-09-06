package com.metacontent.cobblenav.config

import com.cobblemon.mod.common.util.removeIf
import com.metacontent.cobblenav.spawndata.collector.Collector

class CobblenavConfig : Config<CobblenavConfig>() {
	companion object {
		private val collectors = mutableMapOf<String, Boolean>()

		fun addCollector(collectorName: String, defaultValue: Boolean = true) {
			collectors[collectorName] = defaultValue
		}

		fun containsCollector(collectorName: String): Boolean = collectors.containsKey(collectorName)
	}

	@Transient
	override val fileName = "server-config.json"

	var hideUnknownPokemon = false
	var hideConditionsOfUnknownSpawns = true
	var hideNaturalBlockConditions = true
	var percentageForKnownHerd = 0.5f
	var syncLabelsWithClient = true
	var syncEvYieldWithClient = true
	var searchAreaWidth = 128.0
	var searchAreaHeight = 128.0
	var pokemonFeatureWeights = FeatureWeights.BASE
	val collectableConditions = mutableMapOf<String, Boolean>()

	override fun applyToLoadedConfig(default: CobblenavConfig) {
		collectors.forEach { this.collectableConditions.putIfAbsent(it.key, it.value) }
		this.collectableConditions.removeIf { !collectors.keys.contains(it.key) }
	}

	fun collectorEnabled(collector: Collector<*>): Boolean = collectableConditions.contains(collector.name)

	override fun options(): List<ConfigOption<*>> = listOf(
		ConfigOption.BooleanOption("hideUnknownPokemon", { hideUnknownPokemon }, { hideUnknownPokemon = it }, false),
		ConfigOption.BooleanOption(
			"hideConditionsOfUnknownSpawns",
			{ hideConditionsOfUnknownSpawns },
			{ hideConditionsOfUnknownSpawns = it },
			true,
		),
		ConfigOption.BooleanOption(
			"hideNaturalBlockConditions",
			{ hideNaturalBlockConditions },
			{ hideNaturalBlockConditions = it },
			true,
		),
		ConfigOption.FloatOption(
			"percentageForKnownHerd",
			{ percentageForKnownHerd },
			{ percentageForKnownHerd = it },
			0f..1f,
			0.5f,
		),
		ConfigOption.BooleanOption("syncLabelsWithClient", { syncLabelsWithClient }, { syncLabelsWithClient = it }, true),
		ConfigOption.BooleanOption("syncEvYieldWithClient", { syncEvYieldWithClient }, { syncEvYieldWithClient = it }, true),
		ConfigOption.DoubleOption("searchAreaWidth", { searchAreaWidth }, { searchAreaWidth = it }, 128.0),
		ConfigOption.DoubleOption("searchAreaHeight", { searchAreaHeight }, { searchAreaHeight = it }, 128.0),
	)
}
