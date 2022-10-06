package thederpgamer.playerskills.util;

import api.common.GameClient;
import api.common.GameCommon;
import thederpgamer.playerskills.PlayerSkills;

import java.util.logging.Level;

public class DataUtils {

	public static String getResourcesPath() {
		return PlayerSkills.getInstance().getSkeleton().getResourcesFolder().getPath().replace('\\', '/');
	}

	public static String getWorldDataPath() {
		String universeName = GameCommon.getUniqueContextId();
		if(!universeName.contains(":")) return getResourcesPath() + "/data/" + universeName;
		else {
			try {

				PlayerSkills.log.log(Level.WARNING,"Client " + GameClient.getClientPlayerState().getName() + " attempted to illegally access server data.");
			} catch(Exception ignored) { }
			return null;
		}
	}
}
