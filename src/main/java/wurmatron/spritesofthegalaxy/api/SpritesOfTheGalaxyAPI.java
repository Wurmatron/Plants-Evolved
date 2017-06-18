package wurmatron.spritesofthegalaxy.api;

import wurmatron.spritesofthegalaxy.api.mutiblock.IModule;
import wurmatron.spritesofthegalaxy.api.research.IResearch;

import java.util.ArrayList;
import java.util.HashMap;

public class SpritesOfTheGalaxyAPI {

	public static ArrayList <IResearch> research = new ArrayList <> ();
	public static IModule[] modules = new IModule[256];
	private static HashMap <String, IModule> moduleNames = new HashMap <> ();
	private static int MODULE_ID;

	public static void register (IResearch res) {
		if (!research.contains (res))
			research.add (res);
	}

	public static void register (IModule mod) {
		modules[MODULE_ID++] = mod;
		moduleNames.put (mod.getName (),mod);
	}

	public static IModule getModuleFromName (String name) {
		if (moduleNames.size () > 0 && moduleNames.containsKey (name))
			return moduleNames.get (name);
		else if (modules.length > 0) {
			for (IModule module : modules)
				if (module.getName ().equalsIgnoreCase (name)) {
					moduleNames.put (name,module);
					return module;
				}
		}
		return null;
	}
}
