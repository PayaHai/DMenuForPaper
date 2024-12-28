package cn.dzdstudio.dMenuForPaper;

import org.bukkit.Bukkit;

public class ExecuteKit {
    static Boolean runCommand(String command) {
        return Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
