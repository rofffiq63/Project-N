package com.need.unknown.component.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.need.unknown.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.PorterDuff.Mode.SRC_IN;

public class ProgressButtonView extends RelativeLayout {

    String text;
    OnClickListener onClickListener;

    Context context;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.progressBar)
    RelativeLayout progressBar;
    private int buttonColor;
    private boolean isLoading;
    private int textColor;

    public ProgressButtonView(Context context) {
        super(context);
        init(context);
    }

    public ProgressButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_progress_button, this);
        ButterKnife.bind(this);
    }

    public ProgressButtonView setText(String text) {
        this.text = text;
        button.setText(this.text);
        return this;
    }

    public ProgressButtonView setClickListener(OnClickListener onclickListener) {
        this.onClickListener = onclickListener;
        return this;
    }

    public void isLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (isLoading) disableButton();
        else enableButton();
        progressBar.setVisibility(isLoading ? VISIBLE : INVISIBLE);
    }

    public void build() {
        button.setText(text);
        button.setOnClickListener(onClickListener);
        progressBar.setBackgroundColor(getResources().getColor(buttonColor == 0 ? R.color.logoColor : buttonColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((ProgressBar) progressBar.getChildAt(0)).setIndeterminateTintList(ColorStateList.valueOf(getResources().getColor(textColor == 0 ? R.color.white : textColor)));
        }
        enableButton();
    }

    public ProgressButtonView setTextColor(int color) {
        textColor = color;
        return this;
    }

    public ProgressButtonView setButtonColor(@Nullable int color) {
        buttonColor = color;
        return this;
    }

    public void enableButton() {
        button.setEnabled(true);
        button.setBackgroundColor(getResources().getColor(buttonColor == 0 ? R.color.logoColor : buttonColor));
        button.setTextColor(getResources().getColor(textColor == 0 ? R.color.white : textColor));
    }

    public void disableButton() {
        button.setEnabled(false);
        button.setBackgroundColor(getResources().getColor(R.color.grey_400));
        button.setTextColor(getResources().getColor(R.color.grey_100));
    }

    public String getTitle() {
        return button.getText().toString();
    }

    public void click() {
        button.performClick();
    }
}
