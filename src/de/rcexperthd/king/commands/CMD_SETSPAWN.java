package de.rcexperthd.king.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.rcexperthd.king.Factory;
import de.rcexperthd.king.Var;


public class CMD_SETSPAWN implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("setspawn")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("sw.setspawn")) {
					File file = Var.cfgFile;
					YamlConfiguration cfg = Var.cfg;
					
					if(args.length == 1) {
						if(args[0].equalsIgnoreCase("lobby")) {
							Factory.createConfigLocation(p.getLocation(), "Spawn.Lobby", file, cfg);
							p.sendMessage(Var.pr+"§cDer Spawn für die §aLobby §cwurde gesetzt.");
						} else if(args[0].equalsIgnoreCase("spectator")) {
							Factory.createConfigLocation(p.getLocation(), "Spawn.Spectator", file, cfg);
							p.sendMessage(Var.pr+"§cDer Spawn für den §aSpectator §cwurde gesetzt.");
						} else if(args[0].equalsIgnoreCase("Arena")) {
							Factory.createConfigLocation(p.getLocation(), "Spawn.Arena", file, cfg);
							p.sendMessage("§4Der Spawn für die§6 Arena §r4wurde gesetzt.");
						}
							
						} 

						
					} else {
						p.sendMessage(Var.noperm);
					}
				}
			}
	
		return false;
	}

}
