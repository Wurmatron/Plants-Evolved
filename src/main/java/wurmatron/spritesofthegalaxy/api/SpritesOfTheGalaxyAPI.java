package wurmatron.spritesofthegalaxy.api;

import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.research.IResearch;

import java.util.ArrayList;
import java.util.HashMap;

public class SpritesOfTheGalaxyAPI {

	public static ArrayList <IResearch> research = new ArrayList <> ();
	public static ArrayList <IStructure> structures = new ArrayList <> ();
	private static HashMap <String, IStructure> structureNames = new HashMap <> ();

	public static void register (IResearch res) {
		if (!research.contains (res))
			research.add (res);
	}

	public static void register (IStructure structure) {
		if (!structures.contains (structure)) {
			structures.add (structure);
			structureNames.put (structure.getName (),structure);
		}
	}

	public static IStructure getStructureFromName (String name) {
		if (structureNames.containsKey (name))
			return structureNames.get (name);
		for (IStructure structure : structures)
			if (structure.getName ().equalsIgnoreCase (name)) {
				structureNames.put (name,structure);
				return structure;
			}
		return null;
	}
}
