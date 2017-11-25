package wurmatron.spritesofthegalaxy.common.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import java.io.File;
import java.util.HashMap;

public class ConfigHandler {

	public static final File DIRECTORY = new File (Loader.instance ().getConfigDir (),Global.NAME);
	public static Configuration config;

	public static void preInit (FMLPreInitializationEvent e) {
		config = new Configuration (e.getSuggestedConfigurationFile ());
		syncConfig ();
	}

	private static void syncConfig () {
		// General
		Property debug = config.get (Configuration.CATEGORY_GENERAL,"Debug",Defaults.debug,"Enable debug mode");
		Settings.debug = debug.getBoolean ();
		Property maxTier = config.get (Configuration.CATEGORY_GENERAL,"maxTier",Defaults.maxTier,"Max Tier for Modules",1,16);
		Settings.maxTier = maxTier.getInt ();
		// Habitat
		Property maxPopulation = config.get (Global.HABITAT,"defaultMaxPopulation",Defaults.maxPopulation,"Max Population for a newly created Habitat",1,Integer.MAX_VALUE);
		Settings.startPopulation = maxPopulation.getInt ();
		Property populationFoodRequirement = config.get (Global.HABITAT,"populationFoodRequirement",Defaults.foodRequirement,"Amount of food required per population",1,100000);
		Settings.populationFoodRequirement = populationFoodRequirement.getInt ();
		Property populationGrowth = config.get (Global.HABITAT,"populationGrowth",Defaults.populationGrowth,"Rate of population gpopulationGrowthrowth",1.01,100000);
		Settings.populationGrowth = populationGrowth.getDouble ();
		Property defaultStructures = config.get (Global.HABITAT,"defaultStructures",Defaults.defaultStructures,"Added to newly build habitat's along with setting a min level for each one");
		Settings.defaultStructures = getDefaultStructures (defaultStructures.getString ());

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

	private static HashMap <IStructure, Integer> getDefaultStructures (String configLine) {
		HashMap <IStructure, Integer> defaultStructures = new HashMap <> ();
		if (configLine != null && configLine.length () > 0 && configLine.contains (";")) {
			String[] lines = configLine.split (" ");
			for (String line : lines)
				if (line.contains (";")) {
					IStructure structure = SpritesOfTheGalaxyAPI.getStructureFromName (line.substring (0,line.indexOf (";")));
					if (structure != null) {
						try {
							int lvl = Integer.valueOf (line.substring (line.indexOf (";") + 1,line.length ()));
							if (lvl > 0)
								defaultStructures.put (structure,lvl);
							else
								throw new NumberFormatException ("Number Must Be Greater Than 0");
						} catch (NumberFormatException e) {
							LogHandler.info ("Invalid Structure Tier '#', it must be a number and be greater than 0".replaceAll ("#",line.substring (0,line.indexOf (";"))));
						}
					} else
						LogHandler.info ("Invalid Structure Name '#'".replaceAll ("#",line.substring (0,line.indexOf (";"))));
				} else
					LogHandler.info ("Invalid Structure Config line '#', it must follow this format \"structure;level\"".replaceAll ("#",line));
		}
		return defaultStructures;
	}

}
