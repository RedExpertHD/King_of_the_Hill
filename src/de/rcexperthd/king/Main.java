package de.rcexperthd.king;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.rcexperthd.king.commands.CMD_HUB;
import de.rcexperthd.king.commands.CMD_TPA;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.rcexperthd.king.commands.CMD_SETSPAWN;
import de.rcexperthd.king.commands.CMD_START;
import de.rcexperthd.king.gamestats.GameState;
import de.rcexperthd.king.gamestats.GameStateHandler;
import de.rcexperthd.king.listener.EVENT_ATTACK;
import de.rcexperthd.king.listener.EVENT_BUILD;
import de.rcexperthd.king.listener.EVENT_JOIN;
import de.rcexperthd.king.listener.EVENT_MOVE;
import de.rcexperthd.king.listener.EVENT_QUIT;
import de.rcexperthd.king.listener.EVENT_WIN;


public class  Main extends JavaPlugin {
	
	private static Main plugin;
	
	public void onEnable() {
		plugin = this;
		
		MySQLFile file = new MySQLFile();
		file.setStandard();
		file.readData();
		
		MySQL.connect();
		
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PunkteSystem (UUID VARCHAR(100),Spielername VARCHAR(100), Punkte INT(100))");
			ps.executeUpdate();
			System.out.println("[MySQL] Die Punktetabelle wurde creirt.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PunkteSystem (UUID VARCHAR(100),Spielername VARCHAR(100), Punkte INT(100))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ServerSystem (Servername VARCHAR(100), Status INT(100))");
			ps.executeUpdate();
			System.out.println("[MySQL] Die Servertabelle wurde creirt.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		MySQLServer.update(MySQL.Servername, 1);
		
		// 1 = Lobby Phase
		// 2 = Ingame Phase
		// 3 = Offline Phase
		// 4 = Open in a few seconds
		
		//MySQL.update("CREATE TABLE IF NOT EXISTS Craftpunkte (UUID VARCHAR(100),Spielername VARCHAR(100), Punkte INT(100))");
		
		new GameStateHandler();
		GameStateHandler.setGameState(GameState.LOBBY_STATE);
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		this.getCommand("start").setExecutor(new CMD_START());
		this.getCommand("setspawn").setExecutor(new CMD_SETSPAWN());
		this.getCommand("hub").setExecutor(new CMD_HUB());
		this.getCommand("tpa").setExecutor(new CMD_TPA());
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new EVENT_WIN(), this);
		pm.registerEvents(new EVENT_JOIN(), this);
		pm.registerEvents(new EVENT_QUIT(), this);
		pm.registerEvents(new EVENT_BUILD(), this);
		pm.registerEvents(new EVENT_ATTACK(), this);
		pm.registerEvents(new EVENT_MOVE(), this);
		
		System.out.println("[King_Of_The_Hill] Das Plugin wurde aktiviert.");
	}
	public void onDisable() {
		MySQLServer.update(MySQL.Servername, 3);
		MySQL.disconnect();
		System.out.println("[King_Of_The_King] Das Plugin wurde deaktiviert.");
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	   public static final void RELOAD() {
	        Bukkit.getPluginManager().disablePlugin(plugin);
		   Bukkit.getPluginManager().enablePlugin(plugin);
	    }

}
