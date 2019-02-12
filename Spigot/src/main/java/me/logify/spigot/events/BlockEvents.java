package me.logify.spigot.events;

import me.ifydev.logify.api.LogifyAPI;
import me.ifydev.logify.api.log.InteractionType;
import me.logify.spigot.LogifyMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Innectic
 * @since 02/07/2019
 */
public class BlockEvents implements Listener {

    private List<me.ifydev.logify.api.structures.Location> getLocations(List<Block> blocks) {
        return blocks.stream().map(b -> new me.ifydev.logify.api.structures.Location(
                b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ(),
                b.getLocation().getWorld().getName(), Optional.of(b.getType().name())))
                .collect(Collectors.toList());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreakEvent(BlockBreakEvent e) {
        if (!LogifyAPI.get().map(api -> api.getBlockModuleConfig().isSubModuleEnabled("break")).orElse(false)) return;

        String type = e.getBlock().getType().name();
        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(api -> {
            Location location = e.getBlock().getLocation();
            UUID player = e.getPlayer().getUniqueId();

            api.getDatabase().logBlockInteraction(InteractionType.Block.BREAK, player, null,
                    location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName(), type, Material.AIR.name());
        }));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        if (!LogifyAPI.get().map(api -> api.getBlockModuleConfig().isSubModuleEnabled("place")).orElse(false)) return;

        String type = e.getBlock().getType().name();

        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(api -> {
            Location location = e.getBlockPlaced().getLocation();
            UUID player = e.getPlayer().getUniqueId();

            api.getDatabase().logBlockInteraction(InteractionType.Block.PLACE, player, null,
                    location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName(), Material.AIR.name(), type);
        }));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockExplodeEvent(EntityExplodeEvent e) {
        if (!LogifyAPI.get().map(api -> api.getBlockModuleConfig().isSubModuleEnabled("explode")).orElse(false)) return;

        List<me.ifydev.logify.api.structures.Location> locations = getLocations(e.blockList());
        Location location = e.getLocation();

        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(api -> {
            UUID event = UUID.randomUUID();
            locations.forEach(l -> api.getDatabase().logBlockInteraction(InteractionType.Block.EXPLODE, null, event,
                    l.getX(), l.getY(), l.getZ(), location.getWorld().getName(), l.getMaterial().orElse(Material.AIR.name()), "AIR"));
        }));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBurnEvent(BlockBurnEvent e) {
        String type = e.getBlock().getType().name();
        if (!LogifyAPI.get().map(api -> api.getBlockModuleConfig().isSubModuleEnabled("burn")).orElse(false)) return;

        Bukkit.getScheduler().runTaskAsynchronously(LogifyMain.getInstance(), () -> LogifyAPI.get().ifPresent(api -> {
            Location location = e.getBlock().getLocation();

            api.getDatabase().logBlockInteraction(InteractionType.Block.BURN, null, null,
                    location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName(), Material.AIR.name(), type);
        }));
    }
}
