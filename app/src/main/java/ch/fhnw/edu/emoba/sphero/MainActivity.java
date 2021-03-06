package ch.fhnw.edu.emoba.sphero;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;

public class MainActivity extends AppCompatActivity {

    SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();

    private SectionsPagerAdapter sectionsPagerAdapter;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpheroRobotFactory.getActualRobotProxy().disconnect();
    }

    private double heading = 0;

    public void goLeft(View view){
        heading += 10;
        drive(heading, 0);
    }

    public void goRight(View view){
        heading -= 10;
        drive(heading, 0);
    }

    public void setZero(View view){
        spheroRobotProxy.setZeroHeading();
    }

    public void disconnect(View view){
        spheroRobotProxy.disconnect();
    }

    public void drive(double heading, double speed){
        spheroRobotProxy.drive((float)heading, (float)speed);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TabAim tabAim = new TabAim();
                    return tabAim;
                case 1:
                    TabTouch tabTouch = new TabTouch();
                    return tabTouch;

                case 2:
                    TabSensor tabSensor = new TabSensor();
                    return tabSensor;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "AIM";
                case 1:
                    return "TOUCH";
                case 2:
                    return "SENSOR";
            }
            return  null;

        }
    }
}
