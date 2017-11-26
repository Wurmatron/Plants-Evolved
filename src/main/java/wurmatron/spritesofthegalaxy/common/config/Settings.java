package wurmatron.spritesofthegalaxy.common.config;

import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;

import java.util.HashMap;

public class Settings {

	// General
	public static boolean debug;
	public static int maxTier;

	// Habitat
	public static int startPopulation;
	public static int populationFoodRequirement;
	public static double populationGrowth;
	public static HashMap <IStructure, Integer> defaultStructures;
}
