package com.metacontent.cobblenav.config

class ClientCobblenavConfig : Config<ClientCobblenavConfig>() {
	@Transient
	override val fileName = "client-config.json"

	var screenScale = 1f
	var enableBlurEffect = true
	var sendErrorMessagesToChat = true
	var pokefinderScreenScale = 1f
	var pokefinderOverlayScale = 1f
	var pokefinderOverlayOffsetX = 10
	var pokefinderOverlayOffsetY = 10
	var enableDisplayOfNamesOnRadar = false
	var trackArrowYOffset = 80
	var maxCloudNumber = 8
	var maxCloudVelocity = 6
	var enableItemShaking = true
	var enableMultipleModelItems = true

	override fun applyToLoadedConfig(default: ClientCobblenavConfig) {}

	override fun options(): List<ConfigOption<*>> = listOf(
		ConfigOption.FloatOption("screenScale", { screenScale }, { screenScale = it }, 1f),
		ConfigOption.BooleanOption("enableBlurEffect", { enableBlurEffect }, { enableBlurEffect = it }, true),
		ConfigOption.BooleanOption(
			"sendErrorMessagesToChat",
			{ sendErrorMessagesToChat },
			{ sendErrorMessagesToChat = it },
			true,
		),
		ConfigOption.FloatOption("pokefinderScreenScale", { pokefinderScreenScale }, { pokefinderScreenScale = it }, 1f),
		ConfigOption.FloatOption("pokefinderOverlayScale", { pokefinderOverlayScale }, { pokefinderOverlayScale = it }, 1f),
		ConfigOption.IntOption(
			"pokefinderOverlayOffsetX",
			{ pokefinderOverlayOffsetX },
			{ pokefinderOverlayOffsetX = it },
			10,
		),
		ConfigOption.IntOption(
			"pokefinderOverlayOffsetY",
			{ pokefinderOverlayOffsetY },
			{ pokefinderOverlayOffsetY = it },
			10,
		),
		ConfigOption.BooleanOption(
			"enableDisplayOfNamesOnRadar",
			{ enableDisplayOfNamesOnRadar },
			{ enableDisplayOfNamesOnRadar = it },
			false,
		),
		ConfigOption.IntOption("trackArrowYOffset", { trackArrowYOffset }, { trackArrowYOffset = it }, 80),
		ConfigOption.IntOption("maxCloudNumber", { maxCloudNumber }, { maxCloudNumber = it }, 8),
		ConfigOption.IntOption("maxCloudVelocity", { maxCloudVelocity }, { maxCloudVelocity = it }, 6),
		ConfigOption.BooleanOption("enableItemShaking", { enableItemShaking }, { enableItemShaking = it }, true),
		ConfigOption.BooleanOption(
			"enableMultipleModelItems",
			{ enableMultipleModelItems },
			{ enableMultipleModelItems = it },
			true,
		),
	)
}
