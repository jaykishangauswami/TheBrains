package com.the.brain.Model;

import java.util.ArrayList;

/**
 * Created by JAYKISHAN on 01-01-2018.
 */

public class ModelAccounts  {
    String title;
ArrayList<ModelAccountChild>items;

    public ArrayList<ModelAccountChild> getItems() {
        return items;
    }

    public void setItems(ArrayList<ModelAccountChild> items) {
        this.items = items;
    }

    public ModelAccounts() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
