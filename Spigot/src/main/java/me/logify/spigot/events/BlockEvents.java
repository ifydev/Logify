package me.logify.spigot.events;

import me.ifydev.logify.api.LogifyAPI;
import me.logify.spigot.LogifyMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.UUID;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class BlockEvents implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(api -> {
            Location location = e.getBlock().getLocation();
            UUID player = e.getPlayer().getUniqueId();

            api.getLoggerManager().getBlockLogger().blockBreak(player, location.getBlockX(), location.getBlockY(), location.getBlockZ(),
                    location.getWorld().getName(), e.getBlock().getType().name(), "AIR");
        }));
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(api -> {
            Location location = e.getBlock().getLocation();
            UUID player = e.getPlayer().getUniqueId();

            api.getLoggerManager().getBlockLogger().blockBreak(player, location.getBlockX(), location.getBlockY(), location.getBlockZ(),
                    location.getWorld().getName(), e.getBlock().getType().name(), "AIR");
        }));
    }

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent e) {

    }

    @EventHandler
    public void onBlockBurnEvent(BlockBurnEvent e) {

    }
}
