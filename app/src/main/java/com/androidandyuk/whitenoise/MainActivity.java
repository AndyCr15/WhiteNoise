package com.androidandyuk.whitenoise;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AudioManager audioManager;

    private SoundPool mySound;

    ListView timerOptionsList;

    int maxVolume;

    int noiseStreaming = 0;
    int whiteNoiseId;
    double noiseVol = 1;
    SeekBar noiseVolumeControl;

    int rainStreaming = 0;
    int rainId = 0;
    double rainVol = 1;
    SeekBar rainVolumeControl;

    int fanStreaming = 0;
    int fanId = 0;
    double fanVol = 1;
    SeekBar fanVolumeControl;

    int trainStreaming = 0;
    int trainId = 0;
    double trainVol = 1;
    SeekBar trainVolumeControl;

    int wavesStreaming = 0;
    int wavesId = 0;
    double wavesVol = 1;
    SeekBar wavesVolumeControl;

    int windStreaming = 0;
    int windId = 0;
    double windVol = 1;
    SeekBar windVolumeControl;

    int dryerStreaming = 0;
    int dryerId = 0;
    double dryerVol = 1;
    SeekBar dryerVolumeControl;

    int carStreaming = 0;
    int carId = 0;
    double carVol = 1;
    SeekBar carVolumeControl;


    //app starts with no noise playing
    boolean whiteNoisePlaying = false;
    boolean rainPlaying = false;
    boolean fanPlaying = false;
    boolean windPlaying = false;
    boolean wavesPlaying = false;
    boolean trainPlaying = false;
    boolean dryerPlaying = false;
    boolean carPlaying = false;

    int isPlaying = 0;

    public void stopAllMedia() {

        Log.i("Stopping all media", "Starting");

        isPlaying = 0;

        if (whiteNoisePlaying) {
            whiteNoisePlaying = false;
            noiseStreaming=0;
            mySound.stop(whiteNoiseId);
        }

        if (rainPlaying) {
            rainPlaying = false;
            rainStreaming=0;
            mySound.stop(rainId);
        }

        if (carPlaying) {
            carPlaying = false;
            carStreaming=0;
            mySound.stop(carId);
        }

        if (dryerPlaying) {
            dryerPlaying = false;
            dryerStreaming=0;
            mySound.stop(dryerId);
        }

        if (fanPlaying) {
            fanPlaying = false;
            fanStreaming=0;
            mySound.stop(fanId);
        }

        if (trainPlaying) {
            trainPlaying = false;
            trainStreaming=0;
            mySound.stop(trainId);
        }

        if (wavesPlaying) {
            wavesPlaying = false;
            wavesStreaming=0;
            mySound.stop(wavesId);
        }

        if (windPlaying) {
            windPlaying = false;
            windStreaming=0;
            mySound.stop(windId);
        }
        mySound.release();
        setContentView(R.layout.activity_main);

    }

    public void removeSplashScreen(View view){
        View splash = findViewById(R.id.splashScreenLayout);
        splash.setVisibility(View.INVISIBLE);
        View timer = findViewById(R.id.floatingActionButton);
        timer.setVisibility(View.VISIBLE);
    }

    public void timerButton(View view) {

//        timerOptionsList = (ListView)findViewById(timerOptions);
//        timerOptionsList.setVisibility(View.VISIBLE);
//
//        ArrayList<String> times = new ArrayList<>();
//
//        times.add("5");
//        times.add("10");
//        times.add("20");
//        times.add("30");
//        times.add("60");
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.activity_list_item, R.id.timerOptions, times);
//
//        timerOptionsList.setAdapter(arrayAdapter);

        // set how many minutes the timer will run
        int minutes = 15;
        long milliseconds = minutes * 60000;

        // only putting seconds in for testing
