package ro.deiutzblaxo.PEC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickOpen implements Listener {
	private Main pl = Main.getInstance();

	@EventHandler
	public void ClickEvent(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		Material eChest = e.getMaterial();
		if (eChest == Material.ENDER_CHEST) {

			if (Action.LEFT_CLICK_AIR == e.getAction() && pl.getConfig().getBoolean("LeftClickOpenPEC", true)) {
				if (e.getPlayer().hasPermission("PEC.LeftClick")) {
					p.openInventory(p.getEnderChest());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("OpenPEC")));
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
							pl.prefix + p.getName() + " &rOpen his PEC with Left-Click!"));
				}
			}

			if (Action.RIGHT_CLICK_AIR == e.getAction() && pl.getConfig().getBoolean("RightClickOpenPEC", true)) {
				if (e.getPlayer().hasPermission("PEC.RightClick")) {
					p.openInventory(p.getEnderChest());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("OpenPEC")));
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
							pl.prefix + p.getName() + " &rOpen his PEC with Right-Click!"));
				}
			}
		}
	}
}
