package wurmatron.spritesofthegalaxy.api.research;

import net.minecraft.util.ResourceLocation;

public enum ResearchType {

	ARGICULTURE ("agriculture",new ResourceLocation ("","")),INDUSTRY ("industry",new ResourceLocation ("","")),PRODUCTION ("production",new ResourceLocation ("","")),RESEARCH ("research",new ResourceLocation ("","")),MAGIC ("magic",new ResourceLocation ("","")),UNIQUE ("unique",new ResourceLocation ("","")), ENERGY("energy", new ResourceLocation ("", ""));

	private final String displayKey;
	private final ResourceLocation icon;

	ResearchType (String displayKey,ResourceLocation icon) {
		this.displayKey = displayKey;
		this.icon = icon;
	}

	String getDisplayKey () {
		return displayKey;
	}

	ResourceLocation getIcon () {
		return icon;
	}
}
