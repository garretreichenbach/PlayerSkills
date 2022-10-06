package thederpgamer.playerskills.data;

import api.common.GameClient;
import api.common.GameServer;
import api.network.PacketReadBuffer;
import api.network.PacketWriteBuffer;
import api.network.packets.PacketUtil;
import org.schema.game.common.data.player.PlayerState;
import org.schema.game.server.data.PlayerNotFountException;
import thederpgamer.playerskills.PlayerSkills;
import thederpgamer.playerskills.manager.MenuManager;
import thederpgamer.playerskills.network.PlayerOpenMenuPacket;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Stores persistent data for a player.
 *
 * @author TheDerpGamer (MrGoose#0027)
 */
public class PlayerData implements NetworkSendable {

	private String name;
	private boolean newPlayer = false;

	public PlayerData(String name) {
		this.name = name;
	}

	public PlayerData(PacketReadBuffer readBuffer) throws IOException {
		deserialize(readBuffer);
	}

	public String getName() {
		return name;
	}

	/**
	 * Resets all skills to their default values.
	 * <p>Used mainly when a new player joins the server.</p>
	 */
	public void resetSkills() {
		newPlayer = true;
	}

	/**
	 * Checks if the player has spawned in for the first time.
	 * <p>Used mainly when a new player joins the server.</p>
	 *
	 * @return True if the player has spawned in for the first time, false otherwise.
	 */
	public boolean isNewPlayer() {
		return newPlayer;
	}

	/**
	 * Checks if the player is fully spawned in on the server.
	 *
	 * @return True if the player is fully spawned in, false otherwise.
	 */
	public boolean spawnedIn() {
		if(GameServer.getServerState() != null) {
			try {
				if(GameServer.getServerState().getPlayerFromName(name) != null && !GameServer.getServerState().getPlayerFromName(name).hasSpawnWait) return true;
			} catch(PlayerNotFountException exception) {
				PlayerSkills.log.log(Level.WARNING, "Failed to get player from name: " + name, exception);
			}
		}
		return false;
	}

	/**
	 * Opens the player menu for the player.
	 * <p>If server sends this instead of client, sends a packet telling the client to open the menu instead.</p>
	 *
	 * @param playerState The player to open the menu for.
	 */
	public void openPlayerMenu(PlayerState playerState) {
		if(GameClient.getClientState() != null) {
			if(playerState.getName().equals(GameClient.getClientState().getPlayer().getName())) MenuManager.openPlayerMenu();
		} else if(GameServer.getServerState() != null) {
			PacketUtil.sendPacket(playerState, new PlayerOpenMenuPacket(this));
		}
	}

	/**
	 * Checks if the player is still on the server.
	 *
	 * @return True if the player is still on the server, false otherwise.
	 */
	public boolean isOnServer() {
		if(GameServer.getServerState() != null) {
			try {
				if(GameServer.getServerState().getPlayerFromName(name) != null) return true;
			} catch(PlayerNotFountException exception) {
				PlayerSkills.log.log(Level.WARNING, "Failed to get player from name: " + name, exception);
			}
		}
		return false;
	}

	@Override
	public void serialize(PacketWriteBuffer writeBuffer) throws IOException {
		writeBuffer.writeString(name);
		writeBuffer.writeBoolean(newPlayer);
	}

	@Override
	public void deserialize(PacketReadBuffer readBuffer) throws IOException {
		name = readBuffer.readString();
		newPlayer = readBuffer.readBoolean();
	}
}
