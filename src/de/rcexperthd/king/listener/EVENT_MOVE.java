package de.rcexperthd.king.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.rcexperthd.king.Var;

public class EVENT_MOVE implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(Var.canMove == false) {
			e.setCancelled(true);
		}
	}

}
