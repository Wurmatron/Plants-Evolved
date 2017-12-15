package wurmatron.spritesofthegalaxy.api;

import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StructureType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpritesOfTheGalaxyAPI {

	private static ArrayList <IResearch> research = new ArrayList <> ();
	private static ArrayList <IStructure> structures = new ArrayList <> ();
	private static HashMap <String, IStructure> structureNames = new HashMap <> ();
	private static HashMap <String, IResearch> researchNames = new HashMap <> ();
	private static HashMap <StructureType, List <IStructure>> structureType = new HashMap <> ();
	private static HashMap <ResearchType, List <IResearch>> researchType = new HashMap <> ();
	private static ArrayList <IOutput> output = new ArrayList <> ();
	private static HashMap <String, IOutput> outputTypes = new HashMap <> ();

	public static void register (IResearch res) {
		if (!research.contains (res)) {
			research.add (res);
			researchNames.put (res.getName (),res);
			if (researchType.get (res.getResearchTab ()) == null || researchType.get (res.getResearchTab ()).size () <= 0) {
				List <IResearch> researchList = new ArrayList <> ();
				researchList.add (res);
				researchType.put (res.getResearchTab (),researchList);
			} else {
				List <IResearch> researchList = researchType.get (res.getResearchTab ());
				researchList.add (res);
				researchType.put (res.getResearchTab (),researchList);
			}
		}
	}

	public static void register (IStructure structure) {
		if (!structures.contains (structure)) {
			structures.add (structure);
			structureNames.put (structure.getName (),structure);
			if (structureType.get (structure.getStructureType ()) == null || structureType.get (structure.getStructureType ()).size () <= 0) {
				List <IStructure> structureList = new ArrayList <> ();
				structureList.add (structure);
				structureType.put (structure.getStructureType (),structureList);
			} else {
				List <IStructure> structureList = structureType.get (structure.getStructureType ());
				structureList.add (structure);
				structureType.put (structure.getStructureType (),structureList);
			}
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

	public static IResearch getResearchFromName (String name) {
		if (researchNames.containsKey (name))
			return researchNames.get (name);
		for (IResearch r : research)
			if (r.getName ().equalsIgnoreCase (name)) {
				researchNames.put (name,r);
				return r;
			}
		return null;
	}

	public static List <IStructure> getStructuresForType (StructureType type) {
		if (structureType.containsKey (type))
			return structureType.get (type);
		return new ArrayList <> ();
	}

	public static List <IResearch> getResearchForType (ResearchType type) {
		if (researchType.containsKey (type))
			return researchType.get (type);
		return new ArrayList <> ();
	}

	public static void register (IOutput out) {
		if (!output.contains (out) && out.getCost ().size () > 0 && out.getItem () != ItemStack.EMPTY) {
			output.add (out);
			outputTypes.put (out.getName (),out);
		}
	}

	public static List <IOutput> getOutputTypes () {
		return output;
	}

	public static IOutput getOutputFromName (String name) {
		if (outputTypes.containsKey (name))
			return outputTypes.get (name);
		for (IOutput r : output)
			if (r.getName ().equalsIgnoreCase (name)) {
				outputTypes.put (name,r);
				return r;
			}
		return null;
	}
}
