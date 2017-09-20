package com.example.parthdesai.demospotify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;

public class MainActivity extends AppCompatActivity implements
       PlayerNotificationCallback, ConnectionStateCallback {
    // TODO: Replace with your client ID
    private final String CLIENT_ID = getString(R.string.CLIENT_ID);
    // TODO: Replace with your redirect URI
    private final String REDIRECT_URI = getString(R.string.REDIRECT_URI);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Throwable throwable) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {

    }
}
