package me.logify.spigot.commands;

import me.ifydev.logify.api.LogifyAPI;
import me.ifydev.logify.api.log.InteractionType;
import me.ifydev.logify.api.structures.TimeObject;
import me.logify.spigot.actions.revert.RevertManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * @author Innectic
 * @since 02/10/2019
 */
public class TestCommand implements CommandExecutor {

    private me.ifydev.logify.api.structures.Location toLocation(Location location) {
        return new me.ifydev.logify.api.structures.Location(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName(), Optional.empty());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RevertManager manager = new RevertManager();
        Player player = (Player) sender;
        LogifyAPI.get().get().getDatabase().getRecentInteraction(Optional.of(InteractionType.Block.PLACE), args.length == 1 ? Optional.of(TimeObject.fromSeconds(Integer.parseInt(args[0]))) : Optional.empty(), Optional.empty(),
                player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(),
                player.getLocation().getWorld().getName()).forEach(manager::revertInteraction);

        Location location = player.getLocation();
        Location location2 = location.clone();

        location2.setX(location2.getX() + 2);
        location2.setY(location2.getY() + 2);
        location2.setZ(location2.getZ() + 2);

        LogifyAPI.get().get().getDatabase().getRecentInteractionsInRegion(Optional.of(InteractionType.Block.PLACE), Optional.empty(), Optional.empty(), toLocation(location), toLocation(location2)).forEach(manager::revertInteraction);

        return false;
    }
}
