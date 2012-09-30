package us.ryanleonard.bukkit.CustomHeightLimit;

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
		if(player.hasPermission("CustomHeightLimit.nolimit.min")) {
			player.setMetadata("CustomHeightLimitMin", new FixedMetadataValue(this.plugin, -1));
		}
		else {
			/*
			 * The highest sky limit set will be used for the max
			 */
			int limit = 0;
			for(int i = 0; i < skyMax - skyMin; i++) {
				if(player.hasPermission("CustomHeightLimit.skyLimit."+(skyMin+i))) {
					limit = skyMin + i;
				}
			}
			player.setMetadata("CustomHeightLimitMin", new FixedMetadataValue(this.plugin, limit));
		}
		if(player.hasPermission("CustomHeightLimit.nolimit.max")) {
			player.setMetadata("CustomHeightLimitMax", new FixedMetadataValue(this.plugin, -1));
		}
		else {
			int limit = 0;
			for(int i = groundMax; i > groundMin; i--) {
				if(player.hasPermission("CustomHeightLimit.groundLimit." + (groundMax-i))) {
					limit = skyMin + i;
				}
			}
			player.setMetadata("CustomHeightLimitMin", new FixedMetadataValue(this.plugin, limit));
		}
	}
}
