package com.example.parthdesai.demospotify;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parthdesai.demospotify.model.Person;
import com.example.parthdesai.demospotify.utill.ValidationClass;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Player;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity  {
    // TODO: Replace with your client ID
    private final String CLIENT_ID = "4aa6997f64684e70a5f4576eae90e80f";
    // TODO: Replace with your redirect URI
    private final String REDIRECT_URI = "testschema://callback";
    private static final int REQUEST_CODE = 1337;
    public static String Token=null;
    private ProgressDialog pDialog;
    private Player mPlayer;
    private TextView user_id;
    private BroadcastReceiver receiver;
    String httpString = "https://api.spotify.com/v1/me";
    private int resumeFlag = 0;
    Person person = null;
    Button getUserPlayList;
    public static int tokenType;
    AuthenticationResponse response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            response = AuthenticationClient.getResponse(resultCode, intent);
            Token = response.getAccessToken();
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                new GetData().execute();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


        @Override
        protected Void doInBackground(Void... arg0) {


            GetDataExample example = new GetDataExample();
            try {
                String response = example.run(httpString);
                Log.d("Web api",response);
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<Person> personAdapter = moshi.adapter(Person.class);

                try{
                    person = personAdapter.fromJson(response);
                    Log.d("User ID",person.getId());
                }catch (IOException io){

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (pDialog.isShowing()==true){
                pDialog.dismiss();
                pDialog.dismiss();
                setView();
            }
            super.onPostExecute(result);
            // Dismiss the progress dialog

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setStatus();
            }
        };
        this.registerReceiver(receiver, intentFilter);
    }
    public void setStatus() {

        if(ValidationClass.checkOnline(getApplicationContext())==true){
            Toast.makeText(getApplicationContext(),"wifi/data Connected",Toast.LENGTH_SHORT).show();
            if(resumeFlag == 0) {
                resumeFlag = 1;
            loginWithSpotify();}
        }else{
            resumeFlag = 0;
            Toast.makeText(getApplicationContext(),"wifi/data Not Connected",Toast.LENGTH_SHORT).show();
        }
    }
    public void setView(){
        user_id = (TextView)findViewById(R.id.user_id);
        user_id.setText(person.getId());
        getUserPlayList = (Button)findViewById(R.id.get_user_play_list);
        getUserPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PlayList.class);
                intent.putExtra("USERID",person.getId());
                startActivity(intent);
            }
        });
    }
    public void loginWithSpotify(){
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }
}
