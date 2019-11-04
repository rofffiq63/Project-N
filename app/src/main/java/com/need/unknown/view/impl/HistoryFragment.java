package com.need.unknown.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.need.unknown.R;
import com.need.unknown.component.model.ModelCategory;
import com.need.unknown.component.view.EmptyStateView;
import com.need.unknown.injection.AppComponent;
import com.need.unknown.injection.DaggerHistoryViewComponent;
import com.need.unknown.injection.HistoryViewModule;
import com.need.unknown.presenter.HistoryPresenter;
import com.need.unknown.presenter.loader.PresenterFactory;
import com.need.unknown.view.HistoryView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.net.wifi.p2p.WifiP2pDevice.FAILED;

public final class HistoryFragment extends BaseFragment<HistoryPresenter, HistoryView> implements HistoryView {
    private static final int ON_GOING = 0;
    private static final int COMPLETE = 1;
    private static final int WALLET = 3;
    private static final int BOOKING = 4;
    @Inject
    PresenterFactory<HistoryPresenter> mPresenterFactory;
    @BindView(R.id.categoryList)
    RecyclerView categoryList;
    @BindView(R.id.transList)
    RecyclerView transList;
    @BindView(R.id.emptyState)
    EmptyStateView emptyState;
    Unbinder unbinder;

    // Your presenter is available using the mPresenter variable

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        emptyState.builder(R.drawable.ic_wip, "Work In Progress", "Sorry :( we're under construction");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryList.setAdapter(new CategoryAdapter(getContext(), this));
        emptyState.setLoading(false)
                .isThereResult(false);
        // Your code here
        // Do not call mPresenter from here, it will be null! Wait for onStart
    }

    @Override
    protected void setupComponent(@NonNull AppComponent parentComponent) {
        DaggerHistoryViewComponent.builder()
                .appComponent(parentComponent)
                .historyViewModule(new HistoryViewModule())
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<HistoryPresenter> getPresenterFactory() {
        return mPresenterFactory;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
        List<ModelCategory> modelCategories;
        Context mContext;
        private int selected;
        private HistoryView mTransView;

        public CategoryAdapter(Context context, HistoryView transaksiView) {
            mContext = context;
            mTransView = transaksiView;
            modelCategories = new ArrayList<>();
            modelCategories.add(new ModelCategory("On Going", ON_GOING));
            modelCategories.add(new ModelCategory("Complete", COMPLETE));
            modelCategories.add(new ModelCategory("Failed", FAILED));
            modelCategories.add(new ModelCategory("Wallet", WALLET));
//            if (mUserData.getUserIsAgent() == 1) {
//                modelCategories.add(new ModelCategory("Penarikan", PENARIKAN));
//                modelCategories.add(new ModelCategory("Affiliasi", AFFILIASI));
//            }
            modelCategories.add(new ModelCategory("Booking", BOOKING));
//            modelCategories.add(new ModelCategory("Tiket Pesawat", PESAWAT));
//            modelCategories.add(new ModelCategory("Qurban", QURBAN));
            selected = 0;
            selectItem(selected, modelCategories.get(selected).getmOrder());

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.bind(modelCategories.get(i), i);
        }

        @Override
        public int getItemCount() {
            return modelCategories.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bind(final ModelCategory modelCategory, final int i) {
                title.setText(modelCategory.getmTitle());
                title.setBackgroundResource(modelCategory.isSelected() ? R.drawable.category_selected : R.drawable.category_default);
                title.setTextColor(mContext.getResources().getColor(modelCategory.isSelected() ? R.color.white : R.color.grey_700));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectItem(i, modelCategory.getmOrder());
                    }
                });
            }
        }

        private void selectItem(int i, int getmOrder) {
//            mTransView.getHistory(getmOrder);
            modelCategories.get(selected).setSelected(false);
            modelCategories.get(i).setSelected(true);
            notifyItemChanged(selected);
            notifyItemChanged(i);
            selected = i;
        }
    }
}
