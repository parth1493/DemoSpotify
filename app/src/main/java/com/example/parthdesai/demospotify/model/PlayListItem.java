package com.example.parthdesai.demospotify.model;

import java.util.List;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class PlayListItem {

    private List<Items> items;

    public PlayListItem(List<Items> items) {
        this.items = items;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }


}
