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
import android.widget.TextView;

import com.example.parthdesai.demospotify.Adaptor.TrackListAdaptor;
import com.example.parthdesai.demospotify.model.Track;
import com.example.parthdesai.demospotify.model.TrackItems;
import com.example.parthdesai.demospotify.model.TrackDetail;
import com.example.parthdesai.demospotify.utill.ValidationClass;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.ArrayList;

public class TracksList extends AppCompatActivity implements SpotifyPlayer.NotificationCallback{
    String userID;
    String playListName;
    String playListId;
    TextView playListNameTextView;
    private String TAG = TracksList.class.getSimpleName();
    private ListView listView;
    private TrackListAdaptor trackListAdaptor;
    private ProgressDialog pDialog;
    private BroadcastReceiver receiver;
    private ArrayList<Track> tracksList = null;
    private Player mPlayer;
    private int resumeFlag = 0;
    String httpString =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void initXmlView() {
        playListNameTextView = (TextView)findViewById(R.id.list_name);
        listView = (ListView) findViewById(R.id.track_list);
        tracksList = new ArrayList<>();
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
                initXmlView();
                userID = getIntent().getStringExtra("USERID");
                playListName = getIntent().getStringExtra("PLAYLISTNAME");
                playListId = getIntent().getStringExtra("PLAYLISTID");
                playListNameTextView.setText(playListName);
                httpString = "https://api.spotify.com/v1/users/"+userID+"/playlists/"+playListId+"/tracks";
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

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }

    @Override
    public void onPlaybackError(Error error) {

    }


    /**
     * Async task class to get json by making HTTP call
     */
    public class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(TracksList.this);
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
                JsonAdapter<TrackItems> tracklistAdapter = moshi.adapter(TrackItems.class);
                TrackItems trackItems = null;
                try {
                    trackItems = tracklistAdapter.fromJson(response);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "doInBackground() called with: " + "params = [" + tracklistAdapter.toJson(trackItems) + "]");

                for(Track t: trackItems.getItems()){
                    tracksList.add(t);
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
        trackListAdaptor = new TrackListAdaptor(getApplicationContext(),tracksList);
        listView.setAdapter(trackListAdaptor);
        trackListAdaptor.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Log.d(TAG,tracksList.get(i).getTrackDetail().getName());
            }
        });
    }
}
