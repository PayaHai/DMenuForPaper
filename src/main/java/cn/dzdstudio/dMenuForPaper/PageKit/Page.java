package cn.dzdstudio.dMenuForPaper.PageKit;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Page {
    private final String name;
    private final String title;
    private final int rows;
    private final List<Button> buttons;
    private final String item;
    private final boolean lock_item;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getRows() {
        return rows;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public String getItem() {
        return item;
    }

    public boolean isLock_item() {
        return lock_item;
    }

    public Page(@NotNull String name, @NotNull String title, int rows, String item, boolean lock_item, @NotNull List<Button> buttons) {
        this.name = name;
        this.title = title;
        this.rows = rows;

        this.item = item;
        this.lock_item = lock_item;

        this.buttons = buttons;
    }
}
