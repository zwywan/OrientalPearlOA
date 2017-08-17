package com.geely.app.geelyapprove.common.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geely.app.geelyapprove.activities.ItemActivity;
import com.geely.app.geelyapprove.common.utils.DataUtil;
import com.geely.app.geelyapprove.common.utils.RoundRecTransform;
import com.geely.app.geelyapprove.datamanage.HttpManager;
import com.geely.app.orientalpearl.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Oliver on 2016/10/13.
 */

public class FileChooserLayout extends LinearLayout{

    GridLayout layout;
    FrameLayout layoutAdd;
    AlertDialog dialog;
    private int index = 0;
    public static final int TAKE_PICTURE = 0;
    public static final int CHOOSE_PICTURE = 1;
    public static final int CHOOSE_FILE = 2;
    public static final int CHECK_IMAGE_DETAIL = 3;
    private Activity activity;
    private DataChangeListener listener;
    private HashMap<Uri, View> files;
    private HashSet<Uri> paths = new HashSet<>();
    private Uri fileUri;
    private String UUID,WORKFLOWTYPE,CreateBy,GroupName,FileGroupValue;
    private static int column_index;
    private static Cursor cursor;
    private static int sdkVersion;

    public void setorkflowIdentifier(String uuid) {
        this.UUID = uuid;
    }

    public String getUUID(){
        return UUID;
    }

    public void setworkflowType(String workflowType) {
        this.WORKFLOWTYPE = workflowType;
    }

    public String getWORKFLOWTYPE(){
        return WORKFLOWTYPE;
    }

    public void setcreateBy(String createBy) {
        this.CreateBy = createBy;
    }

    public String getCreateBy(){
        return CreateBy;
    }

    public void setGroupName(String groupName) {
        this.GroupName = groupName;
    }

    public String getGroupName(){
        return GroupName;
    }

    public void setfileGroupValue(String fileGroupValue) {
        this.FileGroupValue = fileGroupValue;
    }

    public String getFileGroupValue(){
        return FileGroupValue;
    }

    public interface ActivityResultCallback {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public interface DataChangeListener {
        void onDeleteRemoteFile(Uri uri);
    }

    public DataChangeListener getListener() {
        return listener;
    }

    public HashSet<Uri> getPaths() {
        return paths;
    }

    public void setPaths(HashSet<Uri> paths) {
        this.paths = paths;
        if (this.paths != null) {
            Iterator<Uri> iterator = paths.iterator();
            while (iterator.hasNext()) {
                Uri pa = iterator.next();
                Log.e("extension", pa + "");
                addItem(pa);
            }
        }
    }

