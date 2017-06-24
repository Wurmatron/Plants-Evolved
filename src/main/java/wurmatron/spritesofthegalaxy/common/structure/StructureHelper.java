package wurmatron.spritesofthegalaxy.common.structure;

import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;

public class StructureHelper {

	public static void registerStructures () {
		SpritesOfTheGalaxyAPI.register (new FarmStructure ());
	}
}
