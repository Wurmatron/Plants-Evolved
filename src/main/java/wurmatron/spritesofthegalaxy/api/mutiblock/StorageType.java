package wurmatron.spritesofthegalaxy.api.mutiblock;

import net.minecraft.util.ResourceLocation;
import wurmatron.spritesofthegalaxy.common.reference.NBT;

public enum StorageType {

	POPULATION (NBT.POPULATION,100,2000),MINERAL (NBT.MINERALS,200,500),MAGIC (NBT.MAGIC,50,1000),GEM (NBT.GEM,100,1000);

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
