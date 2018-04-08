package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

public class TileOutput extends TileMutiBlock implements ITickable, IInventory {

	private ItemStack[] inventory = new ItemStack[32];
	private BlockPos outputLocation;

	@Override
	public void update () {
		if (outputLocation != null && world.getWorldTime () % 5 == 0 && hasItems ())
			for (int index = 0; index < getSizeInventory (); index++)
				if (getStackInSlot (index) != null || getStackInSlot (index) != ItemStack.EMPTY && addToStorage (getStackInSlot (index), true))
					setInventorySlotContents (index,ItemStack.EMPTY);
		if (world.getWorldTime () % 20 == 0)
			updateOutputLocation ();
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
		if (index < 0 || index >= this.getSizeInventory ())
			return;
		if (stack != ItemStack.EMPTY && stack.getCount () > this.getInventoryStackLimit ())
			stack.setCount (getInventoryStackLimit ());
		if (stack != ItemStack.EMPTY && stack.getCount () == 0)
			stack = null;
		this.inventory[index] = stack;
		this.markDirty ();
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
		return "output";
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
		int[] output = nbt.getIntArray (NBT.OUTPUT_LOCATION);
		if (output.length == 3)
			outputLocation = new BlockPos (output[0],output[1],output[2]);
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
		if (outputLocation != null)
			nbt.setIntArray (NBT.OUTPUT_LOCATION,new int[] {outputLocation.getX (),outputLocation.getY (),outputLocation.getZ ()});
		return nbt;
	}

	private boolean isValidInventory (BlockPos pos) {
		return world.getTileEntity (pos) != null && world.getTileEntity (pos) instanceof IInventory && !(world.getTileEntity (pos) instanceof TileMutiBlock);
	}

	private boolean hasItems () {
		for (ItemStack item : inventory)
			if (item != ItemStack.EMPTY)
				return true;
		return false;
	}

	public boolean addToStorage (ItemStack stack,boolean firstRun) {
		if (outputLocation != null) {
			IInventory tile = (IInventory) world.getTileEntity (outputLocation);
			if (stack != null && stack != ItemStack.EMPTY && tile != null)
				for (int index = 0; index < tile.getSizeInventory (); index++)
					if (firstRun)
						return addToInventory (tile,index,stack);
		}
		return false;
	}

	private boolean addToInventory (IInventory tile,int index,ItemStack stack) {
		if (tile.getStackInSlot (index) == ItemStack.EMPTY) {
			tile.setInventorySlotContents (index,stack);
			return true;
		} else if (StackHelper.check (stack,tile.getStackInSlot (index),true,false)) {
			if (tile.getStackInSlot (index).getCount () + stack.getCount () <= 64) {
				ItemStack item = stack;
				item.setCount (tile.getStackInSlot (index).getCount () + stack.getCount ());
				tile.setInventorySlotContents (index,item);
				return true;
			} else {
				int amountLeft = stack.getCount () + tile.getStackInSlot (index).getCount ();
				if (amountLeft > 64) {
					ItemStack item = stack.copy ();
					item.setCount (64);
					amountLeft -= 64;
					tile.setInventorySlotContents (index,item);
					ItemStack item2 = stack.copy ();
					item2.setCount (amountLeft);
					addToStorage (item2, false);
					return true;
				}
			}
		}
		return false;
	}

	public void updateOutputLocation () {
		outputLocation = null;
		if (isValidInventory (pos.up ()))
			outputLocation = pos.up ();
		else if (isValidInventory (pos.down ()))
			outputLocation = pos.down ();
		else if (isValidInventory (pos.east ()))
			outputLocation = pos.east ();
		else if (isValidInventory (pos.west ()))
			outputLocation = pos.west ();
		else if (isValidInventory (pos.north ()))
			outputLocation = pos.north ();
		else if (isValidInventory (pos.south ()))
			outputLocation = pos.south ();
	}
}
