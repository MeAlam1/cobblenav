package com.metacontent.cobblenav.client.integration

import com.metacontent.cobblenav.client.gui.config.ConfigListScreen
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

class CobblenavModMenu : ModMenuApi {
	override fun getModConfigScreenFactory(): ConfigScreenFactory<*> = ConfigScreenFactory { parent ->
		ConfigListScreen.defaults(parent)
	}
}
