package cn.dzdstudio.dMenuForPaper;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageKit {
    private static @NotNull String splicing(String... messages){
        return ("&e[&rDMenu&e]&r " + String.join(" ", messages)).replaceAll("&", "§").replaceAll("§§", "&");
    }

    public static void send(@NotNull Player pl, @NotNull String... messages) {
        pl.sendMessage(splicing(messages));
    }

    public static void send(@NotNull CommandSender sender, @NotNull String... messages) {
        sender.sendMessage(splicing(messages));
    }
}
