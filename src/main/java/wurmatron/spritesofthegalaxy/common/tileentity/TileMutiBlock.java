package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.reference.NBT;

import javax.annotation.Nullable;

public class TileMutiBlock extends TileEntity {

	protected BlockPos controllerLoc;

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		if (nbt.getIntArray (NBT.POSITION).length == 3)
			controllerLoc = new BlockPos (nbt.getIntArray (NBT.POSITION)[0],nbt.getIntArray (NBT.POSITION)[1],nbt.getIntArray (NBT.POSITION)[2]);
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		super.writeToNBT (nbt);
		if (controllerLoc != null)
			nbt.setIntArray (NBT.POSITION,new int[] {controllerLoc.getX (),controllerLoc.getY (),controllerLoc.getZ ()});
		markDirty ();
		return nbt;
	}

	@Override
	public void onLoad () {
		lookForController ();
		addToController ();
	}

	protected void lookForController () {
		if (controllerLoc == null || world.getTileEntity (controllerLoc) == null || !(world.getTileEntity (controllerLoc) instanceof TileHabitatController)) {
			if (isController (pos))
				controllerLoc = pos;
			else if (isController (pos.down ()))
				controllerLoc = pos.down ();
			else if (isController (pos.up ()))
				controllerLoc = pos.up ();
			else if (isController (pos.north ()))
				controllerLoc = pos.north ();
			else if (isController (pos.south ()))
				controllerLoc = pos.south ();
			else if (isController (pos.east ()))
				controllerLoc = pos.east ();
			else if (isController (pos.west ()))
				controllerLoc = pos.west ();
			else if (controllerLoc == null) {
				if (knownsAndSetLocationOfController (pos.down ()))
					controllerLoc = pos.down ();
				else if (knownsAndSetLocationOfController (pos.up ()))
					controllerLoc = pos.up ();
				else if (knownsAndSetLocationOfController (pos.north ()))
					controllerLoc = pos.north ();
				else if (knownsAndSetLocationOfController (pos.south ()))
					controllerLoc = pos.south ();
				else if (knownsAndSetLocationOfController (pos.east ()))
					controllerLoc = pos.east ();
				else if (knownsAndSetLocationOfController (pos.west ()))
					controllerLoc = pos.west ();
			}
		}
	}

	protected boolean isController (BlockPos loc) {
		return loc != null && world.getTileEntity (loc) != null && world.getTileEntity (loc) instanceof TileHabitatController;
	}

	protected boolean knownsAndSetLocationOfController (BlockPos loc) {
		if (loc != null && world.getTileEntity (loc) != null && world.getTileEntity (loc) instanceof TileMutiBlock) {
			TileMutiBlock tile = (TileMutiBlock) world.getTileEntity (loc);
			if (tile != null && tile.getControllerLocation () != null && isController (tile.getControllerLocation ()))
				controllerLoc = tile.getControllerLocation ();
		}
		return false;
	}

	public BlockPos getControllerLocation () {
		return controllerLoc;
	}

	public void updateController () {
		if (controllerLoc == null)
			lookForController ();
		else if (!isController (controllerLoc)) {
			controllerLoc = null;
			lookForController ();
		}
	}

	protected void addToController () {
		if (controllerLoc != null && isController (controllerLoc)) {
			TileHabitatController tile = (TileHabitatController) world.getTileEntity (controllerLoc);
			if (tile != null && !tile.getTiles().contains (pos))
				tile.getTiles ().add (pos);
		}
	}

	public void updateDestroyed () {
		if (controllerLoc != null && isController (controllerLoc)) {
			TileHabitatController tile = (TileHabitatController) world.getTileEntity (controllerLoc);
			if (tile != null && tile.getTiles().contains (pos))
				tile.getTiles().remove (pos);
		}
	}
}
