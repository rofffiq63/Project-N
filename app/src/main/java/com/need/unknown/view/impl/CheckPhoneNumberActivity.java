package com.need.unknown.view.impl;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;
import com.need.unknown.R;
import com.need.unknown.component.adapter.PagerFragmentAdapter;
import com.need.unknown.component.model.DataUser;
import com.need.unknown.component.view.FragmentPager;
import com.need.unknown.component.view.ProgressButtonView;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.CheckPhoneNumberViewModule;
import com.need.unknown.injection.DaggerCheckPhoneNumberViewComponent;
import com.need.unknown.presenter.CheckPhoneNumberPresenter;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.view.CheckPhoneNumberView;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class CheckPhoneNumberActivity extends BaseActivity<CheckPhoneNumberPresenter, CheckPhoneNumberView> implements CheckPhoneNumberView {
    private static final String TAG = "CheckPhoneNumberActivit";
    public static final int ASK_PIN = 0;
    public static final int ASK_OTP = 1;

    @Inject
    PresenterFactory<CheckPhoneNumberPresenter> mPresenterFactory;

    @BindView(R.id.progress_button)
    ProgressButtonView progressButton;
    @BindView(R.id.homePager)
    FragmentPager homePager;

    private boolean mVerificationInProgress;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthCredential authCredentiail;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private View validateView;
    private LoginFragment loginFragment;
    private VerifyFragment verifyFragment;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone_number);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        loginFragment = new LoginFragment();
        verifyFragment = new VerifyFragment();

        PagerFragmentAdapter pagerFragmentAdapter = new PagerFragmentAdapter(getSupportFragmentManager());
        pagerFragmentAdapter.addFragment(loginFragment);
        pagerFragmentAdapter.addFragment(verifyFragment);
        homePager.setPagingEnabled(false);
        homePager.setAdapter(pagerFragmentAdapter);

        progressButton.setText("Send Code")
                .setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPhoneNumberVerification();
                    }
                })
                .build();

        initCallback();
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
    }

    @Override
    public void presenterReady(DataUser userData) {
        super.presenterReady(userData);
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerCheckPhoneNumberViewComponent.builder()
                .appComponent(parentComponent)
                .checkPhoneNumberViewModule(new CheckPhoneNumberViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<CheckPhoneNumberPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        checkUser(firebaseUser);
    }

    @Override
    public void onBackPressed() {
        if (homePager.getCurrentItem() != 0) {
            progressButton.setText("Send Code")
                    .setClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPhoneNumberVerification();
                        }
                    })
                    .build();
            homePager.setCurrentItem(0);
            initCallback();
        } else super.onBackPressed();
    }

    @Override
    public void goToRegister() {
        showCodeDialog(ASK_OTP);
    }

    @Override
    public void goToLogin() {
        showCodeDialog(ASK_PIN);
    }

    void initCallback() {
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.i(TAG, "onVerificationCompleted: success");
                progressButton.isLoading(false);
                authCredentiail = phoneAuthCredential;
                showCodeDialog(ASK_PIN);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.e(TAG, "onVerificationFailed: ", e);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(final String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                if (homePager.getCurrentItem() != 1)
                    return;

                progressButton.setText("Resend Code")
                        .setClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                resendVerificationCode(mResendToken);
                            }
                        })
                        .build();
            }

            @Override
            public void onCodeSent(final String s, final PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.i(TAG, "onCodeSent: Code Sent");
                mVerificationId = s;
                mResendToken = forceResendingToken;
                progressButton.isLoading(false);
                progressButton.disableButton();
                showCodeDialog(ASK_OTP);
            }
        };
    }

    private void showCodeDialog(int FLAG) {
        progressButton.isLoading(false);
        homePager.setCurrentItem(1);
        verifyFragment.resetField();
        verifyFragment.verifyAccount(FLAG);
        switch (FLAG) {
            case ASK_PIN:
                progressButton.setText("VERIFY")
                        .setClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                signInWithPhoneAuthCredential(authCredentiail);
                            }
                        })
                        .build();
                break;
            case ASK_OTP:
                progressButton.setText("VERIFY")
                        .setClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (verifyFragment.getCode().isEmpty()) {
                                    verifyFragment.setError(ASK_OTP);
                                    return;
                                }

                                verifyPhoneNumberWithCode(
                                        mVerificationId,
                                        verifyFragment.getCode());
                            }
                        })
                        .build();
                break;
        }
    }

    private void checkUser(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            loginFragment.setPhoneNumber(firebaseUser.getPhoneNumber());
            mAuth.signOut();
        }
    }

    private void startPhoneNumberVerification() {
        if (!loginFragment.validateNumber())
            return;

        progressButton.isLoading(true);
        // [START start_phone_auth]

//        try {
//            mPresenter.checkUserExist(loginFragment.getPhoneNumber());
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                loginFragment.getPhoneNumber(),        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallback);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

//        mVerificationInProgress = true;
    }

    private void resendVerificationCode(PhoneAuthProvider.ForceResendingToken token) {
        if (!loginFragment.validateNumber())
            return;

        progressButton.isLoading(true);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                loginFragment.getPhoneNumber(),        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallback,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        progressButton.isLoading(true);
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        progressButton.isLoading(true);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, updateNetwork UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            startActivity(new Intent(CheckPhoneNumberActivity.this, MainActivity.class));
                            finishAffinity();
                            // [START_EXCLUDE]
//                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and updateNetwork the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(CheckPhoneNumberActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                verifyFragment.resetField();
                                progressButton.isLoading(false);
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
//                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
}
