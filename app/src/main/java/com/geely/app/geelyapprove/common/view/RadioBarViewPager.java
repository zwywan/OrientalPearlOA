package com.geely.app.geelyapprove.common.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.geely.app.orientalpearl.R;


/**
 * <p>This is a radio group，which is smarter and powerful.</p>
 * Created by Oliver on 2016/10/20.
 */
public class RadioBarViewPager extends LinearLayout {

    private SparseArray<String> text;
    private int colorText = Color.BLACK;
    private int colorBackground = Color.WHITE;
    private int colorForeground = Color.WHITE;
    private float[] radius;
    private int index = 0;

    public RadioBarViewPager(Context context) {
        super(context);
    }

    public RadioBarViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioBarViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void add(String title) {
        if (text == null) {
            text = new SparseArray<>();
        }
        text.put(index, title);
        index++;
    }

    public int getColorText() {
        return colorText;
    }

    public RadioBarViewPager setColorText(int colorText) {
        this.colorText = colorText;
        return this;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public RadioBarViewPager setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
        return this;
    }

    public int getColorForeground() {
        return colorForeground;
    }

    public RadioBarViewPager setColorForeground(int colorForeground) {
        this.colorForeground = colorForeground;
        return this;
    }

    public float[] getRadius() {
        return radius;
    }

    /**
     * @param radius topLeftRadius,bottomLeftRadius,bottomRightRadius,topRightRadius
     */
    public RadioBarViewPager setRadius(float... radius) {
        if (radius.length > 4) {
            throw new IllegalArgumentException("radius arguments length must be 4");
        }
        this.radius = radius;
        return this;
    }

    public void create() {
        LinearLayout root = (LinearLayout) inflate(getContext(), R.layout.layout_radio_buttons_bar, null);
        RadioGroup group = (RadioGroup) root.findViewById(R.id.rg_items);
        RadioButton rbLeft = (RadioButton) root.findViewById(R.id.rb_left);
        RadioButton rbRight = (RadioButton) root.findViewById(R.id.rb_right);
        root.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        group.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        if (text != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f);
            for (int i = 0; i < text.size(); i++) {
                if (i == 0) {
                    rbLeft.setText(text.get(i));
                    rbLeft.setId(i);
                    rbLeft.setVisibility(VISIBLE);
                } else if (i == text.size() - 1 && text.size() >= 2) {
                    rbRight.setText(text.get(i));
                    rbRight.setId(i);
                    rbRight.setVisibility(VISIBLE);
                } else if (text.size() >= 3) {
                    RadioButton rbMid = (RadioButton) inflate(getContext(), R.layout.radio_mid, null);
                    rbMid.setId(i);
                    rbMid.setText(text.get(i));
                    rbMid.setGravity(Gravity.CENTER);
                    rbMid.setLayoutParams(rbRight.getLayoutParams());
                    group.addView(rbMid, i);
                }
            }

        }

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (listener != null && text != null) {
                    String title = text.get(checkedId);
                    if (title != null) {
                        listener.onCheckedChanged(title, checkedId);
                    }
                }
            }
        });
        group.check(0);
        addView(root);
        invalidate();
    }

    OnCheckedChangeListener listener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(String title, int index);

    }



}
