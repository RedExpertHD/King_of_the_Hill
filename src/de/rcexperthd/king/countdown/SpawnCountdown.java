package de.rcexperthd.king.countdown;

import org.bukkit.Bukkit;

import de.rcexperthd.king.Main;
import de.rcexperthd.king.Var;



public class SpawnCountdown extends Countdown {
	
	private int taskID;
	private int seconds = 6;
	private boolean isRunning;
	

	@Override
	public void start() {
		
		Var.canAttack = false;
		Var.canMove = false;
		Var.canBuild = false;
		if(isRunning == false) {
		isRunning = true;
		
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				
				
				switch(seconds) {
				case 5: case 4: case 3: case 2:
					Bukkit.broadcastMessage(Var.pr+"§l§a Das Spiel beginnt in §c" +seconds+ "§a Sekunden.");
					break;
				case 1:
					Bukkit.broadcastMessage(Var.pr+"§l§a Das Spiel beginnt in §c" +seconds+ "§a Sekunde.");
					break;
				case 0:
					Bukkit.broadcastMessage(Var.pr+"§aDu kannst dich nun Bewegen. §aMöge der beste Spieler gewinnen. §5Viel Glück.");
					stop();
					Var.canAttack = true;
					Var.canMove = true;
					Var.canBuild = false;
					break;
					default:
						break;
				}
			
				seconds--;
			}
			
		}, 0, 20);

	}
	}
	@Override
	public void stop() {
		Bukkit.getScheduler().cancelTask(taskID);
		
	}
		
	

}

