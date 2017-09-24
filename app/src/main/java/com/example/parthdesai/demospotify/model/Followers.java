package com.example.parthdesai.demospotify.model;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class Followers {
    private String href;
    private String total;
    public Followers(String href, String total) {
        this.href = href;
        this.total = total;
    }
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }




}
