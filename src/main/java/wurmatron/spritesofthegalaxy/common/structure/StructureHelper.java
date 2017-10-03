package wurmatron.spritesofthegalaxy.common.structure;

import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.common.structure.agriculture.FarmStructure;
import wurmatron.spritesofthegalaxy.common.structure.agriculture.GreenhouseStructure;
import wurmatron.spritesofthegalaxy.common.structure.energy.EnergyStructure;
import wurmatron.spritesofthegalaxy.common.structure.mine.MineStructure;

public class StructureHelper {

	// Agriculture
	public static final IStructure farmStructure = new FarmStructure ();
	public static final IStructure greenhouseStructure = new GreenhouseStructure ();

	// Mine
	public static final IStructure mineStructure = new MineStructure ();

	// Energy
	public static final IStructure energyStructure = new EnergyStructure ();

	public static void registerStructures () {
		SpritesOfTheGalaxyAPI.register (farmStructure);
		SpritesOfTheGalaxyAPI.register (mineStructure);
		SpritesOfTheGalaxyAPI.register (greenhouseStructure);
		SpritesOfTheGalaxyAPI.register (energyStructure);
	}
}
