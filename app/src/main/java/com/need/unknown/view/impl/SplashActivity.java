package com.need.unknown.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.need.unknown.R;
import com.need.unknown.view.SplashView;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.SplashPresenter;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.SplashViewModule;
import com.need.unknown.injection.DaggerSplashViewComponent;

import javax.inject.Inject;

public final class SplashActivity extends BaseActivity<SplashPresenter, SplashView> implements SplashView {
    @Inject
    PresenterFactory<SplashPresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, 1000);
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerSplashViewComponent.builder()
                .appComponent(parentComponent)
                .splashViewModule(new SplashViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<SplashPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }
}
