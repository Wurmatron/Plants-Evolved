package wurmatron.spritesofthegalaxy.api.mutiblock;

import net.minecraft.util.ResourceLocation;

public enum StorageType {

	POPULATION ("population",new ResourceLocation ("",""),100,2000),FOOD ("food",new ResourceLocation ("",""),150,1000),MINERAL ("mineral",new ResourceLocation ("",""),200,500);

	private final String displayKey;
	private final ResourceLocation icon;
	private final double scale;
	private final int mineral;

	StorageType (String displayKey,ResourceLocation icon,double scaling,int mineral) {
		this.displayKey = displayKey;
		this.icon = icon;
		this.scale = scaling;
		this.mineral = mineral;
	}

	public String getDisplayKey () {
		return displayKey;
	}

	public ResourceLocation getIcon () {
		return icon;
	}

	public double getScale () {
		return scale;
	}

	public int getMineral () {
		return mineral;
	}
}
