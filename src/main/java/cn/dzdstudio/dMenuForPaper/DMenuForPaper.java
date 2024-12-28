package cn.dzdstudio.dMenuForPaper;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DMenuForPaper extends JavaPlugin {
    Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info("DMenu 加载中...");

        // 注册命令
        this.getCommand("dmenu").setExecutor(new CommandKit());

        logger.info("DMenu 加载完毕。");
    }
}
