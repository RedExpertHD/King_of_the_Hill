package de.rcexperthd.king.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.rcexperthd.king.Var;
import de.rcexperthd.king.gamestats.GameStateHandler;
import de.rcexperthd.king.gamestats.LobbyState;


public class CMD_START implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("start")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("sw.start")) {
					if(args.length == 0) {
						if(GameStateHandler.getCurrentState() instanceof LobbyState) {
							LobbyState ls = (LobbyState) GameStateHandler.getCurrentState();
							if(ls.getCountdown().getSeconds() > 10) {
								ls.getCountdown().setSeconds(10);
								p.sendMessage(Var.pr+"§aDu hast den Countdown auf §c10 §aSekunden gesetzt!");
								
								
							} else {
								p.sendMessage(Var.pr+"§cDas Spiel startet bereits!");
							}
							
						} else {
							p.sendMessage(Var.pr+"§cDer Command funktioniert nur in der Lobby!");
						}
						
					} else {
						p.sendMessage(Var.pr+"§Bitte benutze /start!");
					}
				}else {
					p.sendMessage(Var.noperm);
				}
			}
			
			}
		return false;
	}

}
