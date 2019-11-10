package com.need.unknown.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.need.unknown.R;
import com.need.unknown.view.TopupView;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.TopupPresenter;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.TopupViewModule;
import com.need.unknown.injection.DaggerTopupViewComponent;

import javax.inject.Inject;

public final class TopupActivity extends BaseActivity<TopupPresenter, TopupView> implements TopupView {
    @Inject
    PresenterFactory<TopupPresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);

        setTitle("Topup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerTopupViewComponent.builder()
                .appComponent(parentComponent)
                .topupViewModule(new TopupViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<TopupPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }
}
