package com.tt.voicecurve;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * <���ܼ���> </Br> <������ϸ����> </Br>
 * 
 * @author Kyson
 */
public class LightSensorManager {
    public static final int LIGHT_WHAT = 201;
    private Context mContext;
    private Sensor mLightSensor;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorEventListener;
    private Handler mHandler;

    public LightSensorManager(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        init();
    }

    private void init() {
        mSensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        registerSensor();
    }

    public void registerSensor() {
        mSensorManager.registerListener(
                mSensorEventListener = new SensorEventListener() {

                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        // TODO Auto-generated method stub
                        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = LIGHT_WHAT;
                            msg.obj = event.values[0];
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        // TODO Auto-generated method stub
                        if (sensor.getType() == Sensor.TYPE_LIGHT) {
                            
                        }
                    }
                }, mLightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unRegisterSensor() {
        mSensorManager.unregisterListener(mSensorEventListener, mLightSensor);
    }
}
