package com.need.unknown.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.need.unknown.R;
import com.need.unknown.view.CustomerView;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.CustomerPresenter;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.CustomerViewModule;
import com.need.unknown.injection.DaggerCustomerViewComponent;

import javax.inject.Inject;

public final class CustomerServiceActivity extends BaseActivity<CustomerPresenter, CustomerView> implements CustomerView {
    @Inject
    PresenterFactory<CustomerPresenter> mPresenterFactory;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        setTitle("Customer Service");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerCustomerViewComponent.builder()
                .appComponent(parentComponent)
                .customerViewModule(new CustomerViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<CustomerPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }
}
