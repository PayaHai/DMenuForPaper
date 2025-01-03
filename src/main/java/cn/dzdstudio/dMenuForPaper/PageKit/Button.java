package cn.dzdstudio.dMenuForPaper.PageKit;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Button {
    private final Set<Integer> ids;
    private final String name;
    private final String command;
    private final String open;
    private final String icon;
    private final List<String> lore;

    public Set<Integer> getIds() {
        return ids;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getOpen() {
        return open;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getLore() {
        return lore;
    }

    public Button(@NotNull Set<Integer> ids, String name, String command, String open, @NotNull String icon, List<String> lore) {
        this.ids = ids;
        this.name = name;
        this.command = command;
        this.open = open;
        this.icon = icon;
        this.lore = lore;
    }

    public Button(int id, String name, String command, String open, @NotNull String icon, List<String> lore) {
        this(Set.of(id), name, command, open, icon, lore);
    }
}