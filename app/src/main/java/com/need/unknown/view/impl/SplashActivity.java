package com.need.unknown.view.impl;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;
import com.need.unknown.R;
import com.need.unknown.component.model.DataUser;
import com.need.unknown.view.SplashView;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.SplashPresenter;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.SplashViewModule;
import com.need.unknown.injection.DaggerSplashViewComponent;

import javax.inject.Inject;

public final class SplashActivity extends BaseActivity<SplashPresenter, SplashView> implements SplashView, PermissionCallback, ErrorCallback {
    private static final int REQUEST_PERMISSIONS = 99;
    @Inject
    PresenterFactory<SplashPresenter> mPresenterFactory;
    private DataUser mUserData;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
    }

    @Override
    public void presenterReady(DataUser userData) {
        mUserData = userData;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            new AskPermission.Builder(this).setPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE    ,
                    Manifest.permission.INTERNET)
                    .setCallback(this)
                    .setErrorCallback(this)
                    .setShowRationalDialog(true)
                    .request(REQUEST_PERMISSIONS);
        else new AskPermission.Builder(this).setPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.INTERNET)
                .setCallback(this)
                .setErrorCallback(this)
                .setShowRationalDialog(true)
                .request(REQUEST_PERMISSIONS);
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

    @Override
    public void onPermissionsGranted(int requestCode) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null)
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, CheckPhoneNumberActivity.class));
            }
        }, 1000);
    }

    @Override
    public void onPermissionsDenied(int requestCode) {
        onFailure("Permission denied: " + requestCode);
    }

    @Override
    public void onShowRationalDialog(final PermissionInterface permissionInterface, int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setPositiveButton("Izinkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                permissionInterface.onDialogShown();
            }
        })
                .setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onFailure("Aplikasi tidak mendapatkan izin pengguna");
                        finishAffinity();
                    }
                })
                .setMessage("Izinkan aplikasi untuk menggunakan fitur")
                .show();
    }

    @Override
    public void onShowSettings(PermissionInterface permissionInterface, int requestCode) {

    }
}
