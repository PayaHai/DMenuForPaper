package cn.dzdstudio.dMenuForPaper.PageKit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class Session {
    private final Page page;
    private final Player pl;
    private Boolean isOpen = false;

    private static ItemStack getItem(Button button){
        String name = button.getName();
        String icon = button.getIcon();
        List<String> lore = button.getLore();

        ItemStack item = new ItemStack(Objects.requireNonNull(Material.matchMaterial(icon)), 1);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }

        if (name != null) {
            meta.displayName(Component.text(name));
        }
        if (lore != null) {
            List<TextComponent> loreComponent = lore.stream().map(Component::text).toList();
            meta.lore(loreComponent);
        }

        item.setItemMeta(meta);
        return item;
    }

    public Session(Player pl, Page page) {
        this.pl = pl;
        this.page = page;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 9 * page.getRows(), Component.text(page.getTitle()));
        List<Button> buttons = page.getButtons();

        for (Button button : buttons) {
            for (Integer id : button.getIds()) {
                inv.setItem(id, getItem(button));
            }
        }

        pl.openInventory(inv);
        isOpen = true;
    }

    public void close() {
        pl.closeInventory();
        isOpen = false;
        PageKit.closeSession(pl);
    }

    public void onPlayerClickInventory(InventoryClickEvent event){
        Component title = event.getView().title();
        if (!Objects.equals(title, Component.text(page.getTitle())) || !isOpen) {
            return;
        }
        event.setCancelled(true);

        int slot = event.getSlot();

        // 查找指定槽为的按钮
        for (Button button : page.getButtons()) {
            if (button.getIds().contains(slot)) {
                String command = button.getCommand();
                String open = button.getOpen();

                if (command != null || open != null) {
                    close();
                }

                if (command != null) {
                    Bukkit.dispatchCommand(pl, command);
                }

                if (open != null) {
                    PageKit.open(pl, open);
                }
            }
        }
    }

    public void onPlayerCloseInventory(InventoryCloseEvent event){
        String title = event.getView().title().toString();
        if (!Objects.equals(title, page.getTitle()) || !isOpen) {
            return;
        }

        close();
    }
}