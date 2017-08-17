package com.geely.app.geelyapprove.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.geely.app.orientalpearl.R;


/**
 * Created by Oliver on 2016/9/19.
 */
public class HandToolbar extends FrameLayout {

    private static final int RES_ID = R.layout.layout_tool_bar;
    private TextView tvTitle, tvLeft, tvRight;
    private OnButtonsClickCallback callback;
    public HandToolbar(Context context) {
        super(context);
        setView(context);
    }

    public interface OnButtonsClickCallback {
        void onButtonClickListner(VIEWS views, int radioIndex);
    }

    public HandToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setView(context);

    }

    public HandToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView(context);

    }

    public void setDisplayHomeAsUpEnabled(boolean enable, final Activity activity) {
        if (enable) {
            setLeftButton(R.drawable.ic_arrow_left);
            tvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onBackPressed();
                }
            });
        }
    }

    public void setBackHome(boolean enable,final Activity activity){
        if (enable) {
            setRightButton(R.drawable.ic_home_back);
            tvRight.setVisibility(VISIBLE);
            tvRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    try {
                        //Class<?> clazz = Class.forName("com.movitech.EOP.activity.MainActivity");
                        Class<?> clazz = Class.forName("com.geely.app.geelyapprove.activities.LibMainActivity");
                        intent.setClass(activity, clazz);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    activity.startActivity(intent);
                }
            });
        }else {
            tvRight.setVisibility(GONE);
        }
    }

    public void setView(Context context) {
        inflate(context, R.layout.layout_tool_bar, this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        tvLeft = (TextView) findViewById(R.id.tv_left);
    }


    public void setTitle(CharSequence title) {
        tvTitle.setVisibility(VISIBLE);
        tvTitle.setText(title);
    }

    public void setLeftButton(Integer resDrawable) {

        if (resDrawable != null) {
            Drawable drawable = getResources().getDrawable(resDrawable);
            if (drawable != null) {
                drawable.setBounds(0, 0, 60, 60);
            }
            tvLeft.setCompoundDrawables(drawable, null, null, null);
        }
        if (resDrawable == null) {
            return;
        }
        tvLeft.setVisibility(VISIBLE);
        if (!tvLeft.hasOnClickListeners())
        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onButtonClickListner(VIEWS.LEFT_BUTTON, -1);
            }
        });
    }

    public void setRightButton(Integer resDrawable) {
        if (resDrawable != null) {
            tvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, resDrawable, 0);
        }
        if (resDrawable == null) {
            return;
        }
        tvRight.setVisibility(VISIBLE);
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onButtonClickListner(VIEWS.RIGHT_BUTTON, -1);
            }
        });
    }


    public void setButtonsClickCallback(OnButtonsClickCallback clickCallback) {
        this.callback = clickCallback;
    }

    public enum VIEWS {
        LEFT_BUTTON, RIGHT_BUTTON
    }
}
