package thederpgamer.playerskills.manager;

import api.common.GameServer;
import api.mod.config.PersistentObjectUtil;
import org.schema.game.server.data.PlayerNotFountException;
import thederpgamer.playerskills.PlayerSkills;
import thederpgamer.playerskills.data.PlayerData;

import java.util.PriorityQueue;

/**
 * Manages player data for the PlayerSkills mod.
 *
 * @author TheDerpGamer (MrGoose#0027)
 */
public class PlayerDataManager {

	private static final PriorityQueue<PlayerData> newPlayerQueue = new PriorityQueue<>();

	public static PlayerData getPlayerData(String name) {
		for(Object obj : PersistentObjectUtil.getObjects(PlayerSkills.getInstance().getSkeleton(), PlayerData.class)) {
			PlayerData playerData = (PlayerData) obj;
			if(playerData.getName().equals(name)) return playerData;
		}
		return null;
	}

	public static boolean playerDataExists(String name) {
		for(Object obj : PersistentObjectUtil.getObjects(PlayerSkills.getInstance().getSkeleton(), PlayerData.class)) {
			PlayerData playerData = (PlayerData) obj;
			if(playerData.getName().equals(name)) return true;
		}
		return false;
	}

	public static void savePlayerData(PlayerData playerData) {
		PersistentObjectUtil.removeObject(PlayerSkills.getInstance().getSkeleton(), playerData);
		PersistentObjectUtil.addObject(PlayerSkills.getInstance().getSkeleton(), playerData);
		PersistentObjectUtil.save(PlayerSkills.getInstance().getSkeleton());
	}

	public static void createNewPlayerData(String name) {
		PlayerData playerData = new PlayerData(name);
		playerData.resetSkills();
		savePlayerData(playerData);
		newPlayerQueue.add(playerData);
	}

	public static void processNewPlayers() {
		while(!newPlayerQueue.isEmpty()) { //Todo: Put this on a thread so people cant stall the server just by not pressing the spawn button
			PlayerData playerData = newPlayerQueue.peek();
			if(playerData.spawnedIn()) {
				try {
					playerData.openPlayerMenu(GameServer.getServerState().getPlayerFromName(playerData.getName()));
				} catch(PlayerNotFountException exception) {
					throw new RuntimeException(exception);
				}
				newPlayerQueue.remove();
			} else if(!playerData.isOnServer()) {
				newPlayerQueue.remove();
				PersistentObjectUtil.removeObject(PlayerSkills.getInstance().getSkeleton(), playerData);
				PersistentObjectUtil.save(PlayerSkills.getInstance().getSkeleton());
			}
		}
	}
}
