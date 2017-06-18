package wurmatron.spritesofthegalaxy.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.api.mutiblock.IModuleStorage;
import wurmatron.spritesofthegalaxy.common.module.ModuleHelper;
import wurmatron.spritesofthegalaxy.common.module.ModuleStorage;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import java.util.ArrayList;
import java.util.List;

public class TileHabitatController extends TileMutiBlock implements ITickable {

	private ArrayList <BlockPos> tiles = new ArrayList <> ();
	private static final int CONTROLLER_POP = 1;
	private static final int CONTROLLER_FOOD = 1;
	private int population = 1;

	@Override
	public void update () {
				if (world.getWorldTime () % 20 == 0 && FMLCommonHandler.instance ().getEffectiveSide () == Side.SERVER) {
					LogHandler.debug ("Max Population: " + getMaxPopulation ());
					LogHandler.debug ("Food: " + getFood ());
					LogHandler.debug ("Population: " + population);
					growPopulation ();
				}
	}

	public int getMaxPopulation () {
		int maxPopulation = CONTROLLER_POP;
		if (tiles.size () > 0)
			for (BlockPos pos : tiles)
				if (world.getTileEntity (pos) != null && world.getTileEntity (pos) instanceof TileModule) {
					TileModule tile = (TileModule) world.getTileEntity (pos);
					if (tile != null && tile.module != null && tile.module instanceof IModuleStorage) {
						IModuleStorage moduleStorage = (IModuleStorage) tile.module;
						if (moduleStorage.getStorageType ().equals (ModuleStorage.StorageType.POPULATION))
							maxPopulation += ModuleHelper.adjustStorageForTier (moduleStorage.getBaseStorage (),tile.tier);
					}
				}
		return maxPopulation;
	}

	public int getFood () {
		int food = CONTROLLER_FOOD;
		if (tiles.size () > 0)
			for (BlockPos pos : tiles)
				if (world.getTileEntity (pos) != null && world.getTileEntity (pos) instanceof TileModule) {
					TileModule tile = (TileModule) world.getTileEntity (pos);
					if (tile != null && tile.module != null && tile.module instanceof IModuleStorage) {
						IModuleStorage moduleStorage = (IModuleStorage) tile.module;
						if (moduleStorage.getStorageType ().equals (ModuleStorage.StorageType.FOOD))
							food += ModuleHelper.adjustStorageForTier (moduleStorage.getBaseStorage (),tile.tier);
					}
				}
		food -= population;
		return food;
	}

	public boolean canPopulationGrow () {
		return population < getMaxPopulation () && getFood () > 0;
	}

	public void growPopulation () {
		if (canPopulationGrow ())
			population++;
	}

	public int getPopulation () {
		return population;
	}

	public List<BlockPos> getTiles() {
		return tiles;
	}
}
