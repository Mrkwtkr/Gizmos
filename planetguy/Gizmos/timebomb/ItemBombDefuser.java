package planetguy.Gizmos.timebomb;

import planetguy.Gizmos.ConfigHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBombDefuser extends Item{

	private int[] explosivesID;
	
	public ItemBombDefuser(int par1) {
		super(par1);
		setIconIndex(9);
		// TODO Auto-generated constructor stub
	}
	
	public String getTextureFile(){
		  return "/planetguy/Gizmos/tex.png";
	}

	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer thePlayer, World w, int x, int y, int z, int par7, float par8, float par9, float par10){
		int id=w.getBlockId(x, y, z);
		for(int i=0; i<ConfigHolder.defuseableIDs.length;i++){
			if(ConfigHolder.defuseableIDs[i]==id&&thePlayer.isSneaking()){
	            par1ItemStack.damageItem(1, thePlayer);
				w.setBlock(x, y, z,0);
				return true;
			}
		}
		return false;
	}
 

}
