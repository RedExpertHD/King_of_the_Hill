package de.rcexperthd.king.gamestats;

import de.rcexperthd.king.Main;
import de.rcexperthd.king.Var;
import de.rcexperthd.king.countdown.RestartCountdown;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RestartState extends GameState {
	@Override
	public void init() {

		for(int l = 0; l < Var.playing.size(); l++) {

			Player ar = Var.playing.get(l);
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("Connect");
				out.writeUTF("lobby");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ar.sendMessage("§aVerbinde mit Craftattack...");
			ar.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
		}
		Var.playing.clear();
	}

	@Override
	public void end() {

		
	}

}
