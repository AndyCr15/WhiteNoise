package com.androidandyuk.whitenoise;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mplayer;
    MediaPlayer mpWhiteNoise;
    boolean whiteNoisePlaying = false;
    MediaPlayer mpRain;
    boolean rainPlaying = false;
    MediaPlayer mpFan;
    boolean fanPlaying = false;
    MediaPlayer mpWind;
    boolean windPlaying = false;
    MediaPlayer mpWaves;
    boolean wavesPlaying = false;
    MediaPlayer mpTrain;
    boolean trainPlaying = false;
    MediaPlayer mpDryer;
    boolean dryerPlaying = false;
    MediaPlayer mpCar;
    boolean carPlaying = false;

    int isPlaying = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public void buttonTapped(View view) {
//
//        // get the id in a String, of the button that has been tapped
//        int id = view.getId();
//
//        String ourId = "";
//
//        ourId = view.getResources().getResourceEntryName(id);
//
//        Log.i("button tapped", ourId);
//
//        int resourceId = getResources().getIdentifier(ourId, "raw", "com.androidandyuk.whitenoise");
//
//
//        Log.i("mplayer playing is ", " " + isPlaying);
//        if (isPlaying > 0) {
//            mplayer.pause();
//            isPlaying = 0;
//        } else {
//            mplayer = MediaPlayer.create(this, resourceId);
//            mplayer.start();
//            mplayer.setLooping(true);
//            isPlaying += 1;
//        }
//    }

    public void whiteNoiseTapped(View view) {

        Log.i("White Noise", "button tapped");

        if (whiteNoisePlaying) {
            Log.i("White Noise", "already playing");
            mpWhiteNoise.pause();
            whiteNoisePlaying = false;
            ((ImageButton) view).setImageResource(R.drawable.whitenoise);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("White Noise", "start playing");
            mpWhiteNoise = MediaPlayer.create(this, R.raw.whitenoise);
            mpWhiteNoise.start();
            mpWhiteNoise.setLooping(true);
            whiteNoisePlaying = true;
            ((ImageButton) view).setImageResource(R.drawable.whitenoisepressed);
            isPlaying++;
        }
    }

    public void rainTapped(View view) {

        Log.i("Rain", "button tapped");

        if (rainPlaying) {
            Log.i("Rain", "already playing");
            mpRain.pause();
            rainPlaying = false;
            ((ImageButton) view).setImageResource(R.drawable.rain);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Rain", "start playing");
            mpRain = MediaPlayer.create(this, R.raw.rain);
            mpRain.start();
            mpRain.setLooping(true);
            ((ImageButton) view).setImageResource(R.drawable.rainpressed);
            rainPlaying = true;
            isPlaying++;
        }
    }

    public void fanTapped(View view) {

        Log.i("Fan", "button tapped");

        if (fanPlaying) {
            Log.i("Rain", "already playing");
            mpFan.pause();
            fanPlaying = false;
            ((ImageButton) view).setImageResource(R.drawable.fan);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Fan", "start playing");
            mpFan = MediaPlayer.create(this, R.raw.fan);
            mpFan.start();
            mpFan.setLooping(true);
            ((ImageButton) view).setImageResource(R.drawable.fanpressed);
            fanPlaying = true;
            isPlaying++;
        }
    }

    public void windTapped(View view) {

        Log.i("Wind", "button tapped");

        if (windPlaying) {
            Log.i("Rain", "already playing");
            mpWind.pause();
            windPlaying = false;
            ((ImageButton) view).setImageResource(R.drawable.wind);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Wind", "start playing");
            mpWind = MediaPlayer.create(this, R.raw.wind);
            mpWind.start();
            mpWind.setLooping(true);
            ((ImageButton) view).setImageResource(R.drawable.windpressed);
            windPlaying = true;
            isPlaying++;
        }
    }

    public void wavesTapped(View view) {

        Log.i("Waves", "button tapped");

        if (wavesPlaying) {
            Log.i("Rain", "already playing");
            mpWaves.pause();
            wavesPlaying = false;
            ((ImageButton) view).setImageResource(R.drawable.waves);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Waves", "start playing");
            mpWaves = MediaPlayer.create(this, R.raw.waves);
            mpWaves.start();
            mpWaves.setLooping(true);
            ((ImageButton) view).setImageResource(R.drawable.wavespressed);
            wavesPlaying = true;
            isPlaying++;
        }
    }

    public void trainTapped(View view) {

        Log.i("Train", "button tapped");

        if (trainPlaying) {
            Log.i("Rain", "already playing");
            mpTrain.pause();
            trainPlaying = false;
            ((ImageButton) view).setImageResource(R.drawable.train);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Train", "start playing");
            mpTrain = MediaPlayer.create(this, R.raw.train);
            mpTrain.start();
            mpTrain.setLooping(true);
            ((ImageButton) view).setImageResource(R.drawable.trainpressed);
            trainPlaying = true;
            isPlaying++;
        }
    }

    public void dryerTapped(View view) {

        Log.i("Dryer", "button tapped");

        if (dryerPlaying) {
            Log.i("Rain", "already playing");
            mpDryer.pause();
            dryerPlaying = false;
            ((ImageButton) view).setImageResource(R.drawable.dryer);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Dryer", "start playing");
            mpDryer = MediaPlayer.create(this, R.raw.dryer);
            mpDryer.start();
            mpDryer.setLooping(true);
            ((ImageButton) view).setImageResource(R.drawable.dryerpressed);
            dryerPlaying = true;
            isPlaying++;
        }
    }

    public void carTapped(View view) {

        Log.i("Car", "button tapped");

        if (carPlaying) {
            Log.i("Rain", "already playing");
            mpCar.pause();
            carPlaying = false;
            ((ImageButton) view).setImageResource(R.drawable.car);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Car", "start playing");
            mpCar = MediaPlayer.create(this, R.raw.car);
            mpCar.start();
            mpCar.setLooping(true);
            ((ImageButton) view).setImageResource(R.drawable.carpressed);
            carPlaying = true;
            isPlaying++;
        }
    }
}
