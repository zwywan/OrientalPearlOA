package com.geely.app.geelyapprove.common.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * Created by Oliver on 2016/11/7.
 */

public class RoundRecTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        int min = source.getWidth();
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawRoundRect(new RectF(0, 0, min, min), 15, 15, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        source.recycle();
        return target;
    }

    @Override
    public String key() {
        return "roundRect";
    }
}
