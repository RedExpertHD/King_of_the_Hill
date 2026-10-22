package de.rcexperthd.king;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Var {
	public static final String pr = "§7[§6King of the Hill§7] ", noperm = "§cDazu hast du keine Rechte!";
	public static ArrayList<Player> playing = new ArrayList<>();
	public static ArrayList<Player> spectating = new ArrayList<>();
	
	public static boolean 
							canMove = true,
							canAttack = false,
							canBuild = false;

public static File cfgFile = new File("plugins/King_Plugin/config.yml");
public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

public static int restartSeconds = 30;



}
