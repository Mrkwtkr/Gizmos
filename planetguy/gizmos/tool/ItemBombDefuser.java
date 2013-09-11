package planetguy.gizmos.tool;

import planetguy.gizmos.Gizmos;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Something to counter fork bombs.
 * @author planetguy
 *
 */
public class ItemBombDefuser extends Item{

	//TODO read from config
	private int[] explosivesID;
	
	public ItemBombDefuser(int par1) {
		super(par1);
	}
	
	public void registerIcons(IconRegister ir){
		itemIcon=ir.registerIcon(Gizmos.modName+":"+"defuser");
	}

	//Removes any block at the specified coordinates if it's in the config as defusable or if it uses Material.tnt
	public boolean onItemUse(ItemStack stack, EntityPlayer thePlayer, World w, int x, int y, int z, int par7, float par8, float par9, float par10){
		if(w.getBlockMaterial(x, y, z)==Material.tnt){
			defuse(w,x,y,z,thePlayer,stack);
			return true;
		}
		int id=w.getBlockId(x, y, z);
		for(int i=0; i<Gizmos.defuseableIDs.length;i++){
			if(Gizmos.defuseableIDs[i]==id&&thePlayer.isSneaking()){
	            defuse(w,x,y,z,thePlayer,stack);
				return true;
			}
		}
		return false;
	}
	
	//Damages item and removes target block
	private void defuse(World w, int x, int y, int z, EntityPlayer p, ItemStack stk){
		stk.damageItem(1, p);
		w.setBlockToAir(x, y, z);
	}
 

}
