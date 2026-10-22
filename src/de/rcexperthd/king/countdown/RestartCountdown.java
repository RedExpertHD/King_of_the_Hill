package de.rcexperthd.king.countdown;

import de.rcexperthd.king.Main;
import de.rcexperthd.king.MySQL;
import de.rcexperthd.king.MySQLServer;
import de.rcexperthd.king.Var;
import de.rcexperthd.king.gamestats.GameState;
import de.rcexperthd.king.gamestats.GameStateHandler;
import org.bukkit.Bukkit;

public class RestartCountdown extends Countdown {

    private int taskID;
    private int seconds = Var.restartSeconds;

    @Override
    public void start() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

            @Override
            public void run() {


                switch(seconds) {
                    case 10:
                        System.out.println("Der Server reloaded in 10 Sekunden.");
                        break;
                    case 0:
                        stop();
                        GameStateHandler.setGameState(GameState.LOBBY_STATE);
                        MySQLServer.update(MySQL.Servername, 1);
                        break;
                    default:
                        break;
                }

                seconds--;
            }

        }, 0, 20);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
