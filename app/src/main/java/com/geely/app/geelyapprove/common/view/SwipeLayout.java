package com.geely.app.geelyapprove.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zhy on 2016/11/25.
 */
public class SwipeLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;
    private View mBackLayout; // 后布局
    private View mFrontLayout;// 前布局
    private int mHeight; // 控件高度
    private int mWidth;  // 控件宽度
    private int mRange;  // 拖拽范围
    private Status status = Status.Close;
    private boolean canSlide = true;

    public static enum Status{
        Close,
        Open,
        Swiping
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 1. 创建ViewDragHelper
        mDragHelper = ViewDragHelper.create(this, callback);

    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackLayout = getChildAt(0);
        mFrontLayout = getChildAt(1);
    }


    public boolean getSlide() {
        return canSlide;
    }

    public void setSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }

    // 3. 重写监听回调
    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        // 返回值决定了被触摸到的子控件child能否被拖拽
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return canSlide;
        }

        // 返回一个 > 0 值即可
        @Override
        public int getViewHorizontalDragRange(View child) {
            return 10;
        }

        // 返回值决定了将要移动到的水平方向位置.
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(child == mFrontLayout){
                // 限定前布局的左右边界
                if(left < -mRange){
                    left = -mRange;
                }else if(left > 0){
                    left = 0;
                }
            }else if(child == mBackLayout){
                // 限定后布局的左右边界
                if(left < mWidth - mRange){
                    left = mWidth - mRange;
                }else if(left > mWidth){
                    left = mWidth;
                }
            }

            return left;
        }

        // 当一个控件的位置发生变化了, 此方法会被调用
        // 在此处可以把自己的变化量传递给其他控件
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if(changedView == mFrontLayout){
                // 如果拖拽的是前布局, 把水平变化量传递给后布局
//                mBackLayout.offsetLeftAndRight(dx);
                ViewCompat.offsetLeftAndRight(mBackLayout, dx);
            }else if(changedView == mBackLayout){
                // 如果拖拽的是后布局, 把水平变化量传递给前布局
//                mFrontLayout.offsetLeftAndRight(dx);
                ViewCompat.offsetLeftAndRight(mFrontLayout, dx);
            }

            diapathEvent();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if(xvel == 0 && mFrontLayout.getLeft() < (-mRange * 0.5f)){
                open();
            }else if(xvel < 0){
                open();
            }else {
                close();
            }
        }

    };

    private void diapathEvent() {

        // 记录之前的状态
        Status lastStatus = status;
        // 更新状态
        status = updateStatus();

        // 状态发生变化的时候,执行监听
        if(lastStatus != status && onSwipeListener != null){
            if(status == Status.Close){
                onSwipeListener.onClose(this);
            } else if(status == Status.Open){
                onSwipeListener.onOpen(this);
            } else {
                if(lastStatus == Status.Close){
                    onSwipeListener.onStartOpen(this);
                }
            }
        }

    }

    /**
     * 更新状态
     * @return
     */
    private Status updateStatus() {
        int left = mFrontLayout.getLeft();
        if(left == 0){
            return Status.Close;
        }else if(left == -mRange){
            return Status.Open;
        }
        return Status.Swiping;
    }

    public void close() {
        close(true);
    }

    public void close(boolean isSmooth){
        isOpen = false;
        if(isSmooth){
            if(mDragHelper.smoothSlideViewTo(mFrontLayout, 0, 0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else {
            layoutContent(isOpen);
        }
    }


    public void open() {
        open(true);
    }

    public void open(boolean isSmooth){
        isOpen = true;
        if(isSmooth){
            if(mDragHelper.smoothSlideViewTo(mFrontLayout, -mRange, 0)){
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }else {
            layoutContent(isOpen);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    // 2. 转交 拦截判断, 触摸事件

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mRange = mBackLayout.getMeasuredWidth();

    }

    boolean isOpen = false;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        layoutContent(isOpen);
    }

    /**
     * 根据开关状态布局内容
     * @param isOpen 是否是打开状态
     */
    private void layoutContent(boolean isOpen) {
        // 摆放前布局
        Rect frontRect = computeFrontRect(isOpen);
        mFrontLayout.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);
        // 摆放后布局
        Rect backRect = computeBackRect(frontRect);
        mBackLayout.layout(backRect.left, backRect.top, backRect.right, backRect.bottom);

        // 将指定控件前置
        bringChildToFront(mFrontLayout);
    }

    private Rect computeBackRect(Rect frontRect) {
        int left = frontRect.right;
        return new Rect(left, 0, left + mRange, 0 + mHeight);
    }

    private Rect computeFrontRect(boolean isOpen) {
        int left = 0;
        if(isOpen){
            left = -mRange;
        }
        return new Rect(left, 0, left + mWidth, 0 + mHeight);
    }

    private OnSwipeListener onSwipeListener;

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }

    public interface OnSwipeListener{
        void onClose(SwipeLayout layout);
        void onOpen(SwipeLayout layout);
        void onStartOpen(SwipeLayout layout);
    }



}
