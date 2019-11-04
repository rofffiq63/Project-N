package com.need.unknown.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.need.unknown.R;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.DaggerLoginViewComponent;
import com.need.unknown.injection.LoginViewModule;
import com.need.unknown.presenter.LoginPresenter;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.view.LoginView;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public final class LoginFragment extends BaseFragment<LoginPresenter, LoginView> implements LoginView {
    private static final String TAG = "LoginFragment";

    @Inject
    PresenterFactory<LoginPresenter> mPresenterFactory;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.input_phone)
    EditText inputPhone;
    Unbinder unbinder;

    // Your presenter is available using the mPresenter variable

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerLoginViewComponent.builder()
                .appComponent(parentComponent)
                .loginViewModule(new LoginViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<LoginPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    public String getPhoneNumber() {
        return ccp.getSelectedCountryCodeWithPlus() + inputPhone.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onFailure(String message) {
        super.onFailure(message);
        inputPhone.setError(message);
    }

    public void setPhoneNumber(String phoneNumber) {
        inputPhone.setText(phoneNumber);
    }

    boolean validateNumber() {
        String phoneNumber = getPhoneNumber();
        if (phoneNumber.length() < 8) {
            onFailure("Nomor tidak valid");
            return false;
        }

        if (phoneNumber.substring(0, 1).equals(ccp.getSelectedCountryCode()))
            phoneNumber = phoneNumber.replace(ccp.getSelectedCountryCode(), "");

        if (phoneNumber.substring(0, 2).equals(ccp.getSelectedCountryCodeWithPlus()))
            phoneNumber = phoneNumber.replace(ccp.getSelectedCountryCodeWithPlus(), "");

        if (phoneNumber.charAt(0) == '0') {
            phoneNumber = phoneNumber.substring(1, phoneNumber.length());
            Log.i(TAG, "startPhoneNumberVerification: " + phoneNumber);
        }

        return true;
    }
}
