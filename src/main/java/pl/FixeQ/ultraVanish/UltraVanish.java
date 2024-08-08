package pl.FixeQ.ultraVanish;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraVanish extends JavaPlugin {

    private static UltraVanish instance;
    private VanishCommand vanishCommand;

    @Override
    public void onEnable() {
        instance = this;
        vanishCommand = new VanishCommand(this);

        PluginCommand vanishCmd = this.getCommand("vanish");
        if (vanishCmd != null) {
            vanishCmd.setExecutor(vanishCommand);
        } else {
            getLogger().severe("Command /vanish not found. Please check your plugin.yml.");
        }

        Bukkit.getPluginManager().registerEvents(new VanishListener(), this);
        startActionBarTask();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static UltraVanish getInstance() {
        return instance;
    }

    public VanishCommand getVanishCommand() {
        return vanishCommand;
    }

    private void startActionBarTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : vanishCommand.getVanishedPlayers()) {
                vanishCommand.sendActionBar(player, ChatColor.GRAY + "You are in Vanish mode.");
            }
        }, 0L, 20L);
    }
}
