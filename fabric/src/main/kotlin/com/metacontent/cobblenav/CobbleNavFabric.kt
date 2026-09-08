package com.metacontent.cobblenav

import com.metacontent.cobblenav.utils.I18nUtil.itemGroup
import com.metacontent.cobblenav.utils.cobblenavResource
import com.mojang.brigadier.arguments.ArgumentType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.fabricmc.fabric.api.`object`.builder.v1.trade.TradeOfferHelper
import net.minecraft.commands.synchronization.ArgumentTypeInfo
import net.minecraft.core.Registry.register
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import kotlin.reflect.KClass

class CobbleNavFabric :
	ModInitializer,
	CobbleNavImplementation {
	override val networkManager = CobblenavFabricNetworkManager

	override fun onInitialize() {
		CobbleNav.init(this)
		networkManager.registerMessages()
		networkManager.registerServerHandlers()

		TradeOfferHelper.registerWanderingTraderOffers(2) { factories ->
			factories.addAll(CobbleNav.resolveWandererTrades())
		}
	}

	override fun registerItems() {
		CobbleNavItems.register { resourceLocation, item ->
			register(
				CobbleNavItems.registry,
				resourceLocation,
				item,
			)
		}
		register(
			BuiltInRegistries.CREATIVE_MODE_TAB,
			cobblenavResource("cobblenav"),
			FabricItemGroup
				.builder()
				.title(itemGroup("general"))
				.icon { ItemStack(CobbleNavItems.POKENAV) }
				.displayItems(CobbleNavItems::addToGroup)
				.build(),
		)
	}

	override fun registerCommands() {
		CommandRegistrationCallback.EVENT.register(CobbleNavCommands::register)
	}

	override fun <A : ArgumentType<*>, T : ArgumentTypeInfo.Template<A>> registerCommandArgument(
		identifier: ResourceLocation,
		argumentClass: KClass<A>,
		serializer: ArgumentTypeInfo<A, T>,
	) {
		ArgumentTypeRegistry.registerArgumentType(identifier, argumentClass.java, serializer)
	}

	override fun injectLootTables() {
		LootTableEvents.MODIFY.register { id, tableBuilder, _, _ ->
			CobbleNavLootInjector.inject(id.location(), tableBuilder::withPool)
		}
	}
}
