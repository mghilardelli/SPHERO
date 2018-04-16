package ch.fhnw.edu.emoba.sphero;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

/**
 * Created by marcoghilardelli on 21.03.18.
 */

public class TabSensor extends Fragment {

    private SensorEventListener sensorEventListener;
    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private static final double MIN_ANGLE = 2;

    SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();

    public TabSensor() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_sensor, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorEventListener = new SensorEventListener() {

            private double previousSpeed;

            @Override
            public void onSensorChanged(SensorEvent event) {

                double deltaX = event.values[0];
                double deltaY = event.values[1];

                double rad = Math.atan2(deltaX, deltaY); // start 0° at the top
                double heading = rad * (180 / Math.PI) + 180;
                double angleSum = Math.abs(deltaX) + Math.abs(deltaY);
                double speed = Math.max(0, (angleSum - MIN_ANGLE) / 6d);

                // don't send drive command if nothing changed
                if(speed == previousSpeed)
                    return;
                else
                    previousSpeed = speed;

                updateSpeedOnUI(speed, heading == 180.0);
                if (angleSum > MIN_ANGLE)
                    spheroRobotProxy.drive((float) heading, (float) speed);
                else
                    spheroRobotProxy.drive(0, 0);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) { }
        };
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            enableSensor();
        } else {
            disableSensor();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("hidden", Boolean.toString(hidden));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disableSensor();
    }

    private void enableSensor() {
        if (sensorManager != null) {
            sensorManager.registerListener(sensorEventListener, rotationSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    private void disableSensor() {
        if (sensorManager != null && sensorEventListener != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
        spheroRobotProxy.drive(0, 0);
    }

    private void updateSpeedOnUI(double speed, boolean forward) {
        View view = getView();
        if (view == null)
            return;

        ImageView directionIcon = view.findViewById(R.id.direction_icon);
        directionIcon.setColorFilter(Color.WHITE);
        directionIcon.setImageResource(forward ? R.mipmap.ic_keyboard_arrow_up_black :
                R.mipmap.ic_keyboard_arrow_down_black);

        DecimalFormat format = new DecimalFormat("#0.0000");
        TextView textView = view.findViewById(R.id.speed_display);
        textView.setText(format.format(speed));
    }

}
