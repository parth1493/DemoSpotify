package com.example.parthdesai.demospotify.model;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class Track {
    private TrackDetail track;
    public Track(TrackDetail track) {
        this.track = track;
    }

    public TrackDetail getTrackDetail() {
        return track;
    }

    public void setTrackDetail(TrackDetail trackDetail) {
        this.track = trackDetail;
    }


}
