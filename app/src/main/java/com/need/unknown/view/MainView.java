package com.need.unknown.view;

import android.support.annotation.UiThread;

@UiThread
public interface MainView {

    void selectFragment(int position);
}