package com.need.unknown.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.need.unknown.R;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.DaggerVerifyViewComponent;
import com.need.unknown.injection.VerifyViewModule;
import com.need.unknown.presenter.VerifyPresenter;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.view.VerifyView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.need.unknown.view.impl.CheckPhoneNumberActivity.ASK_OTP;
import static com.need.unknown.view.impl.CheckPhoneNumberActivity.ASK_PIN;

public final class VerifyFragment extends BaseFragment<VerifyPresenter, VerifyView> implements VerifyView {
    @Inject
    PresenterFactory<VerifyPresenter> mPresenterFactory;
    @BindView(R.id.input_code)
    EditText inputCode;
    @BindView(R.id.input_pin)
    EditText inputPin;
    Unbinder unbinder;

    // Your presenter is available using the mPresenter variable

    public VerifyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify, container, false);
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
        DaggerVerifyViewComponent.builder()
                .appComponent(parentComponent)
                .verifyViewModule(new VerifyViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<VerifyPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    public void verifyAccount(int flag) {
        switch (flag) {
            case ASK_PIN:
                inputCode.setVisibility(View.GONE);
                inputPin.setVisibility(View.VISIBLE);
                break;
            case ASK_OTP:
                inputCode.setVisibility(View.VISIBLE);
                inputPin.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public String getCode() {
        return inputCode.getText().toString();
    }

    public void setError(int i) {
        switch (i){
            case ASK_PIN:
                break;
            case ASK_OTP:
                inputCode.setError("Invalid Code");
                break;
        }
    }

    public void resetField() {
        inputCode.setText(null);
        inputPin.setText(null);
    }
}
