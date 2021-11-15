package me.JayMar921.Warps;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiHandler {

	private Main plugin;
	
	private Inventory gui;
	public Material gui_bg;
	
	private Inventory gui_dev;
	
	private ChatColor color = ChatColor.AQUA;
	private ChatColor bold = ChatColor.AQUA;
	private String server = color+"Custom Warps";
	
	Map<Integer, Location> locations = new HashMap<Integer, Location>();
	
	private int slot = 9;
	
	public GuiHandler(Main plugin) {
		this.plugin = plugin;
		init_();
	}
	
	//INITIALIZE
	public void init_() {
		if(plugin.getConfig().contains("ROWS")) {
			slot = slot * plugin.getConfig().getInt("ROWS");
		}
		if(plugin.getConfig().contains("COLOR")) {
			if(new ColorHandler().getColor(plugin.getConfig().getString("COLOR"))!=null)
				color = new ColorHandler().getColor(plugin.getConfig().getString("COLOR"));
		}
		if(plugin.getConfig().contains("BOLD"))
			if(plugin.getConfig().getBoolean("BOLD"))
				bold = ChatColor.BOLD;
			else
				bold = color;
		if(plugin.getConfig().contains("SERVER")) {
			server = color + "" + bold + plugin.getConfig().getString("SERVER");
		}
		changeBG();
		generateGUI();
		loadGUI();
	}
	
	public void setSlot(int row) {
		if(row > 6)
			row = 6;
		else if(row < 1)
			row = 1;
		slot = 9 * row;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public void generateGUI() {
		gui = Bukkit.createInventory(null, slot, server);
		gui_dev = Bukkit.createInventory(null, slot, server + " - DEVELOPER");
		
		ItemStack item = new ItemStack(gui_bg);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN+" ");
		item.setItemMeta(meta);
		
		
		for(int i = 0; i < slot;i++)
			gui.setItem(i, item);
		
		for(int i = 0; i < slot;i++) {
			meta.setDisplayName(ChatColor.GREEN+""+(i+1));
			item.setItemMeta(meta);
			gui_dev.setItem(i, item);
		}
		
	}
	
	private void changeBG() {
		gui_bg = Material.RED_STAINED_GLASS_PANE;
		if(plugin.getConfig().contains("BACKGROUND")) {
			if(plugin.getConfig().getString("BACKGROUND").equals("BLUE"))
				gui_bg = Material.BLUE_STAINED_GLASS_PANE;
			if(plugin.getConfig().getString("BACKGROUND").equals("YELLOW"))
				gui_bg = Material.YELLOW_STAINED_GLASS_PANE;
			if(plugin.getConfig().getString("BACKGROUND").equals("WHITE"))
				gui_bg = Material.WHITE_STAINED_GLASS_PANE;
			if(plugin.getConfig().getString("BACKGROUND").equals("LIME"))
				gui_bg = Material.LIME_STAINED_GLASS_PANE;
		}
	}
	
	public Inventory getDev() {
		return gui_dev;
	}
	
	public Inventory getGUI() {
		return gui;
	}
	
	public void setGUI(Inventory inv) {
		gui = inv;
		for(int i = 0; i < slot; i++) {
			if(!gui.getItem(i).getType().equals(gui_dev.getItem(i).getType()))
				gui_dev.setItem(i, gui.getItem(i));
		}
	}

	public void saveGUI() {
		 ItemStack[] items = gui.getContents();
		 List<String> types = new ArrayList<String>();
		 List<String> display = new ArrayList<String>();
		 
		 for(int i = 0; i < slot; i ++) {
			 types.add(i, items[i].getType().toString());
			 display.add(i, items[i].getItemMeta().getDisplayName());;
		 }
		 
		 plugin.data.getConfig().set("_types", types);
		 plugin.data.getConfig().set("_typesDisplay", display);
		 saveLocation();
		 plugin.data.saveConfig();
	}
	
	private void saveLocation() {
		List<Map<Integer,Location>> locs = new LinkedList<Map<Integer,Location>>();
		locs.add(locations);
		plugin.data.getConfig().set("_location", locs);
	}
	
	@SuppressWarnings("unchecked")
	public void loadGUI() {
		 List<String> types = new ArrayList<String>(slot);
		 List<String> display = new ArrayList<String>(slot);
		 
		 if(plugin.data.getConfig().contains("_types"))
			 types = (List<String>) plugin.data.getConfig().getList("_types");
		 if(plugin.data.getConfig().contains("_typesDisplay"))
			 display = (List<String>) plugin.data.getConfig().getList("_typesDisplay");
		 if(types.size()>1)
		 for(int i = 0 ;i < slot; i++) {
			 for(Material mat : materials()) {
				 if(mat.toString().equals(types.get(i)))
					 gui.getItem(i).setType(mat);
			 }
			 ItemMeta meta = gui.getItem(i).getItemMeta();
			 meta.setDisplayName(display.get(i));
			 gui.getItem(i).setItemMeta(meta);
		 }
		 
		 //load location
		 if(plugin.data.getConfig().contains("_location")) {
			 List<Map<Integer, Location>> locs = new LinkedList<Map<Integer,Location>>();
			 List<Map<?, ?>> list = plugin.data.getConfig().getMapList("_location");
			 for(Map<?,?> content : list)
				 locs.add((Map<Integer, Location>) content);
			 
			 for(Map<Integer, Location> c : locs)
				 locations = c;
		 }
		 
		 if(plugin.getConfig().contains("Enchanted"))
			 if(plugin.getConfig().getBoolean("Enchanted")) {
				 for(int i = 0 ; i < slot; i++) {
					 ItemMeta meta = gui.getItem(i).getItemMeta();
					 meta.addEnchant(Enchantment.DURABILITY, 1, true);
					 meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					 gui.getItem(i).setItemMeta(meta);
				 }
			 }
	}
	
	
	public List<Material> materials(){
		List<Material> materials = new LinkedList<Material>();
		EnumSet.allOf(Material.class)
		  .forEach(mats -> materials.add(mats));
		return materials;
	}
}
