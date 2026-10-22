package de.rcexperthd.king.gamestats;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.rcexperthd.king.Factory;
import de.rcexperthd.king.Var;
import de.rcexperthd.king.countdown.SpawnCountdown;



public class IngameState extends GameState {
	
	private SpawnCountdown spawnCountdown;

	@Override
	public void init() {
		spawnCountdown = new SpawnCountdown();
		
		for(int i = 0; i < Var.playing.size(); i++) {
			Player p = Var.playing.get(i);
			p.teleport(Factory.getConfigLocation("Spawn.Arena", Var.cfg));
			p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
			
			ItemStack knueppel = new ItemStack(Material.STICK);
			ItemMeta knueppelm = knueppel.getItemMeta();
			knueppelm.setDisplayName("§5Superknüppel 3000");
			knueppelm.addEnchant(Enchantment.KNOCKBACK, 2, true);
			knueppel.setItemMeta(knueppelm);

			/*ItemStack buffer = new ItemStack(Material.FIREWORK_CHARGE);
			ItemMeta bufferm = buffer.getItemMeta();
			bufferm.setDisplayName("§aBooster - §cNur einmal Benutzbar");
			buffer.setItemMeta(bufferm);

			p.getInventory().addItem(buffer);*/
			p.getInventory().addItem(knueppel);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600000, 10));
		} 
		spawnCountdown.start();
	}

	@Override
	public void end() {
		
	}

}
