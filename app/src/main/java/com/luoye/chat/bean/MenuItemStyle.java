package com.luoye.chat.bean;

import android.view.MenuItem;

public class MenuItemStyle {

    private MenuItem menuItem;
    private boolean distribute;

    public MenuItemStyle(MenuItem menuItem, boolean distribute) {
        this.menuItem = menuItem;
        this.distribute = distribute;
    }


    public MenuItem getMenuItem() {
        this.distribute = false;
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public boolean isDistribute() {
        return distribute;
    }

    public void setDistribute(boolean distribute) {
        this.distribute = distribute;
    }
}
