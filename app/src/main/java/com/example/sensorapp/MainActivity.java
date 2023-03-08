package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    private SensorManager mSensorManager;
    private TextView mTextLightSensor;
    private TextView mTextProximitySensor;
    private TextView mTextTemperatureSensor;
    private TextView mTextRelativeSensor;
    private TextView mTextPressureSensor;
    private TextView mTextGyroscopeSensor;
    private TextView mTextAccelerometerSensor;
    private TextView mTextRotationSensor;

    private Sensor mlightSensor;
    private Sensor mProximitySensor;
    private Sensor mTemperatureSensor;
    private Sensor mRelativeSensor;
    private Sensor mPressureSensor;
    private Sensor mGyroscopeSensor;
    private Sensor mAccelerometerSensor;
    private Sensor mRotationSensor;

    private LinearLayout ll1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList) {
//            Log.d("Sensor: ", currentSensor.getName());
            sensorText.append(currentSensor.getName()).
                    append(System.getProperty("line.separator"));
        }

        mTextLightSensor = findViewById(R.id.light_label);
        mTextProximitySensor = findViewById(R.id.proximity_label);
        mTextTemperatureSensor = findViewById(R.id.temperature_label);
        mTextRelativeSensor = findViewById(R.id.relative_label);
        mTextPressureSensor = findViewById(R.id.pressure_label);
        mTextGyroscopeSensor = findViewById(R.id.gyroscope_label);
        mTextAccelerometerSensor = findViewById(R.id.accelerometer_label);
        mTextRotationSensor = findViewById(R.id.rotation_label);

        ll1 = findViewById(R.id.ll1);

        mlightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mRelativeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mGyroscopeSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mlightSensor != null) {
            mSensorManager.registerListener(this, mlightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mProximitySensor != null) {
            mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mTemperatureSensor != null) {
            mSensorManager.registerListener(this, mTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mRelativeSensor!= null) {
            mSensorManager.registerListener(this, mRelativeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mPressureSensor != null) {
            mSensorManager.registerListener(this, mPressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mGyroscopeSensor != null) {
            mSensorManager.registerListener(this, mGyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mAccelerometerSensor != null) {
            mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mRotationSensor != null) {
            mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int type = sensorEvent.sensor.getType();
        float result = sensorEvent.values[0];

        switch (type) {
            case Sensor.TYPE_LIGHT:

                if (result < 30000) {
                    int blue = (int)(result * 255 / mlightSensor.getMaximumRange());
                    int red = (int)(result * 55 / mlightSensor.getMaximumRange());
                    int green = (int)(result * 105 / mlightSensor.getMaximumRange());


                    int color = Color.rgb(red,green, blue);
                    ll1.setBackgroundColor(color);

                    mTextLightSensor.setText(getResources().getString(R.string.light_text, result));
                    break;
                }

                if (result > 30000 && result < 35000){
                    ll1.setBackgroundColor(Color.RED);
                    mTextLightSensor.setText(getResources().getString(R.string.light_text, result));
                    break;
                }

                if (result > 35000 && result < 40000) {
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        ll1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ajipulang) );
                    } else {
                        ll1.setBackground(ContextCompat.getDrawable(this, R.drawable.ajipulang));
                    }
                    mTextLightSensor.setText(getResources().getString(R.string.light_text, result));
                    break;
                }

            case Sensor.TYPE_PROXIMITY:
                mTextProximitySensor.setText(getResources().getString(R.string.proximity_text, result));
                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextTemperatureSensor.setText(getResources().getString(R.string.temperature_text, result));
                break;

            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextRelativeSensor.setText(getResources().getString(R.string.relative_text, result));
                break;

            case Sensor.TYPE_PRESSURE:
                mTextPressureSensor.setText(getResources().getString(R.string.pressure_text, result));
                break;

            case Sensor.TYPE_GYROSCOPE:
                mTextGyroscopeSensor.setText(getResources().getString(R.string.pressure_text, result));
                break;

            case Sensor.TYPE_ACCELEROMETER:
                mTextAccelerometerSensor.setText(getResources().getString(R.string.accelerometer_text, result));
                break;

            case Sensor.TYPE_ROTATION_VECTOR:
                mTextRotationSensor.setText(getResources().getString(R.string.rotation_text, result));
                break;

            default:
                break;
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}