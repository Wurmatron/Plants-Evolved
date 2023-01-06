package io.wurmatron.plants.api.mutiblock;

import io.wurmatron.plants.api.research.IResearch;

import java.util.HashMap;

public interface IStructure {

	/**
	 Name for the structure
	 */
	String getName ();

	String getDisplayName ();

	StructureType getStructureType ();

	HashMap <IResearch, Integer> getRequiredResearch ();

	int getCost (int researchLevel,int structureTier);

	int getEnergyUsage (int tier);

	int getBuildTime (int tier);

	double getPopulationRequirment ();

}
