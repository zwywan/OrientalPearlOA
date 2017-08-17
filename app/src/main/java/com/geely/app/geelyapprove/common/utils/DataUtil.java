package com.geely.app.geelyapprove.common.utils;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.geely.app.geelyapprove.common.entity.DataListEntity;
import com.geely.app.geelyapprove.common.fragment.CommonFragment;
import com.geely.app.geelyapprove.common.view.HandInputGroup;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Oliver on 2016/10/21.
 */

public class DataUtil {

    private static String url;

    public static Object listGetValueOf(List list, int index) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ex) {
        }
        return null;
    }

    public static boolean listHasValueOf(List list, int index) {
        try {
            return list.get(index) != null;
        } catch (IndexOutOfBoundsException ex) {

        }
        return false;
    }

    public static String parseDateNormalString(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String parseDateByFormat(String dateStr, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            return "";
        }
        if (date != null) {
            return new SimpleDateFormat(format).format(date);
        } else {
            return "";
        }

    }

    public static String getDicodeByDescr(List<DataListEntity> data, String descr) {
        if (data == null || descr.isEmpty() || descr == null) {
            return "";
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDicDesc().equals(descr)) {
                return data.get(i).getDicCode();
            }
        }
        return "";
    }
    public static String getDicIdByDescr(List<DataListEntity> data, String descr) {
        if (data == null) {
            return "0";
        }
        String dicId = "";
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDicDesc().equals(descr)) {
                dicId = data.get(i).getDicId();
            }
        }
        return dicId;

    }

    public static String[] getDescr(List<DataListEntity> beans) {
        if (beans == null || beans.size() == 0) {
            return new String[]{};
        }
        String[] args = new String[beans.size()];
        for (int i = 0; i < beans.size(); i++) {
            args[i] = beans.get(i).getDicDesc();
        }
        return args;
    }


    public static String[] getDescrWithDicCode(List<DataListEntity> beans) {
        if (beans == null || beans.size() == 0) {
            return new String[]{};
        }
        String[] args = new String[beans.size()];
        for (int i = 0; i < beans.size(); i++) {
            args[i] = beans.get(i).getDicCode() + " " + beans.get(i).getDicDesc();
        }
        return args;
    }


    /**
     * 转换字符串到正整型，转换失败或者为负数则返回-1
     *
     * @param value 待转换的字符串
     * @return >=0
     */
    public static int parseString2UnsignedInt(String value) {
        int parsed;
        try {
            parsed = Integer.valueOf(value);
            if (parsed >= 0) {
                return parsed;
            } else {
                return 0;
            }
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * 输入 是 否 <空> ，仅在为“是”时返回true
     *
     * @param value 传入的数值
     * @return
     */
    public static boolean parseString2Boolean(String value) {
        if (value.equals("是")) {
            return true;
        } else {
            return false;
        }
    }

    public static double parseString2DoubleFormat(String value) {
        try {
            String parsedString = String.format("%.2f", value);
            return Double.valueOf(parsedString);
        } catch (IllegalArgumentException ex) {
            return -1;
        }
    }

    /**
     * @param key
     * @param groups
     * @return 返回null 标识没有找到
     */
    public static String findRealValByKey(String key, List<CommonFragment.Group> groups) {
        if (groups == null || groups.size() == 0) {
            return null;
        } else {
            for (CommonFragment.Group group : groups) {
                List<HandInputGroup.Holder> holders = group.getHolders();
                if (holders != null && holders.size() > 0) {
                    for (HandInputGroup.Holder holder : holders) {
                        if (key.equals(holder.getKey())) {
                            return holder.getRealValue();
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String replaceBracketsOfString(String string) {
        if (string == null) {
            string = "";
        }
        int firstBracket = string.indexOf("(");
        if (firstBracket == -1) {
            firstBracket = string.indexOf("（");
        }
        if (firstBracket != -1) {
            return string.substring(0, firstBracket);
        }
        return "";
    }

    public static String getFileUrl(String fileUrl,String workFlowType) {
        Log.d("DataUtil", url);
        return url;
    }

    public static String valueNullKeepString(Object object) {
        if (object != null && object instanceof String) {
            return (String) object;
        }
        return "";
    }

    public static String emptyStringKeepZeroInteger(String value) {
        if (value != null && TextUtils.isEmpty(value)) {
            return "0";
        }
        return value;
    }
    public static void openFile(Context context, File file) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            String type = getMIMEType(file);
            intent.setDataAndType(Uri.fromFile(file), type);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "附件不能打开，请下载相关软件！", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openFile(Context context, Uri uri,String extension) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, getTypeFromExtension(extension));
            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "附件不能打开，请下载相关软件！", Toast.LENGTH_SHORT).show();
        }
    }


    public static String getTypeFromExtension(String extension){
        if(!extension.startsWith(".")){
            extension+=".";
        }
        return mapMIME.get(extension);
    }

    public static String getExtensionFromIME(String attachType){
        for (String key : mapMIME.keySet()) {
            String keyValue = mapMIME.get(key);
            if (keyValue.equals(attachType)) {
                return key;
            }
        }
        return null;
    }

    private static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        int dotIndex = fName.lastIndexOf(".");
        Log.e("sasd",file.getAbsolutePath());
        if (dotIndex < 0) {
            return type;
        }
        String end = fName.substring(dotIndex, fName.length()).toLowerCase(Locale.getDefault());
        if (end == "")
            return type;
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public static String[][] MIME_MapTable = {
            { ".bmp", "image/bmp" },
            { ".doc", "application/msword" },
            { ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
            { ".xls", "application/vnd.ms-excel" },
            { ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            { ".gif", "image/gif" },
            { ".jpeg", "image/jpeg" },
            { ".jpg", "image/jpeg" },
            { ".png", "image/png" },
            { ".txt", "text/plain" },
            { ".wps", "application/vnd.ms-works" },
            { ".xml", "text/plain" },
    };

    public static File base64ToFile(String base64, String lastStr) {
        File file = null;
        String fileName = "Geely."+lastStr;
        FileOutputStream out = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), fileName);
            if (!file.exists())
                file.createNewFile();
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(file);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public static File base64ToFileWithName(String base64, String lastStr,String name) {
        File file = null;
        String fileName =name+lastStr;
        FileOutputStream out = null;
        try {
            file = new File(Environment.getExternalStorageDirectory(), fileName);
            if (!file.exists())
                file.createNewFile();
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(file);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    private static Map<String,String> mapMIME=new HashMap<>();

    static {
        mapMIME.put( ".bmp", "image/bmp" );
        mapMIME.put( ".doc", "application/msword" );
        mapMIME.put( ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" );
        mapMIME.put( ".xls", "application/vnd.ms-excel" );
        mapMIME.put( ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
        mapMIME.put( ".gif", "image/gif" );
        mapMIME.put( ".jpeg", "image/jpeg" );
        mapMIME.put( ".jpg", "image/jpeg" );
        mapMIME.put( ".png", "image/png" );
        mapMIME.put( ".txt", "text/plain" );
        mapMIME.put( ".wps", "application/vnd.ms-works" );
        mapMIME.put( ".xml", "text/plain" );

    }

    public static Map<String,String> getMapMIME(){
        return mapMIME;
    };



}
