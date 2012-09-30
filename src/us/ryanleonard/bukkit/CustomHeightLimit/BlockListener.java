package us.ryanleonard.bukkit.CustomHeightLimit;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
	private CustomHeightLimit plugin;
	public BlockListener(CustomHeightLimit plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		boolean allowPlace = true;
		Player player = event.getPlayer();
		int blockHeight = event.getBlockPlaced().getY();
		if(player.hasMetadata("CustomHeightLimitMax")) {
			int maxHeight = player.getMetadata("CustomHeightLimitMax").get(0).asInt();
			if(maxHeight != -1 && blockHeight > maxHeight) {
				allowPlace = false;
			}
		}
		if(player.hasMetadata("CustomHeightLimitMin")) {
			int minHeight = player.getMetadata("CustomHeightLimitMin").get(0).asInt();
			if(minHeight != -1 && blockHeight <= minHeight) {
				allowPlace = false;
			}
		}
		if(!allowPlace) {
			boolean alertUser = this.plugin.getConfig().getBoolean("CustomHeightLimit.alertUser", true);
			if(alertUser) {
				player.sendMessage("You are not allowed to place a block here due to height restrictions.");
			}
			event.setCancelled(true);
		}
		if(this.plugin.getConfig().getBoolean("enableLogging", true)) {
			String allowed = allowPlace ? "allowed" : "denied";
			this.plugin.getLogger().log(Level.INFO, "Block placement event cought by CustomHeightLimit.  A block placed by " + player.getName() + " at a height of " + blockHeight + " was " + allowed + ".");
		}
	}
}
