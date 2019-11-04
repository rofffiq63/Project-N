package com.need.unknown.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.need.unknown.R;
import com.need.unknown.component.adapter.MenuAdapter;
import com.need.unknown.component.adapter.PromoAdapter;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.DaggerHomeViewComponent;
import com.need.unknown.injection.HomeViewModule;
import com.need.unknown.presenter.HomePresenter;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.view.HomeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator2;

public final class HomeFragment extends BaseFragment<HomePresenter, HomeView> implements HomeView {
    @Inject
    PresenterFactory<HomePresenter> mPresenterFactory;
    @BindView(R.id.retryButton)
    ImageView retryButton;
    @BindView(R.id.topupButton)
    Button topupButton;
    @BindView(R.id.menuList)
    RecyclerView menuList;
    @BindView(R.id.promoList)
    RecyclerView promoList;
    @BindView(R.id.indicator)
    CircleIndicator2 indicator;

    Unbinder unbinder;

    // Your presenter is available using the mPresenter variable

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuList.setAdapter(new MenuAdapter(getContext()));
        promoList.setAdapter(new PromoAdapter(getContext()));
        GravitySnapHelper gravitySnapHelper = new GravitySnapHelper(Gravity.START);
        gravitySnapHelper.attachToRecyclerView(promoList);

        indicator.attachToRecyclerView(promoList, gravitySnapHelper);

//        ((PromoAdapter) promoList.getAdapter()).registerAdapterDataObserver(indicator.getAdapterDataObserver());
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerHomeViewComponent.builder()
                .appComponent(parentComponent)
                .homeViewModule(new HomeViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<HomePresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
