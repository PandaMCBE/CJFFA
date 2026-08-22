package fr.pandamcbe.cjffa.cjffamanager;

import fr.pandamcbe.cjffa.CJFFA;
import fr.pandamcbe.commons.FFAKitsAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class GiveKitsUtils {

    public GiveKitsUtils(Player p) {

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

            FFAKitsAccount a = (FFAKitsAccount) Main.getInstance().manipulation.getShopAccounts(CJFFA.getInstance().mainMap, p.getUniqueId(), AccountType.CJFFAKITS.getKeyPrefix());
                if (a.getGuerrier() == 2) {

                            p.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("§bSword of Warrior").setLore("§7Cette épée n'est peut-être", "§7pas la master sword mais", "§7elle repoussera quand même", "§7beaucoup d'ennemis sur ton chemin !").addEnchant(Enchantment.DAMAGE_ALL, 1).makeUnbreakable().toItemStack());
                            p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 2).toItemStack());

                        ItemStack casque = new ItemBuilder(Material.CHAINMAIL_HELMET).makeUnbreakable().toItemStack();
                        ItemStack plastron = new ItemBuilder(Material.LEATHER_CHESTPLATE).makeUnbreakable().toItemStack();
                        ItemStack jambieres = new ItemBuilder(Material.DIAMOND_LEGGINGS).makeUnbreakable().toItemStack();
                        ItemStack bottes = new ItemBuilder(Material.DIAMOND_BOOTS).makeUnbreakable().toItemStack();

                        putArmor(p, casque, plastron, jambieres, bottes);
                }
                if (a.getArcher() == 2) {

                            p.getInventory().setItem(2, new ItemBuilder(Material.BOW).setName("§aSuper Bow").setLore("§7Prend cet arc,", "§7il appartenait au roi des Pandas...").addEnchant(Enchantment.ARROW_DAMAGE, 1).makeUnbreakable().toItemStack());
                            p.getInventory().setItem(5, new ItemBuilder(Material.ARROW, 2).toItemStack());
                            p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 2).toItemStack());

                        ItemStack casque = new ItemBuilder(Material.IRON_HELMET).makeUnbreakable().toItemStack();
                        ItemStack plastron = new ItemBuilder(Material.GOLD_CHESTPLATE).makeUnbreakable().toItemStack();
                        ItemStack jambieres = new ItemBuilder(Material.IRON_LEGGINGS).makeUnbreakable().toItemStack();
                        ItemStack bottes = new ItemBuilder(Material.IRON_BOOTS).toItemStack();

                        putArmor(p, casque, plastron, jambieres, bottes);
                }
                if (a.getAssassin() == 2) {
//                            p.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("§bSword of Warrior").setLore("§7Cette épée n'est peut-être", "§7pas la master sword mais", "§7elle repoussera quand même", "§7beaucoup d'ennemis sur ton chemin !").addEnchant(Enchantment.DAMAGE_ALL, 1).makeUnbreakable().toItemStack());
                            p.getInventory().addItem(new ItemBuilder(Material.POTION, 2).setSplashType().setName("Poison 4s").setPotionDuration(PotionEffectType.POISON, 20 * 4, 1).toItemStack());
                            p.getInventory().addItem( new ItemBuilder(Material.POTION, 2).setName("Invisibility 30s").setPotionDuration(PotionEffectType.INVISIBILITY, 20 * 30, 3).setLore("§7Cette potion vous rendra complètement", "§7invisible aux yeux des joueurs.", "§7Malgré votre armure et même votre tag.").toItemStack());

                        //            p.getInventory().addItem(new ItemBuilder(Material.POTION).setDurability((byte) 16388).setName("Poison 20s").setPotionDuration(PotionEffectType.POISON, 20, 0).toItemStack());
//            p.getInventory().addItem( new ItemBuilder(Material.POTION).setDurability((byte) 8233).setName("Invisibility 20s").setPotionDuration(PotionEffectType.INVISIBILITY, 20, 0).toItemStack());
                        ItemStack casque = new ItemBuilder(Material.CHAINMAIL_HELMET).makeUnbreakable().toItemStack();
                        ItemStack plastron = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).makeUnbreakable().toItemStack();
                        ItemStack jambieres = new ItemBuilder(Material.DIAMOND_LEGGINGS).makeUnbreakable().toItemStack();
                        ItemStack bottes = new ItemBuilder(Material.LEATHER_BOOTS).makeUnbreakable().toItemStack();

                       putArmor(p, casque, plastron, jambieres, bottes);
                }
                if (a.getPuncher() == 2) {
                        p.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("§bSword of Warrior").setLore("§7Cette épée n'est peut-être", "§7pas la master sword mais", "§7elle repoussera quand même", "§7beaucoup d'ennemis sur ton chemin !").makeUnbreakable().toItemStack());
                        p.getInventory().setItem(6, new ItemBuilder(Material.STICK).setName("§cOne Punch Stick").setLore("§7Repousse tes énemies vers l'infini", "§7et l'au-delà !").addEnchant(Enchantment.KNOCKBACK, 4).toItemStack());
                        p.getInventory().setItem(1, new ItemBuilder(Material.FISHING_ROD).setName("§dUltimate PvP Item").setLore("§7Seuls les pros arrivent à faire", "§7qu'un avec leur canne à pêche,", "§7pour nous défoncer, nous,", "§7les noobs xd.").makeUnbreakable().toItemStack());
                        p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 2).toItemStack());

                        ItemStack casque = new ItemBuilder(Material.DIAMOND_HELMET).makeUnbreakable().toItemStack();
                        ItemStack plastron = new ItemBuilder(Material.IRON_CHESTPLATE).makeUnbreakable().toItemStack();
                        ItemStack jambieres = new ItemBuilder(Material.CHAINMAIL_LEGGINGS).makeUnbreakable().toItemStack();
                        ItemStack bottes = new ItemBuilder(Material.GOLD_BOOTS).makeUnbreakable().toItemStack();

                        putArmor(p, casque, plastron, jambieres, bottes);

                }
                if (a.getCheated() == 2) {
                            p.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("§bSword of Warrior").setLore("§7Cette épée n'est peut-être", "§7pas la master sword mais", "§7elle repoussera quand même", "§7beaucoup d'ennemis sur ton chemin !").makeUnbreakable().toItemStack());
                            p.getInventory().addItem(new ItemBuilder(Material.GOLDEN_APPLE, 1).setDurability((byte) 1).toItemStack());
                            p.getInventory().addItem(new ItemBuilder(Material.ENDER_PEARL, 3).toItemStack());

                        ItemStack casque = new ItemBuilder(Material.GOLD_HELMET).makeUnbreakable().toItemStack();
                        ItemStack plastron = new ItemBuilder(Material.DIAMOND_CHESTPLATE).makeUnbreakable().toItemStack();
                        ItemStack jambieres = new ItemBuilder(Material.IRON_LEGGINGS).makeUnbreakable().toItemStack();
                        ItemStack bottes = new ItemBuilder(Material.CHAINMAIL_BOOTS).makeUnbreakable().toItemStack();

                        putArmor(p, casque, plastron, jambieres, bottes);

                }
        System.out.println("Kits were gived !");
    }

    private void putArmor(Player p, ItemStack casque, ItemStack plastron, ItemStack jambieres, ItemStack bottes){
        p.getInventory().setHelmet(casque);
        p.getInventory().setChestplate(plastron);
        p.getInventory().setLeggings(jambieres);
        p.getInventory().setBoots(bottes);
    }
}
