package com.androidandyuk.whitenoise;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    public static FirebaseDatabase database;
    public static DatabaseReference myRef;

    public static int currentDefaultTimer;

    private static final String TAG = "MainActivity";

    private AdView mAdView;

    AudioManager audioManager;

    private SoundPool mySound;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor ed;

    public static FloatingActionButton floatingActionButton;
    public static int animTime = 400;
    public static ImageView shield;
    public static TextView twenty;
    public static TextView sixty;
    public static TextView eight;

    public static Boolean timerRunning = false;
    public static Long millisLeft;
    public static CountDownTimer cdt;

    int maxVolume;

    int noiseStreaming = 0;
    int whiteNoiseId;
    SeekBar noiseVolumeControl;

    int rainStreaming = 0;
    int rainId = 0;
    SeekBar rainVolumeControl;

    int fanStreaming = 0;
    int fanId = 0;
    SeekBar fanVolumeControl;

    int trainStreaming = 0;
    int trainId = 0;
    SeekBar trainVolumeControl;

    int wavesStreaming = 0;
    int wavesId = 0;
    SeekBar wavesVolumeControl;

    int windStreaming = 0;
    int windId = 0;
    SeekBar windVolumeControl;

    int dryerStreaming = 0;
    int dryerId = 0;
    SeekBar dryerVolumeControl;

    int carStreaming = 0;
    int carId = 0;
    SeekBar carVolumeControl;


    static double noiseVol;
    static double rainVol;
    static double fanVol;
    static double trainVol;
    static double wavesVol;
    static double windVol;
    static double dryerVol;
    static double carVol;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        sharedPreferences = this.getSharedPreferences("whitenoise", Context.MODE_PRIVATE);
        ed = sharedPreferences.edit();

        eight = (TextView) findViewById(R.id.eight);
        sixty = (TextView) findViewById(R.id.sixty);
        twenty = (TextView) findViewById(R.id.twenty);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        shield = (ImageView) findViewById(R.id.shield);

        setListener();

        loadSettings();
        loadActivity();

    }

    public void setListener(){
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.i(TAG,"onLongClick");
                showFABOptions();
                return true;
            }
        });
    }

    public void showFABOptions() {
        Log.i(TAG,"showFABOptions");

        if(!timerRunning) {
            Animation eFadeIn = new AlphaAnimation(0, 1);
            eFadeIn.setStartOffset(animTime);
            eFadeIn.setDuration(animTime);
            AnimationSet eAnimation = new AnimationSet(true);
            eAnimation.addAnimation(eFadeIn);

            Animation sFadeIn = new AlphaAnimation(0, 1);
            sFadeIn.setStartOffset(animTime / 2);
            sFadeIn.setDuration(animTime);
            AnimationSet sAnimation = new AnimationSet(true);
            sAnimation.addAnimation(sFadeIn);

            Animation tFadeIn = new AlphaAnimation(0, 1);
            tFadeIn.setDuration(animTime);
            AnimationSet tAnimation = new AnimationSet(true);
            tAnimation.addAnimation(tFadeIn);

            Animation shFadeIn = new AlphaAnimation(0, 1);
            shFadeIn.setDuration(animTime);
            AnimationSet shAnimation = new AnimationSet(true);
            shAnimation.addAnimation(shFadeIn);

            eight.setVisibility(View.VISIBLE);
            sixty.setVisibility(View.VISIBLE);
            twenty.setVisibility(View.VISIBLE);
            shield.setVisibility(View.VISIBLE);

            shield.startAnimation(shAnimation);
            twenty.startAnimation(tAnimation);
            sixty.startAnimation(sAnimation);
            eight.startAnimation(eAnimation);
        } else {
            Snackbar.make(findViewById(R.id.main), "Timer Remaining : " + millisInMinutes(millisLeft) + " minutes", Snackbar.LENGTH_SHORT)
                    .setAction("Stop Timer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cdt.onFinish();
                            cdt.cancel();
//                            loadActivity();
                            timerRunning = false;
                            setListener();
                        }
                    }).show();
        }
    }

    public void hideFABOptions() {
        Log.i(TAG,"hideFABOptions");
        if (sixty.isShown()) {
            Animation eFadeOut = new AlphaAnimation(1, 0);
            eFadeOut.setDuration(animTime);
            AnimationSet eAnimation = new AnimationSet(true);
            eAnimation.addAnimation(eFadeOut);

            Animation sFadeOut = new AlphaAnimation(1, 0);
            sFadeOut.setStartOffset(animTime / 2);
            sFadeOut.setDuration(animTime);
            AnimationSet sAnimation = new AnimationSet(true);
            sAnimation.addAnimation(sFadeOut);

            Animation tFadeOut = new AlphaAnimation(1, 0);
            tFadeOut.setStartOffset(animTime);
            tFadeOut.setDuration(animTime);
            AnimationSet tAnimation = new AnimationSet(true);
            tAnimation.addAnimation(tFadeOut);

            Animation shFadeOut = new AlphaAnimation(1, 0);
            shFadeOut.setStartOffset(animTime);
            shFadeOut.setDuration(animTime);
            AnimationSet shAnimation = new AnimationSet(true);
            shAnimation.addAnimation(shFadeOut);

            shield.startAnimation(shAnimation);
            eight.startAnimation(eAnimation);
            sixty.startAnimation(sAnimation);
            twenty.startAnimation(tAnimation);
        }

        eight.setVisibility(View.INVISIBLE);
        sixty.setVisibility(View.INVISIBLE);
        twenty.setVisibility(View.INVISIBLE);
        shield.setVisibility(View.INVISIBLE);
    }

    public void fabClicked(View view) {
        Log.i(TAG,"fabClicked");
        String tag = view.getTag().toString();
        hideFABOptions();
        switch (tag) {
            case "twenty":
                currentDefaultTimer = 20;
                timerButton();
                break;
            case "sixty":
                currentDefaultTimer = 60;
                timerButton();
                break;
            case "eight":
                currentDefaultTimer = 480;
                timerButton();
                break;
        }
    }

    public void shieldClicked(View view) {
        hideFABOptions();
    }

    public static String millisInMinutes(long milliSeconds) {
        String mins = Long.toString(milliSeconds / 60000);
        String secs = Long.toString((milliSeconds % 60000) / 1000);
        if (secs.length() < 2) {
            secs = "0" + secs;
        }

        String value = mins;

        return value;
    }

    private void loadActivity() {
        mySound = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        Log.i("maxVolume", "" + maxVolume);

        whiteNoiseId = mySound.load(this, R.raw.whitenoise, 1);
        noiseVolumeControl = (SeekBar) findViewById(R.id.noiseSeekBar);
        noiseVolumeControl.setMax(maxVolume);
        noiseVolumeControl.setProgress((int) (noiseVol * maxVolume));
        ImageView noiseIV = (ImageView)findViewById(R.id.noise);
        noiseIV.setImageResource(R.drawable.whitenoise);
        noiseVolumeControl.setVisibility(View.INVISIBLE);

        rainId = mySound.load(this, R.raw.rain, 1);
        rainVolumeControl = (SeekBar) findViewById(R.id.rainSeekBar);
        rainVolumeControl.setMax(maxVolume);
        rainVolumeControl.setProgress((int) (rainVol * maxVolume));
        ImageView rainIV = (ImageView)findViewById(R.id.rain);
        rainIV.setImageResource(R.drawable.rain);
        rainVolumeControl.setVisibility(View.INVISIBLE);

        fanId = mySound.load(this, R.raw.fan, 1);
        fanVolumeControl = (SeekBar) findViewById(R.id.fanSeekBar);
        fanVolumeControl.setMax(maxVolume);
        fanVolumeControl.setProgress((int) (fanVol * maxVolume));
        ImageView fanIV = (ImageView)findViewById(R.id.fan);
        fanIV.setImageResource(R.drawable.fan);
        fanVolumeControl.setVisibility(View.INVISIBLE);

        trainId = mySound.load(this, R.raw.train, 1);
        trainVolumeControl = (SeekBar) findViewById(R.id.trainSeekBar);
        trainVolumeControl.setMax(maxVolume);
        trainVolumeControl.setProgress((int) (trainVol * maxVolume));
        ImageView trainIV = (ImageView)findViewById(R.id.train);
        trainIV.setImageResource(R.drawable.train);
        trainVolumeControl.setVisibility(View.INVISIBLE);

        wavesId = mySound.load(this, R.raw.waves, 1);
        wavesVolumeControl = (SeekBar) findViewById(R.id.wavesSeekBar);
        wavesVolumeControl.setMax(maxVolume);
        wavesVolumeControl.setProgress((int) (wavesVol * maxVolume));
        ImageView wavesIV = (ImageView)findViewById(R.id.waves);
        wavesIV.setImageResource(R.drawable.waves);
        wavesVolumeControl.setVisibility(View.INVISIBLE);

        windId = mySound.load(this, R.raw.wind, 1);
        windVolumeControl = (SeekBar) findViewById(R.id.windSeekBar);
        windVolumeControl.setMax(maxVolume);
        windVolumeControl.setProgress((int) (windVol * maxVolume));
        ImageView windIV = (ImageView)findViewById(R.id.wind);
        windIV.setImageResource(R.drawable.wind);
        windVolumeControl.setVisibility(View.INVISIBLE);

        dryerId = mySound.load(getApplicationContext(), R.raw.dryer, 1);
        dryerVolumeControl = (SeekBar) findViewById(R.id.dryerSeekBar);
        dryerVolumeControl.setMax(maxVolume);
        dryerVolumeControl.setProgress((int) (dryerVol * maxVolume));
        ImageView dryerIV = (ImageView)findViewById(R.id.dryer);
        dryerIV.setImageResource(R.drawable.dryer);
        dryerVolumeControl.setVisibility(View.INVISIBLE);

        carId = mySound.load(this, R.raw.car, 1);
        carVolumeControl = (SeekBar) findViewById(R.id.carSeekBar);
        carVolumeControl.setMax(maxVolume);
        carVolumeControl.setProgress((int) (carVol * maxVolume));
        ImageView carIV = (ImageView)findViewById(R.id.car);
        carIV.setImageResource(R.drawable.car);
        carVolumeControl.setVisibility(View.INVISIBLE);


        // **********************************************
        // Seekbar control
        // methods below
        //***********************************************

        noiseVolumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                noiseVol = (double) progress / (double) maxVolume;

                Log.i("Noise Volume", String.valueOf(progress) + " " + String.valueOf((float) noiseVol));
                mySound.setVolume(noiseStreaming, (float) noiseVol, (float) noiseVol);
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
                rainVol = (double) progress / (double) maxVolume;

                Log.i("Rain Volume", String.valueOf(progress) + " " + String.valueOf((float) rainVol));
                mySound.setVolume(rainStreaming, (float) rainVol, (float) rainVol);
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
                fanVol = (double) progress / (double) maxVolume;

                Log.i("Fan Volume", String.valueOf(progress) + " " + String.valueOf((float) fanVol));
                mySound.setVolume(fanStreaming, (float) fanVol, (float) fanVol);
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
                trainVol = (double) progress / (double) maxVolume;

                Log.i("Train Volume", String.valueOf(progress) + " " + String.valueOf((float) trainVol));
                mySound.setVolume(trainStreaming, (float) trainVol, (float) trainVol);
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
                wavesVol = (double) progress / (double) maxVolume;

                Log.i("Waves Volume", "Id: " + wavesId + " Vol : " + String.valueOf(progress) + " " + String.valueOf((float) wavesVol));
                mySound.setVolume(wavesStreaming, (float) wavesVol, (float) wavesVol);
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
                windVol = (double) progress / (double) maxVolume;

                Log.i("Wind Volume", String.valueOf(progress) + " " + String.valueOf((float) windVol));
                mySound.setVolume(windStreaming, (float) windVol, (float) windVol);
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
                dryerVol = (double) progress / (double) maxVolume;

                Log.i("Dryer Volume", String.valueOf(progress) + " " + String.valueOf((float) dryerVol));
                mySound.setVolume(dryerStreaming, (float) dryerVol, (float) dryerVol);
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
                carVol = (double) progress / (double) maxVolume;

                Log.i("Car Volume", String.valueOf(progress) + " " + String.valueOf((float) carVol));
                mySound.setVolume(carStreaming, (float) carVol, (float) carVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void stopAllMedia() {

        Log.i("Stopping all media", "Starting");

        isPlaying = 0;

        if (whiteNoisePlaying) {
            whiteNoisePlaying = false;
            noiseStreaming = 0;
            mySound.stop(whiteNoiseId);
        }

        if (rainPlaying) {
            rainPlaying = false;
            rainStreaming = 0;
            mySound.stop(rainId);
        }

        if (carPlaying) {
            carPlaying = false;
            carStreaming = 0;
            mySound.stop(carId);
        }

        if (dryerPlaying) {
            dryerPlaying = false;
            dryerStreaming = 0;
            mySound.stop(dryerId);
        }

        if (fanPlaying) {
            fanPlaying = false;
            fanStreaming = 0;
            mySound.stop(fanId);
        }

        if (trainPlaying) {
            trainPlaying = false;
            trainStreaming = 0;
            mySound.stop(trainId);
        }

        if (wavesPlaying) {
            wavesPlaying = false;
            wavesStreaming = 0;
            mySound.stop(wavesId);
        }

        if (windPlaying) {
            windPlaying = false;
            windStreaming = 0;
            mySound.stop(windId);
        }
        mySound.release();
//        setContentView(R.layout.activity_main);

    }

    public void removeSplashScreen(View view) {
        View splash = findViewById(R.id.splashScreenLayout);
        splash.setVisibility(View.INVISIBLE);
        View timer = findViewById(R.id.floatingActionButton);
        timer.setVisibility(View.VISIBLE);
    }

    public void timerButtonClicked(View view){
        timerButton();
    }

    public void timerButton() {
        Log.i(TAG,"timerButton");
        if (!timerRunning) {
            if(isPlaying>0) {
                timerRunning = true;
                floatingActionButton.setImageResource(R.drawable.stop);
                // set how many minutes the timer will run
                long milliseconds = currentDefaultTimer * 60000;

                Snackbar.make(findViewById(R.id.main), "Timer started for " + currentDefaultTimer + " minutes. Long press to change.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                cdt = new CountDownTimer(milliseconds, 1000) {

                    public void onTick(long milliSecondsUntilDone) {

                        millisLeft = milliSecondsUntilDone;
                        // timer is counting down, logging every second
                        Log.i("Seconds left : ", String.valueOf(milliSecondsUntilDone / 1000));

                    }

                    public void onFinish() {

                        Log.i("Timer :", "Finished");

                        //stop all media playing
                        stopAllMedia();
                        floatingActionButton.setImageResource(R.drawable.timer);
                        Snackbar.make(findViewById(R.id.main), "Timer Finished", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();

                        loadActivity();
                    }
                }.start();
            } else {
                Log.i(TAG, "No sounds playing");
                Snackbar.make(findViewById(R.id.main), "Select some ambient noises first", Snackbar.LENGTH_SHORT)
                        .setAction("Stop Timer", null).show();
            }
        } else {
            Log.i(TAG, "Timer is already running");
            Snackbar.make(findViewById(R.id.main), "Timer Remaining : " + millisInMinutes(millisLeft) + " minutes", Snackbar.LENGTH_SHORT)
                    .setAction("Stop Timer", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cdt.onFinish();
                            cdt.cancel();
//                            loadActivity();
                            timerRunning = false;
                            setListener();
                        }
                    }).show();
        }
    }


    // **********************************************
    // Button tapped
    // methods below
    //***********************************************

    public void whiteNoiseTapped(View view) {

        String tag = view.getTag().toString();
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
            recordPlay(tag);
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

        String tag = view.getTag().toString();

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
            recordPlay(tag);
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

        String tag = view.getTag().toString();

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
            recordPlay(tag);
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

        String tag = view.getTag().toString();
        recordPlay(tag);

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

        String tag = view.getTag().toString();

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
        } else {
            // check not passed maximum tracks allowed
            Log.i("Waves", "start playing, Id :" + wavesId);
            recordPlay(tag);
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

        String tag = view.getTag().toString();
        recordPlay(tag);

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

        String tag = view.getTag().toString();

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
            recordPlay(tag);
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

        String tag = view.getTag().toString();
        recordPlay(tag);

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

    private void recordPlay(String tag) {
        Log.i("Play Sound", "Tag " + tag);
        DatabaseReference thisRef = myRef.child(tag);

        thisRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.setValue(1);
                } else {
                    int count = mutableData.getValue(Integer.class);
                    mutableData.setValue(count + 1);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean success, DataSnapshot dataSnapshot) {
                // Analyse databaseError for any error during increment
            }
        });
    }

    public static void loadSettings() {
        Log.i(TAG, "loadSettings");
        currentDefaultTimer = sharedPreferences.getInt("currentDefaultTimer", 20);
        noiseVol = Double.parseDouble(sharedPreferences.getString("noiseVol", "1"));
        rainVol = Double.parseDouble(sharedPreferences.getString("rainVol", "1"));
        fanVol = Double.parseDouble(sharedPreferences.getString("fanVol", "1"));
        trainVol = Double.parseDouble(sharedPreferences.getString("trainVol", "1"));
        wavesVol = Double.parseDouble(sharedPreferences.getString("wavesVol", "1"));
        windVol = Double.parseDouble(sharedPreferences.getString("windVol", "1"));
        dryerVol = Double.parseDouble(sharedPreferences.getString("dryerVol", "1"));
        carVol = Double.parseDouble(sharedPreferences.getString("carVol", "1"));
//        timerRunning = sharedPreferences.getBoolean("timerRunning", false);
    }

    public static void saveSettings() {
        Log.i(TAG, "saveSettings");
        ed.putInt("currentDefaultTimer", currentDefaultTimer).apply();
        ed.putString("noiseVol", String.valueOf(noiseVol)).apply();
        ed.putString("rainVol", String.valueOf(rainVol)).apply();
        ed.putString("fanVol", String.valueOf(fanVol)).apply();
        ed.putString("trainVol", String.valueOf(trainVol)).apply();
        ed.putString("wavesVol", String.valueOf(wavesVol)).apply();
        ed.putString("windVol", String.valueOf(windVol)).apply();
        ed.putString("dryerVol", String.valueOf(dryerVol)).apply();
        ed.putString("carVol", String.valueOf(carVol)).apply();
//        ed.putBoolean("timerRunning", timerRunning).apply();
    }

    @Override
    public void onBackPressed() {
        // this must be empty as back is being dealt with in onKeyDown
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (eight.isShown()) {
                hideFABOptions();
            } else {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        saveSettings();
        super.onPause();
    }
}
