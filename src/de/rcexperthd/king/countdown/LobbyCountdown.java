package de.rcexperthd.king.countdown;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.rcexperthd.king.Main;
import de.rcexperthd.king.MySQL;
import de.rcexperthd.king.MySQLServer;
import de.rcexperthd.king.Var;
import de.rcexperthd.king.gamestats.GameState;
import de.rcexperthd.king.gamestats.GameStateHandler;
import de.rcexperthd.king.gamestats.LobbyState;


public class LobbyCountdown extends Countdown {
	
	private int seconds = 60;
	private int taskID, idleID;
	public boolean isIdling = false, isRunning = false;

	@Override
	public void start() {
		isRunning = true;
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			
			public void run() {
				
				if(seconds != 0) {
					seconds--;
				}
				
				for(Player a : Var.playing) a.setLevel(seconds);
				switch(seconds) {
				case 60: case 30: case 15: case 10: case 5: case 3: case 2:
					Bukkit.broadcastMessage(Var.pr+ "§7Das Spiel startet in §6"+seconds+" §7Sekunden.");
					for(Player a : Var.playing) a.playSound(a.getLocation(), Sound.FIRE_IGNITE, 1, 1);
					break;
				case 1:
					Bukkit.broadcastMessage(Var.pr+ "§7Das Spiel startet in §6" +seconds+ " §7Sekunde.");
					for(Player a : Var.playing) a.playSound(a.getLocation(), Sound.ANVIL_LAND, 1, 1);
					break;	
					
				case 0:
					GameStateHandler.setGameState(GameState.INGAME_STATE);
					stop();
					MySQLServer.update(MySQL.Servername, 2);
					break;
					default:
						break;
				}
				
				
			}
			
		}, 0,20*1); 
		
	}
			
	public void idle() {
			isIdling = true;
			idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

				@Override
				public void run() {
					int missing = LobbyState.MIN_PLAYERS - Var.playing.size();
					Bukkit.broadcastMessage(Var.pr +"§7Zum Spielstart fehlen noch§c " +missing+ " §7Spieler!");
					
				}
				
			}, 0, 20*15);
		}
		
	public void stopIdle() {
		if(isIdling) Bukkit.getScheduler().cancelTask(idleID);
		isIdling = false;
		
	}
	public void stopCountdown() {
		if(isRunning) {
			isRunning = false;
			Bukkit.getScheduler().cancelTask(taskID);
			seconds = 60;
			for(Player a : Var.playing) a.setLevel(0);
		}
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	@Override
	public void stop() {
		isIdling = false;
		isRunning = false;
		seconds = 60;
		Bukkit.getScheduler().cancelTask(taskID);
		Bukkit.getScheduler().cancelTask(idleID);
	
	}

	
	
}
