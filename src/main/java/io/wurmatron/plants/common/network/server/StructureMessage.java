package io.wurmatron.plants.common.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import io.wurmatron.plants.api.PlantsEvolvedAPI;
import io.wurmatron.plants.api.mutiblock.IStructure;
import io.wurmatron.plants.common.network.CustomMessage;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;
import io.wurmatron.plants.common.utils.MutiBlockHelper;

import java.io.IOException;

public class StructureMessage extends CustomMessage.CustomtServerMessage <StructureMessage> {

	private NBTTagCompound data;

	public StructureMessage () {
	}

	public StructureMessage (IStructure research,int level,TileHabitatCore core,boolean remove) {
		data = new NBTTagCompound ();
		data.setString (NBT.STRUCTURES,research.getName ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {core.getPos ().getX (),core.getPos ().getY (),core.getPos ().getZ ()});
		data.setBoolean (NBT.TYPE,remove);
	}

	public StructureMessage (IStructure research,int level,BlockPos coreLoc,boolean remove) {
		data = new NBTTagCompound ();
		data.setString (NBT.STRUCTURES,research.getName ());
		data.setInteger (NBT.LEVEL,level);
		data.setIntArray (NBT.POSITION,new int[] {coreLoc.getX (),coreLoc.getY (),coreLoc.getZ ()});
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
		IStructure structure = PlantsEvolvedAPI.getStructureFromName (data.getString (NBT.STRUCTURES));
		int tier = data.getInteger (NBT.LEVEL);
		boolean remove = data.getBoolean (NBT.TYPE);
		BlockPos coreLocation = new BlockPos (coreLoc[0],coreLoc[1],coreLoc[2]);
		if (player.world.getTileEntity (coreLocation) != null && player.world.getTileEntity (coreLocation) instanceof TileHabitatCore) {
			TileHabitatCore core = (TileHabitatCore) player.world.getTileEntity (coreLocation);
			if (core != null) {
				if (remove) {
					core.addColonyValue (NBT.MINERALS,MutiBlockHelper.calcMineralsForStructure (structure,tier,MutiBlockHelper.getStructureLevel (core,structure),0));
					core.removeStructure (structure);
					core.addStructure (structure,tier);

				} else {
					core.consumeColonyValue (NBT.MINERALS,MutiBlockHelper.calcMineralsForStructure (structure,MutiBlockHelper.getStructureLevel (core,structure),tier,0));
					if (tier - MutiBlockHelper.getStructureLevel (core,structure) > 1) {
						for(int index = 0; index < (tier - MutiBlockHelper.getStructureLevel (core,structure)); index++)
							core.buildStructure (structure, MutiBlockHelper.getStructureLevel (core,structure) + index);
					} else
						core.buildStructure (structure,tier);
				}
			}
		}
	}
}
