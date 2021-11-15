package me.JayMar921.Warps;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.*;

import me.JayMar921.Warps.VersionSupport.VersionSupport;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	GuiHandler warp_gui;
	DataHandler data;
	public VersionSupport version;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		data = new DataHandler(this);
		warp_gui = new GuiHandler(this);
		this.getServer().getPluginManager().registerEvents(new EventLoad(this), this);
		
		version = new VersionSupport(Bukkit.getServer().getVersion());
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(label.equalsIgnoreCase("warps")) {
			if(!(sender instanceof Player)) {
				System.out.println("Console cannot use this command :/");
				return false;
			}
			Player player = (Player)sender;
			//Show GUI
			
			player.openInventory(warp_gui.getGUI());
			return true;
		}
		
		if(label.equalsIgnoreCase("warp")) {
			if(!(sender instanceof Player)) {
				System.out.println("Console cannot use this command :/");
				return false;
			}
			Player player=(Player)sender;
			if(!player.hasPermission("CustomWarps.warp")) {
				player.sendMessage(ChatColor.BLUE+"You should be op to use this command");
				return true;
			}
			if(args!=null && args.length>0) {
				if(args[0]!=null) {
					String arg0 = args[0];
					
					//save
					if(arg0.equals("save")) {
						player.sendMessage("[Custom Warps] data saved...");
						warp_gui.saveGUI();
						warp_gui.loadGUI();
						return true;
					}else if(arg0.equals("reset")) {
						
						if(args.length>1 && args[1]!=null) {
							
							if(args[1].equals("slot")) {
								
								if(args.length>2 && args[2]!= null) {
									int slot = 0;
									try {
										slot = Integer.parseInt(args[2]);
									}catch(Exception e) {
										player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+ChatColor.RED+args[2]+ChatColor.AQUA+" <- invalid argument");
										player.sendMessage(ChatColor.AQUA+"Argument: slot number");
										return true;
									}
									
									if(slot > warp_gui.getSlot()) {
										player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+ChatColor.RED+args[2]+ChatColor.AQUA+" <- invalid argument");
										player.sendMessage(ChatColor.AQUA+"Argument: "+ChatColor.RED+"value should not be greater than "+warp_gui.getSlot());
										return true;
									}
									//
									
									Inventory gui = warp_gui.getGUI();
									gui.getItem(slot-1).setType(warp_gui.gui_bg);
									gui.getItem(slot-1).getItemMeta().setDisplayName(ChatColor.GREEN+" ");
									warp_gui.setGUI(gui);
									player.sendMessage(ChatColor.GOLD+"You have reset slot "+ChatColor.RED+slot);
									if(warp_gui.locations.containsKey(slot-1))
										warp_gui.locations.remove(slot-1);
									
								}else {
									player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+ChatColor.RED+"?"+ChatColor.AQUA+" <- invalid argument");
									player.sendMessage(ChatColor.AQUA+"Argument: slot number");
									return true;
								}
								
							}else {
								player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+ChatColor.RED+""+args[1]+ChatColor.AQUA+" <- invalid argument");
								player.sendMessage(ChatColor.AQUA+"Argument: slot");
								return true;
							}
							
						}else {
							player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+ChatColor.RED+"?"+ChatColor.AQUA+" <- invalid argument");
							player.sendMessage(ChatColor.AQUA+"Argument: slot");
							return true;
						}
						
					}else if(arg0.equals("modify")) {
						if(args.length>1 && args[1]!=null) {
							if(args[1].equals("slot")) {
								if(args.length>2 && args[2]!=null) {
									int slot = 0;
									try {
										slot = Integer.parseInt(args[2]);
									}catch(Exception e) {
										player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+ChatColor.RED+args[2]+ChatColor.AQUA+" <- invalid argument");
										player.sendMessage(ChatColor.AQUA+"Argument: slot number");
										return true;
									}
									
									if(slot > warp_gui.getSlot()) {
										player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+ChatColor.RED+args[2]+ChatColor.AQUA+" <- invalid argument");
										player.sendMessage(ChatColor.AQUA+"Argument: "+ChatColor.RED+"value should not be greater than "+warp_gui.getSlot());
										return true;
									}
									
									if(args.length>3 && args[3]!=null) {
										if(args[3].equals("item")) {
											if(player.getInventory().getItemInMainHand()!=null) {
												if(!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
													Inventory inv = warp_gui.getGUI();
													ItemStack item = new ItemStack(player.getInventory().getItemInMainHand().getType());
													ItemMeta meta = item.getItemMeta();
													meta.setDisplayName(ChatColor.WHITE+item.getType().toString());
													item.setItemMeta(meta);
													inv.setItem(slot-1, item);
													warp_gui.setGUI(inv);
													player.sendMessage(ChatColor.GOLD+"You have modified slot "+slot+", item = "+player.getInventory().getItemInMainHand().getType());
													return true;
												}else {
													player.sendMessage(ChatColor.RED+"You should hold the item of your choice");
													return true;
												}
												
											}else {
												player.sendMessage(ChatColor.RED+"You should hold the item of your choice");
												return true;
											}
										}else if(args[3].equals("name")) {
											if(args.length>4 && args[4]!=null) {
												if(new ColorHandler().getColor(args[4])!=null) {
													org.bukkit.ChatColor color = new ColorHandler().getColor(args[4]);
													
													if(args.length>5 && args[5]!=null) {
														Inventory inv = warp_gui.getGUI();
														ItemStack item = inv.getItem(slot-1);
														ItemMeta meta = item.getItemMeta();
														String msg = " ";
														if(args.length>6 && args[6]!=null)
															msg+= args[6];
														if(args.length>7 && args[7]!=null)
															msg+= " "+args[7];
														meta.setDisplayName(color+args[5]+""+msg);
														item.setItemMeta(meta);
														if(item.getType().equals(warp_gui.gui_bg))
															item.setType(Material.BARRIER);
														inv.setItem(slot-1, item);
														warp_gui.setGUI(inv);
														player.sendMessage(ChatColor.AQUA+"Renamed warp slot "+slot+" to "+color+args[5]);
													}else {
														player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+args[2]+" "+args[3]+" "+args[4]+ChatColor.RED+" "+args[5]+ChatColor.AQUA+" <- invalid argument");
														player.sendMessage(ChatColor.AQUA+"Argument: [Name of warp]");
														return true;
													}
													
												}else {
													player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+args[2]+" "+args[3]+ChatColor.RED+" ?"+ChatColor.AQUA+" <- invalid argument");
													String colors = "";
													for(org.bukkit.ChatColor color: new ColorHandler().Colors())
														colors+=color+color.name()+" ";
													player.sendMessage(ChatColor.AQUA+"Argument: "+colors);
													return true;
												}
											}else {
												player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+args[2]+" "+args[3]+ChatColor.RED+" ?"+ChatColor.AQUA+" <- invalid argument");
												String colors = "";
												for(org.bukkit.ChatColor color: new ColorHandler().Colors())
													colors+=color+color.name()+" ";
												player.sendMessage(ChatColor.AQUA+"Argument: "+colors);
												return true;
											}
										}else if(args[3].equals("location")) {
											
											if(args.length>4 && args[4]!=null) {
												if(args[4].equals("set")) {
													warp_gui.locations.put(slot-1, player.getLocation());
													player.sendMessage(ChatColor.GOLD+"You have set the location for slot "+slot+" | warp: "+warp_gui.getGUI().getItem(slot-1).getItemMeta().getDisplayName());
												}else if(args[4].equals("remove")) {
													warp_gui.locations.remove(slot-1);
													player.sendMessage(ChatColor.GOLD+"You have removed the location for slot "+slot+" | warp: "+warp_gui.getGUI().getItem(slot-1).getItemMeta().getDisplayName());
												}else {
													player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+args[2]+" "+args[3]+ChatColor.RED+" "+args[4]+ChatColor.AQUA+" <- invalid argument");
													player.sendMessage(ChatColor.AQUA+"Argument: set, remove");
													return true;
												}
											}else {
												player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+args[2]+" "+args[3]+ChatColor.RED+" ?"+ChatColor.AQUA+" <- invalid argument");
												player.sendMessage(ChatColor.AQUA+"Argument: set, remove");
												return true;
											}
											
											
										}else {
											player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+args[2]+ChatColor.RED+" ?"+ChatColor.AQUA+" <- invalid argument");
											player.sendMessage(ChatColor.AQUA+"Argument: item, name, location");
											return true;
										}
										
									}else {
										player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+" "+args[2]+ChatColor.RED+" ?"+ChatColor.AQUA+" <- invalid argument");
										player.sendMessage(ChatColor.AQUA+"Argument: item, name, location");
										return true;
									}
									
								}else {
									player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+args[1]+ChatColor.RED+" ?"+ChatColor.AQUA+" <- invalid argument");
									player.sendMessage(ChatColor.AQUA+"Argument: slot number");
									return true;
								}
								
							}else {
								player.sendMessage(ChatColor.AQUA+"/warp "+arg0+" "+ChatColor.RED+args[1]+ChatColor.AQUA+" <- invalid argument");
								player.sendMessage(ChatColor.AQUA+"Argument: slot");
								return true;
							}
						}else {
							player.sendMessage(ChatColor.AQUA+"/warp "+arg0+ChatColor.RED+" ?"+ChatColor.AQUA+" <- invalid argument");
							player.sendMessage(ChatColor.AQUA+"Argument: slot");
						}
					}else if(arg0.equals("display")) {
						player.openInventory(warp_gui.getDev());
						return true;
					}else {
						player.sendMessage(ChatColor.AQUA+"/warp "+ChatColor.RED+arg0+ChatColor.AQUA+" <- invalid argument");
						player.sendMessage(ChatColor.AQUA+"Argument: save, modify, display, reset");
					}
					
				}
			}else {
				player.sendMessage(ChatColor.AQUA+"/warp [save | modify | display | reset]");
			}
			return true;
		}
		return false;
	}

}
