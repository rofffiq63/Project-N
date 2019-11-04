package com.need.unknown.component.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.need.unknown.BuildConfig;
import com.need.unknown.R;
import com.need.unknown.component.model.ModelMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<ModelMenu> modelMenus;

    public ListMenuAdapter(Context context, FirebaseUser firebaseUser) {
        mContext = context;
        modelMenus = new ArrayList<>();
        modelMenus.add(new ModelMenu(null, "Balance", "", "Rp1.000.000", R.drawable.ic_balance_asset));
        modelMenus.add(new ModelMenu("Features", "Cashout", "", "", R.drawable.ic_cashout_asset));
        modelMenus.add(new ModelMenu(null, "Promo Code", "", "", R.drawable.ic_coupon_asset));
        modelMenus.add(new ModelMenu("Info", "Help", "", "", 0));
        modelMenus.add(new ModelMenu(null, "Terms of Service", "", "", 0));
        modelMenus.add(new ModelMenu(null, "Privacy Policy", "", "", 0));
        modelMenus.add(new ModelMenu(null, "Need Version", "null", BuildConfig.VERSION_NAME, 0));
        modelMenus.add(new ModelMenu("", "Rate Need", "", "", 0));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MenuHolder) holder).bind(modelMenus.get(position));
    }

    @Override
    public int getItemCount() {
        return modelMenus.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header)
        TextView header;
        @BindView(R.id.imageMenu)
        ImageView imageMenu;
        @BindView(R.id.menu)
        TextView menu;
        @BindView(R.id.subMenu)
        TextView subMenu;
        @BindView(R.id.menuInfo)
        TextView menuInfo;
        @BindView(R.id.arrowIndicator)
        ImageView arrowIndicator;

        public MenuHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void bind(ModelMenu modelMenu) {
            header.setVisibility(modelMenu.getHeader() == null ? View.GONE : View.VISIBLE);
            if (modelMenu.getHeader() != null)
                header.setText(modelMenu.getHeader());
            menu.setText(modelMenu.getTitle());
            subMenu.setText(modelMenu.getSubTitle());
            subMenu.setVisibility((modelMenu.getSubTitle().isEmpty() || modelMenu.getSubTitle().equals("null")) ? View.GONE : View.VISIBLE);
            menuInfo.setText(modelMenu.getMenuInfo());
            arrowIndicator.setVisibility(modelMenu.getSubTitle().equals("null") ? View.GONE : View.VISIBLE);
            imageMenu.setVisibility(modelMenu.getIcon() == 0 ? View.GONE : View.VISIBLE);
            if (modelMenu.getIcon() != 1)
                imageMenu.setImageResource(modelMenu.getIcon());
        }
    }
}
