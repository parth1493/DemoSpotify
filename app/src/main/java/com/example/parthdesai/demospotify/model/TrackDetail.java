package com.example.parthdesai.demospotify.model;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class TrackDetail {

    private long duration_ms;
    private String name;
    private String uri;

    public TrackDetail(long duration_ms, String name, String uri) {
        this.duration_ms = duration_ms;
        this.name = name;
        this.uri = uri;
    }
    public long getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(long duration_ms) {
        this.duration_ms = duration_ms;
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
