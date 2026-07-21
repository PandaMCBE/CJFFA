package fr.pandamcbe.cjffa.listeners;


import fr.pandamcbe.cjffa.CJFFA;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ParameterListeners implements Listener {
    Map<UUID, HashMap<ItemStack, Long>> cooldownUseID = new HashMap<>();
    Map<UUID, ItemStack[]> armor = new HashMap<>();
    //EVENTS:

    @EventHandler
    void onTakeDamages(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {

            Player p = (Player) event.getEntity();
            Location spawn = new Location(p.getWorld(), -108.500, 24.0, -110.500 - 1.0f - 1.0f);
            if (p.getLocation().distanceSquared(spawn) <= 5 * 5) {
                event.setCancelled(true);
            }

        }
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
            // Annulez les dégâts de chute
            event.setCancelled(true);
        }

    }

    @EventHandler
    void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // Annule le changement de niveau de faim du joueur
            event.setCancelled(true);

            // Définit le niveau de faim du joueur à 20 (maximum)
            player.setFoodLevel(20);
        }
    }

//    @EventHandler
//    void onPlayerFighting(EntityDamageByEntityEvent e) {
//        Entity entity = e.getEntity();
//        if(e.getDamager() instanceof Player) {
//            Player p = (Player) entity;
//            if (p.getInventory().getChestplate().getItemMeta().getDisplayName() != null) {
//                if (p.getInventory().getChestplate().getItemMeta().getDisplayName().equalsIgnoreCase("§6Tank"))
//                    p.setVelocity(p.getLocation().getDirection().setY(0).normalize().multiply(5));
//            }
//        }
//    }

    @EventHandler
    public void ProjectileHit(ProjectileHitEvent event){
        if (event.getEntity() instanceof Arrow){
            Arrow arrow = (Arrow) event.getEntity();
            arrow.remove();
        }
    }

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();

        // Vérifier si le joueur quitte le sol (en passant d'une position Y à une autre)
        if (to.getY() > player.getLocation().getY()) {
            // Le joueur a quitté le sol, vérifier les blocs adjacents (y compris les côtés du cactus)
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -1; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        Block block = player.getLocation().subtract(xOffset, yOffset, zOffset).getBlock();

                        // Vérifier si le bloc est un cactus
                        if (block.getType() == Material.CACTUS) {
                            // Appliquer l'effet de jump_boost
                            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 24, true));
                            return;  // Sortir de la boucle si un cactus est trouvé
                        }
                    }
                }
            }
        }
   }

    @EventHandler
    void onPlayerDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack it = new ItemStack(Material.EGG);
        Item item = event.getItemDrop();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(true);
        if(item.getItemStack().equals(it)) event.setCancelled(true);

        //Régler le problème que l'on puisse toujours drop
    }

    @EventHandler
    void onWeatherChange(WeatherChangeEvent event){
        event.setCancelled(true);
    }

    //SECTION ITEMS SPECIAUX
    @EventHandler
    void onPickup(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        Item item = event.getItem();
        getItemKS(item, p);
    }
    
    @EventHandler
    void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        //Mettre un cooldown à plusieurs items
        long currentTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long three = TimeUnit.MILLISECONDS.toSeconds(3000L);
        long fifteen = TimeUnit.MILLISECONDS.toSeconds(15000L);
        long ten = TimeUnit.MILLISECONDS.toSeconds(10000L);
        long twenty = TimeUnit.MILLISECONDS.toSeconds(20000L);
        ItemStack i = e.getPlayer().getItemInHand();
        ItemStack cloneItem = i.clone();
        if(i.hasItemMeta()) {
        ItemMeta meta = i.getItemMeta();
        String displayname = meta.getDisplayName();
        if(!cooldownUseID.containsKey(p.getUniqueId()))
            cooldownUseID.put(p.getUniqueId(), new HashMap<>());
        HashMap<ItemStack, Long> cooldownContent = cooldownUseID.get(p.getUniqueId()); //est égale à l'hashmap de cooldownID
            if(displayname != null) {
                switch (displayname) {
                    case "§bArme Suprême":
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (!cooldownContent.containsKey(cloneItem)) {
                                cooldownContent.put(cloneItem, currentTime);
                            } else {

                                long timeElapsed = currentTime - cooldownContent.get(cloneItem);
                                if (timeElapsed >= fifteen) {
                                    cooldownContent.put(cloneItem, currentTime);
                                } else {
                                    e.setCancelled(true);
                                    p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §cVous devez attendre encore " + (fifteen - timeElapsed) + "§cs");
                                }
                            }

                            if (cooldownContent.get(cloneItem).equals(currentTime))
                                p.getInventory().addItem(cloneItem);
                        }
                        break;
                    case "§bPyroSword":
                        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 5, 5, true, false));
                        }
                        break;
                    case "§aMASTER SWORD":
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (!cooldownContent.containsKey(cloneItem)) {
                                cooldownContent.put(cloneItem, currentTime);
                                LaunchArrows(p);
                            } else {
                                long timeElapsed = currentTime - cooldownContent.get(cloneItem);
                                if (timeElapsed >= three) {
                                    cooldownContent.put(cloneItem, currentTime);
                                    LaunchArrows(p);
                                } else
                                    p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §cVous devez attendre encore " + (three - timeElapsed) + "§cs");
                            }
                        }
                        break;
                    case "§6Jump Boost":
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (!cooldownContent.containsKey(cloneItem)) {
                                cooldownContent.put(cloneItem, currentTime);
                                p.setVelocity(p.getLocation().getDirection().setY(3));
                            } else {
                                long timeElapsed = currentTime - cooldownContent.get(cloneItem);
                                if (timeElapsed >= ten) {
                                    cooldownContent.put(cloneItem, currentTime);
                                    p.setVelocity(p.getLocation().getDirection().setY(3));
                                } else
                                    p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §cVous devez attendre encore " + (ten - timeElapsed) + "§cs");
                            }
                            e.setCancelled(true);
                        }
                        break;
                    case "§aZeus":
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (!cooldownContent.containsKey(cloneItem)) {
                                cooldownContent.put(cloneItem, currentTime);
                                strikeLighning(p);
                            } else {
                                long timeElapsed = currentTime - cooldownContent.get(cloneItem);
                                if (timeElapsed >= twenty) {
                                    cooldownContent.put(cloneItem, currentTime);
                                    strikeLighning(p);
                                } else
                                    p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §cVous devez attendre encore " + (twenty - timeElapsed) + "§cs");
                            }
                            e.setCancelled(true);
                        }
                        break;
                    case "§aKFC Warrior":
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            for (Player player : p.getLocation().getWorld().getPlayers()) {
                                if (player != p) {
                                    Location loc = player.getLocation();
                                    spawnAngryChicken(loc);
                                    p.getInventory().remove(Material.EGG);
                                    break;
                                }
                            }
                            e.setCancelled(true);
                        }
                        break;
                    case "§6Blind Attack":
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (!cooldownContent.containsKey(cloneItem)) {
                                cooldownContent.put(cloneItem, currentTime);
                                BlindOthers(p);
                            } else {
                                long timeElapsed = currentTime - cooldownContent.get(cloneItem);
                                if (timeElapsed >= TimeUnit.SECONDS.toSeconds(fifteen)) {
                                    cooldownContent.put(cloneItem, currentTime);
                                    BlindOthers(p);
                                } else
                                    p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §cVous devez attendre encore " + (TimeUnit.SECONDS.toSeconds(fifteen) - timeElapsed) + "§cs");
                            }
                        }
                        break;
                    case "§bCalcium Boost":
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 30, 5, true, false));
                            p.getInventory().remove(cloneItem);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    void drinking(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack i = event.getItem();
        ItemMeta meta = i.getItemMeta();
        String displayname = meta.getDisplayName();
        if(displayname != null) {
            if (displayname.equalsIgnoreCase("Invisibility 1min")) {
                armor.put(player.getUniqueId(), new ItemStack[4]);
                ItemStack[] armorStacks = armor.get(player.getUniqueId());
                armorStacks[3] = player.getInventory().getHelmet();
                armorStacks[2] = player.getInventory().getChestplate();
                armorStacks[1] = player.getInventory().getLeggings();
                armorStacks[0] = player.getInventory().getBoots();
                player.getInventory().setArmorContents(null);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 2));
                Team team = CJFFA.getInstance().board.getEntryTeam(player.getName());

                if (team != null) {
                    team.setNameTagVisibility(NameTagVisibility.NEVER);
                }

                    Bukkit.getScheduler().runTaskLater(CJFFA.getInstance(), () -> {
                        player.getInventory().setArmorContents(armor.get(player.getUniqueId()));
                        if(team != null) {
                        team.unregister();
                        }

                    }, 20 * 60L);
            }
        }

    }

    //UTIL METHODS

    private void LaunchRemoveTimer(Player p, Item item) {
        ItemStack clone = item.getItemStack().clone();
        long delai = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
        CJFFA.getInstance().itemsRemoveID.computeIfAbsent(p.getUniqueId(), k -> new HashMap<>()).put(clone, delai);
    }

    private void getItemKS(Item item, Player p){
        ItemStack itemStack = item.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayname = itemMeta.getDisplayName();
        if(displayname != null) {
            LaunchRemoveTimer(p, item);
//        for(ItemStack itemGold : CJFFA.getInstance().spawnItems.getGoldList()) {
//            if (displayname.equalsIgnoreCase(itemGold.getItemMeta().getDisplayName())) {
//                if (account.getKillstreaks() <= CJFFA.getInstance().getSpawners.createGoldSpawn().getKillstreak()) {
//                    event.setCancelled(true);
//                    p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l§cVous devez obtenir un KillStreak de §f" + CJFFA.getInstance().getSpawners.createGoldSpawn().getKillstreak() + "§c, vous êtes à §f" + account.getKillstreaks());
//                } else {
//                    account.setKillstreaks(account.getKillstreaks() - CJFFA.getInstance().getSpawners.createGoldSpawn().getKillstreak());
//                    LaunchRemoveTimer(p, item);
//                }
//                break;
////            }
//        }
//        for(ItemStack itemDiamond : CJFFA.getInstance().spawnItems.getDiamondList()) {
//            if (displayname.equalsIgnoreCase(itemDiamond.getItemMeta().getDisplayName())) {
//                if (account.getKillstreaks() <= CJFFA.getInstance().getSpawners.createDiamondSpawn().getKillstreak()) {
//                    event.setCancelled(true);
//                    p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §cVous devez obtenir un KillStreak de §f" + CJFFA.getInstance().getSpawners.createDiamondSpawn().getKillstreak() + "§c, vous êtes à §f" + account.getKillstreaks());
//                } else {
//                    account.setKillstreaks(account.getKillstreaks()- CJFFA.getInstance().getSpawners.createDiamondSpawn().getKillstreak());
//                    LaunchRemoveTimer(p, item);
//                }
//                break;
////            }
//        }
//        for(ItemStack itemEmerald : CJFFA.getInstance().spawnItems.getEmeraldList()) {
//            if (displayname.equalsIgnoreCase(itemEmerald.getItemMeta().getDisplayName())) {
//                if (account.getKillstreaks() < CJFFA.getInstance().getSpawners.createEmeraldSpawn().getKillstreak()) {
//                    event.setCancelled(true);
//                    p.sendMessage("§8§l[§4§lCJFFA§8§l] §d§l> §cVous devez obtenir un KillStreak de §f" + CJFFA.getInstance().getSpawners.createEmeraldSpawn().getKillstreak() + "§c, vous êtes à §f" + account.getKillstreaks());
//                } else {
//                    account.setKillstreaks(account.getKillstreaks() - CJFFA.getInstance().getSpawners.createEmeraldSpawn().getKillstreak());
//                    LaunchRemoveTimer(p, item);
//                }
//                break;
////            }
//        }
////            Main.getInstance().sendMemToCache.pushKillStreaks(p.getUniqueId(), Main.getInstance().table, account);
//        }
        }
    }

    private void LaunchArrows(Player p){
        Arrow arrow = p.launchProjectile(Arrow.class);
        arrow.spigot().setDamage(2);
        arrow.setVelocity(p.getLocation().getDirection().multiply(3)); //ajuster sa vitesse
    }

    private void BlindOthers(Player p){
        for (Player player : p.getLocation().getWorld().getPlayers()) {
            if (player != p) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 30, 3, true, false));
                break;
            }
        }
    }
    private void strikeLighning (Player p){
        for (Player player : p.getLocation().getWorld().getPlayers()) {
            if (player != p) {
                Location playerLocation = player.getLocation();
                Location lightningLocation = playerLocation.clone().add(0, 3, 0); //faire apparaître l'éclair 3 blocks au dessus du joueur
                player.getWorld().strikeLightning(lightningLocation);
                break;
            }
        }
    }

    private void spawnAngryChicken(Location loc) {
        WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        EntityChicken chicken = new EntityChicken(nmsWorld);

        // Modifier la vitesse de déplacement (base: 0.25D, là c’est bien plus rapide)
        chicken.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);

        // Modifier la vie (par défaut : 4.0)
        chicken.getAttributeInstance(GenericAttributes.maxHealth).setValue(999999.0D); // 20 coeurs
        chicken.setHealth(40.0F); // à faire APRES avoir défini la maxHealth

        if (chicken.getAttributeMap().a(GenericAttributes.ATTACK_DAMAGE) == null) {
            chicken.getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE);
        }
        chicken.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6.0D); // dégâts comme une épée en fer
        // Définir l'emplacement
        chicken.setPosition(loc.getX(), loc.getY(), loc.getZ());

        // Nettoyage des pathfinders existants
        ((NavigationAbstract) chicken.getNavigation()).n(); // reset navigation
        ((EntityInsentient) chicken).goalSelector.a(); // clear goal selectors
        ((EntityInsentient) chicken).targetSelector.a(); // clear target selectors


        // Comportements (PathfinderGoals)
        ((EntityInsentient) chicken).goalSelector.a(0, new PathfinderGoalFloat(chicken));
        ((EntityInsentient) chicken).goalSelector.a(1, new PathfinderGoalMeleeAttack(chicken, 1.2D, true));
        ((EntityInsentient) chicken).goalSelector.a(2, new PathfinderGoalRandomStroll(chicken, 1.0D));
        ((EntityInsentient) chicken).goalSelector.a(3, new PathfinderGoalLookAtPlayer(chicken, EntityHuman.class, 8.0F));
        ((EntityInsentient) chicken).goalSelector.a(4, new PathfinderGoalRandomLookaround(chicken));
        ((EntityInsentient) chicken).targetSelector.a(1, new PathfinderGoalHurtByTarget(chicken, true));
        ((EntityInsentient) chicken).targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(chicken, EntityHuman.class, true));
        // Ajouter au monde
        nmsWorld.addEntity(chicken);
//        ItemStack eggDropped = new ItemStack(Material.EGG);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : chicken.getBukkitEntity().getNearbyEntities(1.5, 1.5, 1.5)) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (player.getHealth() > 6) {
                            player.damage(6.0, (Entity) chicken.getBukkitEntity());
                        } else {
                            chicken.getBukkitEntity().remove();
                            cancel();
                        }
                        break;
                    }
                }
            }
        }.runTaskTimer(CJFFA.getInstance(), 0L, 20L);
    }
}