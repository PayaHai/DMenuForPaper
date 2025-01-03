package cn.dzdstudio.dMenuForPaper;

import cn.dzdstudio.dMenuForPaper.PageKit.Read;
import cn.dzdstudio.dMenuForPaper.PageKit.PageKit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandKit implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            MessageKit.send(sender, "&c未知的命令。");
        } else if (args[0].equals("reload")) {
            if (sender.isOp()) {
                Read.loadPages();
                MessageKit.send(sender, "插件重载完毕。");
            } else {
                MessageKit.send(sender, "&c你没有权限执行此命令。");
            }
        } else if (args[0].equals("open") && args.length == 2) {
            if (sender instanceof Player pl) {
                PageKit.open(pl, args[1]);
            } else {
                MessageKit.send(sender, "&c只有玩家才能执行此命令。");
            }
        } else {
            MessageKit.send(sender, "&c未知的命令。");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.isOp()) {
                completions.add("reload");
            }
            completions.add("open");
        }

        if (args.length == 2 && args[0].equals("open")) {
            completions.addAll(Read.pages.keySet());
        }

        return completions;
    }
}
