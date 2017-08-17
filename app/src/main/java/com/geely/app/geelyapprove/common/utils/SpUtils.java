package com.geely.app.geelyapprove.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;


/**zhy  sp卡存储工具
 * Created by zhy on 2016/9/22.
 */
public class SpUtils {
    //保存一个布尔类型的值到sp卡，文件名"mobsafe_sp"
    public static void saveisBoolean(Context context,String key,Boolean value){

        SharedPreferences preferences = context.getSharedPreferences("mobsafe_sp", Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        edit.commit();


    }
    //得到一个布尔类型的值
    public static boolean getisBoolean_false(Context context,String key,boolean b)
    {
        SharedPreferences preferences = context.getSharedPreferences("mobsafe_sp", Context.MODE_PRIVATE);
        return  preferences.getBoolean(key, b);
    }

    //保存一个String类型的值
    public static void saveString(Context context,String key,String value)
    {
        SharedPreferences preferences = context.getSharedPreferences("mobsafe_sp", Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.commit();
    }
    //得到一个String类型的值
    public static String getString(Context context,String key)
    {
        SharedPreferences preferences = context.getSharedPreferences("mobsafe_sp", Context.MODE_PRIVATE);
        return  preferences.getString(key, "");
    }


    public static void clearString(Context context,String key)
    {
        SharedPreferences preferences = context.getSharedPreferences("mobsafe_sp", Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.remove("name");
        edit.remove("pwd");
        edit.clear();
        boolean commit = edit.commit();
        if (commit) {
            Toast.makeText(context, "删除文件成功", Toast.LENGTH_SHORT).show();
        }else{
            //文件不存在
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
        }

    }

}
