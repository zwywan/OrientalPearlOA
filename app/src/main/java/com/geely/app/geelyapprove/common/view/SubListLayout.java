package com.geely.app.geelyapprove.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geely.app.orientalpearl.R;

import java.util.Set;

/**
 * Created by Oliver on 2016/11/25.
 */

public class SubListLayout extends LinearLayout {

    private Set<String> barCode ;
    private int id = 0;
    ResultCallback callback;

    public enum ActionType{
        DELETE,CLICK,ADD
    }

    public SubListLayout(Context context) {
        this(context,null);
    }

    public SubListLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SubListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<String> getBarCode() {
        return barCode;
    }

    public void setBarCode(Set<String> barCode) {
        this.barCode = barCode;
        if(barCode!=null){
            for(String bar:barCode) {
                addItem(bar);
            }
        }
    }

    public void addBarcode(String barcode){
        barCode.add(barcode);
        addItem(barcode);
    }

    private void removeItem(View view, String barcode, int id){
        barCode.remove(barcode);
        removeView(view);
        callback.onAction(barcode,ActionType.DELETE,id);
    }

    public ResultCallback getCallback() {
        return callback;
    }

    public void setCallback(ResultCallback callback) {
        this.callback = callback;
    }

    private void addItem(final String barcode){
        final LinearLayout container = (LinearLayout) inflate(getContext(), R.layout.layout_sublist_item,null);
        container.setId(++id);
        TextView title = (TextView) container.findViewById(R.id.sublist_item_barcode);
        ImageButton delete = (ImageButton) container.findViewById(R.id.sublist_item_delete);
        title.setText(barcode);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback!=null){
                    removeItem(container,barcode,container.getId());
                }
            }
        });
        title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback!=null){
                    callback.onAction(barcode,ActionType.CLICK,container.getId());
                }
            }
        });
        containerView.addView(container,0);
    }

    private LinearLayout containerView;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Button btnAdd = (Button) findViewById(R.id.sublist_add);
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onAction("",ActionType.ADD,0);
            }
        });
        containerView = (LinearLayout) findViewById(R.id.sublist_container);
    }

    public interface ResultCallback{
        void onAction(String barcode,ActionType type,int id);
    }

}
