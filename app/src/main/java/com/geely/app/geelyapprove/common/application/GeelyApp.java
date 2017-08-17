package com.geely.app.geelyapprove.common.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;

import com.geely.app.geelyapprove.activities.login.entity.LoginEntity;


/**zhy
 * Created by liuweiyan_PC on 2016/9/13.
 */
public class GeelyApp extends Application {
    //handler
    public static Handler mainHandler;

    public static Context context;

    private static String Language;

    private static LoginEntity loginEntity;

    @Override
    public void onCreate() {
        super.onCreate();
        mainHandler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
//        checkFirstIn();
    }

    public static void setLanguage(String language){
        Language = language;
    }

    public static String getLanguage(){
        return Language;
    }

    public static LoginEntity getLoginEntity() {
        return loginEntity;
    }

    public static void setLoginEntity(LoginEntity loginEntity) {
        GeelyApp.loginEntity = loginEntity;
    }

//    private void checkFirstIn() {
//        CommonValues.firstIn = getSharedPreferences("app", MODE_PRIVATE).getBoolean("firstIn", true);
//    }



}