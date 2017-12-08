package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import wurmatron.spritesofthegalaxy.common.reference.NBT;

public class TileInput extends TileMutiBlock implements ITickable, IInventory {

	private ItemStack[] inventory = new ItemStack[32];

	@Override
	public void update () {
		if (getCore () != null && getStackInSlot (0) != ItemStack.EMPTY && world.getTileEntity (getCore ()) != null && world.getTileEntity (getCore ()) instanceof TileHabitatCore) {
			TileHabitatCore tile = (TileHabitatCore) world.getTileEntity (getCore ());
			if (tile != null)
				for (int index = 0; index < inventory.length; index++)
					if (getStackInSlot (index) != ItemStack.EMPTY && getStackInSlot (index) != null) {
						if (tile.importStack (getStackInSlot (index)))
							setInventorySlotContents (index,null);
					}
		}
	}

	@Override
	public int getSizeInventory () {
		return inventory.length;
	}

	@Override
	public boolean isEmpty () {
		return false;
	}

	@Override
	public ItemStack getStackInSlot (int index) {
		if (inventory[index] == null)
			return ItemStack.EMPTY;
		return inventory[index];
	}

	@Override
	public ItemStack decrStackSize (int index,int count) {
		if (getStackInSlot (index) != null) {
			if (this.getStackInSlot (index).getCount () <= count) {
				ItemStack stack = this.getStackInSlot (index);
				setInventorySlotContents (index,null);
				markDirty ();
				return stack;
			} else {
				ItemStack stack = getStackInSlot (index).splitStack (count);
				if (getStackInSlot (index).getCount () <= 0)
					setInventorySlotContents (index,null);
				else
					setInventorySlotContents (index,getStackInSlot (index));
				markDirty ();
				return stack;
			}
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot (int index) {
		if (index < inventory.length)
			return inventory[index] = null;
		return null;
	}

	@Override
	public void setInventorySlotContents (int index,ItemStack stack) {
		if (index >= 0 && index <= getSizeInventory ()) {
			if (stack != null && stack.getCount () > getInventoryStackLimit ())
				stack.setCount (getInventoryStackLimit ());
			if (stack != null && stack.getCount () == 0)
				stack = null;
			this.inventory[index] = stack;
			this.markDirty ();
		}
	}

	@Override
	public int getInventoryStackLimit () {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer (EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory (EntityPlayer player) {

	}

	@Override
	public void closeInventory (EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot (int index,ItemStack stack) {
		return true;
	}

	@Override
	public int getField (int id) {
		return 0;
	}

	@Override
	public void setField (int id,int value) {

	}

	@Override
	public int getFieldCount () {
		return 0;
	}

	@Override
	public void clear () {
		for (int i = 0; i < this.getSizeInventory (); i++)
			setInventorySlotContents (i,null);
	}

	@Override
	public String getName () {
		return "input";
	}

	@Override
	public boolean hasCustomName () {
		return false;
	}

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		NBTTagList list = nbt.getTagList (NBT.INVENTORY,10);
		for (int i = 0; i < list.tagCount (); ++i) {
			NBTTagCompound stackTag = list.getCompoundTagAt (i);
			int slot = stackTag.getByte (NBT.SLOT) & 255;
			setInventorySlotContents (slot,new ItemStack (stackTag));
		}
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		super.writeToNBT (nbt);
		NBTTagList list = new NBTTagList ();
		for (int i = 0; i < this.getSizeInventory (); i++)
			if (getStackInSlot (i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound ();
				stackTag.setByte (NBT.SLOT,(byte) i);
				getStackInSlot (i).writeToNBT (stackTag);
				list.appendTag (stackTag);
			}
		nbt.setTag (NBT.INVENTORY,list);
		return nbt;
	}
}
