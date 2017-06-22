package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

import java.util.HashMap;

public class TileHabitatCore extends TileMutiBlock implements ITickable {

	private static final long UPDATE_TIME = 1000;

	private boolean update = false;
	private int maxPopulation = Settings.maxPopulation;
	private int food = Settings.populationFoodRequirement * Settings.maxPopulation;
	private long lastUpdate;
	private HashMap <String, Integer> items = new HashMap <> ();
	// TEMP
	private double population = 1;

	@Override
	public void update () {
		if (!update && world.getWorldTime () % 20 == 0) {
			int isValid = MutiBlockHelper.isValid (world,pos);
			if (isValid > 0) {
				MutiBlockHelper.setTilesCore (world,pos,isValid);
				update = true;
			}
		}
		// TODO Add Support for over 1 update
		if (lastUpdate + UPDATE_TIME <= System.currentTimeMillis ()) {
			handleUpdate ();
			lastUpdate = System.currentTimeMillis ();
			world.markAndNotifyBlock (pos,world.getChunkFromBlockCoords (pos),world.getBlockState (pos),world.getBlockState (pos),3);
		}
	}

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		update = nbt.getBoolean (NBT.UPDATE);
		maxPopulation = nbt.getInteger (NBT.MAX_POPULATION);
		food = nbt.getInteger (NBT.FOOD);
		lastUpdate = nbt.getLong (NBT.LASTUPDATE);
		NBTTagCompound inv = nbt.getCompoundTag (NBT.INVENTORY);
		for (int index = 0; index < inv.getSize (); index++) {
			NBTTagCompound temp = inv.getCompoundTag (Integer.toString (index));
			ItemStack item = new ItemStack (temp.getCompoundTag (NBT.ITEM));
			int amount = temp.getInteger (NBT.AMOUNT);
			if (item != ItemStack.EMPTY && item.getItem () != Item.getItemFromBlock (Blocks.AIR))
				items.put (convertToData (item),amount);
		}
		// Temp
		population = nbt.getDouble ("population");
		markDirty ();
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		nbt.setBoolean (NBT.UPDATE,update);
		nbt.setInteger (NBT.MAX_POPULATION,maxPopulation);
		nbt.setInteger (NBT.FOOD,food);
		nbt.setLong (NBT.LASTUPDATE,lastUpdate);
		NBTTagCompound invList = new NBTTagCompound ();
		int index = 0;
		for (String item : items.keySet ()) {
			index++;
			NBTTagCompound temp = new NBTTagCompound ();
			temp.setInteger (NBT.AMOUNT,items.get (item));
			temp.setTag (NBT.ITEM,convertToStack (item).writeToNBT (new NBTTagCompound ()));
			invList.setTag (Integer.toString (index),temp);
		}
		nbt.setTag (NBT.INVENTORY,invList);
		// Temp
		nbt.setDouble ("population",population);
		super.writeToNBT (nbt);
		return nbt;
	}

	public void requestUpdate () {
		this.update = false;
	}

	public double getPopulation () {
		return population;
	}

	public void setPopulation (double pop) {
		this.population = pop;
	}

	public int getFood () {
		return (int) (food - (Settings.populationFoodRequirement * getPopulation ()));
	}

	private double canPopulationGrow () {
		return (food - (getPopulation () * Settings.populationFoodRequirement));
	}

	public int getMaxPopulation () {
		return maxPopulation;
	}

	public void growPopulation () {
		if (canPopulationGrow () > 0 && getPopulation () < getMaxPopulation ())
			setPopulation (getPopulation () * Settings.populationGrowth);
		else if (canPopulationGrow () < 0 && getFood () < 0)
			setPopulation (getPopulation () - Math.abs (getFood ()));
	}

	public void setStack (ItemStack stack,int amount) {
		if (stack != ItemStack.EMPTY && stack.getItem () != Item.getItemFromBlock (Blocks.AIR))
			items.put (convertToData (stack),amount);
	}

	public void addStack (ItemStack stack) {
		if (items.containsKey (convertToData (stack))) {
			stack.setCount (1);
			setStack (stack,items.get (convertToData (stack)) + stack.getCount ());
		} else {
			stack.setCount (1);
			setStack (stack,stack.getCount ());
		}
	}

	private String convertToData (ItemStack item) {
		item.setCount (1);
		return StackHelper.convertToString (item);
	}

	private ItemStack convertToStack (String stack) {
		return StackHelper.convertToStack (stack);
	}


	public void handleUpdate () {
		growPopulation ();
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket () {
		NBTTagCompound tag = new NBTTagCompound ();
		writeToNBT (tag);
		return new SPacketUpdateTileEntity (pos,0,tag);
	}

	@Override
	public void onDataPacket (NetworkManager net,SPacketUpdateTileEntity packet) {
		population = packet.getNbtCompound ().getDouble ("population");
		readFromNBT (packet.getNbtCompound ());
	}
}
