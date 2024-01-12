package com.jan.todo.core.models;

public class IconModel
{
    private int iconId;
    private String iconDescription;
    private int resourceId;

    public IconModel(int iconId, String iconDescription, int resourceId) {
        this.iconId = iconId;
        this.iconDescription = iconDescription;
        this.resourceId = resourceId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconDescription() {
        return iconDescription;
    }

    public void setIconDescription(String iconDescription) {
        this.iconDescription = iconDescription;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
