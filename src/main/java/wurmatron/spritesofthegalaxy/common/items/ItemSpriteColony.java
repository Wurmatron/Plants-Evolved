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
import wurmatron.spritesofthegalaxy.api.research.IResearch;
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
		nbt.setDouble (NBT.POPULATION,Settings.startPopulation);
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
		return null;
	}
}
