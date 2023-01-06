package io.wurmatron.plants.common.structure;

import io.wurmatron.plants.api.PlantsEvolvedAPI;
import io.wurmatron.plants.api.mutiblock.IStructure;
import io.wurmatron.plants.common.structure.agriculture.FarmStructure;
import io.wurmatron.plants.common.structure.agriculture.GreenhouseStructure;
import io.wurmatron.plants.common.structure.energy.*;
import io.wurmatron.plants.common.structure.magic.MagicStoneMine;
import io.wurmatron.plants.common.structure.mine.MineStructure;
import io.wurmatron.plants.common.structure.mob.ZombieStructure;
import io.wurmatron.plants.common.structure.research.ResearchStructure;

public class StructureHelper {

	// Agriculture
	public static final IStructure farmStructure = new FarmStructure ();
	public static final IStructure greenhouseStructure = new GreenhouseStructure ();

	// Mine
	public static final IStructure mineStructure = new MineStructure ();

	// Energy
	public static final IStructure energyStructure = new EnergyStructure ();
	public static final IStructure nuclearStructure = new NuclearStructure ();
	public static final IStructure windStructure = new WindStructure ();
	public static final IStructure starStructure = new StarStructure ();
	public static final IStructure solarStructure = new StarStructure ();
	public static final IStructure interstellarStructure = new InterstellarStructure ();
	public static final IStructure hydroStructure = new HydroStructure ();
	public static final IStructure fusionStructure = new FusionStructure ();
	public static final IStructure extraDimStructure = new ExtraDimStructure ();

	// Research
	public static final IStructure researchStructure = new ResearchStructure ();

	// Mob
	public static final IStructure zombieStructure = new ZombieStructure ();

	// Magic
	public static final IStructure magicStoneStructure = new MagicStoneMine ();

	public static void registerStructures () {
		PlantsEvolvedAPI.register (farmStructure);
		PlantsEvolvedAPI.register (mineStructure);
		PlantsEvolvedAPI.register (greenhouseStructure);
		PlantsEvolvedAPI.register (energyStructure);
		PlantsEvolvedAPI.register (nuclearStructure);
		PlantsEvolvedAPI.register (windStructure);
		PlantsEvolvedAPI.register (starStructure);
		PlantsEvolvedAPI.register (solarStructure);
		PlantsEvolvedAPI.register (interstellarStructure);
		PlantsEvolvedAPI.register (hydroStructure);
		PlantsEvolvedAPI.register (fusionStructure);
		PlantsEvolvedAPI.register (extraDimStructure);
		PlantsEvolvedAPI.register (researchStructure);
		PlantsEvolvedAPI.register (zombieStructure);
		PlantsEvolvedAPI.register (magicStoneStructure);
	}
}
