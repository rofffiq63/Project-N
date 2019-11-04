package com.need.unknown.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.need.unknown.R;
import com.need.unknown.component.adapter.ListMenuAdapter;
import com.need.unknown.component.view.ProgressButtonView;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.DaggerProfileViewComponent;
import com.need.unknown.injection.ProfileViewModule;
import com.need.unknown.presenter.ProfilePresenter;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.view.ProfileView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public final class ProfileFragment extends BaseFragment<ProfilePresenter, ProfileView> implements ProfileView {
    @Inject
    PresenterFactory<ProfilePresenter> mPresenterFactory;
    @BindView(R.id.logout_button)
    ProgressButtonView logoutButton;
    Unbinder unbinder;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phoneNumber)
    TextView phoneNumber;
    @BindView(R.id.menuList)
    RecyclerView menuList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    // Your presenter is available using the mPresenter variable

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar.inflateMenu(R.menu.home_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_help:
                        startActivity(new Intent(getContext(), CustomerServiceActivity.class));
                        break;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(menuList.getContext(),
//                ((LinearLayoutManager) menuList.getLayoutManager()).getOrientation());
//        menuList.addItemDecoration(dividerItemDecoration);
        menuList.setAdapter(new ListMenuAdapter(getContext(), user));

        logoutButton.setText("Logout")
                .setTextColor(R.color.red_400)
                .setButtonColor(R.color.white)
                .setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutButton.isLoading(true);
                        mAuth.signOut();

                        user = mAuth.getCurrentUser();

                        if (user == null) {
                            getActivity().finishAffinity();
                            startActivity(new Intent(getContext(), SplashActivity.class));
                        } else logoutButton.isLoading(false);
                    }
                })
                .build();
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerProfileViewComponent.builder()
                .appComponent(parentComponent)
                .profileViewModule(new ProfileViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<ProfilePresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
