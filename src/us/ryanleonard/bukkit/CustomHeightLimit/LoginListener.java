package us.ryanleonard.bukkit.CustomHeightLimit;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class LoginListener implements Listener {
	private CustomHeightLimit plugin;
	public LoginListener (CustomHeightLimit plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void playerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		int groundMax = this.plugin.getConfig().getInt("groundLimit.max");
		int groundMin = this.plugin.getConfig().getInt("groundLimit.min");
		int skyMax = this.plugin.getConfig().getInt("skyLimit.max");
		int skyMin = this.plugin.getConfig().getInt("skyLimit.min");
		int skyLimit = 0;
		int groundLimit = 0;
		if(player.hasPermission("CustomHeightLimit.nolimit.max")) {
			player.setMetadata("CustomHeightLimitMax", new FixedMetadataValue(this.plugin, -1));
			skyLimit = -1;
		}
		else {
			/*
			 * The highest sky limit set will be used for the max
			 */
			for(int i = 0; i < skyMax - skyMin; i++) {
				if(player.hasPermission("CustomHeightLimit.skyLimit."+(skyMin+i))) {
					skyLimit = skyMin + i;
				}
			}
			player.setMetadata("CustomHeightLimitMin", new FixedMetadataValue(this.plugin, skyLimit));
		}
		if(player.hasPermission("CustomHeightLimit.nolimit.min")) {
			player.setMetadata("CustomHeightLimitMin", new FixedMetadataValue(this.plugin, -1));
		}
		else {
			for(int i = groundMax; i > groundMin; i--) {
				if(player.hasPermission("CustomHeightLimit.groundLimit." + (groundMax-i))) {
					groundLimit = skyMin + i;
				}
			}
			player.setMetadata("CustomHeightLimitMin", new FixedMetadataValue(this.plugin, groundLimit));
		}
		if(this.plugin.getConfig().getBoolean("enableLogging", true)) {
			this.plugin.getLogger().log(Level.INFO, "Player login cought by CustomHeightLimit.  " + player.getName() + " logged in, and has a sky limit of " + skyLimit + "and a ground limit of " + groundLimit);
		}
	}
}
