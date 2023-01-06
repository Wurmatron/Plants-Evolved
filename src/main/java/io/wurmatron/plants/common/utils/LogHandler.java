package io.wurmatron.plants.common.utils;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.wurmatron.plants.common.config.Settings;
import io.wurmatron.plants.common.reference.Global;

public class LogHandler {

	private static final Logger logger = LogManager.getLogger (Global.NAME);

	public static void log (Level level,String msg) {
		logger.log (level,msg);
	}

	public static void info (String msg) {
		log (Level.INFO,msg);
	}

	public static void warn (String msg) {
		log (Level.WARN,msg);
	}

	public static void all (String msg) {
		log (Level.ALL,msg);
	}

	public static void error (String msg) {
		log (Level.ERROR,msg);
	}

	public static void debug (String msg) {
		if (Settings.debug)
			log (Level.INFO,msg);
	}

	public static void serverInfo (String msg) {
		if (FMLCommonHandler.instance ().getSide () == Side.SERVER)
			info (msg);
	}

	public static void clientInfo (String msg) {
		if (FMLCommonHandler.instance ().getSide () == Side.CLIENT)
			info (msg);
	}
}
