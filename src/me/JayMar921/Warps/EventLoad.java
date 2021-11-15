package me.JayMar921.Warps;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class EventLoad implements Listener{

	private Main plugin;
	private int delay;
	public EventLoad(Main plugin) {
		this.plugin = plugin;
		
		if(plugin.getConfig().contains("DELAY"));
			delay = plugin.getConfig().getInt("DELAY");
	}
	
	@EventHandler()
	public void clickEvent(InventoryClickEvent event) {
		if(!event.getInventory().equals(plugin.warp_gui.getDev()) && !event.getInventory().equals(plugin.warp_gui.getGUI()))
			return;
		event.setCancelled(true);
		
		int clicked_slot = event.getSlot();
		if(plugin.warp_gui.locations.containsKey(clicked_slot)) {
			event.getWhoClicked().closeInventory();
			new BukkitRunnable() {
				int count = delay;
				@Override
				public void run() {
					if(count < 1) {
						final Location previous = event.getWhoClicked().getLocation();
						event.getWhoClicked().teleport(plugin.warp_gui.locations.get(clicked_slot));
						event.getWhoClicked().getWorld().playSound(previous, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);;
						event.getWhoClicked().getWorld().spawnParticle(Particle.PORTAL, previous, 10);
						if(plugin.version.support_1_17())
							event.getWhoClicked().getWorld().spawnParticle(Particle.GLOW, previous, 10);
						event.getWhoClicked().sendMessage(ChatColor.DARK_GREEN+"You have been teleported to "+event.getInventory().getItem(clicked_slot).getItemMeta().getDisplayName());
						cancel();
					}
					if(count>0)
					event.getWhoClicked().sendMessage(ChatColor.YELLOW+"Teleporting in "+ChatColor.RED+ count);
					count--;
				}}.runTaskTimer(plugin, 0, 20);
					}else{
			if(event.getInventory().getItem(clicked_slot).getItemMeta().getDisplayName().equals(ChatColor.GREEN+" "))
				return;
			event.getWhoClicked().sendMessage(ChatColor.RED+"Warp to "+event.getInventory().getItem(clicked_slot).getItemMeta().getDisplayName()+ChatColor.RED+" is not available");
		}
	}
}
