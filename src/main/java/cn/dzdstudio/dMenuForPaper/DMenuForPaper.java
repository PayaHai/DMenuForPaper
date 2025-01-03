package cn.dzdstudio.dMenuForPaper;

import cn.dzdstudio.dMenuForPaper.PageKit.PageKit;
import cn.dzdstudio.dMenuForPaper.PageKit.Read;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DMenuForPaper extends JavaPlugin {
    public static Plugin plugin;
    public static Logger logger;

    @Override
    public void onEnable() {
        plugin = this;
        logger = this.getLogger();

        logger.info("DMenu 加载中...");

        // 注册命令
        this.getCommand("dmenu").setExecutor(new CommandKit());
        this.getCommand("dmenu").setTabCompleter(new CommandKit());

        // 注册监听
        getServer().getPluginManager().registerEvents(new PageKit(), this);

        // 加载页面
        Read.loadPages();

        logger.info("DMenu 加载完毕。");
    }
}
