package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import wurmatron.spritesofthegalaxy.api.mutiblock.*;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.items.ItemSpriteColony;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.structure.agriculture.FarmStructure;
import wurmatron.spritesofthegalaxy.common.structure.energy.EnergyStructure;
import wurmatron.spritesofthegalaxy.common.structure.mine.MineStructure;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileHabitatCore2 extends TileMutiBlock implements ITickable {

	private static final long UPDATE_TIME = 1000;
	private static final double starvationPercentage = .05;

	public int mutiBlockSize;
	private long lastUpdate;
	private boolean update = true;
	private ItemStack colony = ItemStack.EMPTY;
	private List <Object[]> buildQueue = new ArrayList <> ();

	@Override
	public void update () {
		if (getStructures ().size () == 0) {
			addStructure (new FarmStructure (),1);
			addStructure (new MineStructure (),1);
			addStructure (new EnergyStructure (),5);
			//			setStorage (StorageType.POPULATION,1);
			setColonyValue (NBT.MINERALS,10000);
			//			setResearchPoints (ResearchType.ARGICULTURE,1000);
		}
		if (update && world.getWorldTime () % 20 == 0) {
			int isValid = MutiBlockHelper.isValid (world,pos);
			mutiBlockSize = isValid;
			if (isValid > 0) {
				MutiBlockHelper.setTilesCore (world,pos,isValid);
				update = false;
			}
		}
		if (lastUpdate + UPDATE_TIME <= System.currentTimeMillis ()) {
			updateStructures ();
			proccessBuildQueue ();
			proccessOutputSettings ();
			lastUpdate = System.currentTimeMillis ();
			world.markAndNotifyBlock (pos,world.getChunkFromBlockCoords (pos),world.getBlockState (pos),world.getBlockState (pos),3);
		}
	}

	public void requestUpdate () {
		this.update = true;
	}

	public int getColonyValue (String nbt) {
		return colony != ItemStack.EMPTY && colony.hasTagCompound () && colony.getTagCompound ().hasKey (nbt) ? colony.getTagCompound ().getInteger (nbt) : 0;
	}

	public void setColonyValue (String nbt,int value) {
		if (colony != ItemStack.EMPTY && colony.hasTagCompound ()) {
			NBTTagCompound nbtData = colony.getTagCompound ();
			if (nbtData != null && !nbtData.hasNoTags ()) {
				nbtData.setInteger (nbt,value > 0 ? value : 0);
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

	public void setColony (ItemStack colony) {
		this.colony = colony != null ? colony : ItemStack.EMPTY;
		markDirty ();
	}

	public ItemStack getColony () {
		return colony;
	}

	public int getPopulationFoodUsage () {
		return getColonyValue (NBT.POPULATION) * (Settings.populationFoodRequirement > 0 ? Settings.populationFoodRequirement : 1);
	}

	public boolean canPopulationGrow () {
		return getColonyValue (NBT.MAX_POPULATION) >= getPopulationFoodUsage ();
	}

	private void growPopulation () {
		if (canPopulationGrow () && getColonyValue (NBT.POPULATION) < getColonyValue (NBT.MAX_POPULATION))
			setColonyValue (NBT.POPULATION,(int) (getColonyValue (NBT.POPULATION) * Settings.populationGrowth));
		else if (getPopulationFoodUsage () > getColonyValue (NBT.FOOD) + (int) (getColonyValue (NBT.FOOD) * starvationPercentage))
			setColonyValue (NBT.POPULATION,(int) (getColonyValue (NBT.POPULATION) - (getColonyValue (NBT.POPULATION) * Settings.populationGrowth)));
	}

	public HashMap <IStructure, Integer> getStructures () {
		return colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound () && colony.getTagCompound () != null && colony.getTagCompound ().hasKey (NBT.STRUCTURES) ? ItemSpriteColony.getStructures (colony) : new HashMap <> ();
	}

	public void removeStructure (IStructure structure) {
		if (colony != ItemStack.EMPTY && colony.hasTagCompound ())
			if (getStructures ().containsKey (structure)) {
				HashMap <IResearch, Integer> currentStructures = ItemSpriteColony.getResearch (colony);
				currentStructures.remove (structure);
				setColony (ItemSpriteColony.saveResearch (colony,currentStructures));
			}
	}

	public HashMap <IResearch, Integer> getResearch () {
		return colony != ItemStack.EMPTY && colony.hasTagCompound () ? ItemSpriteColony.getResearch (colony) : new HashMap <> ();
	}

	public void setResearch (IResearch research,int lvl) {
		if (colony != ItemStack.EMPTY && colony.hasTagCompound ())
			if (!getResearch ().containsKey (research) || getResearch ().get (research) < lvl) {
				HashMap <IResearch, Integer> currentResearch = ItemSpriteColony.getResearch (colony);
				currentResearch.put (research,lvl);
				setColony (ItemSpriteColony.saveResearch (colony,currentResearch));
			}
	}

	public HashMap <StorageType, Integer> getStorage () {
		return colony != ItemStack.EMPTY && colony.hasTagCompound () ? ItemSpriteColony.getStorage (colony) : new HashMap <> ();
	}

	public void setStorage (StorageType storageType,int lvl) {
		if (colony != ItemStack.EMPTY && colony.hasTagCompound ())
			if (getStorage () != null && !getStorage ().containsKey (storageType) || getStorage ().getOrDefault (storageType,0) < lvl) {
				HashMap <StorageType, Integer> currentStorage = ItemSpriteColony.getStorage (colony);
				currentStorage.put (storageType,lvl);
				setColony (ItemSpriteColony.saveStorage (colony,currentStorage));
			}
	}

	public HashMap <IOutput, Integer> getOutputSettings () {
		return colony != null && colony != ItemStack.EMPTY && colony.hasTagCompound () ? ItemSpriteColony.getOutputSettings (colony) : new HashMap <> ();
	}

	public void setOutput (IOutput output,int lvl) {
		if (colony != ItemStack.EMPTY && colony != null)
			if (!getOutputSettings ().containsKey (output) || getOutputSettings ().get (output) < lvl) {
				HashMap <IOutput, Integer> currentOutput = ItemSpriteColony.getOutputSettings (colony);
				currentOutput.put (output,lvl);
				setColony (ItemSpriteColony.saveOutput (colony,currentOutput));
			}
	}

	public void removeOutput (IOutput output) {
		if (colony != ItemStack.EMPTY && colony.hasTagCompound ())
			if (getOutputSettings ().containsKey (output)) {
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

		if (colony != ItemStack.EMPTY && colony.hasTagCompound ())
			if (!getResearchPoints ().containsKey (researchType) || getResearchPoints ().get (researchType) < lvl) {
				HashMap <ResearchType, Integer> currentResearchPoints = ItemSpriteColony.getResearchPoints (colony);
				currentResearchPoints.put (researchType,lvl);
				setColony (ItemSpriteColony.saveResearchPoints (colony,currentResearchPoints));
			}
	}

	public void buildStructure (IStructure structure,int tier) {
		for (Object[] obj : buildQueue)
			if (obj[0].equals (structure))
				return;
		buildQueue.add (new Object[] {structure,tier,MutiBlockHelper.getBuildTime (structure,tier + 1)});
		consumeColonyValue (NBT.MINERALS,MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (this,structure),tier + 1,MutiBlockHelper.getResearchBonus (this,structure)));
	}

	public void addStructure (IStructure structure,int lvl) {
		LogHandler.info ("AddStr " + colony);
		if (colony != ItemStack.EMPTY && colony != null && colony.getTagCompound () != null && colony.hasTagCompound ()) {
			LogHandler.info ("Early: ");
			if (!getStructures ().containsKey (structure) || getStructures ().get (structure) < lvl) {
				HashMap <IStructure, Integer> currentStructures = ItemSpriteColony.getStructures (colony);
				LogHandler.info ("Adding Structure");
				currentStructures.put (structure,lvl);
				setColony (ItemSpriteColony.saveStructure (colony,currentStructures));
			}
		}
	}

	private void proccessBuildQueue () {
		if (buildQueue.size () > 0) {
			LogHandler.info ("Building");
			if (buildQueue.get (0).length == 3 && ((int) buildQueue.get (0)[2]) > 0)
				buildQueue.set (0,new Object[] {buildQueue.get (0)[0],buildQueue.get (0)[1],((int) buildQueue.get (0)[2]) - getProcessingSpeed ()});
			else if (buildQueue.get (0).length == 3 && ((int) buildQueue.get (0)[2]) <= 0)
				if (getColonyValue (NBT.MINERALS) >= MutiBlockHelper.calcMineralsForStructure ((IStructure) buildQueue.get (0)[0],MutiBlockHelper.getStructureLevel (this,(IStructure) buildQueue.get (0)[0]),((int) buildQueue.get (0)[1]) + 1,MutiBlockHelper.getResearchBonus (this,(IStructure) buildQueue.get (0)[0]))) {
					addStructure ((IStructure) buildQueue.get (0)[0],(int) buildQueue.get (0)[1]);
					buildQueue.remove (0);
				}
		}
	}

	private int getProcessingSpeed () {
		return 1;
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

	private void updateStructures () {
		if (getStructures () != null && getStructures ().size () > 0)
			for (IStructure structure : getStructures ().keySet ())
				if (structure instanceof ITickStructure && getColonyValue (NBT.ENERGY) > 0)
					((ITickStructure) structure).tickStructure (this,getStructures ().get (structure));
				else if (structure instanceof ITickStructure && structure instanceof IEnergy)
					((ITickStructure) structure).tickStructure (this,getStructures ().get (structure));
	}

	private void proccessOutputSettings () {
		if (getOutputSettings () != null && getOutputSettings ().size () > 0)
			for (IOutput output : getOutputSettings ().keySet ())
				if (getOutputSettings ().get (output) * output.getItem ().getCount () <= 64)
					if (addOutput (StackHelper.mutiStackSize (output.getItem (),getOutputSettings ().get (output))))
						for (StorageType type : output.getCost ().keySet ())
							consumeColonyValue (MutiBlockHelper.getType (type),output.getCost ().get (type));
					else {
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
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
		nbt.setBoolean (NBT.UPDATE,update);
		nbt.setLong (NBT.LASTUPDATE,lastUpdate);
		nbt.setString (NBT.COLONY,convertToData (colony));
		nbt.setInteger (NBT.SIZE,mutiBlockSize);
		super.writeToNBT (nbt);
		return nbt;
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
}