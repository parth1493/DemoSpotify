package com.example.parthdesai.demospotify.Adaptor;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.parthdesai.demospotify.R;
import com.example.parthdesai.demospotify.model.Items;

import java.util.ArrayList;

/**
 * Created by parthdesai on 2017-09-24.
 */

public class UserPlayList extends ArrayAdapter<Items>  implements View.OnClickListener {

    Context mContext;
    ArrayList<Items> itemList;

    public UserPlayList(Context applicationContext, ArrayList<Items> playListItemList) {
        super(applicationContext, R.layout.play_list, playListItemList);
        this.mContext = applicationContext;
        this.itemList = playListItemList;
    }


    @Override
    public void onClick(View view) {

    }

    private static class ViewHolder {
        TextView itemName;
        RelativeLayout clickItem;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.play_list, null, true);
            viewHolder.itemName = (TextView)convertView.findViewById(R.id.item_name);
            viewHolder.clickItem = (RelativeLayout)convertView.findViewById(R.id.item_click);
        }else{
            viewHolder = (ViewHolder) convertView.getTag(R.string.VIEW_HOLDER_KEY);
        }
        viewHolder.itemName.setText(itemList.get(position).getName());
        return null;
    }
}
