package us.ryanleonard.bukkit.CustomHeightLimit;

import org.bukkit.plugin.java.JavaPlugin;

public class CustomHeightLimit extends JavaPlugin {
	@Override
	public void onEnable() {
		new LoginListener(this);
		new BlockListener(this);
		getCommand("reloadHeightLimit").setExecutor(new ReloadListener(this));
	}
}
