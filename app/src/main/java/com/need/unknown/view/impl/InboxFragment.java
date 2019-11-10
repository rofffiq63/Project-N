package com.need.unknown.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.need.unknown.R;
import com.need.unknown.component.adapter.NotificationAdapter;
import com.need.unknown.component.view.EmptyStateView;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.DaggerInboxViewComponent;
import com.need.unknown.injection.InboxViewModule;
import com.need.unknown.presenter.InboxPresenter;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.view.InboxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public final class InboxFragment extends BaseFragment<InboxPresenter, InboxView> implements InboxView {
    @Inject
    PresenterFactory<InboxPresenter> mPresenterFactory;
    @BindView(R.id.notiflist)
    RecyclerView notiflist;
    @BindView(R.id.emptyState)
    EmptyStateView emptyState;
    Unbinder unbinder;

    // Your presenter is available using the mPresenter variable

    public InboxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        unbinder = ButterKnife.bind(this, view);
        emptyState.builder(R.drawable.empty_inbox_asset, "Empty!", "News about need will appear here");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notiflist.getContext(),
                ((LinearLayoutManager) notiflist.getLayoutManager()).getOrientation());
        notiflist.addItemDecoration(dividerItemDecoration);
        notiflist.setAdapter(new NotificationAdapter(getContext(), this));
        emptyState.setLoading(false)
                .isThereResult(((NotificationAdapter) notiflist.getAdapter()).getItemCount() > 0);
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerInboxViewComponent.builder()
                .appComponent(parentComponent)
                .inboxViewModule(new InboxViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<InboxPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
