package thederpgamer.playerskills.manager;

import api.network.packets.PacketUtil;
import thederpgamer.playerskills.network.PlayerOpenMenuPacket;

/**
 * [Description]
 *
 * @author TheDerpGamer (MrGoose#0027)
 */
public class NetworkManager {

	public static void initialize() {
		PacketUtil.registerPacket(PlayerOpenMenuPacket.class);
	}
}
