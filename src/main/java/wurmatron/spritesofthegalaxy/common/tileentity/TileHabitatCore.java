package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.EnumProductionType;
import wurmatron.spritesofthegalaxy.api.mutiblock.IProduction;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.items.ItemSpriteColony;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.structure.FarmStructure;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

import java.util.HashMap;

public class TileHabitatCore extends TileMutiBlock implements ITickable {

	private static final long UPDATE_TIME = 1000;
	public int mutiBlockSize;
	private HashMap <String, Integer> items = new HashMap <> ();
	private HashMap <IStructure, Integer> structures = new HashMap <> ();
	private HashMap <StorageType, Integer> storageData = new HashMap <> ();
	private ItemStack colonyItem;
	private boolean update = false;
	private long lastUpdate;

	private int food = Settings.populationFoodRequirement * Settings.startPopulation + 100;
	private int minerals = 5000;
	private int maxPopulation = Settings.startPopulation + 100;
	private int maxMinerals = 5000;

	@Override
	public void update () {
		if (!update && world.getWorldTime () % 20 == 0) {
			int isValid = MutiBlockHelper.isValid (world,pos);
			mutiBlockSize = isValid;
			if (isValid > 0) {
				MutiBlockHelper.setTilesCore (world,pos,isValid);
				update = true;
				if (structures.size () == 0) {
					addStructure (new FarmStructure (),1);
					addStorageType (StorageType.POPULATION, 1);
				}
			}
		}
		if (lastUpdate + UPDATE_TIME <= System.currentTimeMillis ()) {
			handleUpdate ();
			lastUpdate = System.currentTimeMillis ();
			world.markAndNotifyBlock (pos,world.getChunkFromBlockCoords (pos),world.getBlockState (pos),world.getBlockState (pos),3);
		}
	}

	public void handleUpdate () {
		growPopulation ();
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
		colonyItem = convertToStack (nbt.getString (NBT.COLONY));
		NBTTagList structureList = nbt.getTagList (NBT.STRUCTURES,8);
		for (int index = 0; index < structureList.tagCount (); index++) {
			NBTTagString temp = (NBTTagString) structureList.get (index);
			IStructure structure = SpritesOfTheGalaxyAPI.getStructureFromName (temp.getString ().substring (0,temp.getString ().indexOf (".")));
			int tier = Integer.valueOf (temp.getString ().substring (temp.getString ().indexOf (".") + 1,temp.getString ().length ()));
			structures.put (structure,tier);
		}
		mutiBlockSize = nbt.getInteger (NBT.SIZE);
		minerals = nbt.getInteger (NBT.MINERALS);
		maxMinerals = nbt.getInteger (NBT.MAX_MINERALS);
		NBTTagList storageList = nbt.getTagList (NBT.STORAGE,8);
		LogHandler.info ("SL : " + storageList.tagCount ());
		for (int index = 0; index < storageList.tagCount (); index++) {
			NBTTagString temp = (NBTTagString) storageList.get (index);
			StorageType type = Enum.valueOf (StorageType.class,temp.getString ().substring (0,temp.getString ().indexOf (".")));
			int tier = Integer.valueOf (temp.getString ().substring (temp.getString ().indexOf (".") + 1,temp.getString ().length ()));
			storageData.put (type,tier);
		}
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
		nbt.setString (NBT.COLONY,convertToData (colonyItem));
		NBTTagList structureList = new NBTTagList ();
		for (IStructure structure : structures.keySet ())
			structureList.appendTag (new NBTTagString (structure.getName () + "." + structures.get (structure)));
		NBTTagList storageList = new NBTTagList ();
		for (StorageType type : storageData.keySet ())
			storageList.appendTag (new NBTTagString (type.name () + "." + storageData.get (type)));
		nbt.setTag (NBT.STRUCTURES,structureList);
		nbt.setInteger (NBT.SIZE,mutiBlockSize);
		nbt.setInteger (NBT.MINERALS,minerals);
		nbt.setInteger (NBT.MAX_MINERALS,maxMinerals);
		nbt.setTag (NBT.STORAGE,storageList);
		super.writeToNBT (nbt);
		return nbt;
	}

	public void requestUpdate () {
		this.update = false;
	}

	public double getPopulation () {
		if (hasColony () && getColony ().getTagCompound () != null)
			return getColony ().getTagCompound ().getDouble (NBT.POPULATION);
		return -1;
	}

