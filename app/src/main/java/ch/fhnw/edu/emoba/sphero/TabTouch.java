package ch.fhnw.edu.emoba.sphero;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marcoghilardelli on 21.03.18.
 */

public class TabTouch extends Fragment {

    TouchView touchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        touchView = new TouchView(getContext());
        return touchView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isVisibleToUser && touchView != null) {
            touchView.reset();
        }
    }
}
