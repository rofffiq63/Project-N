package com.need.unknown.component.model;

import android.support.annotation.DrawableRes;

public class ModelMenu {
    boolean selected;
    String header;
    String title;
    String subTitle;
    String menuInfo;
    int icon;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    int order;

    public ModelMenu(String header, String title, String subTitle, String menuInfo, int icon, int order) {
        this.header = header;
        this.title = title;
        this.subTitle = subTitle;
        this.menuInfo = menuInfo;
        this.icon = icon;
        this.order = order;
    }

    public ModelMenu(@DrawableRes int ic_need_logo_dark, String home, int i) {
        icon = ic_need_logo_dark;
        title = home;
        order = i;
    }

    public ModelMenu(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(String menuInfo) {
        this.menuInfo = menuInfo;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
