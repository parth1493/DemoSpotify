package com.example.parthdesai.demospotify.model;

import java.util.List;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class TrackItems {

    private List<Track> items;

    public TrackItems(List<Track> items) {
        this.items = items;
    }

    public List<Track> getItems() {
        return items;
    }

    public void setItems(List<Track> items) {
        this.items = items;
    }



}
