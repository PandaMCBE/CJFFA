package fr.pandamcbe.cjffa;

import fr.pandamcbe.commons.FFAKitsAccount;
import fr.pandamcbe.mgapi.data.management.redis.RedisAccess;
import fr.pandamcbe.mgapi.mysql.Managers.FFAKits.KitsManager;
import fr.pandamcbe.mgapi.utils.Items.ArmorBuilder;
import fr.pandamcbe.mgapi.utils.Items.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

public class GiveKitsUtils {

    public static GiveKitsUtils giveKitsUtils(Player p) {

//        if (KitsManager.DEFAULT.getPlayerKit(p.getUniqueId()) == 1) {
//            p.getInventory().addItem(new ItemBuilder(Material.STONE_SWORD).setName("§6Noob Sword").setLore("§7Comme tout bon jeu", "§7qui se réspecte,", "§7voici l'arme de base xd").makeUnbreakable().toItemStack());
//            p.getInventory().setItem(8, new ItemBuilder(Material.GOLDEN_APPLE, 4).toItemStack());
//            ItemStack casque = new ArmorBuilder(Material.LEATHER_HELMET).part(ArmorBuilder.ArmorPart.HELMET).makeUnbreakable().toItemStack();
//            ItemStack plastron = new ArmorBuilder(Material.CHAINMAIL_CHESTPLATE).part(ArmorBuilder.ArmorPart.CHESTPLATE).makeUnbreakable().toItemStack();
//            ItemStack jambieres = new ArmorBuilder(Material.LEATHER_LEGGINGS).part(ArmorBuilder.ArmorPart.LEGGINGS).makeUnbreakable().toItemStack();
//            ItemStack bottes = new ArmorBuilder(Material.CHAINMAIL_BOOTS).part(ArmorBuilder.ArmorPart.BOOTS).makeUnbreakable().toItemStack();
//
//            p.getInventory().setHelmet(casque);p.getInventory().setChestplate(plastron);p.getInventory().setLeggings(jambieres);p.getInventory().setBoots(bottes);
//
//        }
        Bukkit.getScheduler().runTaskAsynchronously(fr.pandamcbe.mgapi.Main.INSTANCE, ()-> {
            RedisAccess redisAccess = RedisAccess.INSTANCE;
            RedissonClient redissonClient = redisAccess.getRedissonClient();
            RBucket<FFAKitsAccount> accountRBucket = redissonClient.getBucket("FFAKitsAccount" + p.getUniqueId().toString());
            FFAKitsAccount a = accountRBucket.get();
            if (KitsManager.GUERRIER.getPlayerKit(p.getUniqueId()) == 1 && a.isGuerrier()) {
                p.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("§bSword of Warrior").setLore("§7Cette épée n'est peut-être", "§7pas la master sword mais", "§7elle repoussera quand même", "§7beaucoup d'ennemis sur ton chemin !").makeUnbreakable().toItemStack());
                p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 2).toItemStack());
                ItemStack casque = new ArmorBuilder(Material.GOLD_HELMET).part(ArmorBuilder.ArmorPart.HELMET).makeUnbreakable().toItemStack();
                ItemStack plastron = new ArmorBuilder(Material.LEATHER_CHESTPLATE).part(ArmorBuilder.ArmorPart.CHESTPLATE).makeUnbreakable().toItemStack();
                ItemStack jambieres = new ArmorBuilder(Material.LEATHER_LEGGINGS).part(ArmorBuilder.ArmorPart.LEGGINGS).makeUnbreakable().toItemStack();
                ItemStack bottes = new ArmorBuilder(Material.CHAINMAIL_BOOTS).part(ArmorBuilder.ArmorPart.BOOTS).makeUnbreakable().toItemStack();

                p.getInventory().setHelmet(casque);
                p.getInventory().setChestplate(plastron);
                p.getInventory().setLeggings(jambieres);
                p.getInventory().setBoots(bottes);

            }
            if (KitsManager.ARCHER.getPlayerKit(p.getUniqueId()) == 1 && a.isArcher()) {
                p.getInventory().setItem(2, new ItemBuilder(Material.BOW).setName("§aSuper Bow").setLore("§7Prend cet arc,", "§7il appartenait au roi des Pandas").addEnchant(Enchantment.ARROW_DAMAGE, 3).makeUnbreakable().toItemStack());
                p.getInventory().setItem(5, new ItemBuilder(Material.ARROW, 4).toItemStack());
                p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 2).toItemStack());
                ItemStack casque = new ArmorBuilder(Material.IRON_HELMET).part(ArmorBuilder.ArmorPart.HELMET).makeUnbreakable().toItemStack();
                ItemStack plastron = new ArmorBuilder(Material.IRON_CHESTPLATE).part(ArmorBuilder.ArmorPart.CHESTPLATE).makeUnbreakable().toItemStack();
                ItemStack jambieres = new ArmorBuilder(Material.CHAINMAIL_LEGGINGS).part(ArmorBuilder.ArmorPart.LEGGINGS).makeUnbreakable().toItemStack();
                ItemStack bottes = new ArmorBuilder(Material.GOLD_BOOTS).part(ArmorBuilder.ArmorPart.BOOTS).makeUnbreakable().toItemStack();

                p.getInventory().setHelmet(casque);
                p.getInventory().setChestplate(plastron);
                p.getInventory().setLeggings(jambieres);
                p.getInventory().setBoots(bottes);

            }

            if (KitsManager.ASSASSIN.getPlayerKit(p.getUniqueId()) == 1 && a.isArcher()) {
                p.getInventory().setItem(3, new ItemBuilder(Material.POTION).setSplashType().setName("Poison 20s").setPotionDuration(PotionEffectType.POISON, 400, 1).toItemStack());
                p.getInventory().setItem(4, new ItemBuilder(Material.POTION).setName("Invisibility 20s").setPotionDuration(PotionEffectType.INVISIBILITY, 400, 1).toItemStack());
//            p.getInventory().addItem(new ItemBuilder(Material.POTION).setDurability((byte) 16388).setName("Poison 20s").setPotionDuration(PotionEffectType.POISON, 20, 0).toItemStack());
//            p.getInventory().addItem( new ItemBuilder(Material.POTION).setDurability((byte) 8233).setName("Invisibility 20s").setPotionDuration(PotionEffectType.INVISIBILITY, 20, 0).toItemStack());
                p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE).toItemStack());
                ItemStack casque = new ArmorBuilder(Material.IRON_HELMET).part(ArmorBuilder.ArmorPart.HELMET).makeUnbreakable().toItemStack();
                ItemStack plastron = new ArmorBuilder(Material.DIAMOND_CHESTPLATE).part(ArmorBuilder.ArmorPart.CHESTPLATE).makeUnbreakable().toItemStack();
                ItemStack jambieres = new ArmorBuilder(Material.IRON_LEGGINGS).part(ArmorBuilder.ArmorPart.LEGGINGS).makeUnbreakable().toItemStack();
                ItemStack bottes = new ArmorBuilder(Material.GOLD_BOOTS).part(ArmorBuilder.ArmorPart.BOOTS).makeUnbreakable().toItemStack();

                p.getInventory().setHelmet(casque);
                p.getInventory().setChestplate(plastron);
                p.getInventory().setLeggings(jambieres);
                p.getInventory().setBoots(bottes);

            }
            if (KitsManager.PUNCHER.getPlayerKit(p.getUniqueId()) == 1 && a.isAssassin()) {
                p.getInventory().setItem(6, new ItemBuilder(Material.STICK).setName("§cOne Punch Stick").setLore("§7Repousse tes énemies vers l'infini", "§7et l'au-delà !").addEnchant(Enchantment.KNOCKBACK, 4).toItemStack());
                p.getInventory().setItem(1, new ItemBuilder(Material.FISHING_ROD).setName("§dUltimate PvP Item").setLore("§7Seuls les pros arrivent à faire", "§7qu'un avec leur canne à pêche,", "§7pour nous défoncer, nous,", "§7les noobs xd").makeUnbreakable().toItemStack());
                p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 2).toItemStack());
                ItemStack casque = new ArmorBuilder(Material.IRON_HELMET).part(ArmorBuilder.ArmorPart.HELMET).makeUnbreakable().toItemStack();
                ItemStack plastron = new ArmorBuilder(Material.DIAMOND_CHESTPLATE).part(ArmorBuilder.ArmorPart.CHESTPLATE).makeUnbreakable().toItemStack();
                ItemStack jambieres = new ArmorBuilder(Material.IRON_LEGGINGS).part(ArmorBuilder.ArmorPart.LEGGINGS).makeUnbreakable().toItemStack();
                ItemStack bottes = new ArmorBuilder(Material.GOLD_BOOTS).part(ArmorBuilder.ArmorPart.BOOTS).makeUnbreakable().toItemStack();

                p.getInventory().setHelmet(casque);
                p.getInventory().setChestplate(plastron);
                p.getInventory().setLeggings(jambieres);
                p.getInventory().setBoots(bottes);

            }
            if (KitsManager.CHEATED.getPlayerKit(p.getUniqueId()) == 1 && a.isCheated()) {
                p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 1).setDurability((byte) 1).toItemStack());
                p.getInventory().addItem(new ItemBuilder(Material.ENDER_PEARL, 6).toItemStack());
                ItemStack casque = new ArmorBuilder(Material.IRON_HELMET).part(ArmorBuilder.ArmorPart.HELMET).makeUnbreakable().toItemStack();
                ItemStack plastron = new ArmorBuilder(Material.DIAMOND_CHESTPLATE).part(ArmorBuilder.ArmorPart.CHESTPLATE).makeUnbreakable().toItemStack();
                ItemStack jambieres = new ArmorBuilder(Material.DIAMOND_LEGGINGS).part(ArmorBuilder.ArmorPart.LEGGINGS).makeUnbreakable().toItemStack();
                ItemStack bottes = new ArmorBuilder(Material.GOLD_BOOTS).part(ArmorBuilder.ArmorPart.BOOTS).makeUnbreakable().toItemStack();

                p.getInventory().setHelmet(casque);
                p.getInventory().setChestplate(plastron);
                p.getInventory().setLeggings(jambieres);
                p.getInventory().setBoots(bottes);

            }
        });
        return null;
    }
}
