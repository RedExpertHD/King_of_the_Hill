package de.rcexperthd.king.gamestats;

import org.bukkit.Bukkit;

import de.rcexperthd.king.Var;
import de.rcexperthd.king.countdown.LobbyCountdown;


public class LobbyState extends GameState {
		
		public static final int MIN_PLAYERS = 2,
								MAX_PLAYERS = 8;
		
		private LobbyCountdown countdown;

		@Override
		public void init() {
			countdown = new LobbyCountdown();
			
		}

		@Override
		public void end() {
			Bukkit.broadcastMessage(Var.pr+"§aAlle Spieler werden nun auf ihre Plätze teleportiert!");
			
		}
		public LobbyCountdown getCountdown() {
			return countdown;
			
		}

}
