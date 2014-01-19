package planetguy.simpleLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.swing.text.html.parser.Entity;

import planetguy.gizmos.Gizmos;
import planetguy.gizmos.gravitybomb.EntityGravityBomb;
import planetguy.util.Debug;



import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**Class containing logic to allow easily-created content modules with dependency management.
 *  
 * @author planetguy
 *
 */


public class SimpleLoader {

	public Class[] moduleClasses; //unfiltered, unsorted classes
	public Class[] filteredSortedClasses;
	public Class[] blocks,items,entities,custom;
	public String modname;
	
	public static File mcdir;

	public ObfuscatedClassHelper OCHelper;

	/**
	 * The mod container class that is starting this SimpleLoader instance
	 */
	private Object modcontainer;

	private List<String> moduleList=new ArrayList<String>();

	/**
	 * IDMap contains all the config data loaded. 
	 */

	private HashMap<String,Integer> IDMap=new HashMap<String,Integer>();
	private int passLimit;
	private boolean generateCode;

	private boolean staticLoading;

	public CodeWriter cw;

	public int lookupInt(String s){
		return IDMap.get(s);
	}

	public SimpleLoader(String modname, SLModContainer modcontainer, Configuration cfg) throws Exception{
		try{
			moduleClasses=discoverSLModules(cfg);
		}catch(Exception e){
			e.printStackTrace();
			moduleClasses=fallbackDiscoverSLModules();
		}
		Arrays.sort(moduleClasses,new Comparator<Class>(){
			@Override
			public int compare(Class paramT1, Class paramT2) {
				return getPrimacy(paramT1)-getPrimacy(paramT2);
			}});
		this.modname=modname;
		this.modcontainer=modcontainer;
		Property prop=cfg.get("[SL] Framework","SL loader",1);
		prop.comment="0=static, 1=dynamic no-gen, 2=dynamic code gen. If using 1 crashes, try 0. 2 is 1 plus a code generator thingy, which you don't need unless you're a developer.";
		int slMode=prop.getInt(1);
		
		Debug.enable=cfg.get("[SL] Framework", "Debug messages", false).getBoolean(false);
		
		staticLoading=slMode==0;
		
		if(staticLoading){
			modcontainer.setStaticLoading(true);
		}else{
			modcontainer.setStaticLoading(false);
			if(slMode==2){
				generateCode=true;
				cw=new CodeWriter(modcontainer);
			}
			initDynamically(cfg);
		}

		//Debug.dbg(formatClasses(moduleClasses));
	}

