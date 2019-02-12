package me.logify.spigot.actions.revert;

import me.ifydev.logify.api.structures.Interaction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

/**
 * @author Innectic
 * @since 02/10/2019
 */
public class RevertManager {

    public void revertInteractions(List<Interaction> interaction) {
        interaction.forEach(this::revertInteraction);
    }

    public void revertInteraction(Interaction interaction) {
        Location location = new Location(Bukkit.getWorld(interaction.getLocation().getWorld()), interaction.getLocation().getX(), interaction.getLocation().getY(),
                interaction.getLocation().getZ());
        location.getWorld().getBlockAt(location).setType(Material.getMaterial(interaction.getLocation().getMaterial().orElse("AIR")));
    }
}