	public void setPopulation (double pop) {
		if (pop < Integer.MAX_VALUE && pop <= getMaxPopulation () && hasColony () && getColony ().getTagCompound () != null) {
			ItemStack colony = getColony ();
			colony.getTagCompound ().setDouble (NBT.POPULATION,pop < maxPopulation ? pop : maxPopulation);
			addColony (colony);
		}
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

	public void setMaxPopulation (int max) {
		if (max >= 0)
			this.maxPopulation = max;
		else
			this.maxPopulation = 0;
		markDirty ();
	}

	public void addMaxPopulation (int amount) {
		setMaxPopulation (getMaxPopulation () + amount);
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
		if (items != null && stack != null && !stack.isEmpty ())
			if (items.containsKey (convertToData (stack))) {
				stack.setCount (1);
				setStack (stack,items.get (convertToData (stack)) + stack.getCount ());
			} else {
				stack.setCount (1);
				setStack (stack,stack.getCount ());
			}
	}

	private String convertToData (ItemStack item) {
		if (item != null) {
			item.setCount (1);
			return StackHelper.convertToString (item);
		}
		return "";
	}

	private ItemStack convertToStack (String stack) {
		return StackHelper.convertToStack (stack);
	}


	@Override
	public SPacketUpdateTileEntity getUpdatePacket () {
		NBTTagCompound tag = new NBTTagCompound ();
		writeToNBT (tag);
		return new SPacketUpdateTileEntity (pos,0,tag);
	}

	@Override
	public void onDataPacket (NetworkManager net,SPacketUpdateTileEntity packet) {
		readFromNBT (packet.getNbtCompound ());
	}

	public void addColony (ItemStack stack) {
		this.colonyItem = stack;
		markDirty ();
	}

	public ItemStack getColony () {
		return colonyItem;
	}

	public boolean hasColony () {
		return colonyItem != null && colonyItem != ItemStack.EMPTY;
	}

	public HashMap <IStructure, Integer> getStructures () {
		return structures;
	}

	public HashMap <StorageType, Integer> getStorageData () {
		return storageData;
	}

	public void addStructure (IStructure structure,int tier) {
		structures.put (structure,tier);
		if (structure instanceof IProduction) {
			IProduction production = (IProduction) structure;
			if (production.getType () == EnumProductionType.VALUE)
				production.addProduction (this,tier);
		}
	}

	public void removeStructure (IStructure structure) {
		if (structures.containsKey (structure)) {
			if (structure instanceof IProduction) {
				IProduction production = (IProduction) structure;
				if (production.getType () == EnumProductionType.VALUE)
					production.removeProduction (this,structures.remove (structure));
			}
			structures.remove (structure);
		}
	}

	public void reloadStructure (IStructure structure,int newTier) {
		removeStructure (structure);
		addStructure (structure,newTier);
	}

	public void addStorageType (StorageType type,int tier) {
		storageData.put (type,tier);
		MutiBlockHelper.addStorageType (this,type,tier);
	}

	public void removeStorageType (StorageType type,int tier) {
		storageData.remove (type);
		setMaxPopulation (getMaxPopulation () - (int) (tier * type.getScale ()));
		markDirty ();
	}

	public void reloadStorageType (StorageType type,int currentTier,int newTier) {
		LogHandler.serverInfo ("Rem And Add " + storageData.size ());
		removeStorageType (type,currentTier);
		LogHandler.serverInfo ("Rem And Add " + storageData.size ());
		addStorageType (type,newTier);
		LogHandler.serverInfo ("Rem And Add " + storageData.size ());
	}

	public void addFood (int food) {
		this.food += food;
	}

	public void removeFood (int food) {
		this.food -= food;
	}

	public void setResearchLevel (IResearch r,int level) {
		if (colonyItem != null && colonyItem != ItemStack.EMPTY && colonyItem.getTagCompound () != null) {
			HashMap <IResearch, Integer> research = ItemSpriteColony.getResearch (colonyItem);
			if (research == null)
				research = new HashMap <> ();
			if (ResearchHelper.isValidMove (research,r)) {
				research.put (r,level);
				ItemStack colony = ItemSpriteColony.createColony (colonyItem.getTagCompound ().getString (NBT.LINEAGE),getPopulation (),research);
				addColony (colony);
			}
		}
	}

	public HashMap <IResearch, Integer> getResearch () {
		return ItemSpriteColony.getResearch (colonyItem);
	}

	public int getMinerals () {
		return minerals;
	}

	public void addMinerals (int minerals) {
		this.maxMinerals = this.minerals + minerals > maxMinerals ? maxMinerals : this.minerals + minerals;
		markDirty ();
	}

	public void consumeMinerals (int minerals) {
		this.minerals -= minerals;
		if (this.minerals <= 0)
			this.minerals = 0;
		markDirty ();
	}

	public void addMaxMinerals (int amt) {
		setMaxMinerals (maxMinerals + amt);
	}

	public void consumeMaxMinerals (int amt) {
		maxMinerals -= amt;
		markDirty ();
	}

	public int getMaxMinerals () {
		return maxMinerals;
	}

	public void setMaxMinerals (int amt) {
		this.maxMinerals = amt;
		markDirty ();
	}
}
