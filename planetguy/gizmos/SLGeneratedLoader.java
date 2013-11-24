package planetguy.gizmos;

import net.minecraftforge.common.Configuration;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
/*This class has been automatically generated by SimpleLoader.
Note that it may have errors that require manual correction.
To help with this, known errors are flagged with "!!!".
Also note that the code generated may be difficult to read.
Sorry about that, but that's how the cookie crumbles.*/

public class SLGeneratedLoader{

private static HashMap<String, Integer> idMap=new HashMap<String,Integer>();
private static List<String> moduleList=new ArrayList(Arrays.asList(new String[]{
"entityArrowNova"
,"entityTunnelBomb"
,"entityGravityBomb"
,"eventHandler"
,"flowerFix"
,"anyShapePortals"
,"Lens"
,"CESBomb"
,"GravityBomb"
,"RedstoneResponsiveWool"
,"accelerator"
,"fireExtinguisher"
,"composter"
,"forestFire"
,"temporalDislocator"
,"inserter"
,"arrowNova"
,"luncher"
,"invenswapperTop"
,"launcher"
,"superFire"
,"telekinesisCatalyst"
,"timeBombs"
,"bombDefuser"
,"buildTool"
,"deforestator"
,"minersLighter"
,"invenswapper"
}));

public static void setupConfigs(Configuration config){
if(!config.get("[SL] Item-restrict","allow 'entityArrowNova'",true).getBoolean(true))
moduleList.remove("entityArrowNova");
if(!config.get("[SL] Item-restrict","allow 'entityTunnelBomb'",true).getBoolean(true))
moduleList.remove("entityTunnelBomb");
if(!config.get("[SL] Item-restrict","allow 'entityGravityBomb'",true).getBoolean(true))
moduleList.remove("entityGravityBomb");
if(!config.get("[SL] Item-restrict","allow 'eventHandler'",true).getBoolean(true))
moduleList.remove("eventHandler");
if(!config.get("[SL] Item-restrict","allow 'flowerFix'",true).getBoolean(true))
moduleList.remove("flowerFix");
if(!config.get("[SL] Item-restrict","allow 'anyShapePortals'",true).getBoolean(true))
moduleList.remove("anyShapePortals");
if(!config.get("[SL] Item-restrict","allow 'Lens'",true).getBoolean(true))
moduleList.remove("Lens");
if(!config.get("[SL] Item-restrict","allow 'CESBomb'",true).getBoolean(true))
moduleList.remove("CESBomb");
if(!config.get("[SL] Item-restrict","allow 'GravityBomb'",true).getBoolean(true))
moduleList.remove("GravityBomb");
if(!config.get("[SL] Item-restrict","allow 'RedstoneResponsiveWool'",true).getBoolean(true))
moduleList.remove("RedstoneResponsiveWool");
if(!config.get("[SL] Item-restrict","allow 'accelerator'",true).getBoolean(true))
moduleList.remove("accelerator");
if(!config.get("[SL] Item-restrict","allow 'fireExtinguisher'",true).getBoolean(true))
moduleList.remove("fireExtinguisher");
if(!config.get("[SL] Item-restrict","allow 'composter'",true).getBoolean(true))
moduleList.remove("composter");
if(!config.get("[SL] Item-restrict","allow 'forestFire'",true).getBoolean(true))
moduleList.remove("forestFire");
if(!config.get("[SL] Item-restrict","allow 'temporalDislocator'",true).getBoolean(true))
moduleList.remove("temporalDislocator");
if(!config.get("[SL] Item-restrict","allow 'inserter'",true).getBoolean(true))
moduleList.remove("inserter");
if(!config.get("[SL] Item-restrict","allow 'arrowNova'",true).getBoolean(true))
moduleList.remove("arrowNova");
if(!config.get("[SL] Item-restrict","allow 'luncher'",true).getBoolean(true))
moduleList.remove("luncher");
if(!config.get("[SL] Item-restrict","allow 'invenswapperTop'",true).getBoolean(true))
moduleList.remove("invenswapperTop");
if(!config.get("[SL] Item-restrict","allow 'launcher'",true).getBoolean(true))
moduleList.remove("launcher");
if(!config.get("[SL] Item-restrict","allow 'superFire'",true).getBoolean(true))
moduleList.remove("superFire");
if(!config.get("[SL] Item-restrict","allow 'telekinesisCatalyst'",true).getBoolean(true))
moduleList.remove("telekinesisCatalyst");
if(!config.get("[SL] Item-restrict","allow 'timeBombs'",true).getBoolean(true))
moduleList.remove("timeBombs");
if(!config.get("[SL] Item-restrict","allow 'bombDefuser'",true).getBoolean(true))
moduleList.remove("bombDefuser");
if(!config.get("[SL] Item-restrict","allow 'buildTool'",true).getBoolean(true))
moduleList.remove("buildTool");
if(!config.get("[SL] Item-restrict","allow 'deforestator'",true).getBoolean(true))
moduleList.remove("deforestator");
if(!config.get("[SL] Item-restrict","allow 'minersLighter'",true).getBoolean(true))
moduleList.remove("minersLighter");
if(!config.get("[SL] Item-restrict","allow 'invenswapper'",true).getBoolean(true))
moduleList.remove("invenswapper");
planetguy.sltweaks.FlowerFix.redFlowerWeight=config.get("Details","redFlowerWeight",planetguy.sltweaks.FlowerFix.redFlowerWeight).getInt(planetguy.sltweaks.FlowerFix.redFlowerWeight);
planetguy.sltweaks.FlowerFix.yellowFlowerWeight=config.get("Details","yellowFlowerWeight",planetguy.sltweaks.FlowerFix.yellowFlowerWeight).getInt(planetguy.sltweaks.FlowerFix.yellowFlowerWeight);
planetguy.sltweaks.PortalLiberator.sizeLimit=config.get("Details","portalSizeLimit",planetguy.sltweaks.PortalLiberator.sizeLimit).getInt(planetguy.sltweaks.PortalLiberator.sizeLimit);
planetguy.gizmos.motiontools.BlockAccelerator.accelRate=config.get("Details","acceleratorRate",planetguy.gizmos.motiontools.BlockAccelerator.accelRate).getDouble(planetguy.gizmos.motiontools.BlockAccelerator.accelRate);
planetguy.gizmos.inserter.BlockInserter.doBlockDamage=config.get("Details","doBlockDamage",planetguy.gizmos.inserter.BlockInserter.doBlockDamage).getBoolean(planetguy.gizmos.inserter.BlockInserter.doBlockDamage);
planetguy.gizmos.inserter.BlockInserter.nerfHiding=config.get("Details","limitQuantityHideable",planetguy.gizmos.inserter.BlockInserter.nerfHiding).getBoolean(planetguy.gizmos.inserter.BlockInserter.nerfHiding);
planetguy.gizmos.invUtils.ItemLuncher.MAX_FOOD_CARRIED=config.get("Details","Luncher capacity, in half food units",planetguy.gizmos.invUtils.ItemLuncher.MAX_FOOD_CARRIED).getInt(planetguy.gizmos.invUtils.ItemLuncher.MAX_FOOD_CARRIED);
planetguy.gizmos.motiontools.BlockLauncher.launcherPower=config.get("Details","launcherPower",planetguy.gizmos.motiontools.BlockLauncher.launcherPower).getDouble(planetguy.gizmos.motiontools.BlockLauncher.launcherPower);
planetguy.gizmos.invUtils.BlockTelekinesisCatalyst.maxBlockReach=config.get("Details","telekinesisCatalystReach",planetguy.gizmos.invUtils.BlockTelekinesisCatalyst.maxBlockReach).getInt(planetguy.gizmos.invUtils.BlockTelekinesisCatalyst.maxBlockReach);
planetguy.gizmos.timebomb.BlockTimeBomb.fuse=config.get("Details","timeBombFuse",planetguy.gizmos.timebomb.BlockTimeBomb.fuse).getInt(planetguy.gizmos.timebomb.BlockTimeBomb.fuse);
planetguy.gizmos.tool.ItemBombDefuser.explosivesID=config.get("Details","explosivesID",planetguy.gizmos.tool.ItemBombDefuser.explosivesID)/*!!!*/.getIntList();

idMap.put("entityArrowNova.entityID",config.get("Entities","entityArrowNova",201).getInt(201));
idMap.put("entityTunnelBomb.entityID",config.get("Entities","entityTunnelBomb",202).getInt(202));
idMap.put("entityGravityBomb.entityID",config.get("Entities","entityGravityBomb",203).getInt(203));
idMap.put("Lens.itemID",config.getItem("Lens",8100).getInt(8100));
idMap.put("CESBomb.blockID",config.getBlock("CESBomb",3980).getInt(3980));
idMap.put("GravityBomb.blockID",config.getBlock("GravityBomb",3981).getInt(3981));
idMap.put("RedstoneResponsiveWool.blockID",config.getBlock("RedstoneResponsiveWool",3982).getInt(3982));
idMap.put("accelerator.blockID",config.getBlock("accelerator",3983).getInt(3983));
idMap.put("fireExtinguisher.itemID",config.getItem("fireExtinguisher",8101).getInt(8101));
idMap.put("composter.blockID",config.getBlock("composter",3984).getInt(3984));
idMap.put("forestFire.blockID",config.getBlock("forestFire",3985).getInt(3985));
idMap.put("temporalDislocator.itemID",config.getItem("temporalDislocator",8102).getInt(8102));
idMap.put("inserter.blockID",config.getBlock("inserter",3986).getInt(3986));
idMap.put("arrowNova.itemID",config.getItem("arrowNova",8103).getInt(8103));
idMap.put("luncher.itemID",config.getItem("luncher",8104).getInt(8104));
idMap.put("invenswapperTop.blockID",config.getBlock("invenswapperTop",3987).getInt(3987));
idMap.put("launcher.blockID",config.getBlock("launcher",3988).getInt(3988));
idMap.put("superFire.blockID",config.getBlock("superFire",3989).getInt(3989));
idMap.put("telekinesisCatalyst.blockID",config.getBlock("telekinesisCatalyst",3990).getInt(3990));
idMap.put("timeBombs.blockID",config.getBlock("timeBombs",3991).getInt(3991));
idMap.put("bombDefuser.itemID",config.getItem("bombDefuser",8105).getInt(8105));
idMap.put("buildTool.itemID",config.getItem("buildTool",8106).getInt(8106));
idMap.put("deforestator.itemID",config.getItem("deforestator",8107).getInt(8107));
idMap.put("minersLighter.itemID",config.getItem("minersLighter",8108).getInt(8108));
idMap.put("invenswapper.blockID",config.getBlock("invenswapper",3992).getInt(3992));
}

public static void loadThings(){
if(moduleList.contains("entityArrowNova")){
EntityRegistry.registerModEntity(planetguy.gizmos.tool.EntityArrowNova.class,"entityArrowNova",idMap.get("entityArrowNova.entityID"),Gizmos.instance,80,3,true);
}
if(moduleList.contains("entityTunnelBomb")){
EntityRegistry.registerModEntity(planetguy.gizmos.gravitybomb.EntityTunnelBomb.class,"entityTunnelBomb",idMap.get("entityTunnelBomb.entityID"),Gizmos.instance,80,3,true);
}
if(moduleList.contains("entityGravityBomb")){
EntityRegistry.registerModEntity(planetguy.gizmos.gravitybomb.EntityGravityBomb.class,"entityGravityBomb",idMap.get("entityGravityBomb.entityID"),Gizmos.instance,80,3,true);
}
if(moduleList.contains("eventHandler")){
planetguy.gizmos.Gizmos.eventHandler=new planetguy.gizmos.GizmosEventWatcher();
planetguy.gizmos.Gizmos.eventHandler.load();
}
if(moduleList.contains("flowerFix")){
planetguy.gizmos.Gizmos.flowerFix=new planetguy.sltweaks.FlowerFix();
planetguy.gizmos.Gizmos.flowerFix.load();
}
if(moduleList.contains("anyShapePortals")){
planetguy.gizmos.Gizmos.anyShapePortals=new planetguy.sltweaks.PortalLiberator();
planetguy.gizmos.Gizmos.anyShapePortals.load();
}
if(moduleList.contains("Lens")){
planetguy.gizmos.Gizmos.Lens=new planetguy.gizmos.inserter.ItemLens(idMap.get("Lens.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.Lens,"Gizmos.Lens");
}
if(moduleList.contains("CESBomb")){
planetguy.gizmos.Gizmos.CESBomb=new planetguy.gizmos.CES.BlockCESBomb(idMap.get("CESBomb.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.CESBomb,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.CESBomb");
}
if(moduleList.contains("GravityBomb")&&moduleList.contains("entityGravityBomb")&&moduleList.contains("entityTunnelBomb")){
planetguy.gizmos.Gizmos.GravityBomb=new planetguy.gizmos.gravitybomb.BlockGraviBomb(idMap.get("GravityBomb.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.GravityBomb,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.GravityBomb");
}
if(moduleList.contains("RedstoneResponsiveWool")){
planetguy.gizmos.Gizmos.RedstoneResponsiveWool=new planetguy.gizmos.randomblock.BlockDynamicWool(idMap.get("RedstoneResponsiveWool.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.RedstoneResponsiveWool,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.RedstoneResponsiveWool");
}
if(moduleList.contains("accelerator")){
planetguy.gizmos.Gizmos.accelerator=new planetguy.gizmos.motiontools.BlockAccelerator(idMap.get("accelerator.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.accelerator,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.accelerator");
}
if(moduleList.contains("fireExtinguisher")){
planetguy.gizmos.Gizmos.fireExtinguisher=new planetguy.gizmos.tool.ItemFireExtinguisher(idMap.get("fireExtinguisher.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.fireExtinguisher,"Gizmos.fireExtinguisher");
}
if(moduleList.contains("composter")){
planetguy.gizmos.Gizmos.composter=new planetguy.gizmos.multiblock.BlockMultiMachine(idMap.get("composter.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.composter,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.composter");
}
if(moduleList.contains("forestFire")){
planetguy.gizmos.Gizmos.forestFire=new planetguy.gizmos.tool.BlockForestFire(idMap.get("forestFire.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.forestFire,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.forestFire");
}
if(moduleList.contains("temporalDislocator")){
planetguy.gizmos.Gizmos.temporalDislocator=new planetguy.gizmos.tool.ItemBlockTicker(idMap.get("temporalDislocator.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.temporalDislocator,"Gizmos.temporalDislocator");
}
if(moduleList.contains("inserter")){
planetguy.gizmos.Gizmos.inserter=new planetguy.gizmos.inserter.BlockInserter(idMap.get("inserter.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.inserter,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.inserter");
}
if(moduleList.contains("arrowNova")&&moduleList.contains("entityArrowNova")){
planetguy.gizmos.Gizmos.arrowNova=new planetguy.gizmos.tool.ItemArrowNova(idMap.get("arrowNova.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.arrowNova,"Gizmos.arrowNova");
}
if(moduleList.contains("luncher")){
planetguy.gizmos.Gizmos.luncher=new planetguy.gizmos.invUtils.ItemLuncher(idMap.get("luncher.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.luncher,"Gizmos.luncher");
}
if(moduleList.contains("invenswapperTop")){
planetguy.gizmos.Gizmos.invenswapperTop=new planetguy.gizmos.invUtils.BlockInvenswapperTop(idMap.get("invenswapperTop.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.invenswapperTop,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.invenswapperTop");
}
if(moduleList.contains("launcher")){
planetguy.gizmos.Gizmos.launcher=new planetguy.gizmos.motiontools.BlockLauncher(idMap.get("launcher.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.launcher,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.launcher");
}
if(moduleList.contains("superFire")){
planetguy.gizmos.Gizmos.superFire=new planetguy.gizmos.tool.BlockSuperFire(idMap.get("superFire.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.superFire,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.superFire");
}
if(moduleList.contains("telekinesisCatalyst")){
planetguy.gizmos.Gizmos.telekinesisCatalyst=new planetguy.gizmos.invUtils.BlockTelekinesisCatalyst(idMap.get("telekinesisCatalyst.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.telekinesisCatalyst,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.telekinesisCatalyst");
}
if(moduleList.contains("timeBombs")){
planetguy.gizmos.Gizmos.timeBombs=new planetguy.gizmos.timebomb.BlockTimeBomb(idMap.get("timeBombs.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.timeBombs,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.timeBombs");
}
if(moduleList.contains("bombDefuser")&&moduleList.contains("Lens")&&moduleList.contains("GravityBomb")&&moduleList.contains("timeBombs")){
planetguy.gizmos.Gizmos.bombDefuser=new planetguy.gizmos.tool.ItemBombDefuser(idMap.get("bombDefuser.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.bombDefuser,"Gizmos.bombDefuser");
}
if(moduleList.contains("buildTool")&&moduleList.contains("inserter")){
planetguy.gizmos.Gizmos.buildTool=new planetguy.gizmos.tool.ItemBuildTool(idMap.get("buildTool.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.buildTool,"Gizmos.buildTool");
}
if(moduleList.contains("deforestator")&&moduleList.contains("forestFire")){
planetguy.gizmos.Gizmos.deforestator=new planetguy.gizmos.tool.ItemDeforester(idMap.get("deforestator.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.deforestator,"Gizmos.deforestator");
}
if(moduleList.contains("minersLighter")&&moduleList.contains("superFire")){
planetguy.gizmos.Gizmos.minersLighter=new planetguy.gizmos.tool.ItemMinersLighter(idMap.get("minersLighter.itemID"));
GameRegistry.registerItem(planetguy.gizmos.Gizmos.minersLighter,"Gizmos.minersLighter");
}
if(moduleList.contains("invenswapper")&&moduleList.contains("invenswapperTop")){
planetguy.gizmos.Gizmos.invenswapper=new planetguy.gizmos.invUtils.BlockInvenswapperBase(idMap.get("invenswapper.blockID"));
GameRegistry.registerBlock(planetguy.gizmos.Gizmos.invenswapper,planetguy.simpleLoader.SLItemBlock.class,"Gizmos.invenswapper");
}
}
}
