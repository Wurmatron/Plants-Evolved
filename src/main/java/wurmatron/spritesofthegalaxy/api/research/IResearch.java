package wurmatron.spritesofthegalaxy.api.research;

import java.util.HashMap;

public interface IResearch {

	/**
	 Name of this research
	 */
	String getName ();

	/**
	 Base amount of research required
	 */
	int getBaseResearchCost ();

	/**
	 Required prerequisites to start this research
	 */
	HashMap <IResearch, Integer> getPreReq ();

	/**
	 Which Tab this Research is related too
	 */
	ResearchType getResearchTab ();
}
