package me.logify.spigot.events;

import me.ifydev.logify.api.LogifyAPI;
import me.logify.spigot.LogifyMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class BlockEvents implements Listener {

    private List<me.ifydev.logify.api.log.Location> getLocations(List<Block> blocks) {
        return blocks.stream().map(b -> new me.ifydev.logify.api.log.Location(
                    b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ(), b.getType().name()))
                .collect(Collectors.toList());
    }

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

            api.getLoggerManager().getBlockLogger().blockPlace(player, location.getBlockX(), location.getBlockY(), location.getBlockZ(),
                    location.getWorld().getName(), "AIR", e.getBlock().getType().name());
        }));
    }

    @EventHandler
    public void onBlockExplodeEvent(EntityExplodeEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(api -> {
            Location location = e.getLocation();
            UUID eventId = UUID.randomUUID();

            api.getLoggerManager().getBlockLogger().regionExplode(null, eventId, location.getWorld().getName(), getLocations(e.blockList()), "AIR");
        }));
    }

    @EventHandler
    public void onBlockBurnEvent(BlockBurnEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(api -> {
            Location location = e.getBlock().getLocation();

            api.getLoggerManager().getBlockLogger().blockBurn(null, location.getBlockX(), location.getBlockY(),
                    location.getBlockZ(), location.getWorld().getName(), e.getBlock().getType().name(), "AIR");
        }));
    }
}
