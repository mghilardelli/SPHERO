package ch.fhnw.edu.emoba.sphero;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marcoghilardelli on 21.03.18.
 */

public class TabTouch extends Fragment {

    private Paint circlePaint;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_touch, container, false);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.WHITE);
        return rootView;
    }
}
