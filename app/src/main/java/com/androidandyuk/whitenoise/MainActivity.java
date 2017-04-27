package com.androidandyuk.whitenoise;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AudioManager audioManager;

    private SoundPool mySound;

    int maxVolume;

    int noiseStreaming = 0;
    int whiteNoiseId;
    double noiseVol = 1;
    SeekBar noiseVolumeControl;

    int rainStreaming = 0;
    int rainId = 0;
    double rainVol = 1;
    SeekBar rainVolumeControl;


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
//            mpWhiteNoise.stop();
//            mpWhiteNoise.release();
        }

        if (rainPlaying) {
            rainPlaying = false;
//            mpRain.stop();
//            mpRain.release();
        }

        if (carPlaying) {
            carPlaying = false;
//            mpCar.stop();
//            mpCar.release();
        }

        if (dryerPlaying) {
            dryerPlaying = false;
//            mpDryer.stop();
//            mpDryer.release();
        }

        if (fanPlaying) {
            fanPlaying = false;
//            mpFan.stop();
//            mpFan.release();
        }

        if (trainPlaying) {
            trainPlaying = false;
//            mpTrain.stop();
//            mpTrain.release();
        }

        if (wavesPlaying) {
            wavesPlaying = false;
//            mpWaves.stop();
//            mpWaves.release();
        }

        if (windPlaying) {
            windPlaying = false;
//            mpWind.stop();
//            mpWind.release();
        }

        setContentView(R.layout.activity_main);

    }

    public void startTimer(View view) {

        // set how many minutes the timer will run
        int minutes = 15;
        long milliseconds = minutes * 60000;

        // only putting seconds in for testing
//        int seconds = 10;
//        long timerMinutes = seconds * 1000;
//        int minutes = seconds / 60;


        Toast.makeText(MainActivity.this, minutes + " minute timer started", Toast.LENGTH_LONG).show();

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
            }
        }.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySound = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        whiteNoiseId = mySound.load(this, R.raw.whitenoise, 1);
        rainId = mySound.load(this, R.raw.rain, 1);

        noiseVolumeControl = (SeekBar) findViewById(R.id.noiseSeekBar);
        noiseVolumeControl.setMax(maxVolume);
        noiseVolumeControl.setProgress(maxVolume);

        rainVolumeControl = (SeekBar) findViewById(R.id.rainSeekBar);
        rainVolumeControl.setMax(maxVolume);
        rainVolumeControl.setProgress(maxVolume);


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

    }


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
            Log.i("Rain", "already playing");
//            mpFan.pause();
            fanPlaying = false;
            ((ImageView) view).setImageResource(R.drawable.fan);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Fan", "start playing");
//            mpFan = MediaPlayer.create(this, R.raw.fan);
//            mpFan.start();
//            mpFan.setLooping(true);
            ((ImageView) view).setImageResource(R.drawable.fanpressed);
            fanPlaying = true;
            isPlaying++;
        }
    }

    public void windTapped(View view) {

        Log.i("Wind", "button tapped");

        if (windPlaying) {
            Log.i("Rain", "already playing");
//            mpWind.pause();
            windPlaying = false;
            ((ImageView) view).setImageResource(R.drawable.wind);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Wind", "start playing");
//            mpWind = MediaPlayer.create(this, R.raw.wind);
//            mpWind.start();
//            mpWind.setLooping(true);
            ((ImageView) view).setImageResource(R.drawable.windpressed);
            windPlaying = true;
            isPlaying++;
        }
    }

    public void wavesTapped(View view) {

        Log.i("Waves", "button tapped");

        if (wavesPlaying) {
            Log.i("Rain", "already playing");
//            mpWaves.pause();
            wavesPlaying = false;
            ((ImageView) view).setImageResource(R.drawable.waves);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Waves", "start playing");
//            mpWaves = MediaPlayer.create(this, R.raw.waves);
//            mpWaves.start();
//            mpWaves.setLooping(true);
            ((ImageView) view).setImageResource(R.drawable.wavespressed);
            wavesPlaying = true;
            isPlaying++;
        }
    }

    public void trainTapped(View view) {

        Log.i("Train", "button tapped");

        if (trainPlaying) {
            Log.i("Rain", "already playing");
//            mpTrain.pause();
            trainPlaying = false;
            ((ImageView) view).setImageResource(R.drawable.train);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Train", "start playing");
//            mpTrain = MediaPlayer.create(this, R.raw.train);
//            mpTrain.start();
//            mpTrain.setLooping(true);
            ((ImageView) view).setImageResource(R.drawable.trainpressed);
            trainPlaying = true;
            isPlaying++;
        }
    }

    public void dryerTapped(View view) {

        Log.i("Dryer", "button tapped");

        if (dryerPlaying) {
            Log.i("Rain", "already playing");
//            mpDryer.pause();
            dryerPlaying = false;
            ((ImageView) view).setImageResource(R.drawable.dryer);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Dryer", "start playing");
//            mpDryer = MediaPlayer.create(this, R.raw.dryer);
//            mpDryer.start();
//            mpDryer.setLooping(true);
            ((ImageView) view).setImageResource(R.drawable.dryerpressed);
            dryerPlaying = true;
            isPlaying++;
        }
    }

    public void carTapped(View view) {

        Log.i("Car", "button tapped");

        if (carPlaying) {
            Log.i("Rain", "already playing");
//            mpCar.pause();
            carPlaying = false;
            ((ImageView) view).setImageResource(R.drawable.car);
            isPlaying--;
        } else if (isPlaying < 3) {
            Log.i("Car", "start playing");
//            mpCar = MediaPlayer.create(this, R.raw.car);
//            mpCar.start();
//            mpCar.setLooping(true);
            ((ImageView) view).setImageResource(R.drawable.carpressed);
            carPlaying = true;
            isPlaying++;
        }
    }
}
