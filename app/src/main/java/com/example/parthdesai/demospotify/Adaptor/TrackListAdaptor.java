package com.example.parthdesai.demospotify.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.parthdesai.demospotify.R;
import com.example.parthdesai.demospotify.model.Track;
import com.example.parthdesai.demospotify.model.TrackDetail;

import java.util.ArrayList;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class TrackListAdaptor extends ArrayAdapter<Track> {
    Context mContext;
    ArrayList<Track> tracksLists;

    public TrackListAdaptor(Context applicationContext, ArrayList<Track> tracksLists) {
        super(applicationContext, R.layout.play_list, tracksLists);
        this.mContext = applicationContext;
        this.tracksLists = tracksLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.play_list, null);
        }
        TextView itemName = (TextView) v.findViewById(R.id.item_name);
        itemName.setText(tracksLists.get(position).getTrackDetail().getName());
        itemName.setTextColor(Color.BLACK);
        return v;
    }
}