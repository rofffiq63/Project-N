package com.need.unknown.component.model;

import android.support.annotation.DrawableRes;

public class MenuModel {
    String title;
    int icon;
    boolean selected;

    public MenuModel(@DrawableRes int ic_need_logo_dark, String home) {
        icon = ic_need_logo_dark;
        title = home;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return this.title;
    }

    public int getIcon() {
        return this.icon;
    }
}