    public FileChooserLayout(Context context) {
        super(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else {
            throw new IllegalArgumentException("Context must be activity.");
        }
        initView();
    }

    public FileChooserLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FileChooserLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
        files = new HashMap<>();
        TextView tvName = new TextView(activity);
        tvName.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
        layout = new GridLayout(activity);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setColumnCount(4);
        layoutAdd = (FrameLayout) inflate(activity, R.layout.layout_grid_item, null);
        ImageView view = (ImageView) layoutAdd.findViewById(R.id.iv_image);
        view.setImageResource(R.mipmap.ic_add);
        addView(tvName);
        layout.addView(layoutAdd);
        layoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        addView(layout);
        ((ItemActivity) activity).setCallback(new ActivityResultCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                onActivityResultExec(requestCode, resultCode, data);
            }
        });
    }

    public FileChooserLayout setListener(DataChangeListener listener) {
        this.listener = listener;
        return this;
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(activity).setItems(new CharSequence[]{"相机", "照片", "手机文件"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        takePicture();
                    } else if (which == 1) {
                        choosePicture();
                    } else {
                        chooseFile();
                    }
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create();
        }
        dialog.show();
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = getContext().getExternalCacheDir().getAbsolutePath() + File.separator + "GE" + System.currentTimeMillis() + ".jpg";
        File tempFile = new File(path);
        fileUri = Uri.fromFile(tempFile);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        activity.startActivityForResult(Intent.createChooser(intent, "拍摄照片"), TAKE_PICTURE);
    }

    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        activity.startActivityForResult(intent, CHOOSE_PICTURE);
    }

    private void chooseFile() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            activity.startActivityForResult(Intent.createChooser(intent,"请选择您要上传的文件"),CHOOSE_FILE);
        }catch (ActivityNotFoundException ex){
            Toast.makeText(getContext(), "抱歉，没找到适合选取文件的程序", Toast.LENGTH_SHORT).show();
        }
    }

    public enum FileType {
        IMAGE,WORD,EXCEL,RAR
    }

    private void addItem(Uri uri) {
        if (paths == null) {
            return;
        }
        String extension = null;
        String path = null;
        if("file".equals(uri.getScheme())){
            path = uri.getPath();
            extension = path.substring(path.lastIndexOf("."));
        }else {
            path = HttpManager.getRealPathFromUri(uri, getContext());
            if(path!=null){
                uri = Uri.parse(path);
            }
            uri = Uri.fromFile(new File(uri.toString()));
            extension = path.substring(path.lastIndexOf("."));
        }

        FileType file = null;
        if (extension != null){
            extension = extension.toLowerCase();
            if(extension.endsWith(".jpg")||extension.endsWith(".jpeg")|| extension.endsWith(".png")||extension.endsWith(".bmp")|| extension.endsWith(".gif")) {
                file = FileType.IMAGE;
            }else if(extension.endsWith(".doc")||extension.endsWith(".docx")){
                file = FileType.WORD;
            }else if(extension.endsWith(".xls")||extension.endsWith(".xlsx")){
                file = FileType.EXCEL;
            }else if(extension.endsWith(".rar")){
                file = FileType.RAR;
            }else {
                Toast.makeText(activity, "不支持的文件格式", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        final FrameLayout frameLayout = (FrameLayout) inflate(activity, R.layout.layout_grid_item, null);
        if (files.put(uri, frameLayout) != null) {
            Toast.makeText(activity, "你已经添加过了", Toast.LENGTH_SHORT).show();
        } else {
            paths.add(uri);
            final TextView tvName = (TextView) frameLayout.findViewById(R.id.tv_file_name);
            final ImageView ivPic = (ImageView) frameLayout.findViewById(R.id.iv_image);
            final Uri finalUri = uri;
            final String finalExtension = extension;
            ivPic.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataUtil.openFile(getContext(), finalUri, finalExtension);
                }
            });
            ImageView ivDelete = (ImageView) frameLayout.findViewById(R.id.iv_delete);
            ivDelete.setVisibility(VISIBLE);
            ivDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    layout.post(new Runnable() {
                        @Override
                        public void run() {
                            delete(finalUri);
                        }
                    });
                }
            });
            if (file == FileType.IMAGE) {
                loadFile(activity, uri, ivPic, tvName, 1);
            } else if (file == FileType.WORD) {
                loadFile(activity, uri, ivPic, tvName, 2);
            } else if (file == FileType.EXCEL) {
                loadFile(activity, uri, ivPic, tvName, 3);
            } else if (file == FileType.RAR) {
                loadFile(activity, uri, ivPic, tvName, 4);
            }
            layout.addView(frameLayout, 0);
        }

    }


    private void loadFile(final Activity activity, final Uri uri, final ImageView ivPic, final TextView tvName ,final int type) {
        new AsyncTask<Void, Integer, Boolean>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                if (aBoolean){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (type){
                                case 1:
                                    Picasso.with(activity).load(uri).resize(100, 100).centerCrop().transform(new RoundRecTransform()).into(ivPic);
                                    tvName.setText(DataUtil.getCurrentDate());
                                    break;
                                case 2:
                                    Picasso.with(activity).load(R.drawable.ic_word).resize(100, 100).centerCrop().transform(new RoundRecTransform()).into(ivPic);
                                    tvName.setText(DataUtil.getCurrentDate());
                                    break;
                                case 3:
                                    Picasso.with(activity).load(R.drawable.ic_excel).resize(100, 100).centerCrop().transform(new RoundRecTransform()).into(ivPic);
                                    tvName.setText(DataUtil.getCurrentDate());
                                    break;
                                case 4:
                                    Picasso.with(activity).load(R.drawable.ic_rar).resize(100,100).centerCrop().transform(new RoundRecTransform()).into(ivPic);
                                    tvName.setText(DataUtil.getCurrentDate());
                                    break;
                            }

                        }
                    });
                }
            }

        }.execute();

    }

    private void delete(Uri path) {
        layout.removeView(files.get(path));
        files.remove(path);
        paths.remove(path);
        if(listener!=null){
            listener.onDeleteRemoteFile(path);
        }
    }

    @RequiresApi
    public void onActivityResultExec(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch(requestCode){
                case TAKE_PICTURE:
                    if (fileUri != null) {
                        addItem(fileUri);
                    }
                    break;
                case CHOOSE_PICTURE:
                    if (data.getData() != null) {
                        addItem(data.getData());
                    }
                    break;
                case CHOOSE_FILE:
                    if(data.getData()!=null){
                        addItem(data.getData());
                    }
                    break;
                case CHECK_IMAGE_DETAIL:
                    if (data != null) {
                        Uri path = (Uri) (data.getExtras().get(ImageActivity.EXTRA_FILE_NAME));
                        delete(path);
                    }
                    break;
            }

        }
    }

    public static String getPathByUri(Context context, Uri uri) {
        sdkVersion = Build.VERSION.SDK_INT;
        /*if (sdkVersion < 11) {
            // SDK < Api11
            return getRealPathFromUri_BelowApi11(context, uri);
        }
        if (sdkVersion < 19) {
            // SDK > 11 && SDK < 19
            return getRealPathFromUri_Api11To18(context, uri);
        }
        // SDK > 19*/
        if (sdkVersion < 19){
            return getRealPathFromUri_BelowApi19(context, uri);
        }else {
            return getRealPathFromUri_AboveApi19(context, uri);
        }
    }

    private static String getRealPathFromUri_BelowApi19(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    @TargetApi(19)
    public static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        String column = "_data";
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 适配api11-api18,根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri fileUrl) {
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(fileUrl, proj, null, null, null);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
        } catch (Exception e) {
            return null;
        }
        if (column_index == -1) {
            return null;
        }
        String path = cursor.getString(column_index);
        return path;

    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }
}
