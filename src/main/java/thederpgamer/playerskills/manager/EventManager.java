package thederpgamer.playerskills.manager;

import api.listener.Listener;
import api.listener.events.player.PlayerJoinWorldEvent;
import api.mod.StarLoader;
import thederpgamer.playerskills.PlayerSkills;

/**
 * [Description]
 *
 * @author TheDerpGamer (MrGoose#0027)
 */
public class EventManager {

	public static void initialize(PlayerSkills playerSkills) {
		StarLoader.registerListener(PlayerJoinWorldEvent.class, new Listener<PlayerJoinWorldEvent>() {
			@Override
			public void onEvent(PlayerJoinWorldEvent event) {
				if(!PlayerDataManager.playerDataExists(event.getPlayerName())) { //New player, queue creation and bring up menu when they are logged in fully.
					PlayerDataManager.createNewPlayerData(event.getPlayerName());
					PlayerDataManager.processNewPlayers();
				}
			}
		}, playerSkills);
	}
}
