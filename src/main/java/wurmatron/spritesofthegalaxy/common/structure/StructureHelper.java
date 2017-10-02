package wurmatron.spritesofthegalaxy.common.structure;

import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;

public class StructureHelper {

	public static final IStructure farmStructure = new FarmStructure ();

	public static void registerStructures () {
		SpritesOfTheGalaxyAPI.register (farmStructure);
	}
}
