package wurmatron.spritesofthegalaxy.common.research;

import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;

import java.util.HashMap;

public class ResearchHelper {

	// Argiculture
	public static final Research land = new Research ("land",100,null,ResearchType.ARGICULTURE);
	public static final Research greenHouse = new Research ("greenHouse",1000,new HashMap <IResearch, Integer> () {{
		put (land,3);
	}},ResearchType.ARGICULTURE);
	public static final Research underGround = new Research ("underGround",2000,new HashMap <IResearch, Integer> () {{
		put (land,5);
		put (greenHouse,3);
	}},ResearchType.ARGICULTURE);
	public static final Research hydroPonics = new Research ("hydroPonics",100000,new HashMap <IResearch, Integer> () {{
		put (greenHouse,5);
	}},ResearchType.ARGICULTURE);
	public static final Research synchysis = new Research ("synchysis",1000000,new HashMap <IResearch, Integer> () {{
		put (hydroPonics,10);
		put (greenHouse,10);
		put (underGround,10);
		put (land,10);
	}},ResearchType.ARGICULTURE);
	public static final Research augmentation = new Research ("augmentation",500000,new HashMap <IResearch, Integer> () {{
		put (synchysis,5);
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
		SpritesOfTheGalaxyAPI.register (synchysis);
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
}

