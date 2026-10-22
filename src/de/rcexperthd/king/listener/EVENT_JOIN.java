package de.rcexperthd.king.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.rcexperthd.king.Factory;
import de.rcexperthd.king.Main;
import de.rcexperthd.king.Var;
import de.rcexperthd.king.gamestats.End_State;
import de.rcexperthd.king.gamestats.GameStateHandler;
import de.rcexperthd.king.gamestats.IngameState;
import de.rcexperthd.king.gamestats.LobbyState;
import de.rcexperthd.king.gamestats.RestartState;



public class EVENT_JOIN implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		if(GameStateHandler.getCurrentState() instanceof LobbyState) {
			LobbyState ls = (LobbyState) GameStateHandler.getCurrentState();
			Var.playing.add(p);
			Bukkit.broadcastMessage(Var.pr+ "§7Der Spieler §a" +p.getDisplayName()+ "§7 hat das Spiel betreten. [" +Var.playing.size() + "/" +LobbyState.MAX_PLAYERS+"]");
			p.teleport(Factory.getConfigLocation("Spawn.Lobby", Var.cfg));
			
			if(Var.playing.size() >= LobbyState.MIN_PLAYERS) {
				if(ls.getCountdown().isRunning == false) {
					ls.getCountdown().stopIdle();
					ls.getCountdown().start();
					
				}
				
			}
			if(Var.playing.size() < LobbyState.MIN_PLAYERS) {
				if(ls.getCountdown().isIdling == false) {
					ls.getCountdown().idle();
				}
			}
		} else if(GameStateHandler.getCurrentState() instanceof IngameState) {
			Var.spectating.add(p);
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(Factory.getConfigLocation("Spawn.Spectator", Var.cfg));
		} else if(GameStateHandler.getCurrentState() instanceof RestartState || GameStateHandler.getCurrentState() instanceof End_State) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
				try {
					out.writeUTF("Connect");
					out.writeUTF("lobby");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				p.sendMessage("§aVerbinde mit Craftattack...");
				p.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
			
		}
		
		
	}

}
