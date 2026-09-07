package com.metacontent.cobblenav.neoforge.client

import com.metacontent.cobblenav.Cobblenav
import com.metacontent.cobblenav.client.ClientImplementation
import com.metacontent.cobblenav.client.CobblenavClient
import com.metacontent.cobblenav.client.gui.config.ConfigListScreen
import net.minecraft.util.Unit
import net.neoforged.fml.ModList
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent
import net.neoforged.neoforge.client.gui.IConfigScreenFactory
import net.neoforged.neoforge.common.NeoForge
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object CobblenavNeoForgeClient : ClientImplementation {
	fun init() {
		with(MOD_BUS) {
			addListener(this@CobblenavNeoForgeClient::initialize)
			addListener(this@CobblenavNeoForgeClient::onRegisterReloadListener)
		}
		with(NeoForge.EVENT_BUS) {
		}
	}

	private fun initialize(event: FMLClientSetupEvent) {
		CobblenavClient.init(this)
		val modContainer = ModList.get().getModContainerById(Cobblenav.ID).get()
		modContainer.registerExtensionPoint(
			IConfigScreenFactory::class.java,
			IConfigScreenFactory { _, parent -> ConfigListScreen.defaults(parent) },
		)
	}

	private fun onRegisterReloadListener(event: RegisterClientReloadListenersEvent) {
		event.registerReloadListener { synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor ->
			return@registerReloadListener synchronizer.wait(Unit.INSTANCE).thenRun {
				CobblenavClient.reloadAssets(manager)
			}
		}
	}
}
