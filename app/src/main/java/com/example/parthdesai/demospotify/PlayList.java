package com.example.parthdesai.demospotify;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.parthdesai.demospotify.Adaptor.ItemListAdaptor;
import com.example.parthdesai.demospotify.model.Items;
import com.example.parthdesai.demospotify.model.PlayListItem;
import com.example.parthdesai.demospotify.utill.ValidationClass;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;

public class PlayList extends AppCompatActivity {

    private String TAG = PlayList.class.getSimpleName();
    private ListView listView;
    private ItemListAdaptor userPlayListAdaptor;
    private ProgressDialog pDialog;
    private BroadcastReceiver receiver;
    private ArrayList<Items> playListItemList = null;
    private int resumeFlag = 0;
    String httpString =null;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userID = getIntent().getStringExtra("USERID");
        httpString = "https://api.spotify.com/v1/users/"+userID+"/playlists";
        initXmlView();
    }

    private void initXmlView() {
        listView = (ListView) findViewById(R.id.play_list);
        playListItemList = new ArrayList<>();
    }
    @Override
    protected void onResume() {
        resumeFlag = 0;
        super.onResume();
        registerReceiver();

    }
    public void registerReceiver() {
        Log.d("Check internet","User partiicpant register");
        //register connection status listener

        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setStatus();
            }
        };
        registerReceiver(receiver, intentFilter);

    }
    public void setStatus() {
        if(ValidationClass.checkOnline(getApplicationContext())){
            if(resumeFlag == 0) {
                resumeFlag = 1;
                 new GetData().execute();
            }
        }else{
            resumeFlag = 0;
        }

    }

    /**
     * Async task class to get json by making HTTP call
     */
    public class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(PlayList.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Void doInBackground(Void... arg0) {


            GetDataExample example = new GetDataExample();
            try {
                String response = example.run(httpString );
                Log.d(TAG, response );

                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<PlayListItem> playlistItemAdapter = moshi.adapter(PlayListItem.class);
                PlayListItem playlistitem = null;
                try {
                    playlistitem = playlistItemAdapter.fromJson(response);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "doInBackground() called with: " + "params = [" + playlistItemAdapter.toJson(playlistitem) + "]");
                playListItemList.clear();
                for(Items p:playlistitem.getItems()){
                    playListItemList.add(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()==true){
                pDialog.dismiss();
                pDialog.dismiss();

                setViewList();
            }
        }
    }
    public void setViewList(){
        userPlayListAdaptor = new ItemListAdaptor(getApplicationContext(),playListItemList);
        listView.setAdapter(userPlayListAdaptor);
        userPlayListAdaptor.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,playListItemList.get(i).getName());
                Intent intent = new Intent(PlayList.this,TracksList.class);
                intent.putExtra("USERID",userID);
                intent.putExtra("PLAYLISTNAME",playListItemList.get(i).getName());
                intent.putExtra("PLAYLISTID",playListItemList.get(i).getId());
                startActivity(intent);
            }
        });
    }
}
