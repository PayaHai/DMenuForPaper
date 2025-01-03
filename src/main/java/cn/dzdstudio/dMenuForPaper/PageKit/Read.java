package cn.dzdstudio.dMenuForPaper.PageKit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cn.dzdstudio.dMenuForPaper.DMenuForPaper.logger;
import static cn.dzdstudio.dMenuForPaper.DMenuForPaper.plugin;

public class Read {
    public static Map<String, Page> pages = new HashMap<>();

    private static @NotNull String optimization(String messages){
        return messages.replaceAll("&", "§").replaceAll("§§", "&");
    }

    private static boolean createPage(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
            logger.info("初始化页面：" + fileName);
            return true;
        }
        return false;
    }

    private static @NotNull Page getPage(String fileName){
        File file = new File(plugin.getDataFolder(), fileName);
        FileConfiguration pageConf = YamlConfiguration.loadConfiguration(file);

        List<Button> buttons = new ArrayList<>();
        List<Map<?, ?>> buttonConfigs = pageConf.getMapList("buttons");

        for (Map<?, ?> buttonConfig : buttonConfigs) {
            Object idObj = buttonConfig.get("id");
            Set<Integer> ids;
            if (idObj instanceof String idRange) {
                String[] parts = idRange.split("-");
                if (parts.length == 2) {
                    int start = Integer.parseInt(parts[0]);
                    int end = Integer.parseInt(parts[1]);
                    ids = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toSet());
                } else {
                    ids = Set.of(Integer.parseInt(idRange));
                }
            } else {
                ids = Set.of((int) idObj);
            }

            String name = null;
            if (buttonConfig.get("name") != null) {
                name = optimization((String) buttonConfig.get("name"));
            }
            String icon = "minecraft:light_gray_stained_glass_pane";
            if (buttonConfig.get("icon") !=null) {
                icon = (String) buttonConfig.get("icon");
            }
            List<String> lore = null;
            if (buttonConfig.get("lore") != null) {
                lore = ((List<String>) buttonConfig.get("lore")).stream().map(Read::optimization).collect(Collectors.toList());
            }

            String command = null;
            if (buttonConfig.get("command") != null) {
                command = optimization((String) buttonConfig.get("command"));
            }
            String open = null;
            if (buttonConfig.get("open") != null) {
                open = (String) buttonConfig.get("open");
            }

            buttons.add(new Button(ids, name, command, open, icon, lore));
        }

        return new Page(
                optimization(fileName.replace(".yml", "")),
                optimization(Objects.requireNonNull(pageConf.getString("title"))),
                pageConf.getInt("rows"),
                pageConf.getString("item"),
                pageConf.getBoolean("lock_item"),
                buttons
        );
    }

    public static void loadPages() {
        // 清空原有页面
        pages.clear();

        if (plugin.getDataFolder().listFiles() == null || Objects.requireNonNull(plugin.getDataFolder().listFiles()).length <= 1) {
            createPage("菜单.yml");
            createPage("测试.yml");
        }

        plugin.reloadConfig();

        for (File file : Objects.requireNonNull(plugin.getDataFolder().listFiles())) {
            if (file.getName().endsWith(".yml")) {
                logger.info("加载页面：" + file.getName());
                pages.put(file.getName().replace(".yml", ""), getPage(file.getName()));
            }
        }
    }
}