package com.geely.app.geelyapprove.common.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Oliver on 2016/11/17.
 */
public class AwesomeImageView extends ImageView {
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    float minScaleR = 1f;
    private int mode = NONE;
    private float oldDist;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private DisplayMetrics dm;
    private float srcWidth = 0, srcHeight = 0;


    public AwesomeImageView(Context context) {
        super(context);
        init();
    }

    public AwesomeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AwesomeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dm = getResources().getDisplayMetrics();
        float[] values = new float[9];
        setImageMatrix(matrix);
        matrix.set(getMatrix());
        matrix.getValues(values);
        srcWidth = getWidth() / values[Matrix.MSCALE_X];
        srcHeight = (getHeight() - values[Matrix.MTRANS_Y] * 2) / values[Matrix.MSCALE_Y];
        setOnTouchListener(new ViewTouchListener());
        center(true, true);

    }

    private class ViewTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView view = (ImageView) v;
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    mode = DRAG;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;

                    break; //多点触控
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    if (oldDist > 10f) {
                        savedMatrix.set(matrix);
                        midPoint(mid, event);
                        mode = ZOOM;
                    }
                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                    } else if (mode == ZOOM) {
                        float newDist = spacing(event);
                        if (newDist > 10f) {
                            matrix.set(savedMatrix);
                            float scale = newDist / oldDist;
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }
                    }
            }
            view.setImageMatrix(matrix);
            checkView();
            return true;
        }


    }

    //两点间距离
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    //中点坐标
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * 横向、纵向居中
     */
    protected void center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, srcWidth, srcWidth);
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            int screenHeight = dm.heightPixels;
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = getHeight() - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = dm.widthPixels;
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }

        matrix.postTranslate(deltaX, deltaY);
    }


    /**
     * 限制最大最小缩放比例，自动居中
     */
    private void checkView() {
        float p[] = new float[9];
        matrix.getValues(p);
        if (mode == ZOOM) {
            if (p[0] < minScaleR) {
                matrix.setScale(minScaleR, minScaleR);
            }
            if (p[0] > 15f) {
                matrix.set(savedMatrix);
            }
        }
        center(true, true);
    }
}
