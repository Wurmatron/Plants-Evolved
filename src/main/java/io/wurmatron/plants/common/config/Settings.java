package io.wurmatron.plants.common.config;

import io.wurmatron.plants.api.mutiblock.IStructure;

import java.util.HashMap;

public class Settings {

	// General
	public static boolean debug;
	public static int maxTier;
	public static boolean createDefaultOutput;

	// Habitat
	public static int startPopulation;
	public static int populationFoodRequirement;
	public static double populationGrowth;
	public static HashMap <IStructure, Integer> defaultStructures;
	public static double workerPercentage;
	public static double structurePopulationRequirement;
}
