package com.androidandyuk.whitenoise;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonTapped (View view){

        // get the id in a String, of the button that has been tapped
        int id = view.getId();

        String ourId = "";

        ourId = view.getResources().getResourceEntryName(id);

        Log.i("button tapped", ourId);

        int resourceId = getResources().getIdentifier(ourId, "raw", "com.androidandyuk.whitenoise");

        MediaPlayer mplayer = MediaPlayer.create(this, resourceId);
        mplayer.start();

        Log.i("button tapped", ourId);

    }
}
