package io.wurmatron.plants.common.tileentity;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import io.wurmatron.plants.api.PlantsEvolvedAPI;
import io.wurmatron.plants.api.mutiblock.*;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.api.research.ResearchType;
import io.wurmatron.plants.common.config.Settings;
import io.wurmatron.plants.common.items.ItemSpriteColony;
import io.wurmatron.plants.common.items.SpriteItems;
import io.wurmatron.plants.common.network.NetworkHandler;
import io.wurmatron.plants.common.network.client.ClientBuildQueueRequest;
import io.wurmatron.plants.common.reference.NBT;

import io.wurmatron.plants.common.utils.MutiBlockHelper;
import io.wurmatron.plants.common.utils.StackHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileHabitatCore extends TileMutiBlock implements ITickable {

	private static final long UPDATE_TIME = 1000;
	private static final double starvationPercentage = .01;

	public int mutiBlockSize;
	private long lastUpdate;
	private boolean update = true;
	private ItemStack colony = ItemStack.EMPTY;
	private List <Object[]> buildQueue = new ArrayList <> ();

	@Override
	public void update () {
		if (update && world.getWorldTime () % 20 == 0) {
			int isValid = MutiBlockHelper.getSize (world,pos);
			mutiBlockSize = isValid;
			if (isValid > 0) {
				MutiBlockHelper.setTilesCore (world,pos,isValid);
				update = false;
			}
		}
		if (lastUpdate + UPDATE_TIME <= System.currentTimeMillis ()) {
			if (getStructures ().size () == 0 && colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound ()) {
				for (IStructure defaultStr : Settings.defaultStructures.keySet ())
					addStructure (defaultStr,Settings.defaultStructures.get (defaultStr));
				setStorage (StorageType.POPULATION,1);
				setStorage (StorageType.MINERAL,1);
				setStorage (StorageType.BUILD_QUEUE,1);
			}
			if (canPopulationGrow ())
				growPopulation ();
			updateStructures ();
			proccessBuildQueue ();
			processOutputSettings ();
			lastUpdate = System.currentTimeMillis ();
			world.markAndNotifyBlock (pos,world.getChunkFromBlockCoords (pos),world.getBlockState (pos),world.getBlockState (pos),3);
		}
	}

	public void requestUpdate () {
		this.update = true;
	}

	public int getColonyValue (String nbt) {
		if (nbt.equalsIgnoreCase (NBT.POPULATION))
			return (int) getColonyValue (nbt,null);
		return colony != null && colony != ItemStack.EMPTY && colony.getTagCompound () != null && colony.getTagCompound ().hasKey (nbt) && colony.getTagCompound ().getInteger (nbt) != -1 ? colony.getTagCompound ().getInteger (nbt) : 0;
	}

	public double getColonyValue (String nbt,Void empty) {
		return colony != null && colony != ItemStack.EMPTY && colony.getTagCompound () != null && colony.getTagCompound ().hasKey (nbt) && colony.getTagCompound ().getDouble (nbt) != -1 ? colony.getTagCompound ().getDouble (nbt) : 0;
	}

	public void setColonyValue (String nbt,int value) {
		if (colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound ()) {
			NBTTagCompound nbtData = colony.getTagCompound ();
			if (nbtData != null && !nbtData.hasNoTags ()) {
				nbtData.setInteger (nbt,value > 0 ? value : 0);
				colony.setTagCompound (nbtData);
				setColony (colony);
			}
		}
	}

	public void setColonyValue (String nbt,String maxNBT,double value) {
		if (colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound ()) {
			NBTTagCompound nbtData = colony.getTagCompound ();
			if (nbtData != null && !nbtData.hasNoTags ()) {
				nbtData.setDouble (nbt,value > 0 ? value <= getColonyValue (maxNBT) ? value : getColonyValue (maxNBT) : 0);
				colony.setTagCompound (nbtData);
				setColony (colony);
			}
		}
	}

	public void setColonyValue (String nbt,String nbtMax,int value) {
		setColonyValue (nbt,value > getColonyValue (nbtMax) ? getColonyValue (nbtMax) : value);
	}

	public void consumeColonyValue (String nbt,int value) {
		setColonyValue (nbt,getColonyValue (nbt) - value > 0 ? getColonyValue (nbt) - value : 0);
	}

	public void addColonyValue (String nbt,int value) {
		setColonyValue (nbt,getColonyValue (nbt) + value > 0 ? getColonyValue (nbt) + value : 0);
	}

	public void addColonyValue (String nbt,String nbtMax,int value) {
		setColonyValue (nbt,nbtMax,getColonyValue (nbt) + value > 0 ? getColonyValue (nbt) + value : 0);
	}

	public ItemStack getColony () {
		return colony;
	}

	public void setColony (ItemStack colony) {
		this.colony = colony != null ? colony : ItemStack.EMPTY;
		markDirty ();
		requestUpdate ();
	}

	public int getPopulationFoodUsage () {
		return (int) (getColonyValue (NBT.POPULATION,null) * (Settings.populationFoodRequirement > 0 ? Settings.populationFoodRequirement : 1));
	}

	public double calcPopulationFoodUsage (double population) {
		return (population * (Settings.populationFoodRequirement > 0 ? Settings.populationFoodRequirement : 1));
	}

	public boolean canPopulationGrow () {
		return ((int) getColonyValue (NBT.POPULATION,null)) < getColonyValue (NBT.MAX_POPULATION) && (getColonyValue (NBT.FOOD) - getPopulationFoodUsage ()) > 0;
	}

	private void growPopulation () {
		if (canPopulationGrow () && getColonyValue (NBT.POPULATION,null) < getColonyValue (NBT.MAX_POPULATION))
			setColonyValue (NBT.POPULATION,NBT.MAX_POPULATION,checkForZero ((getColonyValue (NBT.POPULATION,null) * Settings.populationGrowth)));
		else if (getPopulationFoodUsage () > getColonyValue (NBT.FOOD) + ((int) (getColonyValue (NBT.FOOD) * starvationPercentage)))
			setColonyValue (NBT.POPULATION,NBT.MAX_POPULATION,(getColonyValue (NBT.POPULATION,null) - (getColonyValue (NBT.POPULATION,null) * (1 - Settings.populationGrowth))));
	}

	private double checkForZero (double amount) {
		double reqFood = calcPopulationFoodUsage (amount);
		if (reqFood > getColonyValue (NBT.FOOD)) {
			int foodLeft = getColonyValue (NBT.FOOD) - (int) (getColonyValue (NBT.POPULATION,null) * Settings.populationFoodRequirement);
			return getColonyValue (NBT.POPULATION,null) + ((double) foodLeft / Settings.populationFoodRequirement);
		}
		return amount;
	}

	public HashMap <IStructure, Integer> getStructures () {
		return colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound () && colony.getTagCompound () != null && colony.getTagCompound ().hasKey (NBT.STRUCTURES) ? ItemSpriteColony.getStructures (colony) : new HashMap <> ();
	}

	public void removeStructure (IStructure structure) {
		if (structure != null && colony != ItemStack.EMPTY && colony.hasTagCompound () && getStructures ().containsKey (structure)) {
			HashMap <IStructure, Integer> currentStructures = ItemSpriteColony.getStructures (colony);
			if (currentStructures.containsKey (structure) && structure instanceof IProduction) {
				IProduction production = (IProduction) structure;
				production.removeProduction (this,currentStructures.get (structure));
			}
			currentStructures.remove (structure);
			setColony (ItemSpriteColony.saveStructure (colony,currentStructures));
		}
	}

	public HashMap <IResearch, Integer> getResearch () {
		return colony != ItemStack.EMPTY && colony.hasTagCompound () ? ItemSpriteColony.getResearch (colony) : new HashMap <> ();
	}

	public void setResearch (IResearch research,int lvl) {
		if (colony != ItemStack.EMPTY && colony.hasTagCompound () && !getResearch ().containsKey (research) || getResearch ().get (research) < lvl) {
			HashMap <IResearch, Integer> currentResearch = ItemSpriteColony.getResearch (colony);
			currentResearch.put (research,lvl);
			setColony (ItemSpriteColony.saveResearch (colony,currentResearch));
		}
	}

	public HashMap <StorageType, Integer> getStorage () {
		return colony != ItemStack.EMPTY && colony.hasTagCompound () ? ItemSpriteColony.getStorage (colony) : new HashMap <> ();
	}

	public void setStorage (StorageType storageType,int lvl) {
		if (colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound () && getStorage () != null && !getStorage ().containsKey (storageType) || getStorage ().getOrDefault (storageType,0) < lvl) {
			HashMap <StorageType, Integer> currentStorage = getStorage ();
			currentStorage.put (storageType,lvl);
			setColony (ItemSpriteColony.saveStorage (colony,currentStorage));
			MutiBlockHelper.recalcStorage (this);
		}
	}

	public HashMap <IOutput, Integer> getOutputSettings () {
		return colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound () ? ItemSpriteColony.getOutputSettings (colony) : new HashMap <> ();
	}

	public void setOutput (IOutput output,int lvl) {
		if (colony != ItemStack.EMPTY && colony != null && !getOutputSettings ().containsKey (output) || getOutputSettings ().get (output) < lvl) {
			HashMap <IOutput, Integer> currentOutput = ItemSpriteColony.getOutputSettings (colony);
			currentOutput.put (output,lvl);
			setColony (ItemSpriteColony.saveOutput (colony,currentOutput));
		}
	}

	public void removeOutput (IOutput output) {
		if (colony != ItemStack.EMPTY && colony.hasTagCompound () && getOutputSettings ().containsKey (output)) {
			HashMap <IOutput, Integer> currentOutput = ItemSpriteColony.getOutputSettings (colony);
			currentOutput.remove (output);
			setColony (ItemSpriteColony.saveOutput (colony,currentOutput));
		}
	}

	public HashMap <ResearchType, Integer> getResearchPoints () {
		return colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound () ? ItemSpriteColony.getResearchPoints (colony) : new HashMap <> ();
	}

	public int getResearchPoints (ResearchType type) {
		return getResearchPoints ().getOrDefault (type,0);
	}

	public void setResearchPoints (ResearchType researchType,int lvl) {
		if (colony != ItemStack.EMPTY && colony.hasTagCompound () && !getResearchPoints ().containsKey (researchType) || getResearchPoints ().get (researchType) < lvl) {
			HashMap <ResearchType, Integer> currentResearchPoints = ItemSpriteColony.getResearchPoints (colony);
			currentResearchPoints.put (researchType,lvl);
			setColony (ItemSpriteColony.saveResearchPoints (colony,currentResearchPoints));
		}
	}

	public void consumeResearchPoints (ResearchType researchType,int amt) {
		setResearchPoints (researchType,getResearchPoints (researchType) - amt);
	}

	public void buildStructure (Object structure,int tier) {
		for (Object[] obj : buildQueue)
			if (obj[0].equals (structure) && (tier == (int) obj[1]))
				return;
		if (getColonyValue (NBT.BUILD_QUEUE) > buildQueue.size ()) {
			if (structure instanceof IStructure) {
				buildQueue.add (new Object[] {structure,tier,MutiBlockHelper.getBuildTime ((IStructure) structure,tier + 1)});
				consumeColonyValue (NBT.MINERALS,MutiBlockHelper.calcMineralsForStructure ((IStructure) structure,MutiBlockHelper.getStructureLevel (this,(IStructure) structure),tier + 1,MutiBlockHelper.getResearchBonus (this,(IStructure) structure)));
				NetworkHandler.sendToServer (new ClientBuildQueueRequest (pos));
				markDirty ();
			} else if (structure instanceof StorageType) {
				buildQueue.add (new Object[] {structure,tier,MutiBlockHelper.getBuildTime ((StorageType) structure,tier + 1)});
				NetworkHandler.sendToServer (new ClientBuildQueueRequest (pos));
				markDirty ();
			}
		}
	}

	public void updateBuildQueue (List <Object[]> buildQueue) {
		this.buildQueue = buildQueue;
	}

	public void addStructure (IStructure structure,int lvl) {
		if (colony != ItemStack.EMPTY && colony != null && colony.getTagCompound () != null && colony.hasTagCompound () && !getStructures ().containsKey (structure) || colony != ItemStack.EMPTY && colony != null && colony.getTagCompound () != null && colony.hasTagCompound () && getStructures ().get (structure) < lvl) {
			HashMap <IStructure, Integer> currentStructures = ItemSpriteColony.getStructures (colony);
			currentStructures.put (structure,lvl);
			if (structure instanceof IProduction)
				((IProduction) structure).addProduction (this,lvl);
			setColony (ItemSpriteColony.saveStructure (colony,currentStructures));
		}
	}

	// TODO Build Queue Selection (Diff Category's)
	private void proccessBuildQueue () {
		if (buildQueue.size () > 0 && buildQueue.get (0).length == 3) {
			if (((int) buildQueue.get (0)[2]) > 0)
				buildQueue.set (0,new Object[] {buildQueue.get (0)[0],buildQueue.get (0)[1],decressTime (((int) buildQueue.get (0)[2]))});
			if ((((int) buildQueue.get (0)[2]) <= 0))
				if (buildQueue.get (0)[0] instanceof IStructure) {
					addStructure ((IStructure) buildQueue.get (0)[0],(int) buildQueue.get (0)[1]);
					buildQueue.remove (0);
				} else if (buildQueue.get (0)[0] instanceof StorageType) {
					setStorage ((StorageType) buildQueue.get (0)[0],(int) buildQueue.get (0)[1]);
					buildQueue.remove (0);
				}
		}
	}

	private int decressTime (int time) {
		int t = time - getProcessingSpeed ();
		return t > 0 ? t : 0;
	}

	private int getProcessingSpeed () {
		return 1 + MutiBlockHelper.countAccelerators (world,this);
	}

	public boolean addOutput (ItemStack stack) {
		BlockPos output = MutiBlockHelper.findOutput (world,this);
		if (output != null && world.getTileEntity (output) instanceof TileOutput) {
			TileOutput tile = (TileOutput) world.getTileEntity (output);
			if (tile != null)
				return tile.addToStorage (stack,true);
		}
		return false;
	}

	private void updateStructures () {
		if (getStructures () != null && getStructures ().size () > 0 && getPowerUsage () <= getColonyValue (NBT.ENERGY))
			if (hasWorkers ()) {
				for (IStructure structure : getStructures ().keySet ())
					if (structure instanceof ITickStructure && getPowerUsage () <= getColonyValue (NBT.ENERGY))
						((ITickStructure) structure).tickStructure (this,getStructures ().get (structure));
			} else {
				int popLeft = getColonyValue (NBT.POPULATION);
				for (IStructure structure : getStructures ().keySet ())
					if (structure instanceof ITickStructure && getPowerUsage () <= getColonyValue (NBT.ENERGY) && (structure.getPopulationRequirment () * getStructures ().get (structure)) <= popLeft) {
						((ITickStructure) structure).tickStructure (this,getStructures ().get (structure));
						popLeft -= (int) (structure.getPopulationRequirment () * getStructures ().get (structure));
					}
			}
	}

	private void processOutputSettings () {
		if (getOutputSettings () != null && getOutputSettings ().size () > 0)
			for (IOutput output : getOutputSettings ().keySet ())
				if (MutiBlockHelper.isOutputRunning (output,this) && canOutput (output.getCost ()))
					handleOutput (output);
	}

	private void handleOutput (IOutput output) {
		if (getOutputSettings ().get (output) * output.getItem ().getCount () <= 64) {
			if (addOutput (output.getItem ()))
				for (StorageType type : output.getCost ().keySet ())
					consumeColonyValue (MutiBlockHelper.getType (type),output.getCost ().get (type));
		} else {
			int amountLeftToAdd = getOutputSettings ().get (output) * output.getItem ().getCount ();
			for (int times = 0; times < (getOutputSettings ().get (output) * output.getItem ().getCount ()) % 64; times++)
				if (((getOutputSettings ().get (output) * output.getItem ().getCount ()) % 64) - 1 == times) {
					if (addOutput (StackHelper.setStackSize (output.getItem (),amountLeftToAdd)))
						for (StorageType type : output.getCost ().keySet ())
							consumeColonyValue (MutiBlockHelper.getType (type),output.getCost ().get (type));
				} else if (addOutput (StackHelper.setStackSize (output.getItem (),64))) {
					amountLeftToAdd -= 64;
					for (StorageType type : output.getCost ().keySet ())
						consumeColonyValue (MutiBlockHelper.getType (type),output.getCost ().get (type));
				}
		}
	}

	@Override
	public void readFromNBT (NBTTagCompound nbt) {
		super.readFromNBT (nbt);
		update = nbt.getBoolean (NBT.UPDATE);
		lastUpdate = nbt.getLong (NBT.LASTUPDATE);
		colony = convertToStack (nbt.getString (NBT.COLONY));
		mutiBlockSize = nbt.getInteger (NBT.SIZE);
		NBTTagList structureList = nbt.getTagList (NBT.STRUCTURES,8);
		for (int index = 0; index < structureList.tagCount (); index++) {
			NBTTagCompound temp = (NBTTagCompound) structureList.get (index);
			if (temp.hasKey (NBT.STRUCTURE)) {
				IStructure structure = PlantsEvolvedAPI.getStructureFromName (temp.getString (NBT.STRUCTURE));
				int tier = temp.getInteger (NBT.LEVEL);
				int time = temp.getInteger (NBT.TIME);
				buildQueue.add (new Object[] {structure,tier,time});
			} else if (temp.hasKey (NBT.STORAGE)) {
				StorageType structure = StorageType.valueOf (temp.getString (NBT.STORAGE));
				int tier = temp.getInteger (NBT.LEVEL);
				int time = temp.getInteger (NBT.TIME);
				buildQueue.add (new Object[] {structure,tier,time});
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		nbt.setBoolean (NBT.UPDATE,update);
		nbt.setLong (NBT.LASTUPDATE,lastUpdate);
		nbt.setString (NBT.COLONY,convertToData (colony));
		nbt.setInteger (NBT.SIZE,mutiBlockSize);
		NBTTagList structureList = new NBTTagList ();
		for (Object[] obj : buildQueue) {
			if (obj[0] instanceof IStructure) {
				NBTTagCompound temp = new NBTTagCompound ();
				temp.setString (NBT.STRUCTURE,((IStructure) obj[0]).getName ());
				temp.setInteger (NBT.LEVEL,((int) obj[1]));
				temp.setInteger (NBT.TIME,((int) obj[2]));
				structureList.appendTag (temp);
			} else if (obj[0] instanceof StorageType) {
				NBTTagCompound temp = new NBTTagCompound ();
				temp.setString (NBT.STORAGE,((StorageType) obj[0]).name ());
				temp.setInteger (NBT.LEVEL,((int) obj[1]));
				temp.setInteger (NBT.TIME,((int) obj[2]));
				structureList.appendTag (temp);
			}
		}
		nbt.setTag (NBT.STRUCTURES,structureList);
		super.writeToNBT (nbt);
		return nbt;
	}

	private String convertToData (ItemStack item) {
		if (item != null) {
			item.setCount (1);
			return StackHelper.convert (item,false);
		}
		return "";
	}

	private ItemStack convertToStack (String stack) {
		return StackHelper.convert (stack);
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

	public int getPowerUsage () {
		int energy = 0;
		if (getStructures ().size () > 0)
			for (IStructure structure : getStructures ().keySet ())
				energy += structure.getEnergyUsage (getStructures ().get (structure));
		return energy;
	}

	public List <Object[]> getBuildQueue () {
		return buildQueue;
	}

	public void removeFromBuildQueue (Object structure) {
		if (buildQueue.size () > 0) {
			Object[] markForRemoval = new Object[0];
			for (Object[] bq : buildQueue)
				if (bq[0] instanceof IStructure) {
					if (((IStructure) bq[0]).getName ().equalsIgnoreCase (((IStructure) structure).getName ()))
						markForRemoval = bq;
				} else if (bq[0] instanceof StorageType)
					if (((StorageType) bq[0]).name ().equalsIgnoreCase (((StorageType) structure).name ()))
						markForRemoval = bq;
			if (markForRemoval.length > 0) {
				if (structure instanceof IStructure)
					addColonyValue (NBT.MINERALS,NBT.MAX_MINERALS,MutiBlockHelper.calcMineralsForStructure (((IStructure) markForRemoval[0]),getStructures ().get (markForRemoval[0]),((int) markForRemoval[1]),0));
				if (structure instanceof StorageType)
					addColonyValue (NBT.MINERALS,NBT.MAX_MINERALS,MutiBlockHelper.calcMineralsForStorage ((StorageType) markForRemoval[0],getStorage ().get (markForRemoval[0]),((int) markForRemoval[1]),0));
				buildQueue.remove (markForRemoval);
			}
		}
	}

	public boolean importStack (ItemStack stack) {
		if (stack.getItem () == SpriteItems.mineral && stack.getItemDamage () == 0) {
			if (getColonyValue (NBT.MAX_MINERALS) >= getColonyValue (NBT.MINERALS) + (10 * stack.getCount ())) {
				addColonyValue (NBT.MINERALS,10 * stack.getCount ());
				return true;
			}
		} else if (stack.getItem () == SpriteItems.mineral && stack.getItemDamage () == 1) {
			if (getColonyValue (NBT.MAX_GEM) >= getColonyValue (NBT.GEM) + (10 * stack.getCount ())) {
				addColonyValue (NBT.GEM,10 * stack.getCount ());
				return true;
			}
		} else if (stack.getItem () == Items.IRON_INGOT) {
			if (getColonyValue (NBT.MAX_MINERALS) >= getColonyValue (NBT.MINERALS) + (100 * stack.getCount ())) {
				addColonyValue (NBT.MINERALS,100 * stack.getCount ());
				return true;
			}
		} else if (stack.getItem () == SpriteItems.mineral && stack.getItemDamage () == 3) {
			if (buildQueue.size () > 0) {
				buildQueue.set (0,new Object[] {buildQueue.get (0)[0],buildQueue.get (0)[1],((int) buildQueue.get (0)[2]) - ((int) buildQueue.get (0)[2] / 100)});
				return true;
			}
		} else if (stack.getItem () == SpriteItems.mineral && stack.getItemDamage () == 4) {
			if (buildQueue.size () > 0) {
				buildQueue.set (0,new Object[] {buildQueue.get (0)[0],buildQueue.get (0)[1],((int) buildQueue.get (0)[2]) - ((int) buildQueue.get (0)[2] / 10)});
				NetworkHandler.sendToServer (new ClientBuildQueueRequest (getPos ()));
				return true;
			}
		}
		return false;
	}

	private boolean canOutput (HashMap <StorageType, Integer> outputCost) {
		for (StorageType type : outputCost.keySet ())
			if (!(getColonyValue (MutiBlockHelper.getType (type)) >= outputCost.get (type)))
				return false;
		return true;
	}

	public int getAmountOfWorkers () {
		return (int) (getColonyValue (NBT.POPULATION,null) * Settings.workerPercentage);
	}

	private boolean hasWorkers () {
		if (getStructures ().size () > 0) {
			int amountRequired = 0;
			for (IStructure structure : getStructures ().keySet ())
				amountRequired += MutiBlockHelper.getRequiredPopulation (structure,getStructures ().get (structure));
			return getAmountOfWorkers () >= amountRequired;
		}
		return true;
	}

	public int getRequiredWorkers () {
		if (getStructures ().size () > 0) {
			int amountRequired = 0;
			for (IStructure structure : getStructures ().keySet ())
				amountRequired += MutiBlockHelper.getRequiredPopulation (structure,getStructures ().get (structure));
			return amountRequired;
		}
		return 0;
	}
}
