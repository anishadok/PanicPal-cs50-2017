package com.example.michaelhuang.panicpalreal;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static com.example.michaelhuang.panicpalreal.MainActivity.isAttacked;


public class ShakeEventListener implements SensorEventListener {
// referenced from https://stackoverflow.com/questions/2317428/android-i-want-to-shake-it

    private OnShakeListener mShakeListener;

    public interface OnShakeListener {
        void onShake();
    }

    public void setOnShakeListener(OnShakeListener listener) {
        mShakeListener = listener;
    }

    @Override
    public void onSensorChanged(SensorEvent se) {
        Log.d("Changing ", "data");
        // get sensor data
        float x = se.values[SensorManager.DATA_X];
        float y = se.values[SensorManager.DATA_Y];
        float z = se.values[SensorManager.DATA_Z];

        double total = 0.0;
        for (int i = 0; i < 3; i++){
            total += Math.pow(se.values[i],2);
        }
        Log.d("total", "is "+total);
        if (total > 300) {
            isAttacked = true;
            mShakeListener.onShake();
            resetShakeParameters();
            Log.d("hi", "greater than 700 " + total);
        }
    }

    private void resetShakeParameters() {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}