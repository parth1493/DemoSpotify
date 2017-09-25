package com.example.parthdesai.demospotify.model;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class TrackDetail {

    private String name;
    private String uri;

    public TrackDetail(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
