package com.the.brain.Model;

import java.util.ArrayList;

/**
 * Created by JAYKISHAN on 01-01-2018.
 */

public class ModelInventory {
    String title;
ArrayList<ModelInventoryChild>items;

    public ArrayList<ModelInventoryChild> getItems() {
        return items;
    }

    public void setItems(ArrayList<ModelInventoryChild> items) {
        this.items = items;
    }

    public ModelInventory() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
