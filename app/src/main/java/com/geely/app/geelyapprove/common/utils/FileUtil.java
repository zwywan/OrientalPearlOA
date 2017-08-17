package com.geely.app.geelyapprove.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Oliver on 2016/9/22.
 */

public class FileUtil {
    public static boolean writeSerializable(Object object, String path) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream stream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            stream = new ObjectOutputStream(fileOutputStream);
            stream.writeObject(object);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (stream != null)
                    stream.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public static Object readSerializable(String path) {
        FileInputStream inputStream = null;
        ObjectInputStream stream = null;
        Object object = null;
        try {
            inputStream = new FileInputStream(path);
            stream = new ObjectInputStream(inputStream);
            object = stream.readObject();
            stream.close();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return object;
    }

    public static void writePreference(Context context, String fieldName, String key, Object value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(fieldName, MODE_PRIVATE).edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
            editor.apply();
        }

    }

}
