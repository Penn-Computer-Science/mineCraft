package org.example;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.TNTPrimed;

public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("PENN PLUGIN ENABLED");
    }

    @Override
    public void onDisable() {
        getLogger().info("PLUGIN DISABLED");
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.STICK) {

            World world = event.getEntity().getWorld();

            world.createExplosion(event.getEntity().getLocation(), 125.0f);
            world.strikeLightning(event.getEntity().getLocation());
        }
    }

    @EventHandler

    public void onSnowballThrow(ProjectileLaunchEvent event) {

        if (!(event.getEntity() instanceof Snowball snowball)) {

            return;

        }

        new BukkitRunnable() {

            @Override

            public void run() {

                if (snowball.isDead() || snowball.isOnGround()) {

                    cancel();

                    return;

                }

                snowball.getWorld().spawn(
                        snowball.getLocation(),
                        org.bukkit.entity.TNTPrimed.class
                );

            }

        }.runTaskTimer(this, 0L, 2L);

    }
}