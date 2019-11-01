package com.need.unknown.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.need.unknown.R;
import com.need.unknown.component.adapter.BarAdapter;
import com.need.unknown.component.adapter.MenuAdapter;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.DaggerMainViewComponent;
import com.need.unknown.injection.MainViewModule;
import com.need.unknown.presenter.MainPresenter;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.view.MainView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {
    @Inject
    PresenterFactory<MainPresenter> mPresenterFactory;
    @BindView(R.id.menuList)
    RecyclerView menuList;
    @BindView(R.id.promoList)
    RecyclerView promoList;
    @BindView(R.id.bottomBar)
    RecyclerView bottomBar;

    // Your presenter is available using the mPresenter variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("Home");
        bottomBar.setAdapter(new BarAdapter(this));
        menuList.setAdapter(new MenuAdapter(this));
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart or onPostCreate.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerMainViewComponent.builder()
                .appComponent(parentComponent)
                .mainViewModule(new MainViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<MainPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }
}
