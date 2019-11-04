package com.need.unknown.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.need.unknown.NeedApp;
import com.need.unknown.component.model.DataUser;
import com.need.unknown.component.model.ResponseNetworkError;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.presenter.loader.PresenterLoader;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.presenter.BasePresenter;
import com.need.unknown.view.BaseView;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseActivity<P extends BasePresenter<V>, V> extends AppCompatActivity implements LoaderManager.LoaderCallbacks<P>, BaseView {
    /**
     * Do we need to call {@link #doStart()} from the {@link #onLoadFinished(Loader, BasePresenter)} method.
     * Will be true if presenter wasn't loaded when {@link #onStart()} is reached
     */
    private final AtomicBoolean mNeedToCallStart = new AtomicBoolean(false);
    /**
     * The presenter for this view
     */
    @Nullable
    protected P mPresenter;
    /**
     * Is this the first start of the activity (after onCreate)
     */
    private boolean mFirstStart;
    public NeedApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirstStart = true;
        myApp = (NeedApp) getApplication();
        injectDependencies();

        getSupportLoaderManager().initLoader(0, null, this).startLoading();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                super.onBackPressed();
                return true;
        }
    }

    private void injectDependencies() {
        setupComponent(((NeedApp) getApplication()).getAppComponent());
    }

    @Override
    public void presenterReady(DataUser userData) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mNeedToCallStart.set(true);
        } else {
            doStart();
        }
    }

    @Override
    public void onProgress() {

    }

    @Override
    public <T> void onSuccess(T object) {

    }

    @Override
    public void onFailure(ResponseNetworkError networError) {

    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(myApp, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateNetwork(long totalBytesRead, long contentLength, boolean b) {

    }

    /**
     * Call the presenter callbacks for onStart
     */
    @SuppressWarnings("unchecked")
    private void doStart() {
        assert mPresenter != null;
        mPresenter.onViewAttached((V) this, myApp);
        mPresenter.onStart(mFirstStart);
        mFirstStart = false;
    }

    @Override
    protected void onStop() {
        if (mPresenter != null) {
            mPresenter.onStop();
            mPresenter.onViewDetached();
        }

        super.onStop();
    }

    @Override
    public final Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, getPresenterFactory());
    }

    @Override
    public final void onLoadFinished(Loader<P> loader, P presenter) {
        mPresenter = presenter;

        if (mNeedToCallStart.compareAndSet(true, false)) {
            doStart();
        }
    }

    @Override
    public final void onLoaderReset(Loader<P> loader) {
        mPresenter = null;
    }

    /**
     * Get the presenter factory implementation for this view
     *
     * @return the presenter factory
     */
    @NonNull
    protected abstract PresenterFactory<P> getPresenterFactory();

    /**
     * Setup the injection component for this view
     *
     * @param appComponent the app component
     */
    protected abstract void setupComponent(@NonNull AppComponent appComponent);
}