//        int seconds = 10;
//        long timerMinutes = seconds * 1000;
//        int minutes = seconds / 60;


        Toast.makeText(MainActivity.this, minutes + " minute sleep timer started", Toast.LENGTH_LONG).show();

        new CountDownTimer(milliseconds, 1000) {

            public void onTick(long milliSecondsUntilDone) {

                // timer is counting down, logging every second
                Log.i("Seconds left : ", String.valueOf(milliSecondsUntilDone / 1000));

            }

            public void onFinish() {

                Log.i("Timer :", "Finished");

                //stop all media playing
                stopAllMedia();
                Toast.makeText(MainActivity.this, "Timer finished", Toast.LENGTH_LONG).show();
                //play alarm sound effect
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), notification);
                mp.start();

                loadActivity();
            }
        }.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadActivity();

    }

    private void loadActivity(){
        mySound = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        whiteNoiseId = mySound.load(this, R.raw.whitenoise, 1);
        noiseVolumeControl = (SeekBar) findViewById(R.id.noiseSeekBar);
        noiseVolumeControl.setMax(maxVolume);
        noiseVolumeControl.setProgress(maxVolume);

        rainId = mySound.load(this, R.raw.rain, 1);
        rainVolumeControl = (SeekBar) findViewById(R.id.rainSeekBar);
        rainVolumeControl.setMax(maxVolume);
        rainVolumeControl.setProgress(maxVolume);

        fanId = mySound.load(this, R.raw.fan, 1);
        fanVolumeControl = (SeekBar) findViewById(R.id.fanSeekBar);
        fanVolumeControl.setMax(maxVolume);
        fanVolumeControl.setProgress(maxVolume);

        trainId = mySound.load(this, R.raw.train, 1);
        trainVolumeControl = (SeekBar) findViewById(R.id.trainSeekBar);
        trainVolumeControl.setMax(maxVolume);
        trainVolumeControl.setProgress(maxVolume);

        wavesId = mySound.load(this, R.raw.waves, 1);
        wavesVolumeControl = (SeekBar) findViewById(R.id.wavesSeekBar);
        wavesVolumeControl.setMax(maxVolume);
        wavesVolumeControl.setProgress(maxVolume);

        windId = mySound.load(this, R.raw.wind, 1);
        windVolumeControl = (SeekBar) findViewById(R.id.windSeekBar);
        windVolumeControl.setMax(maxVolume);
        windVolumeControl.setProgress(maxVolume);

        dryerId = mySound.load(getApplicationContext(), R.raw.dryer, 1);
        dryerVolumeControl = (SeekBar) findViewById(R.id.dryerSeekBar);
        dryerVolumeControl.setMax(maxVolume);
        dryerVolumeControl.setProgress(maxVolume);

        carId = mySound.load(this, R.raw.car, 1);
        carVolumeControl = (SeekBar) findViewById(R.id.carSeekBar);
        carVolumeControl.setMax(maxVolume);
        carVolumeControl.setProgress(maxVolume);


        // **********************************************
        // Seekbar control
        // methods below
        //***********************************************

        noiseVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double noiseVol = (double) progress / (double) maxVolume;

                Log.i("Noise Volume", String.valueOf(progress) + " " + String.valueOf((float) noiseVol));
                mySound.setVolume(whiteNoiseId, (float) noiseVol, (float) noiseVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rainVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double rainVol = (double) progress / (double) maxVolume;

                Log.i("Rain Volume", String.valueOf(progress) + " " + String.valueOf((float) rainVol));
                mySound.setVolume(rainId, (float) rainVol, (float) rainVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fanVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double fanVol = (double) progress / (double) maxVolume;

                Log.i("Fan Volume", String.valueOf(progress) + " " + String.valueOf((float) fanVol));
                mySound.setVolume(fanId, (float) fanVol, (float) fanVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        trainVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double trainVol = (double) progress / (double) maxVolume;

                Log.i("Train Volume", String.valueOf(progress) + " " + String.valueOf((float) trainVol));
                mySound.setVolume(trainId, (float) trainVol, (float) trainVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        wavesVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double wavesVol = (double) progress / (double) maxVolume;

                Log.i("Waves Volume", String.valueOf(progress) + " " + String.valueOf((float) wavesVol));
                mySound.setVolume(wavesId, (float) wavesVol, (float) wavesVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        windVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double windVol = (double) progress / (double) maxVolume;

                Log.i("Wind Volume", String.valueOf(progress) + " " + String.valueOf((float) windVol));
                mySound.setVolume(windId, (float) windVol, (float) windVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dryerVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double dryerVol = (double) progress / (double) maxVolume;

                Log.i("Dryer Volume", String.valueOf(progress) + " " + String.valueOf((float) dryerVol));
                mySound.setVolume(dryerId, (float) dryerVol, (float) dryerVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        carVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double carVol = (double) progress / (double) maxVolume;

                Log.i("Car Volume", String.valueOf(progress) + " " + String.valueOf((float) carVol));
                mySound.setVolume(carId, (float) carVol, (float) carVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    // **********************************************
    // Button tapped
    // methods below
    //***********************************************


    public void whiteNoiseTapped(View view) {

        Log.i("White Noise", "button tapped");

        if (whiteNoisePlaying) {
            Log.i("White Noise", "already playing");
            mySound.pause(noiseStreaming);
            whiteNoisePlaying = false;
            ((ImageView) view).setImageResource(R.drawable.whitenoise);
            isPlaying--;
            // make seekbar invisible
            noiseVolumeControl.setVisibility(View.INVISIBLE);
        } else if (isPlaying < 3) {
            Log.i("White Noise", "start playing");
            if (noiseStreaming == 0) {
                noiseStreaming = mySound.play(whiteNoiseId, (float) noiseVol, (float) noiseVol, 1, -1, 1);
            } else {
                mySound.resume(noiseStreaming);
            }
            whiteNoisePlaying = true;
            ((ImageView) view).setImageResource(R.drawable.whitenoisepressed);
            isPlaying++;
            noiseVolumeControl.setVisibility(View.VISIBLE);
        }
    }

    public void rainTapped(View view) {

        Log.i("Rain", "button tapped");
        if (rainPlaying) {
            Log.i("Rain", "already playing");
            mySound.pause(rainStreaming);
            rainPlaying = false;
            ((ImageView) view).setImageResource(R.drawable.rain);
            isPlaying--;
            // make seekbar invisible
            rainVolumeControl.setVisibility(View.INVISIBLE);
        } else if (isPlaying < 3) {
            Log.i("Rain", "start playing");
            if (rainStreaming == 0) {
                rainStreaming = mySound.play(rainId, (float) rainVol, (float) rainVol, 1, -1, 1);
            } else {
                mySound.resume(rainStreaming);
            }
            rainPlaying = true;
            ((ImageView) view).setImageResource(R.drawable.rainpressed);
            isPlaying++;
            rainVolumeControl.setVisibility(View.VISIBLE);
        }
    }


    public void fanTapped(View view) {

        Log.i("Fan", "button tapped");
        if (fanPlaying) {
            Log.i("Fan", "already playing");
            mySound.pause(fanStreaming);
            fanPlaying = false;
            //change image to that showing the button is pressed
            ((ImageView) view).setImageResource(R.drawable.fan);
            isPlaying--;
            // make seekbar invisible
            fanVolumeControl.setVisibility(View.INVISIBLE);
        } else {
            // check not passed maximum tracks allowed
            Log.i("Fan", "start playing");
            // check if the sound item hasn't been set up yet, set it up if it hasn't
            if (fanStreaming == 0) {
                fanStreaming = mySound.play(fanId, (float) fanVol, (float) fanVol, 1, -1, 1);
            } else {
                //if it's been set up, simply resume it
                mySound.resume(fanStreaming);
            }
            fanPlaying = true;
            ((ImageView) view).setImageResource(R.drawable.fanpressed);
            isPlaying++;
            fanVolumeControl.setVisibility(View.VISIBLE);
        }
    }

    public void trainTapped(View view) {
        Log.i("Train", "button tapped");
        if (trainPlaying) {
            Log.i("Train", "already playing");
            mySound.pause(trainStreaming);
            trainPlaying = false;
            //change image to that showing the button is pressed
            ((ImageView) view).setImageResource(R.drawable.train);
            isPlaying--;
            // make seekbar invisible
            trainVolumeControl.setVisibility(View.INVISIBLE);
        } else if (isPlaying < 3) {
            // check not passed maximum tracks allowed
            Log.i("Train", "start playing");
            // check if the sound item hasn't been set up yet, set it up if it hasn't
            if (trainStreaming == 0) {
                trainStreaming = mySound.play(trainId, (float) trainVol, (float) trainVol, 1, -1, 1);
            } else {
                //if it's been set up, simply resume it
                mySound.resume(trainStreaming);
            }
            trainPlaying = true;
            ((ImageView) view).setImageResource(R.drawable.trainpressed);
            isPlaying++;
            trainVolumeControl.setVisibility(View.VISIBLE);
        }
    }

    public void wavesTapped(View view) {
        Log.i("Waves", "button tapped");
        if (wavesPlaying) {
            Log.i("Waves", "already playing");
            mySound.pause(wavesStreaming);
            wavesPlaying = false;
            //change image to that showing the button is pressed
            ((ImageView) view).setImageResource(R.drawable.waves);
            isPlaying--;
            // make seekbar invisible
            wavesVolumeControl.setVisibility(View.INVISIBLE);
        } else if (isPlaying < 3) {
            // check not passed maximum tracks allowed
            Log.i("Waves", "start playing");
            // check if the sound item hasn't been set up yet, set it up if it hasn't
            if (wavesStreaming == 0) {
                wavesStreaming = mySound.play(wavesId, (float) wavesVol, (float) wavesVol, 1, -1, 1);
            } else {
                //if it's been set up, simply resume it
                mySound.resume(wavesStreaming);
            }
            wavesPlaying = true;
            ((ImageView) view).setImageResource(R.drawable.wavespressed);
            isPlaying++;
            wavesVolumeControl.setVisibility(View.VISIBLE);
        }
    }

    public void windTapped(View view) {
        Log.i("Wind", "button tapped");
        if (windPlaying) {
            Log.i("Wind", "already playing");
            mySound.pause(windStreaming);
            windPlaying = false;
            //change image to that showing the button is pressed
            ((ImageView) view).setImageResource(R.drawable.wind);
            isPlaying--;
            // make seekbar invisible
            windVolumeControl.setVisibility(View.INVISIBLE);
        } else if (isPlaying < 3) {
            // check not passed maximum tracks allowed
            Log.i("Wind", "start playing");
            // check if the sound item hasn't been set up yet, set it up if it hasn't
            if (windStreaming == 0) {
                windStreaming = mySound.play(windId, (float) windVol, (float) windVol, 1, -1, 1);
            } else {
                //if it's been set up, simply resume it
                mySound.resume(windStreaming);
            }
            windPlaying = true;
            ((ImageView) view).setImageResource(R.drawable.windpressed);
            isPlaying++;
            windVolumeControl.setVisibility(View.VISIBLE);
        }
    }

    public void dryerTapped(View view) {
        Log.i("Dryer", "button tapped");
        if (dryerPlaying) {
            Log.i("Dryer", "already playing");
            mySound.pause(dryerStreaming);
            dryerPlaying = false;
            //change image to that showing the button is pressed
            ((ImageView) view).setImageResource(R.drawable.dryer);
            isPlaying--;
            // make seekbar invisible
            dryerVolumeControl.setVisibility(View.INVISIBLE);
        } else if (isPlaying < 3) {
            // check not passed maximum tracks allowed
            Log.i("Dryer", "start playing");
            // check if the sound item hasn't been set up yet, set it up if it hasn't
            if (dryerStreaming == 0) {
                dryerStreaming = mySound.play(dryerId, (float) dryerVol, (float) dryerVol, 1, -1, 1);
            } else {
                //if it's been set up, simply resume it
                mySound.resume(dryerStreaming);
            }
            dryerPlaying = true;
            ((ImageView) view).setImageResource(R.drawable.dryerpressed);
            isPlaying++;
            dryerVolumeControl.setVisibility(View.VISIBLE);
        }
    }

    public void carTapped(View view) {
        Log.i("Car", "button tapped");
        if (carPlaying) {
            Log.i("Car", "already playing");
            mySound.pause(carStreaming);
            carPlaying = false;
            //change image to that showing the button is pressed
            ((ImageView) view).setImageResource(R.drawable.car);
            isPlaying--;
            // make seekbar invisible
            carVolumeControl.setVisibility(View.INVISIBLE);
        } else {
            // check not passed maximum tracks allowed
            Log.i("Car", "start playing");
            // check if the sound item hasn't been set up yet, set it up if it hasn't
            if (carStreaming == 0) {
                carStreaming = mySound.play(carId, (float) carVol, (float) carVol, 1, -1, 1);
            } else {
                //if it's been set up, simply resume it
                mySound.resume(carStreaming);
            }
            carPlaying = true;
            ((ImageView) view).setImageResource(R.drawable.carpressed);
            isPlaying++;
            carVolumeControl.setVisibility(View.VISIBLE);
        }
    }
}
