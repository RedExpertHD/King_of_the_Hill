package de.rcexperthd.king.listener;


import de.rcexperthd.king.countdown.RestartCountdown;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import de.rcexperthd.king.Factory;
import de.rcexperthd.king.Main;
import de.rcexperthd.king.MySQL;
import de.rcexperthd.king.MySQLPoints;
import de.rcexperthd.king.MySQLServer;
import de.rcexperthd.king.Var;
import de.rcexperthd.king.gamestats.GameState;
import de.rcexperthd.king.gamestats.GameStateHandler;

public class EVENT_WIN implements Listener {

	private RestartCountdown restartCountdown;
	private int seconds = 16, kickseconds = 30;
	private int taskID, kickID;
	private boolean isRunning, isKicking;

	@EventHandler
	public void onWin(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.GOLD_BLOCK) {
			if (p.getGameMode() != GameMode.SPECTATOR) {
				for (int i = 0; i < Var.playing.size(); i++) {
					Player a = Var.playing.get(i);
					MySQLServer.update(MySQL.Servername, 4);
					a.teleport(Factory.getConfigLocation("Spawn.Lobby", Var.cfg));
					Var.canAttack = false;
					Var.canMove = true;
					Var.canBuild = false;
					a.getInventory().clear();
					a.setGameMode(GameMode.SURVIVAL);
					if (isRunning == false) {
						isRunning = true;
						GameStateHandler.setGameState(GameState.END_STATE);
					}
					Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);

					FireworkEffect effect = FireworkEffect.builder()
							.withColor(Color.GREEN)
							.flicker(true)
							.trail(true)
							.withFade(Color.RED)
							.with(FireworkEffect.Type.BALL_LARGE)
							.build();

					FireworkMeta meta = firework.getFireworkMeta();
					meta.addEffect(effect);
					meta.setPower(1);
					firework.setFireworkMeta(meta);
				}
				MySQLPoints.update(p.getUniqueId(), 2, false, p.getName());
				p.sendMessage("§aFür den Sieg an diesem Spiel erhälst du 3 Craftpunkte.");
				Bukkit.broadcastMessage(Var.pr + "§5 " + p.getName() + " §ahat das Spiel gewonnen.");
				for (int t = 0; t < Var.spectating.size(); t++) {
					Player s = Var.playing.get(t);
					s.teleport(Factory.getConfigLocation("Spawn.Lobby", Var.cfg));
					s.setGameMode(GameMode.SURVIVAL);
				}
				taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

					@Override
					public void run() {
						restartCountdown = new RestartCountdown();
						seconds--;

						switch (seconds) {
							case 15:
							case 10:
							case 5:
							case 3:
							case 2:
								Bukkit.broadcastMessage(Var.pr + "§cDer Server schließt in §5" + seconds + "§c Sekunden.");
								break;
							case 1:
								Bukkit.broadcastMessage(Var.pr + "§cDer Server schließt in §5" + seconds + "§c Sekunde.");
								break;
							case 0:
								GameStateHandler.setGameState(GameState.RESTART_STATE);
								seconds = 16;
								isRunning = false;
								Bukkit.getScheduler().cancelTask(taskID);
								break;
							default:
								break;
						}

					}

				}, 0, 20);

				if (isKicking == false) {
					isKicking = true;
					kickID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

						@Override
						public void run() {
							kickseconds--;
							restartCountdown = new RestartCountdown();
							switch (kickseconds) {
								case 0:

									Bukkit.getScheduler().cancelTask(kickID);
									isKicking = false;
									kickseconds = 20;
									if (Var.playing.size() == 0) {
										System.out.println("Alle Spieler haben den Server verlassen.");
										restartCountdown.start();
									} else {
										Bukkit.broadcastMessage("§cIrgendwas ist schiefgelaufen. Bitte verlasse den Server manuell.");
										MySQLServer.update(MySQL.Servername, 3);
									}
									break;
								default:
									break;
							}

						}

					}, 0, 20);
				}
			}
		}
	}

}
