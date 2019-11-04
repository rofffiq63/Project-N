package com.need.unknown.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.need.unknown.R;
import com.need.unknown.view.RegisterView;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.RegisterPresenter;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.RegisterViewModule;
import com.need.unknown.injection.DaggerRegisterViewComponent;

import javax.inject.Inject;

public final class RegisterActivity extends BaseActivity<RegisterPresenter, RegisterView> implements RegisterView {
    @Inject
    PresenterFactory<RegisterPresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerRegisterViewComponent.builder()
                .appComponent(parentComponent)
                .registerViewModule(new RegisterViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<RegisterPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }
}
