package wurmatron.spritesofthegalaxy.common.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import java.io.File;

public class ConfigHandler {


	public static final File DIRECTORY = new File (Loader.instance ().getConfigDir (),Global.NAME);
	public static Configuration config;
	// General
	private static Property debug;
	private static Property maxTier;

	public static void preInit (FMLPreInitializationEvent e) {
		config = new Configuration (e.getSuggestedConfigurationFile ());
		syncConfig ();
	}

	private static void syncConfig () {
		// General
		debug = config.get (Configuration.CATEGORY_GENERAL,"Debug",Defaults.debug,"Enable debug mode");
		Settings.debug = debug.getBoolean ();
		maxTier = config.get (Configuration.CATEGORY_GENERAL, "maxTier", Defaults.maxTier, "Max Tier for Modules",1,16);
		Settings.maxTier = maxTier.getInt ();

		if (!DIRECTORY.exists ())
			DIRECTORY.mkdir ();
		LogHandler.info ("Config Loaded");
		if (config.hasChanged ()) {
			config.save ();
			LogHandler.info ("Config Saved");
		}
	}

	@SubscribeEvent
	public void configChanged (ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID ().equals (Global.MODID))
			syncConfig ();
	}

}
