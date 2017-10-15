package wurmatron.spritesofthegalaxy.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.api.SpritesOfTheGalaxyAPI;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.api.mutiblock.IStructure;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.api.research.ResearchType;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.reference.Lineages;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class ItemSpriteColony extends Item {

	public ItemSpriteColony () {
		setCreativeTab (SpritesOfTheGalaxy.tabSprites);
		setUnlocalizedName ("spriteColony");
		setHasSubtypes (true);
		setMaxStackSize (1);
		setMaxDamage (0);
	}

	@Override
	public void onCreated (ItemStack stack,World world,EntityPlayer player) {
		NBTTagCompound nbt = new NBTTagCompound ();
		nbt.setString (NBT.LINEAGE,Lineages.COMMON);
		nbt.setInteger (NBT.POPULATION,Settings.startPopulation);
		nbt.setInteger (NBT.MINERALS,10000);
		stack.setTagCompound (nbt);
	}


	@Override
	public void addInformation (ItemStack stack,@Nullable World worldIn,List <String> tip,ITooltipFlag flagIn) {
		if (stack.getTagCompound () != null) {
			tip.add (TextFormatting.GOLD + I18n.format (Local.POPULATION) + ": " + (int) stack.getTagCompound ().getDouble (NBT.POPULATION));
			tip.add (TextFormatting.GOLD + I18n.format (Local.LINEAGE) + ": " + stack.getTagCompound ().getString (NBT.LINEAGE));
			if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) && stack.getTagCompound ().hasKey (NBT.RESEARCH)) {
				tip.add ("");
				HashMap <IResearch, Integer> research = getResearch (stack);
				for (IResearch res : research.keySet ())
					tip.add (TextFormatting.AQUA + I18n.format ("research." + res.getName () + ".name") + " lvl " + research.get (res));
			}
		}
	}

	public static ItemStack createColony (String lineage,double population,HashMap <IResearch, Integer> research) {
		ItemStack stack = new ItemStack (SpriteItems.spriteColony,1,0);
		NBTTagCompound nbt = new NBTTagCompound ();
		nbt.setDouble (NBT.POPULATION,population);
		nbt.setString (NBT.LINEAGE,lineage);
		if (research != null && research.size () > 0) {
			NBTTagList researchList = new NBTTagList ();
			for (IResearch r : research.keySet ()) {
				NBTTagCompound temp = new NBTTagCompound ();
				temp.setString (NBT.NAME,r.getName ());
				temp.setInteger (NBT.LEVEL,research.get (r));
				researchList.appendTag (temp);
			}
			nbt.setTag (NBT.RESEARCH,researchList);
		}
		stack.setTagCompound (nbt);
		return stack;
	}

	@Override
	public void getSubItems (CreativeTabs tab,NonNullList <ItemStack> sub) {
		HashMap <IResearch, Integer> temp = new HashMap <> ();
		temp.put (ResearchHelper.land,1);
		sub.add (createColony (Lineages.COMMON,Settings.startPopulation,temp));
	}

	public static HashMap <IResearch, Integer> getResearch (ItemStack stack) {
		if (stack != null && stack != ItemStack.EMPTY && stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.RESEARCH)) {
			HashMap <IResearch, Integer> research = new HashMap <> ();
			NBTTagList researchList = stack.getTagCompound ().getTagList (NBT.RESEARCH,10);
			for (int index = 0; index < researchList.tagCount (); index++) {
				NBTTagCompound temp = researchList.getCompoundTagAt (index);
				IResearch researchTemp = SpritesOfTheGalaxyAPI.getResearchFromName (temp.getString (NBT.NAME));
				if (researchTemp != null)
					research.put (researchTemp,temp.getInteger (NBT.LEVEL));
			}
			return research;
		}
		return new HashMap <> ();
	}

	public static HashMap <IStructure, Integer> getStructures (ItemStack stack) {
		if (stack != null && stack != ItemStack.EMPTY && stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.STRUCTURES)) {
			HashMap <IStructure, Integer> structure = new HashMap <> ();
			NBTTagList structureList = stack.getTagCompound ().getTagList (NBT.STRUCTURES,10);
			for (int index = 0; index < structureList.tagCount (); index++) {
				NBTTagCompound temp = structureList.getCompoundTagAt (index);
				IStructure structureTemp = SpritesOfTheGalaxyAPI.getStructureFromName (temp.getString (NBT.NAME));
				if (structureTemp != null)
					structure.put (structureTemp,temp.getInteger (NBT.LEVEL));
			}
			return structure;
		}
		return new HashMap <> ();
	}

	public static HashMap <StorageType, Integer> getStorage (ItemStack stack) {
		if (stack != null && stack != ItemStack.EMPTY && stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.STORAGE)) {
			HashMap <StorageType, Integer> storage = new HashMap <> ();
			NBTTagList storageData = stack.getTagCompound ().getTagList (NBT.STORAGE,10);
			for (int index = 0; index < storageData.tagCount (); index++) {
				NBTTagCompound temp = storageData.getCompoundTagAt (index);
				StorageType storageTemp = Enum.valueOf (StorageType.class,temp.getString (NBT.NAME));
				storage.put (storageTemp,temp.getInteger (NBT.LEVEL));
			}
			return storage;
		}
		return new HashMap <> ();
	}

	public static HashMap <IOutput, Integer> getOutputSettings (ItemStack stack) {
		if (stack != null && stack != ItemStack.EMPTY && stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.OUTPUT)) {
			HashMap <IOutput, Integer> output = new HashMap <> ();
			NBTTagList outputData = stack.getTagCompound ().getTagList (NBT.OUTPUT,10);
			for (int index = 0; index < outputData.tagCount (); index++) {
				NBTTagCompound temp = outputData.getCompoundTagAt (index);
				IOutput outputTemp = SpritesOfTheGalaxyAPI.getOutputFromName (temp.getString (NBT.NAME));
				output.put (outputTemp,temp.getInteger (NBT.LEVEL));
			}
			return output;
		}
		return new HashMap <> ();
	}

	public static HashMap <ResearchType, Integer> getResearchPoints (ItemStack stack) {
		if (stack != null && stack != ItemStack.EMPTY && stack.getTagCompound () != null && stack.getTagCompound ().hasKey (NBT.RESEARCH_POINTS)) {
			HashMap <ResearchType, Integer> researchPoints = new HashMap <> ();
			NBTTagList pointsData = stack.getTagCompound ().getTagList (NBT.RESEARCH_POINTS,10);
			for (int index = 0; index < pointsData.tagCount (); index++) {
				NBTTagCompound temp = pointsData.getCompoundTagAt (index);
				ResearchType researchPointTemp = Enum.valueOf (ResearchType.class,temp.getString (NBT.NAME));
				researchPoints.put (researchPointTemp,temp.getInteger (NBT.LEVEL));
			}
			return researchPoints;
		}
		return new HashMap <> ();
	}

	public static ItemStack saveResearch (ItemStack colony,HashMap <IResearch, Integer> research) {
		ItemStack newColony = colony.copy ();
		NBTTagList researchList = new NBTTagList ();
		for (IResearch r : research.keySet ()) {
			NBTTagCompound temp = new NBTTagCompound ();
			temp.setString (NBT.NAME,r.getName ());
			temp.setInteger (NBT.LEVEL,research.get (r));
			researchList.appendTag (temp);
		}
		newColony.getTagCompound ().setTag (NBT.RESEARCH,researchList);
		return newColony;
	}

	public static ItemStack saveStorage (ItemStack colony,HashMap <StorageType, Integer> storage) {
		ItemStack newColony = colony.copy ();
		NBTTagList storageList = new NBTTagList ();
		for (StorageType r : storage.keySet ()) {
			NBTTagCompound temp = new NBTTagCompound ();
			temp.setString (NBT.NAME,r.name ());
			temp.setInteger (NBT.LEVEL,storage.get (r));
			storageList.appendTag (temp);
		}
		newColony.getTagCompound ().setTag (NBT.STORAGE,storageList);
		return newColony;
	}

	public static ItemStack saveOutput (ItemStack colony,HashMap <IOutput, Integer> output) {
		ItemStack newColony = colony.copy ();
		NBTTagList outputList = new NBTTagList ();
		for (IOutput r : output.keySet ()) {
			NBTTagCompound temp = new NBTTagCompound ();
			temp.setString (NBT.NAME,r.getName ());
			temp.setInteger (NBT.LEVEL,output.get (r));
			outputList.appendTag (temp);
		}
		newColony.getTagCompound ().setTag (NBT.OUTPUT,outputList);
		return newColony;
	}

	public static ItemStack saveResearchPoints (ItemStack colony,HashMap <ResearchType, Integer> output) {
		ItemStack newColony = colony.copy ();
		NBTTagList pointsList = new NBTTagList ();
		for (ResearchType r : output.keySet ()) {
			NBTTagCompound temp = new NBTTagCompound ();
			temp.setString (NBT.NAME,r.name ());
			temp.setInteger (NBT.LEVEL,output.get (r));
			pointsList.appendTag (temp);
		}
		newColony.getTagCompound ().setTag (NBT.RESEARCH_POINTS,pointsList);
		return newColony;
	}

	public static ItemStack saveStructure (ItemStack colony,HashMap <IStructure, Integer> structures) {
		ItemStack newColony = colony.copy ();
		NBTTagList structureList = new NBTTagList ();
		for (IStructure r : structures.keySet ()) {
			NBTTagCompound temp = new NBTTagCompound ();
			temp.setString (NBT.NAME,r.getName ());
			temp.setInteger (NBT.LEVEL,structures.get (r));
			structureList.appendTag (temp);
		}
		newColony.getTagCompound ().setTag (NBT.STRUCTURES,structureList);
		return newColony;
	}
}
