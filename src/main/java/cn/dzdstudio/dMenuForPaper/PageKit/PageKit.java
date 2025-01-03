package cn.dzdstudio.dMenuForPaper.PageKit;

import cn.dzdstudio.dMenuForPaper.MessageKit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.dzdstudio.dMenuForPaper.DMenuForPaper.logger;

public class PageKit implements Listener {
    private static final Map<Player, Session> sessions = new java.util.HashMap<>();

    private static ItemStack getItem(Page page) {
        if (page.getItem() == null) {
            return null;
        }
        ItemStack item = new ItemStack(Objects.requireNonNull(Material.matchMaterial(page.getItem())), 1);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        meta.displayName(Component.text(page.getTitle()));
        List<TextComponent> loreComponent = new ArrayList<>();
        loreComponent.add(Component.text("DMENU"));
        loreComponent.add(Component.text(page.getName()));
        meta.lore(loreComponent);

        item.setItemMeta(meta);
        return item;
    }

    private static Page isItem(ItemStack item){
        if (item.hasItemMeta() && item.getItemMeta().lore() != null) {
            ItemMeta meta = item.getItemMeta();
            @Nullable List<Component> lore = meta.lore();
            if (lore != null && Objects.equals(lore.get(0), Component.text("DMENU"))) {
                for (String key : Read.pages.keySet()) {
                    if (Objects.equals(lore.get(1), Component.text(Read.pages.get(key).getName()))) {
                        return Read.pages.get(key);
                    }
                }
            }
        }

        return null;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player pl = event.getPlayer();
        for (String key : Read.pages.keySet()) {
            Page page = Read.pages.get(key);
            // 遍历玩家背包，查找是否已经存在
            for (ItemStack item : pl.getInventory().getContents()) {
                if (item != null && item.hasItemMeta() && item.getItemMeta().lore() != null && item.getItemMeta().lore().contains(Component.text(page.getName()))) {
                    return;
                }
            }

            if (getItem(page) != null) {
                pl.getInventory().addItem(getItem(page));
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player pl = event.getPlayer();
        if (event.getItem() != null && isItem(event.getItem()) != null) {
            Page page = isItem(event.getItem());
            if (page != null) {
                open(pl, page.getName());
                if (page.isLock_item()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (isItem(item) != null && isItem(item).isLock_item()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player pl) {
            if (sessions.containsKey(pl)) {
                Session session = sessions.get(pl);
                session.onPlayerClickInventory(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player pl) {
            if (sessions.containsKey(pl)) {
                Session session = sessions.get(pl);
                session.onPlayerCloseInventory(event);
            }
        }
    }

    public static void closeSession(Player pl){
        sessions.remove(pl);
    }

    public static void open(Player pl, String pageName){
        Page page = Read.pages.get(pageName);
        if (page == null) {
            MessageKit.send(pl, "&c没有找到这个页面。");
            return;
        }

        if (sessions.containsKey(pl)) {
            Session session = sessions.get(pl);
            session.close();
        }

        Session session = new Session(pl, page);
        sessions.put(pl, session);

        session.open();
    }
}
