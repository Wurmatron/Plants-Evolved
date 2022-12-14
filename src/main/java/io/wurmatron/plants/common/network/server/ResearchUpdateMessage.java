package io.wurmatron.plants.common.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import io.wurmatron.plants.api.PlantsEvolvedAPI;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.common.network.CustomMessage;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;
import io.wurmatron.plants.common.utils.MutiBlockHelper;

import java.io.IOException;

public class ResearchUpdateMessage extends CustomMessage.CustomtServerMessage <ResearchUpdateMessage> {

	private NBTTagCompound data;

	public ResearchUpdateMessage () {
	}

	public ResearchUpdateMessage (IResearch research,int level,BlockPos coreLoc,boolean remove) {
		data = new NBTTagCompound ();
		data.setString (NBT.RESEARCH,research.getName ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {coreLoc.getX (),coreLoc.getY (),coreLoc.getZ ()});
		data.setBoolean (NBT.TYPE,remove);
	}

	public ResearchUpdateMessage (IResearch research,int level,TileHabitatCore tile,boolean remove) {
		data = new NBTTagCompound ();
		data.setString (NBT.RESEARCH,research.getName ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {tile.getPos ().getX (),tile.getPos ().getY (),tile.getPos ().getZ ()});
		data.setBoolean (NBT.TYPE,remove);
	}

	@Override
	protected void read (PacketBuffer buff) throws IOException {
		data = buff.readCompoundTag ();
	}

	@Override
	protected void write (PacketBuffer buff) throws IOException {
		buff.writeCompoundTag (data);
	}

	@Override
	public void process (EntityPlayer player,Side side) {
		int[] coreLoc = data.getIntArray (NBT.POSITION);
		IResearch research = PlantsEvolvedAPI.getResearchFromName (data.getString (NBT.RESEARCH));
		int level = data.getInteger (NBT.LEVEL);
		boolean remove = data.getBoolean (NBT.TYPE);
		BlockPos coreLocation = new BlockPos (coreLoc[0],coreLoc[1],coreLoc[2]);
		if (player.world.getTileEntity (coreLocation) != null && player.world.getTileEntity (coreLocation) instanceof TileHabitatCore) {
			TileHabitatCore core = (TileHabitatCore) player.world.getTileEntity (coreLocation);
			if (core != null) {
				if (remove) {
					core.setResearchPoints (research.getResearchTab (),core.getResearchPoints (research.getResearchTab ()) + MutiBlockHelper.calcPointsForResearch (research,level,MutiBlockHelper.getResearchLevel (core,research)));
				} else
					core.consumeResearchPoints (research.getResearchTab (),MutiBlockHelper.calcPointsForResearch (research,MutiBlockHelper.getResearchLevel (core,research),level));
				core.setResearch (research,level);
			}
		}
	}
}
