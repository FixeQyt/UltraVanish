package pl.FixeQ.ultraVanish;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class VanishCommand implements CommandExecutor {

    private final UltraVanish plugin;
    private final Set<Player> vanishedPlayers = new HashSet<>();

    public VanishCommand(UltraVanish plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return true;
        }

        if (!player.hasPermission("ultravanish.command")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (vanishedPlayers.contains(player)) {
            showPlayer(player);
            vanishedPlayers.remove(player);
            player.sendMessage(ChatColor.GREEN + "You are now visible.");
        } else {
            hidePlayer(player);
            vanishedPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + "You are now vanished.");
            sendActionBar(player, ChatColor.GRAY + "You are in Vanish mode.");
        }

        return true;
    }

    public boolean isVanished(Player player) {
        return vanishedPlayers.contains(player);
    }

    public Set<Player> getVanishedPlayers() {
        return vanishedPlayers;
    }

    private void hidePlayer(Player player) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("ultravanish.see")) {
                p.hidePlayer(plugin, player);
            }
        }
        player.setCollidable(false); 
    }

    private void showPlayer(Player player) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(plugin, player);
        }
        player.setCollidable(true);
    }
    

    void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}
