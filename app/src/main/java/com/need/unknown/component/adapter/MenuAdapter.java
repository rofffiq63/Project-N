package com.need.unknown.component.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.need.unknown.R;
import com.need.unknown.component.model.ModelMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context mContext;
    List<ModelMenu> modelMenus;

    public MenuAdapter(Context context) {
        mContext = context;
        modelMenus = new ArrayList<>();
        modelMenus.add(new ModelMenu("Prepaid", R.drawable.ic_prepaid_asset));
        modelMenus.add(new ModelMenu("Postpaid", R.drawable.ic_postpaid_asset));
        modelMenus.add(new ModelMenu("Games", R.drawable.ic_games_asset ));
        modelMenus.add(new ModelMenu("Electricity", R.drawable.ic_electricity_asset));
        modelMenus.add(new ModelMenu("Water", R.drawable.ic_water_asset));
        modelMenus.add(new ModelMenu("BPJS", R.drawable.ic_bpjs_asset));
        modelMenus.add(new ModelMenu("Telkom", R.drawable.ic_telkom_asset));
        modelMenus.add(new ModelMenu("Internet", R.drawable.ic_internet_asset));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((MenuHolder) holder).bind(modelMenus.get(position));
    }

    @Override
    public int getItemCount() {
        return modelMenus.size();
    }

    public class MenuHolder extends ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.title)
        TextView title;

        public MenuHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void bind(ModelMenu modelMenu) {
            title.setText(modelMenu.getTitle());
            icon.setImageResource(modelMenu.getIcon());
        }
    }
}
