package ch.fhnw.edu.emoba.sphero;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

import static ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener.SpheroRobotBluetoothNotification.Online;


public class PairingActivity extends AppCompatActivity implements SpheroRobotDiscoveryListener {

    public static final boolean MOCK_MODE = true;

    TextView textView;
    SpheroRobotProxy spheroRobotProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        textView = findViewById(R.id.textView);

        if (MOCK_MODE) {
            spheroRobotProxy = SpheroRobotFactory.createRobot(true);
            launchMainActivity();
        } else {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                textView.setText(R.string.bluetooth_msg);
                return;
            }

            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
            }

            spheroRobotProxy = SpheroRobotFactory.createRobot(MOCK_MODE);
            spheroRobotProxy.setDiscoveryListener(this);
            spheroRobotProxy.startDiscovering(getApplicationContext());
        }
    }

    private void launchMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void updateText(String text) {
        textView.setText(text);
    }

    @Override
    public void handleRobotChangedState(final SpheroRobotBluetoothNotification spheroRobotBluetoothNotification) {
        Log.d("robotState", "changed");
        if (spheroRobotBluetoothNotification.equals(Online)) {
            runOnUiThread(this::launchMainActivity);
        } else {
            runOnUiThread(() -> updateText(spheroRobotBluetoothNotification.name()));
        }
    }
}
