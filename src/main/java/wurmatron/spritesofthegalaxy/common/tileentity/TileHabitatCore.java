package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.items.ItemSpriteColony;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.structure.agriculture.FarmStructure;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileHabitatCore extends TileMutiBlock implements ITickable {

	private static final long UPDATE_TIME = 1000;
	public int mutiBlockSize;
	private HashMap <IStructure, Integer> structures = new HashMap <> ();
	private HashMap <StorageType, Integer> storageData = new HashMap <> ();
	private HashMap <ResearchType, Integer> researchPoints = new HashMap <> ();
	private List <Object[]> buildQueue = new ArrayList <> ();
	private ItemStack colonyItem;
	private boolean update = false;
	private long lastUpdate;

	private int food = Settings.populationFoodRequirement * Settings.startPopulation + 100;
	private int energy = 10;
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
					addStorageType (StorageType.POPULATION,1);
					researchPoints.put (ResearchType.ARGICULTURE,1000);
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
		if (structures.size () > 0)
			for (IStructure structure : structures.keySet ())
				if (structure instanceof ITickStructure && getEnergy () > 0)
					((ITickStructure) structure).tickStructure (this,structures.get (structure));
				else if (structure instanceof ITickStructure && structure instanceof IEnergy)
					((ITickStructure) structure).tickStructure (this,structures.get (structure));
		proccessBuildQueue ();
	}

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		update = nbt.getBoolean (NBT.UPDATE);
		maxPopulation = nbt.getInteger (NBT.MAX_POPULATION);
		food = nbt.getInteger (NBT.FOOD);
		lastUpdate = nbt.getLong (NBT.LASTUPDATE);
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
		for (int index = 0; index < storageList.tagCount (); index++) {
			NBTTagString temp = (NBTTagString) storageList.get (index);
			StorageType type = Enum.valueOf (StorageType.class,temp.getString ().substring (0,temp.getString ().indexOf (".")));
			int tier = Integer.valueOf (temp.getString ().substring (temp.getString ().indexOf (".") + 1,temp.getString ().length ()));
			storageData.put (type,tier);
		}
		NBTTagList researchPointList = nbt.getTagList (NBT.RESEARCH_POINTS,8);
		for (int index = 0; index < researchPointList.tagCount (); index++) {
			NBTTagString temp = (NBTTagString) researchPointList.get (index);
			ResearchType researchType = Enum.valueOf (ResearchType.class,temp.getString ().substring (0,temp.getString ().indexOf (".")));
			int tier = Integer.valueOf (temp.getString ().substring (temp.getString ().indexOf (".") + 1,temp.getString ().length ()));
			researchPoints.put (researchType,tier);
		}
		energy = nbt.getInteger (NBT.ENERGY);
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		nbt.setBoolean (NBT.UPDATE,update);
		nbt.setInteger (NBT.MAX_POPULATION,maxPopulation);
		nbt.setInteger (NBT.FOOD,food);
		nbt.setLong (NBT.LASTUPDATE,lastUpdate);
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
		NBTTagList researchList = new NBTTagList ();
		for (ResearchType type : researchPoints.keySet ())
			researchList.appendTag (new NBTTagString (type.name () + "." + researchPoints.get (type)));
		nbt.setTag (NBT.RESEARCH_POINTS,researchList);
		nbt.setInteger (NBT.ENERGY,energy);
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
		consumeEnergy (structure.getEnergyUsage (tier));
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
					production.removeProduction (this,structures.get (structure));
			}
			addEnergy (structure.getEnergyUsage (structures.get (structure)));
			structures.remove (structure);
		}
	}

	public void reloadStructure (IStructure structure,int newTier) {
		if (getStructures ().containsKey (structure)) {
			removeStructure (structure);
			addStructure (structure,newTier);
		} else {
			addBuildQueue (structure,newTier);
		}
	}

	public void addStorageType (StorageType type,int tier) {
		storageData.put (type,tier);
		MutiBlockHelper.addStorageType (this,type,tier);
	}

	public void removeStorageType (StorageType type,int tier) {
		storageData.remove (type);
		markDirty ();
	}

	public void reloadStorageType (StorageType type,int currentTier,int newTier) {
		removeStorageType (type,currentTier);
		addStorageType (type,newTier);
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
		this.minerals = this.minerals + minerals > maxMinerals ? maxMinerals : this.minerals + minerals;
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

	public int getResearshPoints (ResearchType type) {
		return researchPoints.getOrDefault (type,0);
	}

	public void setResearchPoints (ResearchType type,int amount) {
		researchPoints.put (type,amount);
		markDirty ();
	}

	public void addResearchPoint (ResearchType type,int amount) {
		setResearchPoints (type,getResearshPoints (type) + amount);
	}

	public void consumeResearchPoints (ResearchType type,int amount) {
		setResearchPoints (type,getResearshPoints (type) - amount);
	}

	public int getEnergy () {
		return energy;
	}

	public void setEnergy (int energy) {
		this.energy = energy;
		markDirty ();
	}

	public void consumeEnergy (int amount) {
		this.energy -= amount;
		markDirty ();
	}

	public void addEnergy (int amount) {
		this.energy += amount;
		markDirty ();
	}

	public boolean addOutput (ItemStack stack) {
		BlockPos output = MutiBlockHelper.findOutput (world,this);
		if (output != null && world.getTileEntity (output) instanceof TileOutput) {
			TileOutput tile = (TileOutput) world.getTileEntity (output);
			if (tile != null)
				return tile.addOutput (stack);
		}
		return false;
	}

	// Supprt for AllReady Existing Structures
	public void addBuildQueue (IStructure structure,Integer tier) {
		if (MutiBlockHelper.hasRequiredResearch (this,structure) && structure != null)
			if (getStructures () != null && getStructures ().get (structure) == null) {
				LogHandler.info ("Str: " + structure.getName () + " " + tier);
				buildQueue.add (new Object[] {structure,tier,MutiBlockHelper.getBuildTime (structure,tier)});
			}
	}

	public void proccessBuildQueue () {
		if (buildQueue.size () > 0) {
			LogHandler.info ("Building Structures");
			for (int index = 0; index < buildQueue.size (); index++)
				if (((int) buildQueue.get (index)[2]) <= 0) {
					addStructure ((IStructure) buildQueue.get (index)[0],(int) buildQueue.get (index)[1]);
					buildQueue.remove (index);
				} else
					buildQueue.set (index,new Object[] {buildQueue.get (index)[0],buildQueue.get (index)[1],((int) buildQueue.get (index)[2]) - 1});
		}
	}
}
