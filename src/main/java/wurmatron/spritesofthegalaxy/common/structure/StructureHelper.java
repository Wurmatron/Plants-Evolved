package wurmatron.spritesofthegalaxy.common.structure;

import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.common.structure.agriculture.FarmStructure;
import wurmatron.spritesofthegalaxy.common.structure.agriculture.GreenhouseStructure;
import wurmatron.spritesofthegalaxy.common.structure.energy.*;
import wurmatron.spritesofthegalaxy.common.structure.mine.MineStructure;
import wurmatron.spritesofthegalaxy.common.structure.research.ResearchStructure;

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

	public static void registerStructures () {
		SpritesOfTheGalaxyAPI.register (farmStructure);
		SpritesOfTheGalaxyAPI.register (mineStructure);
		SpritesOfTheGalaxyAPI.register (greenhouseStructure);
		SpritesOfTheGalaxyAPI.register (energyStructure);
		SpritesOfTheGalaxyAPI.register (nuclearStructure);
		SpritesOfTheGalaxyAPI.register (windStructure);
		SpritesOfTheGalaxyAPI.register (starStructure);
		SpritesOfTheGalaxyAPI.register (solarStructure);
		SpritesOfTheGalaxyAPI.register (interstellarStructure);
		SpritesOfTheGalaxyAPI.register (hydroStructure);
		SpritesOfTheGalaxyAPI.register (fusionStructure);
		SpritesOfTheGalaxyAPI.register (extraDimStructure);
		SpritesOfTheGalaxyAPI.register (researchStructure);
	}
}
