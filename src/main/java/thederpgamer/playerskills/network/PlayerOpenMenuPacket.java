package thederpgamer.playerskills.network;

import api.common.GameClient;
import api.network.Packet;
import api.network.PacketReadBuffer;
import api.network.PacketWriteBuffer;
import org.schema.game.common.data.player.PlayerState;
import thederpgamer.playerskills.data.PlayerData;

import java.io.IOException;

/**
 * Makes the client open the PlayerSkills menu.
 * [SERVER->CLIENT]
 *
 * @author TheDerpGamer (MrGoose#0027)
 */
public class PlayerOpenMenuPacket extends Packet {

	private PlayerData playerData;

	public PlayerOpenMenuPacket() {
	}

	public PlayerOpenMenuPacket(PlayerData playerData) {
		this.playerData = playerData;
	}

	@Override
	public void readPacketData(PacketReadBuffer packetReadBuffer) throws IOException {
		this.playerData = new PlayerData(packetReadBuffer);
	}

	@Override
	public void writePacketData(PacketWriteBuffer packetWriteBuffer) throws IOException {
		playerData.serialize(packetWriteBuffer);
	}

	@Override
	public void processPacketOnClient() {
		playerData.openPlayerMenu(GameClient.getClientPlayerState());
	}

	@Override
	public void processPacketOnServer(PlayerState playerState) {

	}
}
