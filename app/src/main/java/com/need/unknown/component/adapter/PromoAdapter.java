package com.need.unknown.component.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.need.unknown.R;
import com.need.unknown.component.view.RoundRectCornerImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<String> imageList;

    public PromoAdapter(Context context) {
        mContext = context;
        imageList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            imageList.add("https://picsum.photos/id/" + (i * 12) + "/480/640");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PromoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_promo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PromoHolder) holder).bind(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class PromoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imagePromo)
        RoundRectCornerImageView imagePromo;

        public PromoHolder(View inflate) {
            super(inflate);
            ButterKnife.bind(this, inflate);
        }

        public void bind(String s) {
            Picasso.get()
                    .load(s)
                    .into(imagePromo);
        }
    }
}
