package wurmatron.spiritsofthegalaxy.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spiritsofthegalaxy.common.utils.LogHandler;

import java.util.ArrayList;

public class TileHabitatController extends TileMutiBlock implements ITickable {

	public ArrayList <BlockPos> tiles = new ArrayList <> ();

	@Override
	public void update () {
		if (world.getWorldTime () % 20 == 0 && FMLCommonHandler.instance ().getEffectiveSide () == Side.SERVER)
			LogHandler.debug ("Size: " + tiles.size ());
	}
}
