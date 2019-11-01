package com.need.unknown.presenter.loader;

import android.support.annotation.NonNull;

import com.need.unknown.presenter.BasePresenter;

/**
 * Factory to implement to create a presenter
 */
public interface PresenterFactory<T extends BasePresenter> {
    @NonNull
    T create();
}
