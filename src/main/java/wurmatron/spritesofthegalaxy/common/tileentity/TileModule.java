package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IModule;
import wurmatron.spritesofthegalaxy.common.reference.NBT;

public class TileModule extends TileMutiBlock {

	protected IModule module;
	protected int tier;

	public TileModule (IModule module,int tier) {
		this.module = module;
		this.tier = tier;
	}

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		module = SpritesOfTheGalaxyAPI.getModuleFromName (nbt.getString (NBT.MODULE));
		tier = nbt.getInteger (NBT.TIER);
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		super.writeToNBT (nbt);
		nbt.setString (NBT.MODULE,module.getName ());
		nbt.setInteger (NBT.TIER,tier);
		markDirty ();
		return nbt;
	}
}
