package ca.qc.johnabbott.cs5a6.virtualdressroom.ui.helper;

import android.view.View;
import android.view.ViewGroup;

// Source: https://stackoverflow.com/questions/17062924/how-to-replace-view-programmatically-in-android
public class ViewHelper {

    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);
        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }
}
