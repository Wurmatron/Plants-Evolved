package wurmatron.spritesofthegalaxy.common.structure;

import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.common.structure.agriculture.FarmStructure;
import wurmatron.spritesofthegalaxy.common.structure.mine.MineStructure;

public class StructureHelper {

	// Agriculture
	public static final IStructure farmStructure = new FarmStructure ();

	// Mine
	public static final IStructure mineStructure = new MineStructure ();

	public static void registerStructures () {
		SpritesOfTheGalaxyAPI.register (farmStructure);
		SpritesOfTheGalaxyAPI.register (mineStructure);
	}
}
