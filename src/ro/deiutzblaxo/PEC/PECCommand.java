package ro.deiutzblaxo.PEC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class PECCommand implements CommandExecutor {
	private Main main = Main.getInstance();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("pec")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("OnlyPlayers")));
				return true;
			}
			HumanEntity humanSender = (HumanEntity) sender;

			if (args.length == 0) {


				if (sender.hasPermission("PEC.use")) {

					humanSender.openInventory(humanSender.getEnderChest());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("OpenPEC")));
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
							main.prefix + humanSender.getName() + " &ropen his PEC!"));
					return true;
				}
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("NoPermission")));
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
						main.prefix + humanSender.getName() + "&r try to open PEC but don`t have access."));
				return true;
			}
			if (args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (sender.hasPermission("PEC.use.others")) {

					if (target == null) {
						sender.sendMessage(
								ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("NoPlayerFound")));
						Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.translateAlternateColorCodes('&', main.prefix + "&r"
								+ sender.getName() + "&rtry to open " + args[0] + "&r.But itsn`t online!"));
						return true;
					}
					if(target.hasPermission("PEC.use.others.deny")) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("OpenOtherPlayerPECDeny")));
						Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', main.prefix + "&r" + sender.getName() + " try to open "
								+ target.getName() + "&r. But " +target.getName() + " have permission PEC.use.others.deny ."));
						return true;
					}

					humanSender.openInventory(target.getEnderChest());

					sender.sendMessage(
							ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("OpenOtherPlayerPEC")
									.replaceAll("%player%", target.getName())));
					Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.translateAlternateColorCodes('&', main.prefix + "&r" + sender.getName()
					+ "&r Open " + target.getName() + "'s PEC"));
					return true;
				}
				sender.sendMessage(
						ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("NoPermissionOtherPEC")));
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
						main.prefix + sender.getName() + "&r try to open other PEC but don`t have access."));
				return true;
			}
			if (args.length > 1) {
				sender.sendMessage(
						ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("TooManyArguments")));
				Bukkit.getServer().getConsoleSender().sendMessage(
						ChatColor.translateAlternateColorCodes('&', main.prefix + sender.getName() + "&rFail to use /PEC"));
				return true;
			}
		}
		return false;
	}

}
