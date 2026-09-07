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
		ConfigOption.FloatOption("screen_scale", { screenScale }, { screenScale = it }, 1f),
		ConfigOption.BooleanOption("enable_blur_effect", { enableBlurEffect }, { enableBlurEffect = it }, true),
		ConfigOption.BooleanOption(
			"send_error_messages_to_chat",
			{ sendErrorMessagesToChat },
			{ sendErrorMessagesToChat = it },
			true,
		),
		ConfigOption.FloatOption("pokefinder_screen_scale", { pokefinderScreenScale }, { pokefinderScreenScale = it }, 1f),
		ConfigOption.FloatOption("pokefinder_overlay_scale", { pokefinderOverlayScale }, { pokefinderOverlayScale = it }, 1f),
		ConfigOption.IntOption(
			"pokefinder_overlay_offset_x",
			{ pokefinderOverlayOffsetX },
			{ pokefinderOverlayOffsetX = it },
			10,
		),
		ConfigOption.IntOption(
			"pokefinder_overlay_offset_y",
			{ pokefinderOverlayOffsetY },
			{ pokefinderOverlayOffsetY = it },
			10,
		),
		ConfigOption.BooleanOption(
			"enable_display_of_names_on_radar",
			{ enableDisplayOfNamesOnRadar },
			{ enableDisplayOfNamesOnRadar = it },
			false,
		),
		ConfigOption.IntOption("track_arrow_y_offset", { trackArrowYOffset }, { trackArrowYOffset = it }, 80),
		ConfigOption.IntOption("max_cloud_number", { maxCloudNumber }, { maxCloudNumber = it }, 8),
		ConfigOption.IntOption("max_cloud_velocity", { maxCloudVelocity }, { maxCloudVelocity = it }, 6),
		ConfigOption.BooleanOption("enable_item_shaking", { enableItemShaking }, { enableItemShaking = it }, true),
		ConfigOption.BooleanOption(
			"enable_multiple_model_items",
			{ enableMultipleModelItems },
			{ enableMultipleModelItems = it },
			true,
		),
	)
}
