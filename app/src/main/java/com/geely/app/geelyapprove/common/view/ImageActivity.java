package com.geely.app.geelyapprove.common.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.geely.app.orientalpearl.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Oliver on 2016/11/8.
 */

public class ImageActivity extends Activity {

    public final static String EXTRA_FILE_NAME = "FILE";
    public final static String EXTRA_BUTTON_MODE = "BUTTON_MODE";
    private ImageView ivDelete;
    private DisplayMetrics dm;
    private Uri path;
    private final static int RESULT_DELETED = 9;
    private BUTTON_MODE mode = BUTTON_MODE.NONE;

    public static void showImage(Activity activity, Uri uri, BUTTON_MODE mode) {
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra(EXTRA_FILE_NAME, uri);
        intent.putExtra(EXTRA_BUTTON_MODE, mode);
        activity.startActivity(intent);
    }

    public static void showImage(Activity activity, String url, BUTTON_MODE mode) {
        Uri uri = Uri.parse(url);
        showImage(activity, uri, mode);
    }

    public enum BUTTON_MODE {
        DELETE, SAVE, SEND, NONE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image);
        bindView();
    }

    private void bindView() {
        path = (Uri) getIntent().getExtras().get(EXTRA_FILE_NAME);
        mode = (BUTTON_MODE) getIntent().getSerializableExtra(EXTRA_BUTTON_MODE);
        ivDelete = (ImageView) findViewById(R.id.iv_delete);
        if (mode == BUTTON_MODE.NONE) {
            ivDelete.setVisibility(View.GONE);
        }
        ImageButton ivClose = (ImageButton) findViewById(R.id.ib_close);
        AwesomeImageView imageView = (AwesomeImageView) findViewById(R.id.iv_image);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Picasso.with(this).load(path).into(imageView);
        ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult(RESULT_OK, new Intent().putExtra(EXTRA_FILE_NAME, path));
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        Picasso.with(this).invalidate(path);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onBackPressed();
    }

    private void sendFile() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path.getPath())));
        startActivity(Intent.createChooser(intent, "分享图片"));
    }

}