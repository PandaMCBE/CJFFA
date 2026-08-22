package fr.pandamcbe.cjffa.cjffamanager;

import fr.pandamcbe.cjffa.CJFFA;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class SpawnItems {
    private int timer;
    private int task;
    private Spawners emeraldSpawners;
    private Spawners diamondSpawners;
    private Spawners goldSpawners;

    public SpawnItems(CJFFA plugin, int task, int timer) {
        this.task = task;
        this.timer = timer;
        this.emeraldSpawners = plugin.getSpawners.createEmeraldSpawn();
        this.diamondSpawners = plugin.getSpawners.createDiamondSpawn();
        this.goldSpawners = plugin.getSpawners.createGoldSpawn();
    }

    private List<ItemStack> goldList;
    private List<ItemStack> diamondList;
    private List<ItemStack> emeraldList;


    private World world = Bukkit.getWorld("worldffa");

    private ItemStack getTankChestplate(){
        ItemStack TankStack = new ItemStack(Material.IRON_CHESTPLATE);
        List<String> loreTank = new ArrayList<>();
        loreTank.add("");
        loreTank.add("§fVous croulez sous le poids");
        loreTank.add("§fd'un tank. Par conséquent,");
        loreTank.add("§fvous résistez désormais");
        loreTank.add("§fà de gros knockbacks.");
        loreTank.add("");
        ItemMeta TankMeta = TankStack.getItemMeta();
        TankMeta.setDisplayName("§6Tank");
        TankMeta.setLore(loreTank);
        TankMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        TankMeta.spigot().setUnbreakable(true);
        TankStack.setItemMeta(TankMeta);
        return TankStack;
    }

    public void addAllItems(){
     goldList = new ArrayList<>();
     diamondList = new ArrayList<>();
     emeraldList = new ArrayList<>();

        ItemStack Tank = new ItemBuilder(getTankChestplate()).setAttributs("knockbackResistance", "generic.knockbackResistance", 0.4D, 0, 1);
        goldList.add(Tank);
        ItemStack Blind = new ItemBuilder(Material.INK_SACK).setName("§6Blind Attack").setLore("§fCette attaque va rendre", "§ftous les joueurs", "§fincapables de voir.", "", "§7Clique-Droit", "").toItemStack();
        goldList.add(Blind);
        ItemStack Jump = new ItemBuilder(Material.SLIME_BLOCK).setName("§6Jump Boost").setLore("§fD'habitude, dans tous les serveurs,", "§fcet item est une plume. Mais pour", "§fêtre plus original, j'ai remarqué", "§fdans le film Minecraft que le", "§fslime_block a été utilisé dans une scène", "§fpour sauter alors voilà..", "§fVous pouvez vous en servir", "§fpour attraper des cactus ou fuir.", "", "§7Clique-Droit", "").toItemStack();
        goldList.add(Jump);
        ItemStack Fire = new ItemBuilder(Material.GOLD_SWORD).setName("§bPyroSword").setLore("§fOn dirait l'effet d'un souffle du feu.", "", "§7+Fire Resistance", "").addEnchant(Enchantment.FIRE_ASPECT, 2).toItemStack();//.setDurability((short) 22)
//        Fire = new ItemBuilder(Fire).setAttributs("burning_time", "Fire Resistance", 0, 0, "chest");
        diamondList.add(Fire);
        ItemStack Teleportation = new ItemBuilder(Material.ENDER_PEARL).setName("§bArme Suprême").setLore("§fCette arme aurait peut-être", "§fservi à Herobrine ?", "", "§7Clique-Droit", "").addEnchant(Enchantment.DAMAGE_ALL, 4).toItemStack();
        diamondList.add(Teleportation);
//        ItemStack MynthosJuice = new ItemBuilder(Material.POTION).setDurability((byte) 8225).setName("§bJus de Mynthos").setLore("§fCe jus ferait un parfait cocktail", "§fque l'on pourrait appeler", "§fle Mynthos Paradise (une ref)", "", "§7donne des effets sympas", "§7(pas de la drogue)").toItemStack();
//        this.diamondList.add(MynthosJuice);
        ItemStack SuperCalciumMan = new ItemBuilder(Material.MILK_BUCKET).setName("§bCalcium Boost").setLore("§fDevenez Super Calcium Man, en buvant","§fce breuvage qui vous donnera", "§fla résistance à tous les dégats." ,"", "§7Dure 30s", "⚠ §cAttention un clique suffis", "").addEnchant(Enchantment.ARROW_DAMAGE, 1).toItemStack();
        diamondList.add(SuperCalciumMan);
        ItemStack Mastersword = new ItemBuilder(Material.DIAMOND_SWORD).setName("§aMASTER SWORD").setLore("§fen gros c'est la master des swords", "", "§7Clique-Droit pour", "§7tirer des flèches.", "").addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 1).toItemStack(); //.setDurability((short) 1461) durabilité = 100
       emeraldList.add(Mastersword);
        ItemStack chicken = new ItemBuilder(Material.EGG).setName("§aKFC Warrior").setLore("§fCe poulet est une vraie", "§fmachine de guerre.", "§fSurtout après avoir vu", "§fsa famille dans un", "§fbucket KFC... Snif..", "", "§7Clique-Droit", "").toItemStack();
        emeraldList.add(chicken);
        ItemStack NetherStar = new ItemBuilder(Material.NETHER_STAR).setName("§aZeus").setLore("§fComme vous l'avez (peut-être)", "§fcompris, vous disposez du", "§fpouvoir d'abattre les éclairs", "§fsur les autres joueurs.", "", "§7Cela ne oneshot pas.", "§7Clique-Droit", "").toItemStack();
        emeraldList.add(NetherStar);
    }

    public List<ItemStack> getGoldList() {
        return goldList;
    }

    public List<ItemStack> getDiamondList() {
        return diamondList;
    }

    public List<ItemStack> getEmeraldList() {
        return emeraldList;
    }

    private void spawngoldItem(){
        if (goldSpawners != null) {
            for(Location loc : goldSpawners.getSpawns()) {
                ItemStack original = getGoldList().get(new Random().nextInt(getGoldList().size()));
                ItemStack randomItem = original.clone();
                ItemMeta meta = randomItem.getItemMeta();
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                lore.add("§7ID: " + new Random().nextInt(10000));
                meta.setLore(lore);
                randomItem.setItemMeta(meta);
                world.dropItem(loc, randomItem);
            }
        }
    }

    private void spawndiamondItem(){
        if (diamondSpawners != null) {
            for(Location loc : diamondSpawners.getSpawns()) {
                ItemStack original = getDiamondList().get(new Random().nextInt(getDiamondList().size()));
                ItemStack randomItem = original.clone();
                ItemMeta meta = randomItem.getItemMeta();
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                lore.add("§7ID: " + new Random().nextInt(10000));
                meta.setLore(lore);
                randomItem.setItemMeta(meta);
                world.dropItem(loc, randomItem);
            }
        }
    }

    private void spawnemeraldItem(){
        if (emeraldSpawners != null) {
            for(Location loc : emeraldSpawners.getSpawns()) {
                ItemStack original = getEmeraldList().get(new Random().nextInt(getEmeraldList().size()));
                ItemStack randomItem = original.clone();
                ItemMeta meta = randomItem.getItemMeta();
                List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
                lore.add("§7ID: " + new Random().nextInt(10000));
                meta.setLore(lore);
                randomItem.setItemMeta(meta);
                world.dropItem(loc, randomItem);
            }
        }
    }

    private void despawngold(){
        for(Entity entity : world.getEntities()) {
            if (entity instanceof Item) {
                Item itemEntity = (Item) entity;
                ItemStack droppedStack = itemEntity.getItemStack();
                for (ItemStack referenceStack : getGoldList()) {
                    if (droppedStack.getItemMeta().getDisplayName().equalsIgnoreCase(referenceStack.getItemMeta().getDisplayName())) {
                        itemEntity.remove();
                        break;
                    }
                }
            }
        }
    }

    private void despawndiamond(){
        for(Entity entity : world.getEntities()) {
            if (entity instanceof Item) {
                Item itemEntity = (Item) entity;
                ItemStack droppedStack = itemEntity.getItemStack();
                for (ItemStack referenceStack : getDiamondList()) {
                    if (droppedStack.getItemMeta().getDisplayName().equalsIgnoreCase(referenceStack.getItemMeta().getDisplayName())) {
                        itemEntity.remove();
                        break;
                    }
                }
            }
        }
    }

    private void despawnemerald(){
        for(Entity entity : world.getEntities()) {
            if (entity instanceof Item) {
                Item itemEntity = (Item) entity;
                ItemStack droppedStack = itemEntity.getItemStack();
                for (ItemStack referenceStack : getEmeraldList()) {
                    if (droppedStack.getItemMeta().getDisplayName().equalsIgnoreCase(referenceStack.getItemMeta().getDisplayName())) {
                        itemEntity.remove();
                        //Ajouter des sons
                        break;
                    }
                }
            }
        }
    }
    public int getTask() {
        return task;
    }
    public int getTimer() {
        return timer;
    }
    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void TaskspawnAll(){
        task = new BukkitRunnable() {
            @Override
            public void run() {

                switch (getTimer()) {
                    case 4 * 60:
                        spawngoldItem();
                        Bukkit.broadcastMessage("§8§l[§4§lCJFFA§8§l] §d§l> §f§lLe niveau §6§lor§f§l a spawn !");
                        break;
                    case 8 * 60:
                        spawndiamondItem();
                        Bukkit.broadcastMessage("§8§l[§4§lCJFFA§8§l] §d§l> §f§lLe niveau §b§ldiamand§f§l a spawn !");
                        break;
//                    case 8 * 60:
//                        despawngold();
//                        Bukkit.broadcastMessage("§8§l[§4§lCJFFA§8§l] §d§l> §f§lLe niveau §6§lor§f§l a despawn !");
//                        break;
//                    case 11 * 60:
//                        despawndiamond();
//                        Bukkit.broadcastMessage("§8§l[§4§lCJFFA§8§l] §d§l> §f§lLe niveau §b§ldiamand§f§l a despawn !");
//                        break;
                    case 12 * 60:
                        spawnemeraldItem();
                        Bukkit.broadcastMessage("§8§l[§4§lCJFFA§8§l] §d§l> §f§lLe niveau §a§lémeraude§f§l a spawn !");
                        break;
                    case 16 * 60:
                        despawnemerald();
                        despawngold();
                        despawndiamond();
//                        Bukkit.broadcastMessage("§8§l[§4§lCJFFA§8§l] §d§l> §f§lLe niveau §a§lémeraude§f§l a despawn !");
                        Bukkit.broadcastMessage("§8§l[§4§lCJFFA§8§l] §d§l> §a§lTous les niveaux ont §f§ldespawn ! §d§lNouveau Cycle !");
                        timer = 0;
                        break;
                    default:
                        break;
                }
//                    Main.getInstance().timer = timer;

                long now = System.currentTimeMillis();
                Iterator<Map.Entry<UUID, HashMap<ItemStack, Long>>> it = CJFFA.getInstance().itemsRemoveID.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<UUID, HashMap<ItemStack, Long>> entry = it.next();
                    UUID uuid = entry.getKey();
                    Player p = Bukkit.getPlayer(uuid);
                    if (p == null) continue;
                    Map<ItemStack, Long> itemMap = entry.getValue();
                    Iterator<Map.Entry<ItemStack, Long>> itItems = itemMap.entrySet().iterator();
                    while (itItems.hasNext()) {
                        Map.Entry<ItemStack, Long> itemEntry = itItems.next();
                        if (now >= itemEntry.getValue()) {
                            for (ItemStack rightItem : p.getInventory().getContents()) {
                                if (rightItem != null) {
                                    if (rightItem.getItemMeta().hasLore()) {
                                        if (rightItem.getItemMeta().getLore().equals(itemEntry.getKey().getItemMeta().getLore())) {
                                            p.getInventory().remove(rightItem);
                                            break;
                                        }
                                    }
                                }
                            }
                            for (ItemStack rightItem : p.getInventory().getArmorContents()) {
                                if (rightItem != null) {
                                    if (rightItem.getItemMeta().hasLore()) {
                                        if (rightItem.getItemMeta().getLore().equals(itemEntry.getKey().getItemMeta().getLore())) {
                                            p.getInventory().remove(rightItem);
                                            break;
                                        }
                                    }
                                }
                            }
                            itItems.remove();
                        }
                    }

                    if (itemMap.isEmpty()) it.remove();
                }

                if (world.getPlayers().size() < 2 || world.getPlayers().size() == 1) {
                    for (Player pls : world.getPlayers())
                        pls.sendTitle("§aGénérateur d'items", "§cdésactivés");
                    despawnemerald();
                    despawngold();
                    despawndiamond();
                    if(CJFFA.getInstance().itemsRemoveID.isEmpty()) {
                        timer = 0;
                        cancel();
                    }
                }


                timer++;
            }

        }.runTaskTimer(CJFFA.getInstance(), 20L, 20L).getTaskId();
    }

//    public void stopTask(){
//        Bukkit.getScheduler().cancelTask(task);
//    }

}