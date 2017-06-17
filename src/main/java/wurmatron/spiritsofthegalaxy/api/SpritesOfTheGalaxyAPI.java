package wurmatron.spiritsofthegalaxy.api;

import wurmatron.spiritsofthegalaxy.api.research.IResearch;

import java.util.ArrayList;

public class SpritesOfTheGalaxyAPI {

	public static ArrayList <IResearch> research = new ArrayList <> ();

	public static void register (IResearch res) {
		if (!research.contains (res))
			research.add (res);
	}
}
