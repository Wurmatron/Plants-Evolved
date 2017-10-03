package wurmatron.spritesofthegalaxy.common.research;

import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResearchHelper {

	// Argiculture
	public static final Research land = new Research ("Land",100,null,ResearchType.ARGICULTURE);
	public static final Research greenHouse = new Research ("GreenHouse",1000,new HashMap <IResearch, Integer> () {{
		put (land,3);
	}},ResearchType.ARGICULTURE);
	public static final Research underGround = new Research ("UnderGround",2000,new HashMap <IResearch, Integer> () {{
		put (land,5);
		put (greenHouse,3);
	}},ResearchType.ARGICULTURE);
	public static final Research hydroPonics = new Research ("HydroPonics",100000,new HashMap <IResearch, Integer> () {{
		put (greenHouse,5);
	}},ResearchType.ARGICULTURE);
	public static final Research synthesis = new Research ("Synthesis",1000000,new HashMap <IResearch, Integer> () {{
		put (hydroPonics,10);
		put (greenHouse,10);
		put (underGround,10);
		put (land,10);
	}},ResearchType.ARGICULTURE);
	public static final Research augmentation = new Research ("Augmentation",500000,new HashMap <IResearch, Integer> () {{
		put (synthesis,5);
		put (hydroPonics,20);
	}},ResearchType.ARGICULTURE);

	// Mining
	public static final Research basic = new Research ("basic",100,null,ResearchType.PRODUCTION);
	public static final Research quarry = new Research ("quarry",1000,new HashMap <IResearch, Integer> () {{
		put (basic,5);
	}},ResearchType.PRODUCTION);
	public static final Research sifting = new Research ("sifting",1000,new HashMap <IResearch, Integer> () {{
		put (basic,5);
	}},ResearchType.PRODUCTION);
	public static final Research deep = new Research ("deep",5000,new HashMap <IResearch, Integer> () {{
		put (quarry,5);
	}},ResearchType.PRODUCTION);
	public static final Research deepCore = new Research ("deepCore",10000,new HashMap <IResearch, Integer> () {{
		put (deep,5);
	}},ResearchType.PRODUCTION);
	public static final Research deepSea = new Research ("deepSea",50000,new HashMap <IResearch, Integer> () {{
		put (deep,10);
		put (deepCore,5);
	}},ResearchType.PRODUCTION);
	public static final Research astro = new Research ("astro",1000000,new HashMap <IResearch, Integer> () {{
		put (quarry,20);
		put (deepCore,5);
	}},ResearchType.PRODUCTION);
	public static final Research extraDimensional = new Research ("extraDimensional",50000000,new HashMap <IResearch, Integer> () {{
		put (astro,20);
		put (basic,50);
		put (deepCore,20);
	}},ResearchType.PRODUCTION);
	public static final Research synchysisMine = new Research ("synchysisMine",100000000,new HashMap <IResearch, Integer> () {{
		put (extraDimensional,5);
		put (astro,50);
	}},ResearchType.PRODUCTION);

	// Energy
	public static final Research fuel = new Research ("fuel",100,null,ResearchType.PRODUCTION);
	public static final Research nuclear = new Research ("nuclear",10000,new HashMap <IResearch, Integer> () {{
		put (fuel,5);
	}},ResearchType.PRODUCTION);
	public static final Research fusion = new Research ("fusion",100000,new HashMap <IResearch, Integer> () {{
		put (fuel,20);
		put (nuclear,5);
	}},ResearchType.PRODUCTION);
	public static final Research solar = new Research ("solar",1000,new HashMap <IResearch, Integer> () {{
		put (fuel,5);
	}},ResearchType.PRODUCTION);
	public static final Research wind = new Research ("wind",1000,new HashMap <IResearch, Integer> () {{
		put (fuel,5);
	}},ResearchType.PRODUCTION);
	public static final Research hydro = new Research ("hydro",1000,new HashMap <IResearch, Integer> () {{
		put (fuel,5);
		put (wind,1);
	}},ResearchType.PRODUCTION);
	public static final Research extraDimensionalEnergy = new Research ("extraDimensionalEnergy",1000000000,new HashMap <IResearch, Integer> () {{
		put (fuel,100);
		put (fusion,50);
		put (solar,50);
	}},ResearchType.PRODUCTION);
	public static final Research star = new Research ("star",1000000,new HashMap <IResearch, Integer> () {{
		put (fusion,25);
		put (hydro,20);
	}},ResearchType.PRODUCTION);
	public static final Research interstellar = new Research ("interstellar",100000000,new HashMap <IResearch, Integer> () {{
		put (star,5);
		put (fusion,25);
	}},ResearchType.PRODUCTION);

	// Industry
	public static final Research primative = new Research ("primative",100,null,ResearchType.PRODUCTION);
	public static final Research steam = new Research ("steam",1000,new HashMap <IResearch, Integer> () {{
		put (primative,5);
	}},ResearchType.INDUSTRY);
	public static final Research mass = new Research ("mass",10000,new HashMap <IResearch, Integer> () {{
		put (steam,10);
	}},ResearchType.INDUSTRY);
	public static final Research robotic = new Research ("robotic",1000000,new HashMap <IResearch, Integer> () {{
		put (mass,10);
	}},ResearchType.INDUSTRY);
	public static final Research atomicAssembly = new Research ("atomicAssembly",5000000,new HashMap <IResearch, Integer> () {{
		put (mass,50);
		put (robotic,10);
	}},ResearchType.INDUSTRY);

	// Research
	public static final Research written = new Research ("written",100,null,ResearchType.RESEARCH);
	public static final Research computational = new Research ("computational",100000,new HashMap <IResearch, Integer> () {{
		put (written,20);
		put (nuclear,1);
	}},ResearchType.RESEARCH);
	public static final Research ai = new Research ("ai",50000000,new HashMap <IResearch, Integer> () {{
		put (computational,10);
		put (robotic,5);
		put (star,1);
	}},ResearchType.RESEARCH);

	public static void registerResearch () {
		SpritesOfTheGalaxyAPI.register (land);
		SpritesOfTheGalaxyAPI.register (greenHouse);
		SpritesOfTheGalaxyAPI.register (underGround);
		SpritesOfTheGalaxyAPI.register (hydroPonics);
		SpritesOfTheGalaxyAPI.register (synthesis);
		SpritesOfTheGalaxyAPI.register (augmentation);
		SpritesOfTheGalaxyAPI.register (basic);
		SpritesOfTheGalaxyAPI.register (quarry);
		SpritesOfTheGalaxyAPI.register (sifting);
		SpritesOfTheGalaxyAPI.register (deep);
		SpritesOfTheGalaxyAPI.register (deepCore);
		SpritesOfTheGalaxyAPI.register (deepSea);
		SpritesOfTheGalaxyAPI.register (astro);
		SpritesOfTheGalaxyAPI.register (extraDimensional);
		SpritesOfTheGalaxyAPI.register (synchysisMine);
		SpritesOfTheGalaxyAPI.register (fuel);
		SpritesOfTheGalaxyAPI.register (nuclear);
		SpritesOfTheGalaxyAPI.register (fusion);
		SpritesOfTheGalaxyAPI.register (solar);
		SpritesOfTheGalaxyAPI.register (wind);
		SpritesOfTheGalaxyAPI.register (hydro);
		SpritesOfTheGalaxyAPI.register (extraDimensionalEnergy);
		SpritesOfTheGalaxyAPI.register (star);
		SpritesOfTheGalaxyAPI.register (interstellar);
		SpritesOfTheGalaxyAPI.register (primative);
		SpritesOfTheGalaxyAPI.register (steam);
		SpritesOfTheGalaxyAPI.register (mass);
		SpritesOfTheGalaxyAPI.register (robotic);
		SpritesOfTheGalaxyAPI.register (atomicAssembly);
		SpritesOfTheGalaxyAPI.register (written);
		SpritesOfTheGalaxyAPI.register (computational);
		SpritesOfTheGalaxyAPI.register (ai);
	}

	public static boolean isValidMove (HashMap <IResearch, Integer> currentResearch,IResearch newLevel) {
		if (newLevel != null && newLevel.getPreReq () != null && newLevel.getPreReq ().size () > 0) {
			List <IResearch> hasForLevel = new ArrayList <> ();
			for (IResearch needed : newLevel.getPreReq ().keySet ())
				for (IResearch has : currentResearch.keySet ())
					if (has == needed && currentResearch.get (has) >= newLevel.getPreReq ().get (needed))
						hasForLevel.add (needed);
			if (newLevel.getPreReq ().size () == hasForLevel.size ())
				return true;
			return false;
		}
		return true;
	}

	public static HashMap <IResearch, Integer> getNeededResearch (HashMap <IResearch, Integer> currentResearch,IResearch test) {
		if (test.getPreReq () == null || test.getPreReq ().size () == 0)
			return new HashMap <> ();
		HashMap <IResearch, Integer> data = new HashMap <> ();
		if (test.getPreReq ().size () > 0)
			for (IResearch needed : test.getPreReq ().keySet ())
				for (IResearch has : currentResearch.keySet ())
					if (has == needed && currentResearch.get (has) >= test.getPreReq ().get (needed))
						data.put (needed,test.getPreReq ().get (needed));
		HashMap <IResearch, Integer> need = new HashMap <> ();
		for (IResearch res : test.getPreReq ().keySet ())
			if (!data.containsKey (res) || data.containsKey (res) && data.get (res) < test.getPreReq ().get (res))
				need.put (res,test.getPreReq ().get (res));
		return need;
	}

	public static boolean hasResearch (TileHabitatCore tile,HashMap <IResearch, Integer> required) {
		if(required == null || required.size () == 0)
			return true;
		for (IResearch research : required.keySet ())
			if (!hasResearch (tile,research,required.get (research)))
				return false;
		return true;
	}

	public static boolean hasResearch (TileHabitatCore tile,IResearch research,int level) {
		for (IResearch res : tile.getResearch ().keySet ())
			if (res == research && level == tile.getResearch ().get (res))
				return true;
		return false;
	}

	public static HashMap <IResearch, Integer> getNeededResearch (HashMap <IResearch, Integer> currentResearch,IStructure structure) {
		HashMap <IResearch, Integer> data = new HashMap <> ();
		for (IResearch res : structure.getRequiredResearch ().keySet ())
			data.putAll (getNeededResearch (currentResearch,res));
		return data;
	}
}

