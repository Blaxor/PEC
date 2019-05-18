package ro.deiutzblaxo.PEC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private Metrics Metrics;
	private static Main instance;

	@Override
	public void onEnable() {
		setInstance(this);
		setMetrics(new Metrics(this));
		getMetrics();
		loadConfig();
		getVersion();
		getLatesVersion();
		updateChecker();
		this.getServer().getPluginManager().registerEvents(new ClickOpen(), this);
		this.getCommand("pec").setExecutor(new PECCommand());
	}

	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

	@Override
	public void onDisable() {

	}



	PluginDescriptionFile pdffile = getDescription();
	public String version = this.pdffile.getVersion();
	public String latestversion;

	public String prefix = "&7[&aPEC&7]&r";

	public String getVersion() {
		return this.version;
	}

	public String getLatesVersion() {

		return this.latestversion;
	}

	public void updateChecker() {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(
					"https://api.spigotmc.org/legacy/update.php?resource=41510").openConnection();
			int timed_out = 1250;
			con.setConnectTimeout(timed_out);
			con.setReadTimeout(timed_out);
			this.latestversion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
			if ((this.latestversion.length() <= 7) && (!this.version.equals(this.latestversion))) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&8&m--------------------------------------------------------------------------------------"));
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
						this.prefix + "&8There is a new version available. &9" + this.latestversion));
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
						this.prefix + "&8You can download it at: &9https://www.spigotmc.org/resources/41510/"));
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&8&m--------------------------------------------------------------------------------------"));
			}
		} catch (Exception ex) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&m--------------------------------------------------------------------------------------"));
			Bukkit.getConsoleSender().sendMessage(
					ChatColor.translateAlternateColorCodes('&', this.prefix + "&cPlease connect to the internet."));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&8&m--------------------------------------------------------------------------------------"));
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().isOp()) {
			try {
				updateCheckerPlayer(this , e.getPlayer() , prefix , 41510);
			} catch (IOException e1) {

			}
		}
	}
	public void updateCheckerPlayer(Plugin plugin, Player player, String prefix, Integer ResourceNumber)
			throws MalformedURLException, IOException {

		PluginDescriptionFile pdffile = plugin.getDescription();
		HttpURLConnection con = (HttpURLConnection) new URL(
				"https://api.spigotmc.org/legacy/update.php?resource=" + ResourceNumber).openConnection();
		int timed_out = 1250;
		con.setConnectTimeout(timed_out);
		con.setReadTimeout(timed_out);
		String latestversion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
		if ((latestversion.length() <= 100) && (!pdffile.getVersion().equals(latestversion))) {

			if ((player.isOp()) && (!pdffile.getVersion().equals(latestversion))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&8&m-----------------------------------------------------"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						prefix + "&8There is a new version available. &9" + latestversion));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						prefix + "&8You can download it at: &9https://www.spigotmc.org/resources/65244/"));
				Bukkit.getConsoleSender().sendMessage(
						ChatColor.translateAlternateColorCodes('&', prefix + "&8Don`t forget to rate our plugin!"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&8&m-----------------------------------------------------"));
				player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
			}
		}
	}

	public Metrics getMetrics() {
		return Metrics;
	}

	public void setMetrics(Metrics metrics) {
		Metrics = metrics;
	}

	public static Main getInstance() {
		return instance;
	}

	public static void setInstance(Main instance) {
		Main.instance = instance;
	}
}
