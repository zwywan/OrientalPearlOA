package com.geely.app.geelyapprove.datamanage;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.geely.app.geelyapprove.common.utils.GsonUtil;
import com.geely.app.geelyapprove.common.utils.StringUtils;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * zhy
 * Created on 2016/7/23.
 */
//统一网络管理类
public class HttpManager {

    private static OkHttpClient okHttpClient = new OkHttpClient() ;
    private Response response;
    private static final MediaType FORM_TYPE = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    private HttpManager() {

    }

    private static final HttpManager sHttpManager = new HttpManager();

    public static HttpManager getInstance() {

        return sHttpManager;
    }

    //从网络获取数据
    //get方式
    public <T> T get(String url, Class<T> clazz) {
        //1. 优先从网络获取数据

        String content = sHttpManager.dataGet(url);

       /* System.out.println("当前网络获取的数据:"+content);


        if (TextUtils.isEmpty(content)) {
            //2. 如果网络数据为空
            //去缓存 获取数据
            content = CacheManger.getInstance().getData(url);
            System.out.println("得到缓存数据");

        } else {
            //3. 保存数据
            CacheManger.getInstance().saveData(url,content);

        }*/

        //解析json数据

        Object obj = parseJson(content, clazz);
        return parseJson(content, clazz);
    }

    public String get(String url) {
        String content = sHttpManager.dataGet(url);
        return content;
    }

    public <T> T requestResultFormSync(final String url, final Map<String, Object> map, final Class<T> clazz) {

        FormBody.Builder requestBody = new FormBody.Builder();
        for (String key : map.keySet()) {
            try {
                requestBody.add(key, String.valueOf(map.get(key)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final Request request = new Request.Builder().url(url).post(requestBody.build()).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            T t = null;
            String content = "";
            try {
                content = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            t = parseJson(content, clazz);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public <T> T requestResult(String url, Map<String, Object> params, TypeToken<T> token) {
        String content = sHttpManager.dataGet(StringUtils.getUrlWithGet(url, params));
        T t = GsonUtil.parseJsonWithType(content, token);
        return t;
    }

    public String requestResultPost(String url, Map<String, Object> params) {
        FormBody.Builder body = new FormBody.Builder();
        for (String key : params.keySet()) {
            body.add(key, (String) params.get(key));
        }
        return doFormPost(url, body.build());
    }

    public interface ResultCallback<T> {
        void onSuccess(String json, T t) throws InterruptedException;

        void onFailure(String msg);
    }

    public <T> void requestResultForm(final String url, final Map<String, Object> map, final Class<T> clazz, final ResultCallback<T> callback) {

        try {
            FormBody.Builder requestBody = new FormBody.Builder();
            for (String key : map.keySet()) {
                requestBody.add(key, String.valueOf(map.get(key)));
            }
            final Request request = new Request.Builder()
                    .url(url).post(requestBody.build()).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    T t = null;
                    try {
                        callback.onFailure(e.getMessage());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    T t = null;
                    String content = "";
                    try {
                        content = response.body().string();
                        setJson(content);
                        t = parseJson(content, clazz);
                        if (t != null) {
                            callback.onSuccess(content, t);
                        } else {
                            callback.onFailure(content);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(e.getMessage());
        }

    }


    public HttpManager setJson(String json) {
        return this;
    }
    public <T> void requestResultForm(final String url, final Map<String, Object> map, final TypeToken<T> token, final ResultCallback<T> callback) {

        FormBody.Builder requestBody = new FormBody.Builder();
        for (String key : map.keySet()) {
            //加密
            try {
                requestBody.add(key, String.valueOf(map.get(key)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final Request request = new Request.Builder()
                .url(url).post(requestBody.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                try {
                    callback.onFailure(e.getMessage());

                }catch (NullPointerException ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                T t = null;
                String content = "";
                try {
                    content = response.body().string();
                    setJson(content);
                    t = GsonUtil.parseJsonWithType(content, token);
                    if (t != null) {
                        callback.onSuccess(content, t);
                    } else {
                        callback.onFailure("转换Bean失败");
                    }
                    return;
                } catch (Exception e) {
                    try{
                        callback.onFailure(e.getMessage());
                    }catch (NullPointerException ex){

                    }
                }

            }
        });

    }

    private String doFormPost(String url, RequestBody body) {
        final Request request = new Request.Builder()
                .url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return "";
    }


    private <T> T parseJson(String content, Class<T> clazz) {
        return GsonUtil.parseJsonToBean(content, clazz);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static String uri2Base64(Uri uri, Context context) {
        try {
            String scheme = uri.getScheme();
            String path = "";
            if(ContentResolver.SCHEME_FILE.equalsIgnoreCase(scheme)){
                path = uri.getPath();
            }else if(ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(scheme)){
                path = getRealPathFromUri(uri,context);
            }
            String extension = path.toLowerCase();
            if(extension.endsWith(".doc")||extension.endsWith(".docx")||
                    extension.endsWith(".xls")||extension.endsWith(".xlsx")){
                File file = new File(path);
                FileInputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                inputStream.read(bytes);
                inputStream.close();
                return Base64.encodeToString(bytes,Base64.DEFAULT);
            }else if(extension.endsWith(".jpg")||extension.endsWith(".jpeg")||
                    extension.endsWith(".png")||extension.endsWith(".bmp")||
                    extension.endsWith(".gif")){
                String string = bitmapToString(path);
                return string;
            }else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getRealPathFromUri(Uri contentUri, Context context) {

        if(contentUri.toString().startsWith("file")){
            return contentUri.toString();
        }
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);

        if(cursor == null){
            return contentUri.getPath();
        }
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private String dataGet(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
        return response.toString();
    }

    public <T> T getDataBean(String url, Class<T> clazz) {
        //1. 优先从网络获取数据

        String content = HttpManager.getInstance().dataGet(url);

        /*System.out.println("当前网络获取的数据:"+content);


        if (TextUtils.isEmpty(content)) {
            //2. 如果网络数据为空
            //去缓存 获取数据
            content = CacheManger.getInstance().getData(url);
            System.out.println("得到缓存数据");

        } else {
            //3. 保存数据
            CacheManger.getInstance().saveData(url,content);

        }*/

        //解析json数据

        Object obj = parseJson(content, clazz);
/*
        return obj;*/

        return parseJson(content, clazz);
        // return  content;

    }

    public void destroy() {
        okHttpClient = null;
    }
}
