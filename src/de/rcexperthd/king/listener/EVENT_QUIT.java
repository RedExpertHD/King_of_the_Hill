package de.rcexperthd.king.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.rcexperthd.king.MySQL;
import de.rcexperthd.king.MySQLServer;
import de.rcexperthd.king.Var;
import de.rcexperthd.king.gamestats.GameState;
import de.rcexperthd.king.gamestats.GameStateHandler;
import de.rcexperthd.king.gamestats.IngameState;
import de.rcexperthd.king.gamestats.LobbyState;


public class EVENT_QUIT implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		if(GameStateHandler.getCurrentState() instanceof LobbyState) {
			LobbyState ls = (LobbyState) GameStateHandler.getCurrentState();
			Var.playing.remove(p);
			Bukkit.broadcastMessage(Var.pr+ "§7Der Spieler §c" +p.getDisplayName()+ " §7 hat das Spiel verlassen. [" +Var.playing.size() + "/" +LobbyState.MAX_PLAYERS+"]");
			
			if(Var.playing.size() < LobbyState.MIN_PLAYERS) {
				if(ls.getCountdown().isRunning) {
					ls.getCountdown().stopCountdown();
					ls.getCountdown().idle();
					
				}
			}
		} else if(GameStateHandler.getCurrentState() instanceof IngameState) {
			Var.playing.remove(p);
			Var.spectating.remove(p);
			Bukkit.broadcastMessage(Var.pr+"§5 "+e.getPlayer().getName()+" §chat das Spiel verlassen.");
			if(Var.spectating.size() == 0 && Var.playing.size() == 0) {
				GameStateHandler.setGameState(GameState.LOBBY_STATE);
				MySQLServer.update(MySQL.Servername, 1);
			}
		}
		
	}

}
