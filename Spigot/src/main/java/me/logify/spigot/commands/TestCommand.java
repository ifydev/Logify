package me.logify.spigot.commands;

import me.ifydev.logify.api.LogifyAPI;
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        LogifyAPI.get().get().getDatabase().getRecentInteraction(Optional.empty(), Optional.empty(), Optional.empty(),
                player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(),
                player.getLocation().getWorld().getName()).forEach(interaction -> {
                    player.sendMessage(interaction.getWhen() + ", " + interaction.getLocation() + ", " + interaction.getType());
        });
        return false;
    }
}
