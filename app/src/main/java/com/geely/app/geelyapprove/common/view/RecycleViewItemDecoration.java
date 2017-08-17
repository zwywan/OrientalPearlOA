package com.geely.app.geelyapprove.common.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Oliver on 2016/11/8.
 */
public class RecycleViewItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;

    public RecycleViewItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(view.getVisibility()==View.VISIBLE){
            if(parent.getChildAdapterPosition(view) !=parent.getAdapter().getItemCount()-1){
                outRect.bottom = mSpace;
            }else {
                outRect.bottom = 0;
            }
        }
    }
}
