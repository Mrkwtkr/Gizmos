package planetguy.Gizmos.spy;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import planetguy.Gizmos.ContentLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockInserter extends Block{
	

    public BlockInserter (int id, int texture) {
        super(id, Material.iron);
        //System.out.println("New spy lab!");
        setHardness(2.0F);
        setResistance(5.0F);
        setBlockName("blockSpyLab");
        setCreativeTab(CreativeTabs.tabTools);
    }
    
	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if(par1==1){
			return 8;
		}else{
			return 6;
		}
	}
	

    public boolean onBlockActivated(World world, int x, int y, int z,
                EntityPlayer player, int idk, float what, float these, float are) {
        //code to open gui explained later
        player.openGui(ContentLoader.instance, 0, world, x, y, z);
        return true;
    }

	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        //dropItems(world, x, y, z);
	}
	
	public String getTextureFile(){
		  return "/planetguy/Gizmos/tex.png";
	}
	

}
