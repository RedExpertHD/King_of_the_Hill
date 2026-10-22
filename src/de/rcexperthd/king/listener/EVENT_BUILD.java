package de.rcexperthd.king.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.rcexperthd.king.Var;

public class EVENT_BUILD implements Listener {
	
	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		if(Var.canBuild == false) {
			e.setCancelled(true);
		}
	}



	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(Var.canBuild == false) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
}
	