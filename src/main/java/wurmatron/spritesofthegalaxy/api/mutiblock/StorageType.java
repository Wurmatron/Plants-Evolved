package wurmatron.spritesofthegalaxy.api.mutiblock;

import wurmatron.spritesofthegalaxy.common.reference.NBT;

public enum StorageType {

	POPULATION (NBT.MAX_POPULATION,100,2000),MINERAL (NBT.MAX_MINERALS,10,50),MAGIC (NBT.MAX_MAGIC,50,1000),GEM (NBT.MAX_GEM,100,1000);

	private final String displayKey;
	private final double scale;
	private final int cost;

	StorageType (String displayKey,double scaling,int mineral) {
		this.displayKey = displayKey;
		this.scale = scaling;
		this.cost = mineral;
	}

	public String getDisplayKey () {
		return displayKey;
	}

	public double getScale () {
		return scale;
	}

	public int getCost () {
		return cost;
	}
}
