package com.metacontent.cobblenav.client.integration

import com.metacontent.cobblenav.client.gui.screen.ConfigListScreen
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.minecraft.client.Minecraft

class CobblenavModMenu : ModMenuApi {
	override fun getModConfigScreenFactory(): ConfigScreenFactory<*> = ConfigScreenFactory { parent ->
		ConfigListScreen.defaults(parent)
	}
}
