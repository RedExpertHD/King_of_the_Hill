package de.rcexperthd.king.commands;

import de.rcexperthd.king.Main;
import de.rcexperthd.king.MySQL;
import de.rcexperthd.king.MySQLServer;
import de.rcexperthd.king.Var;
import de.rcexperthd.king.gamestats.GameState;
import de.rcexperthd.king.gamestats.GameStateHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_TPA implements CommandExecutor {

    private int taskID, seconds = 10;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpa")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("sw.start")) {
                    if (args.length == 0) {

                        Var.canAttack = false;
                        Bukkit.broadcastMessage(Var.pr+"§cACHTUNG. Du wurdest teleportiert.");
                        for(int i = 0; i < Var.playing.size(); i++) {
                            Player a = Var.playing.get(i);
                            a.teleport(p.getLocation());
                        }

                        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

                            public void run() {

                                if(seconds != 0) {
                                    seconds--;
                                }

                                for(Player a : Var.playing) a.setLevel(seconds);
                                switch(seconds) {
                                    case 10: case 5: case 3: case 2:
                                        Bukkit.broadcastMessage(Var.pr+ "§7Ihr seid angreifbar in §6"+seconds+" §7Sekunden.");
                                        for(Player a : Var.playing) a.playSound(a.getLocation(), Sound.FIRE_IGNITE, 1, 1);
                                        break;
                                    case 1:
                                        Bukkit.broadcastMessage(Var.pr+ "§7Ihr seid angreifbar in §6" +seconds+ " §7Sekunde.");
                                        break;
                                    case 0:
                                        Bukkit.broadcastMessage(Var.pr+ "§7Ihr seid nun wieder angreifbar.");
                                        Var.canAttack = true;
                                        stop();
                                        break;
                                    default:
                                        break;
                                }


                            }

                        }, 0,20*1);

                    }
                }
            }
        }

        return false;
    }

    public void stop() {
        seconds = 10;
        Bukkit.getScheduler().cancelTask(taskID);



    }
}