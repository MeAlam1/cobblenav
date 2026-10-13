package com.metacontent.cobblenav.networking.handler.server

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler
import com.metacontent.cobblenav.networking.packet.client.LocationScreenInitDataPacket
import com.metacontent.cobblenav.networking.packet.server.RequestLocationScreenInitDataPacket
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

object RequestLocationScreenInitDataHandler : ServerNetworkPacketHandler<RequestLocationScreenInitDataPacket> {
	private const val BOSS_BUCKET = "boss"

	override fun handle(packet: RequestLocationScreenInitDataPacket, server: MinecraftServer, player: ServerPlayer) {
		server.execute {
			val buckets = Cobblemon.bestSpawner.config.worldBuckets.entries
				.sortedWith(
					compareBy<Map.Entry<String, Float>> { it.key.equals(BOSS_BUCKET, ignoreCase = true) }
						.thenByDescending { it.value },
				)
				.map { it.key }

			val biome = player.level().getBiome(player.onPos).registeredName

			LocationScreenInitDataPacket(buckets, biome).sendToPlayer(player)
		}
	}
}
