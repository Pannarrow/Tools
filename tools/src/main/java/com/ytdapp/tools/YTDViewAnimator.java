package com.ytdapp.tools;

import android.animation.ObjectAnimator;
import android.view.View;

public class YTDViewAnimator {
    private final View view;
    public YTDViewAnimator(View target) {
        view = target;
    }

    public int getWidth() {
        return view.getLayoutParams().width;
    }

    public void setWidth(int width) {
        view.getLayoutParams().width = width;
        view.requestLayout();
    }

    public int getHeight() {
        return view.getLayoutParams().height;
    }

    public void setHeight(int height) {
        view.getLayoutParams().height = height;
        view.requestLayout();
    }

    public static void doAnimator(View view,int duration, String po, int value){
        YTDViewAnimator viewAnimator = new  YTDViewAnimator(view);
        ObjectAnimator.ofInt(viewAnimator,po,value).setDuration(duration).start();
    }

    public static void doAnimator(View view,int duration, String po, int start,int end){
        ObjectAnimator.ofInt(view,po,start,end).setDuration(duration).start();
    }

}
