package wurmatron.spritesofthegalaxy.api.colony;

public interface IColony {

	/**
	 Basic Sprite Attributes
	 */
	String getLineage ();

	/**
	 Population of the current Colony
	 */
	int getPopulation ();

	/**
	 Max Population that this colony can support
	 */
	int getMaxPopulation ();

	/**
	 Amount of food the colony currently has
	 */
	int getFood ();
}
