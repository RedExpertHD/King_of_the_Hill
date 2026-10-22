package de.rcexperthd.king.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.rcexperthd.king.Var;

public class EVENT_ATTACK implements Listener {
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if(Var.canAttack == false) {
			e.setCancelled(true);
		}
	}

}
