package me.logify.spigot.events;

import me.ifydev.logify.api.LogifyAPI;
import me.ifydev.logify.api.log.InteractionType;
import me.logify.spigot.LogifyMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

/**
 * @author Innectic
 * @since 02/09/2019
 */
public class PlayerEvents implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Optional<LogifyAPI> api = LogifyAPI.get();
        if (!api.map(a -> a.getPlayerModuleConfig().isSubModuleEnabled("join")).orElse(false)) return;
        if (e.getPlayer() == null) return;

        Player player = e .getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(a ->
                a.getDatabase().logPlayerInteraction(InteractionType.Player.JOIN, player.getUniqueId())));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Optional<LogifyAPI> api = LogifyAPI.get();
        if (!api.map(a -> a.getPlayerModuleConfig().isSubModuleEnabled("leave")).orElse(false)) return;
        if (e.getPlayer() == null) return;

        Player player = e .getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(a ->
                a.getDatabase().logPlayerInteraction(InteractionType.Player.LEAVE, player.getUniqueId())));
    }
}
