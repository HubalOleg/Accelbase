package com.oleg.hubal.accelbase.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oleg.hubal.accelbase.Constants;

/**
 * Created by User on 01.11.2016.
 */

public class AccelerometerService extends Service implements SensorEventListener {
    private SensorManager mSensorManager = null;
    private Sensor mSensor = null;

    private long accelerometerDelay;

    final Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        accelerometerDelay = intent.getLongExtra(Constants.EXTRA_EDIT_TEXT_DELAY, 1000);
        startAccelerometer();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        saveDataAndUnregisterListener(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void startAccelerometer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                registerListener();
                handler.postDelayed(this, accelerometerDelay);
            }
        }, accelerometerDelay);
    }

    private void saveDataAndUnregisterListener(SensorEvent event) {
//        TODO save date
        float value1 = event.values[0];
        float value2 = event.values[1];
        float value3 = event.values[2];

        Log.d("log123", value1 + " " + value2 + " " + value3);

        mSensorManager.unregisterListener(this);
    }

    private void registerListener() {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
}
