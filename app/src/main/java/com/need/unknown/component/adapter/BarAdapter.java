package com.need.unknown.component.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.need.unknown.R;
import com.need.unknown.component.model.MenuModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {
    List<MenuModel> menuModels;
    Context mContext;
    private int selected;

    public BarAdapter(Context context) {
        mContext = context;
        menuModels = new ArrayList<>();
        menuModels.add(new MenuModel(R.drawable.ic_home_black_24dp, "Home"));
        menuModels.add(new MenuModel(R.drawable.ic_inbox_black_24dp, "Inbox"));
        menuModels.add(new MenuModel(R.drawable.ic_need_logo_dark, "Buy"));
        menuModels.add(new MenuModel(R.drawable.ic_history_black_24dp, "History"));
        menuModels.add(new MenuModel(R.drawable.ic_person_black_24dp, "Me"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(menuModels.get(position), position);
    }

    @Override
    public int getItemCount() {
        return menuModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MenuModel menuModel, final int position) {
            icon.setImageResource(menuModel.getIcon());
            title.setText(menuModel.getTitle());
            icon.setImageTintList(ColorStateList.valueOf(mContext.getResources().getColor(menuModel.isSelected() ? R.color.logoColor : R.color.grey_400)));
            title.setTextColor(mContext.getResources().getColor(menuModel.isSelected() ? R.color.logoColor : R.color.grey_400));
            View view = (View) icon.getParent();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select(position);
                }
            });
        }
    }

    private void select(int position) {
        menuModels.get(selected).setSelected(false);
        menuModels.get(position).setSelected(true);
        notifyItemChanged(selected);
        notifyItemChanged(position);
        selected = position;
    }
}
