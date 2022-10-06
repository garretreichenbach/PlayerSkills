package thederpgamer.playerskills;

import api.mod.StarMod;
import thederpgamer.playerskills.manager.ConfigManager;
import thederpgamer.playerskills.manager.EventManager;
import thederpgamer.playerskills.manager.NetworkManager;
import thederpgamer.playerskills.util.DataUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * [Description]
 *
 * @author TheDerpGamer (MrGoose#0027)
 */
public class PlayerSkills extends StarMod {

	//Instance
	private static PlayerSkills instance;
	public PlayerSkills() {

	}
	public static PlayerSkills getInstance() {
		return instance;
	}
	public static void main(String[] args) {
	}

	//Data
	public static Logger log;

	@Override
	public void onEnable() {
		instance = this;
		ConfigManager.initialize(this);
		initLogger();
		EventManager.initialize(this);
		NetworkManager.initialize();
	}

	private void initLogger() {
		String logFolderPath = DataUtils.getWorldDataPath() + "/logs";
		File logsFolder = new File(logFolderPath);
		if(!logsFolder.exists()) logsFolder.mkdirs();
		else {
			if(logsFolder.listFiles() != null && logsFolder.listFiles().length > 0) {
				File[] logFiles = new File[logsFolder.listFiles().length];
				int j = logFiles.length - 1;
				for(int i = 0; i < logFiles.length && j >= 0; i++) {
					logFiles[i] = logsFolder.listFiles()[j];
					j--;
				}

				for(File logFile : logFiles) {
					String fileName = logFile.getName().replace(".txt", "");
					int logNumber = Integer.parseInt(fileName.substring(fileName.indexOf("log") + 3)) + 1;
					String newName = logFolderPath + "/log" + logNumber + ".txt";
					if(logNumber < ConfigManager.getMainConfig().getInt("max-world-logs") - 1) logFile.renameTo(new File(newName));
					else logFile.delete();
				}
			}
		}
		try {
			File newLogFile = new File(logFolderPath + "/log0.txt");
			if(newLogFile.exists()) newLogFile.delete();
			newLogFile.createNewFile();
			log = Logger.getLogger(newLogFile.getPath());
			FileHandler handler = new FileHandler(newLogFile.getPath());
			log.addHandler(handler);
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);
		} catch(IOException exception) {
			exception.printStackTrace();
		}
	}
}
