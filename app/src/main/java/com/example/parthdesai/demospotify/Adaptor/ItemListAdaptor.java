package com.example.parthdesai.demospotify.Adaptor;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parthdesai.demospotify.R;
import com.example.parthdesai.demospotify.model.Items;

import java.util.ArrayList;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class ItemListAdaptor extends ArrayAdapter<Items> {
    Context mContext;
    ArrayList<Items> itemList;

    public ItemListAdaptor(Context applicationContext, ArrayList<Items> playListItemList) {
        super(applicationContext, R.layout.play_list, playListItemList);
        this.mContext = applicationContext;
        this.itemList = playListItemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.play_list, null);
        }
        TextView itemName = (TextView)v.findViewById(R.id.item_name);
        itemName.setText(itemList.get(position).getName());
        itemName.setTextColor(Color.BLACK);
        return v;
    }

}
