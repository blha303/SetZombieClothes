package me.blha303.zombieclothes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Location loc;
		Player target = null;
		EntityType entitytype = null;
		World world;
		if (!sender.hasPermission("zombieclothes.use")) {
			sender.sendMessage("You can't use this command.");
			return true;
		}
		if (sender instanceof Player) {
			Player p = (Player)sender;
			if (args.length < 1) {
				loc = p.getLocation();
			} else {
				if (args.length >= 3) {
					if (args.length == 6) {
						world = getServer().getWorld(args[5]);
					} else {
						world = p.getWorld();
					}
					try {
						loc = new Location(world, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
					} catch (NumberFormatException e) {
						return false;
					}
					if (args.length >= 4) {
						target = getServer().getPlayer(args[3]);
						if (target == null) return false;
						if (args.length == 5) {
							entitytype = EntityType.fromName(args[4]);
						}
					}
				} else {
					return false;
				}
			}
			if (entitytype == null) entitytype = EntityType.ZOMBIE;
			if (target == null) target = p;
			Entity entity = target.getWorld().spawnEntity(loc, entitytype);
			if (entity == null) return false;
			if (entity instanceof LivingEntity) {
				LivingEntity le = (LivingEntity)entity;
				le.getEquipment().setHelmet(target.getEquipment().getHelmet());
				le.getEquipment().setChestplate(target.getEquipment().getChestplate());
				le.getEquipment().setLeggings(target.getEquipment().getLeggings());
				le.getEquipment().setBoots(target.getEquipment().getBoots());
				le.setCustomName(target.getName());
				return true;
			} else {
				sender.sendMessage("Use another entity please, this one won't work.");
				return false;
			}
		} else { ////////////////////////////////
			if (args.length < 1) {
				sender.sendMessage("Every argument is required.");
				return false;
			} else {
				if (args.length < 6) {
					sender.sendMessage("Every argument is required.");
					return false;
				} else {
					world = getServer().getWorld(args[5]);
					if (world == null) { sender.sendMessage("Invalid world name"); return false; }
					try {
						loc = new Location(world, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
					} catch (NumberFormatException e) {
						return false;
					}
					target = getServer().getPlayer(args[3]);
					if (target == null) { sender.sendMessage("Invalid target name"); return false; }
					entitytype = EntityType.fromName(args[4]);
				}
			}
			if (entitytype == null) { sender.sendMessage("Invalid entitytype. Picking zombie instead."); entitytype = EntityType.ZOMBIE; }
			Entity entity = target.getWorld().spawnEntity(loc, entitytype);
			if (entity == null) return false;
			if (entity instanceof LivingEntity) {
				LivingEntity le = (LivingEntity)entity;
				le.getEquipment().setHelmet(target.getEquipment().getHelmet());
				le.getEquipment().setChestplate(target.getEquipment().getChestplate());
				le.getEquipment().setLeggings(target.getEquipment().getLeggings());
				le.getEquipment().setBoots(target.getEquipment().getBoots());
				le.setCustomName(target.getName());
				return true;
			} else {
				sender.sendMessage("Use another entity please, this one won't work.");
				return false;
			}
		}
	}
}