	public void initDynamically(Configuration cfg){
		blocks=filterClassesBySuper(Block.class);
		items=filterClassesBySuper(Item.class);
		entities=filterClassesBySuper(Entity.class);
		custom=filterClassesBySuper(CustomModuleLoader.class);
		try {
			setupAndReadConfig(cfg);
		} catch (Exception e) {
		}
		filterAndSortClasses();
		if(generateCode){
			cw.classes=Arrays.asList(filteredSortedClasses);
			List<String> classnames=new ArrayList<String>();
			for(Class c:filteredSortedClasses){
				classnames.add(getModuleName(c));
			}
			try{
				cw.writeLoaderClass(classnames);
			}catch(Exception e){
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	/**Utility method to get the module name of a class
	 * 
	 * @param c class to get module name from
	 * @return name of module as declared in its @SLLoad annotation
	 */

	private String getModuleName(Class c){
		return getSLL(c).name();
	}
	
	private int getPrimacy(Class c){
		return getSLL(c).primacy();
	}

	private SLLoad getSLL(Class c){
		return (SLLoad) c.getAnnotation(SLLoad.class);
	}

	/**
	 * Nicely formats class names for printing.
	 */
	private String formatClasses(Class[] classes){ 
		String s="[";
		for(Class c:classes){
			s+=c.getName()+",";
		}s+="]";
		return s;
	}

	/**
	 * 
	 * @return the names of modules to be loaded
	 */

	public String[] getModuleNames(){
		LinkedList<String> moduleNames=new LinkedList<String>();
		for(Class c:moduleClasses){
			moduleNames.add(getModuleName(c));
		}
		return moduleNames.toArray(new String[0]);
	}

	/**
	 * Gets Forge config options for SL framework stuff, properties marked to fill with @SLProp and any IDs needed by modules
	 * @param config passed from the Forge
	 * @throws Exception if anything goes wrong
	 */

	private void setupAndReadConfig(Configuration config) throws Exception{
		if(staticLoading)return;
		for(int i=0; i<moduleClasses.length; i++){
			boolean show=!(getSLL(moduleClasses[i]).isTechnical());
			if(!show||config.get("[SL] Item-restrict","allow '"+moduleList.get(i)+"'",true).getBoolean(true))
				moduleList.add(getModuleName(moduleClasses[i]));
		}
		Debug.dbg("[SL] Modules: "+moduleList);
		//moduleList=Arrays.asList(config.get("[SL] Framework","List of allowed modules", moduleList.toArray(new String[0])).getStringList());
		passLimit=config.get("[SL] Framework", "Maximum dependency passes", 10).getInt(10);

		//and get config values for the game content...
		int currentID=3980;
		for(Class c:blocks){ //go through blocks and ask the config for an ID for each
			int id=config.getBlock(getModuleName(c), currentID).getInt(currentID);
			IDMap.put(getModuleName(c), id);
			++currentID;
			//Debug.dbg(currentID);
		}
		currentID=8100;
		for(Class c:items){ //do the same for items
			int id=config.getItem(getModuleName(c), currentID).getInt(currentID);
			IDMap.put(getModuleName(c), id);
			++currentID;
		}

		currentID=201;
		for(Class c:entities){//and entities
			int id=config.get("Entities", getModuleName(c), currentID).getInt(currentID);
			++currentID;
		}

		for(Class c:custom){
			//Debug.dbg(c.getName());
			CustomModuleLoader cml=(CustomModuleLoader) c.newInstance();
			cml.load();
		}
		for(Class c:moduleClasses){
			for(Field f:c.getDeclaredFields()){
				SLProp prop=f.getAnnotation(SLProp.class);
				if(prop!=null){
					String category=prop.category();
					String key=prop.name();
					Object defaultVal=f.get(null); //do field operations with null, assuming static field.
					//Get config props for items that request them.
					if(f.getGenericType().equals(Integer.TYPE)){
						f.setInt(null, config.get(category, key, (Integer) defaultVal).getInt());
					}else if(f.getGenericType().equals(Double.TYPE)){
						f.setDouble(null, config.get(category, key, (Double) defaultVal).getDouble((Double) defaultVal));
					}else if(f.getGenericType().toString()=="class java.lang.String"){
						f.set(null, config.get(category, key, (String) defaultVal).getString());
					}else if(f.getGenericType().toString()=="class [Ljava.lang.String;"){
						f.set(null, config.get(category, key, (String[]) defaultVal).getStringList());
					}else if(f.getGenericType().toString()=="class [I"){
						f.set(null, config.get(category, key, (int[]) defaultVal).getIntList());
					}else if(f.getGenericType().toString()=="class [D"){
						f.set(null, config.get(category, key, (double[]) defaultVal).getDoubleList());
					}

				}
			}
		}
	}

	public void filterAndSortClasses(){
		if(staticLoading)return;

		int pass=0; //How many passes the dependency manager has gone over the class list.
		ArrayList<Class> sortedClasses=new ArrayList<Class>();
		HashSet<String> loadedClasses=new HashSet<String>();
		ArrayDeque<Class> classes=new ArrayDeque<Class>();
		for(Class c:moduleClasses){
			SLLoad sll=(SLLoad) c.getAnnotation(SLLoad.class);
			if(moduleList.contains(sll.name())){
				classes.add(c);
			}
		}
		classes.addLast(SimpleLoader.class);
		int seq=0;
		while(!classes.isEmpty()&&pass<passLimit&&seq<1000){
			++seq;
			Class c=classes.pop();
			if(c==SimpleLoader.class){
				++pass; 
				classes.addLast(SimpleLoader.class);
				continue;//Don't get SLLoad from SimpleLoader, it would explode!
			}
			SLLoad slload=(SLLoad) c.getAnnotation(SLLoad.class);
			boolean allowSoFar=true;
			for(String s:slload.dependencies()){
				if(!loadedClasses.contains(s)){
					allowSoFar=false;
					break;
				}
			}
			if(allowSoFar){
				loadedClasses.add(slload.name());
				sortedClasses.add(c);
			}else{
				classes.addLast(c);
			}
		}
		filteredSortedClasses=sortedClasses.toArray(new Class[0]);
		Debug.dbg("Classes not loaded: "+classes.toString());
	}

	/**A way to filter classes by superclass (get anything extending, for example, Block)
	 * 
	 * @param superclass the class to filter by
	 * @return classes that inherit the superclass
	 */

	private Class[] filterClassesBySuper(Class superclass){
		ArrayList<Class> classes=new ArrayList<Class>();
		for(Class c:moduleClasses){
			if(superclass.isAssignableFrom(c)){
				classes.add(c);
			}
		}
		return classes.toArray(new Class[0]);
	}

	/**A way to load a single class with @SLLoad. DANGER: BYPASSES DEPENDENCY MANAGEMENT!
	 * 
	 * @param c
	 * @throws Exception
	 */

	public void loadClass(Class c) throws Exception{
		if(staticLoading)return;
		if(Block.class.isAssignableFrom(c)){
			loadBlock(c);
		}else if(Item.class.isAssignableFrom(c)){
			loadItem(c);
		}else if(Entity.class.isAssignableFrom(c)){
			loadEntity(c);
		}else if(CustomModuleLoader.class.isAssignableFrom(c)){
			loadCustomModule(c);
		}
	}

	/**
	 * Loads all the classes in SL's arrays of classes to load
	 * @throws Exception
	 */

	public void loadClasses() throws Exception{
		if(staticLoading)return;
		
		//Items and blocks must not have primacies of 0. Should fix unstable configs.
		for(Class c:blocks){assert(getPrimacy(c)!=0);} 
		for(Class c:items){assert(getPrimacy(c)!=0);} 
		
		Debug.dbg("[SL] Loading classes...");
		for(Class c:filteredSortedClasses){
			loadClass(c);
		}
		/*
		Debug.dbg("[SL] Loading blocks...");
		for(Class c:blocks){
			loadBlock(c);
		}
		Debug.dbg("[SL] Loading items...");
		for(Class c:items){
			loadItem(c);
		}
		Debug.dbg("[SL] Loading entities...");
		for(Class c:entities){
			loadEntity(c);
		}
		Debug.dbg("[SL] Loading custom modules...");
		for(Class c:custom){
			loadCustomModule(c);
		}*/
	}

	private void loadCustomModule(Class c)throws Exception{
		Debug.dbg("[SL] Loading "+c.getName());
		CustomModuleLoader cml=(CustomModuleLoader) c.newInstance();
		Field f=modcontainer.getClass().getDeclaredField(getModuleName(c));
		f.set(modcontainer,cml);
		cml.load();
	}

	private void loadEntity(Class c){
		Debug.dbg("[SL] Loading "+c.getName());
		EntityRegistry.registerModEntity(c, getModuleName(c), IDMap.get(getModuleName(c)), Gizmos.instance, 80, 3, true);
	}

	private void loadItem(Class c) throws Exception{
		Debug.dbg("[SL] Loading "+c.getName());
		Object item = null;
		Constructor[] cons=c.getConstructors();
		for(Constructor con : cons){
			if(con.isAnnotationPresent(SLLoad.class)){
				item=con.newInstance(IDMap.get(getModuleName(c)));
				GameRegistry.registerItem((Item)item, modname+"."+getModuleName(c));
				Field f=modcontainer.getClass().getDeclaredField(getModuleName(c));
				f.set(modcontainer,item);
			}
		}
		for(Method m:c.getMethods()){
			if(m.isAnnotationPresent(SLLoad.class)){
				m.invoke(item);
			}
		}
	}

	private void loadBlock(Class c) throws Exception{
		Debug.dbg("[SL] Loading "+c.getName());
		SLLoad slload=(SLLoad) c.getAnnotation(SLLoad.class);
		Object block = null;
		Constructor[] cons=c.getConstructors();
		for(Constructor con : cons){
			if(con.isAnnotationPresent(SLLoad.class)){
				try{
					block=con.newInstance(lookupInt(getModuleName(c)));
					Field f=modcontainer.getClass().getDeclaredField(getModuleName(c));
					f.set(modcontainer,block);
				}catch(Exception e){
					e.printStackTrace();
					Debug.dbg(c.getName());
					throw(e);
				}

				GameRegistry.registerBlock((Block) block, 
						(Class<? extends ItemBlock>)
						(slload.itemClass()!="net.minecraft.item.ItemBlockWithMetadata"? 
								Class.forName(slload.itemClass()) 
								: ItemBlock.class),
								modname+"."+getModuleName(c));
			}
		}
		for(Method m:c.getMethods()){
			if(m.isAnnotationPresent(SLLoad.class)){
				m.invoke(block);
			}
		}
	}

	/**
	 * 
	 * @return the classes with source attached in the same directory
	 */
	private Class[] discoverSLModules(Configuration conf) throws Exception{
		Field fileLoc=Configuration.class.getDeclaredField("file");
		fileLoc.setAccessible(true);
		mcdir=((File) fileLoc.get(conf)).getParentFile().getParentFile();

		/*try{
			mcdir=Minecraft.getMinecraft().mcDataDir;
		}catch(NullPointerException e){
			try{
				mcdir=MinecraftServer.getServer().getFile("mods");
			}catch(NullPointerException npe){
				try{
					mcdir=new File(conf.get("[SL] Framework", "FS path", "").getString());
				}catch(Exception x){
					mcdir=null;
				}
			}
		}*/
		String pathToDir=mcdir.getAbsolutePath(); 
		List<String> filenames=new ArrayList<String>(); 

		Debug.dbg("[SL] MC dir: "+mcdir.getAbsolutePath());

		File[] alldirs=mcdir.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return dir.isDirectory();
			}
		});
		//Add all classes either 2 or 3 folders deep in the mod zip file to the list of class names

		Debug.dbg("[SL] Got mods dir");
		for(File modfolder:alldirs){
			if(!modfolder.isDirectory())continue;
			for(File modzip:modfolder.listFiles()){
				if(!modzip.isDirectory())continue;
				for(File g:modzip.listFiles()){
					if(!g.isDirectory())continue;
					for(File h:g.listFiles()){
						if(!h.isDirectory())continue;
						for(File maybeclass:h.listFiles()){						
							if(maybeclass.getName().endsWith(".java")){//filter for source files
								filenames.add(g.getName()+"."+h.getName()+"."+maybeclass.getName().replaceAll("\\.java", ""));
							}
							if(!maybeclass.isDirectory())continue;
							for(File level2classes:maybeclass.listFiles()){
								if(level2classes.getName().endsWith(".java")){
									filenames.add(g.getName()+"."+h.getName()+"."+maybeclass.getName()+"."+level2classes.getName().replaceAll("\\.java", ""));
								}
							}


						}
					}
				}
			}
		}

		//Debug.dbg(filenames); //debug
		Iterator<String> i=filenames.iterator();

		//collect all the class names from the list
		LinkedList<Class> classesFound=new LinkedList<Class>();

		Debug.dbg(filenames.toString());

		while(i.hasNext()){
			try{ //if it isn't possible to load a class from a name, ignore the name
				String classname=i.next();

				if(!classname.startsWith("planetguy"))continue;//only in packages planetguy.* Change?
				Class c=Class.forName(classname);
				//Debug.dbg("[SL] class "+c.getName()+", "+c.getAnnotation(SLLoad.class));
				if(c.getAnnotation(SLLoad.class)!=null){ //if it isn't marked @SLLoad ignore it

					classesFound.add(c);
					moduleList.add(classname);
				}
			}catch(ClassNotFoundException cnfe){continue;}//if there's a problem go on to next class
		}
		Class[] result=classesFound.toArray(new Class[0]);
		for(Class c:result){
			Debug.dbg(c.toString()+"\",\"");
		}
		//Debug.dbg(classesFound);//debug

		return result;
	}

	public Class[] fallbackDiscoverSLModules() throws ClassNotFoundException{
		return null;
	}


}