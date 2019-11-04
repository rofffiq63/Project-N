package com.need.unknown.component.model;

public class ModelCategory {

    String mTitle;

    public String getmTitle() {
        return mTitle;
    }

    public int getmOrder() {
        return mOrder;
    }

    int mOrder;
    boolean selected;

    public ModelCategory(String title, int order) {
        mTitle = title;
        mOrder = order;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
