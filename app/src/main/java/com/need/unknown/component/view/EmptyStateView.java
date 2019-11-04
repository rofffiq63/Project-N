package com.need.unknown.component.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.need.unknown.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyStateView extends LinearLayout {
    @BindView(R.id.stateImage)
    ImageView stateImage;
    @BindView(R.id.stateTitle)
    TextView stateTitle;
    @BindView(R.id.stateDesc)
    TextView stateDesc;
    @BindView(R.id.no_result)
    LinearLayout noResult;
    @BindView(R.id.progress)
    CardView progress;
    Context context;

    public EmptyStateView(Context context) {
        super(context);
        init(context);
    }

    public EmptyStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyStateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.state_empty, this);
        ButterKnife.bind(this);
    }

    public void builder(@DrawableRes int imageRes, String title, String desc) {
        stateImage.setImageResource(imageRes);
        stateTitle.setText(title);
        stateDesc.setText(desc);
    }

    public void isThereResult(boolean thereIs) {
        noResult.setVisibility(thereIs ? GONE : VISIBLE);
    }

    public EmptyStateView setImage(@DrawableRes int imageRes) {
        stateImage.setImageResource(imageRes);
        return this;
    }

    public EmptyStateView setTitle(String title) {
        stateTitle.setText(title);
        return this;
    }

    public EmptyStateView setDesc(String desc) {
        stateDesc.setText(desc);
        return this;
    }

    public EmptyStateView setLoading(boolean isIt) {
        progress.setVisibility(isIt ? VISIBLE : GONE);
        return this;
    }

    public void reset() {
        noResult.setVisibility(GONE);
        progress.setVisibility(GONE);
    }
}
