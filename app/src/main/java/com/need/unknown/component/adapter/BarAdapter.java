package com.need.unknown.component.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.need.unknown.R;
import com.need.unknown.component.model.ModelMenu;
import com.need.unknown.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder> {
    List<ModelMenu> modelMenus;
    Context mContext;
    MainView mainView;
    private int selected;

    public BarAdapter(Context context, MainView mainView) {
        mContext = context;
        this.mainView = mainView;
        modelMenus = new ArrayList<>();
        modelMenus.add(new ModelMenu(R.drawable.ic_home_black_24dp, "Home", 0));
        modelMenus.add(new ModelMenu(R.drawable.ic_inbox_black_24dp, "Inbox", 1));
        modelMenus.add(new ModelMenu(R.drawable.ic_need_logo_dark, "Buy", 5));
        modelMenus.add(new ModelMenu(R.drawable.ic_history_black_24dp, "History", 2));
        modelMenus.add(new ModelMenu(R.drawable.ic_person_black_24dp, "Me", 3));
        select(modelMenus.get(0).getOrder(), 0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(modelMenus.get(position), position);
    }

    @Override
    public int getItemCount() {
        return modelMenus.size();
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

        public void bind(ModelMenu modelMenu, final int position) {
            icon.setImageResource(modelMenu.getIcon());
            title.setText(modelMenu.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                icon.setImageTintList(ColorStateList.valueOf(mContext.getResources().getColor(modelMenu.isSelected() ? R.color.logoColor : R.color.grey_400)));
            }
            title.setTextColor(mContext.getResources().getColor(modelMenu.isSelected() ? R.color.logoColor : R.color.grey_400));
            View view = (View) icon.getParent();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select(modelMenu.getOrder(), position);
                }
            });
        }
    }

    private void select(int order, int position) {
        mainView.selectFragment(order);
        switch (position) {
            case 2:
                return;
            default:
                modelMenus.get(selected).setSelected(false);
                modelMenus.get(position).setSelected(true);
                notifyItemChanged(selected);
                notifyItemChanged(position);
                selected = position;
                break;
        }
    }
}
