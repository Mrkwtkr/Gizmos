package planetguy.gizmos.industry;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import planetguy.gizmos.Gizmos;
import planetguy.simpleLoader.SLItemBlock;
import planetguy.simpleLoader.SLLoad;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

@SLLoad(name="composter", primacy=5)
public class BlockMultiMachine extends BlockContainer {

	public static Material machinery=new Material(MapColor.ironColor);
	
	public IIcon[] icons=new IIcon[16];
	
	@SLLoad
	public BlockMultiMachine(int id) {
		super(id,machinery);
		this.func_149663_c("gizmos_MultiMachine");
		GameRegistry.registerTileEntity(TileEntityMultiMachine.class,"gizmos_MultiMachine");
		GameRegistry.addRecipe(new ItemStack(this,4,0), new Object[]{
			"i","c",
			Character.valueOf('i'),new ItemStack(Items.iron_ingot),
			Character.valueOf('c'),new ItemStack((Block)Block.field_149771_c.getObject("cauldron)
		});
		for(int i=0; i<16; i++){
			SLItemBlock.registerString(id, i, "Multi-Machine", new String[]{"For all your industrial needs."});
			GameRegistry.addShapelessRecipe(new ItemStack(this,1,i),new ItemStack(this,1,(i+15)%16));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir){
		for(int i=0; i<15; i++){
			icons[i]=ir.registerIcon(Gizmos.modName+":MultiMachine_"+i);
		}
	}
	
	@Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune){
		ArrayList<ItemStack> stacks=new ArrayList<ItemStack>();
		if(world.func_147438_o(x, y, z) instanceof TileEntityMultiMachine)
			stacks.add(new ItemStack(this,metadata));
		return stacks;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
    	return icons[meta];
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMultiMachine();
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = w.func_147438_o(x, y, z);
		if (tileEntity == null 
				||!(tileEntity instanceof TileEntityMultiMachine) 
				|| player.isSneaking()) {
			return false;
		}
		TileEntityMultiMachine composter=(TileEntityMultiMachine) tileEntity;
		if(composter.isValidComposter()){ //center==null means this block is the center.
			player.openGui(Gizmos.instance, 2, w, x, y, z);
			return true;
		}
		return false;
	}

}
