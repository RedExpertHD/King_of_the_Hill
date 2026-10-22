package de.rcexperthd.king.commands;

import de.rcexperthd.king.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CMD_HUB implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("hub")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(args.length == 0) {
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
        return false;
    }
}
