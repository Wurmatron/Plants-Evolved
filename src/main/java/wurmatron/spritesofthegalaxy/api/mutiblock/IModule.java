package wurmatron.spritesofthegalaxy.api.mutiblock;

import wurmatron.spritesofthegalaxy.api.research.IResearch;

public interface IModule {

	/**
	 Name for This Module
	 */
	String getName ();

	/**
	 Type of this Module
	 */
	ModuleType getType ();

	/**
	 Max Tier for this Module
	 */
	int getMaxTier ();

	/**
	 Research required to use this Module
	 */
	IResearch getResearch ();
}
