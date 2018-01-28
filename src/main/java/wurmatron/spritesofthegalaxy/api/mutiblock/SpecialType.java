package wurmatron.spritesofthegalaxy.api.mutiblock;

import net.minecraft.util.IStringSerializable;

public enum SpecialType implements IStringSerializable {

	ACCELERATOR (0,"accelerator");

	private int ID;
	private String NAME;

	SpecialType (int id,String name) {
		this.ID = id;
		this.NAME = name;
	}

	@Override
	public String getName () {
		return NAME;
	}

	public int getID () {
		return ID;
	}
}
