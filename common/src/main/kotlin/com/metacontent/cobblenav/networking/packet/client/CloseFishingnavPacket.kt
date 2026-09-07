package com.metacontent.cobblenav.networking.packet.client

import com.metacontent.cobblenav.networking.packet.CobblenavNetworkPacket
import com.metacontent.cobblenav.utils.cobblenavResource
import net.minecraft.network.RegistryFriendlyByteBuf

class CloseFishingnavPacket : CobblenavNetworkPacket<CloseFishingnavPacket> {
	companion object {
		val ID = cobblenavResource("close_fishingnav")
		fun decode(buffer: RegistryFriendlyByteBuf) = CloseFishingnavPacket()
	}

	override val id = ID

	override fun encode(buffer: RegistryFriendlyByteBuf) {
	}
}
