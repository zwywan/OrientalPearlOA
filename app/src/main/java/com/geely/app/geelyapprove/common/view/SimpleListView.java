package com.geely.app.geelyapprove.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geely.app.orientalpearl.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oliver on 2016/11/1.
 */

public class SimpleListView extends ListView implements AbsListView.OnScrollListener {

    private View headerView;
    private int headerViewHeight;
    private ImageView iv_arrow;
    private TextView tv_state;
    private TextView tv_time;
    private View footerView;
    private ProgressBar pb_rotate;
    private int footerViewHeight;
    private int downY;

    private final int PULL_REFRESH = 0;     //下拉刷新的状态值
    private final int RELEASE_REFRESH = 1;  //松开刷新的状态值
    private final int REFRESHING = 2;       //正在刷新的状态值
    private int currentState = PULL_REFRESH;

    private RotateAnimation upAnimation, downAnimation;

    private boolean isLoadingMore = false;

    private boolean isPullRefreshed = false;

    public SimpleListView(Context context) {
        this(context, null);
    }

    public SimpleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public int getCurrentState(){
        return currentState;
    }

    private void init() {
        setOnScrollListener(this);
        initHeaderView();
        initRotateAnimation();
        initFooterView();
    }


    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.layout_loading, null);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_rotate = (ProgressBar) headerView.findViewById(R.id.pb_rotate);
        tv_state = (TextView) headerView.findViewById(R.id.tv_state);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        headerView.measure(0, 0);
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        addHeaderView(headerView);
    }

    private void initRotateAnimation() {

        upAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(300);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(300);
        downAnimation.setFillAfter(true);
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.layout_loading, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        addFooterView(footerView);
    }


    int x = 0;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (currentState == REFRESHING) {
                    break;
                }
                downY = (int) ev.getY();
                x = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) (ev.getY() - downY);
                if (downY == 0 || x == 0){
                    downY = (int) ev.getY();
                    x = (int) ev.getX();
                    break;
                }
                int paddingTop = -headerViewHeight + deltaY;
                if ((paddingTop > -headerViewHeight && getFirstVisiblePosition() == 0) || this.getCount() == 0) {
                    if (paddingTop >= 0 && currentState == PULL_REFRESH) {
                        currentState = RELEASE_REFRESH;
                        refreshHeaderView();
                    } else if (paddingTop < 0 && currentState == RELEASE_REFRESH) {

                        currentState = PULL_REFRESH;
                        refreshHeaderView();
                    }
                    headerView.setPadding(0, paddingTop > 40?40:paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                x = 0;
                downY = 0;
                if (currentState == PULL_REFRESH) {
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                } else if (currentState == RELEASE_REFRESH) {
                    currentState = REFRESHING;
                    refreshHeaderView();
                    if (listener != null) {
                        listener.onPullRefresh();
                        isPullRefreshed = true;
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshHeaderView() {
        switch (currentState) {
            case PULL_REFRESH:
                tv_state.setText("下拉刷新");
                iv_arrow.startAnimation(downAnimation);
                break;
            case RELEASE_REFRESH:
                tv_state.setText("松开刷新");
                iv_arrow.startAnimation(upAnimation);
                break;
            case REFRESHING:
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(View.GONE);
                pb_rotate.setVisibility(View.VISIBLE);
                tv_state.setText("正在刷新...");
                isPullRefreshed = true;
                break;
        }
    }

    public boolean isPullRefreshed() {
        return isPullRefreshed;
    }

    public void setPullRefreshed(boolean pullRefreshed) {
        isPullRefreshed = pullRefreshed;
    }

    public void completeRefresh() {
        if (isLoadingMore) {
            footerView.setPadding(0, -footerViewHeight, 0, 0);
            isLoadingMore = false;
        } else {
            headerView.setPadding(0, -headerViewHeight, 0, 0);
            currentState = PULL_REFRESH;
            pb_rotate.setVisibility(View.GONE);
            iv_arrow.setVisibility(View.VISIBLE);
            tv_state.setText("下拉刷新");
            tv_time.setText("最后刷新：" + getCurrentTime());
        }
        isPullRefreshed = false;
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private OnRefreshListener listener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public interface OnRefreshListener {
        void onPullRefresh();

        void onLoadingMore();

        void onScrollOutside();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && getLastVisiblePosition() == (getCount() - 1) && !isLoadingMore) {
            isLoadingMore = true;
            footerView.setPadding(0, 0, 0, 0);
            setSelection(getCount());
            if (listener != null) {
                listener.onLoadingMore();
            }
        }
        if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
            listener.onScrollOutside();
        }

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}
