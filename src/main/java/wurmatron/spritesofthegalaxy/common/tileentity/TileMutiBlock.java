package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

public class TileMutiBlock extends TileEntity {

	private BlockPos CoreLoc;

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		if (nbt.getIntArray (NBT.POSITION).length == 3)
			CoreLoc = new BlockPos (nbt.getIntArray (NBT.POSITION)[0],nbt.getIntArray (NBT.POSITION)[1],nbt.getIntArray (NBT.POSITION)[2]);
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		super.writeToNBT (nbt);
		if (CoreLoc != null)
			nbt.setIntArray (NBT.POSITION,new int[] {CoreLoc.getX (),CoreLoc.getY (),CoreLoc.getZ ()});
		markDirty ();
		return nbt;
	}

	public void setCore (BlockPos pos) {
		CoreLoc = pos;
	}

	public BlockPos getCore () {
		return CoreLoc;
	}

	public void checkIfValid () {
		int isValid = MutiBlockHelper.isValid (world,getCore ());
		if (isValid <= 0)
			MutiBlockHelper.delTilesCore (world,getCore (),9);
		else
			MutiBlockHelper.setTilesCore (world,getCore (),isValid);
	}
}
