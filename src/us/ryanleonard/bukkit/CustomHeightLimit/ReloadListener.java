package us.ryanleonard.bukkit.CustomHeightLimit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadListener implements CommandExecutor {
	private CustomHeightLimit plugin;
	public ReloadListener(CustomHeightLimit plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
		if(!player.hasPermission("CustomHeightLimit.reloadConfig")) {
			return false;
		}
		this.plugin.reloadConfig();
		player.sendMessage("CustomHeightLimit config reloaded.  Changes will affect players the next time they login.");
		/*
		 * Currently this only reloads the config.
		 * A loop should be added to reload the max/min values for each player.
		 * Currently players should be instructed to relogin to get the new height limit after reloading the config.
		 */
		return true;
	}
}
