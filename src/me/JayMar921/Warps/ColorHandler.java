package me.JayMar921.Warps;

import java.util.ArrayList;

import org.bukkit.ChatColor;


public class ColorHandler {
	
	public ChatColor getColor(String color) {
		for(ChatColor col : Colors()) {
			if(col.name().equals(color)) 
				return col;
		}
		return null;
	}
	
	public ArrayList<ChatColor> Colors(){
		ArrayList<ChatColor> color = new ArrayList<ChatColor>();
		
		color.add(ChatColor.AQUA);
		color.add(ChatColor.BLACK);
		color.add(ChatColor.BLUE);
		color.add(ChatColor.DARK_AQUA);
		color.add(ChatColor.DARK_BLUE);
		color.add(ChatColor.DARK_GRAY);
		color.add(ChatColor.DARK_GREEN);
		color.add(ChatColor.DARK_PURPLE);
		color.add(ChatColor.DARK_RED);
		color.add(ChatColor.GOLD);
		color.add(ChatColor.GRAY);
		color.add(ChatColor.LIGHT_PURPLE);
		color.add(ChatColor.RED);
		color.add(ChatColor.WHITE);
		color.add(ChatColor.YELLOW);
		
		return color;
	}
}
