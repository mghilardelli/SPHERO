package ch.fhnw.edu.emoba.sphero;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by marcoghilardelli on 21.03.18.
 */

public class TabAim extends Fragment {


   // SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();

    public TabAim() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_aim, container, false);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
/*
        if (spheroRobotProxy == null) return;

        if (isVisibleToUser) {
            spheroRobotProxy.setBackLedBrightness(1);
            spheroRobotProxy.drive(0, 0);
        } else {
            spheroRobotProxy.setBackLedBrightness(0);
            spheroRobotProxy.drive(0, 0);
        }
 */   }
}
