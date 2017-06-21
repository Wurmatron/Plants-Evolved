package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

public class TileHabitatCore extends TileMutiBlock implements ITickable {

	private boolean update = false;

	@Override
	public void update () {
		if (world.getWorldTime () % 20 == 0) {
			int isValid = MutiBlockHelper.isValid (world,pos);
			if (isValid > 0 && !update) {
				MutiBlockHelper.setTilesCore (world,pos,isValid);
				update = true;
			}
		}
	}

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		update = nbt.getBoolean (NBT.UPDATE);
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		nbt.setBoolean (NBT.UPDATE,update);
		super.writeToNBT (nbt);
		return nbt;
	}

	public void requestUpdate() {
		this.update = false;
	}
}
