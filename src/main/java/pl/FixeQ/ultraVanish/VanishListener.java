package pl.FixeQ.ultraVanish;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;

public class VanishListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joiningPlayer = event.getPlayer();

        for (Player vanishedPlayer : Bukkit.getOnlinePlayers()) {
            if (vanishedPlayer.hasPermission("ultravanish.command")) {
                if (UltraVanish.getInstance().getVanishCommand().isVanished(vanishedPlayer)) {
                    if (!joiningPlayer.hasPermission("ultravanish.see")) {
                        joiningPlayer.hidePlayer(UltraVanish.getInstance(), vanishedPlayer);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player quittingPlayer = event.getPlayer();

        if (UltraVanish.getInstance().getVanishCommand().isVanished(quittingPlayer)) {
            quittingPlayer.setInvisible(false);
        }
    }
}
