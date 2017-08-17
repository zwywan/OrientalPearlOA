package com.geely.app.geelyapprove.common.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jimda on 2015/11/27.
 */
public class StringUtils {
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}d{1}-?d{8}$)|"
                + "(^0[3-9] {1}d{2}-?d{7,8}$)|"
                + "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
                + "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static String requestResult(String urlStr, Map<String, Object> params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            if (!urlStr.endsWith("?"))
                urlStr += "?";
            URL resUrl = new URL(urlStr);
            URLConnection conn = resUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.addRequestProperty("connection", "Keep_Alive");
            conn.setRequestProperty("method", "get");
            conn.addRequestProperty("user-agent", "Mozilla/4.0(compatinle;MSIE 6.0;Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(9000);
            StringBuilder builder = new StringBuilder();
            if (!params.isEmpty()) {
                int i = 0;
                for (String key : params.keySet()) {
                    builder.append(key);
                    builder.append("=");
                    builder.append(params.get(key));
                    if (i < params.size() - 1) {
                        builder.append("&");
                    }
                    i++;
                }
            } else {
                builder.append("");
            }
            Log.e("HttpParams", builder.toString());
            out = new PrintWriter(conn.getOutputStream());
            out.print(builder);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {

                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("result", result);
            return result;
        }

    }

    public static String getUrlWithGet(String urlStr, Map<String, Object> params) {
        if (!urlStr.endsWith("?"))
            urlStr += "?";
        StringBuilder builder = new StringBuilder(urlStr);
        if (!params.isEmpty()) {
            int i = 0;
            for (String key : params.keySet()) {
                builder.append(key);
                builder.append("=");
                builder.append(params.get(key));
                if (i < params.size() - 1) {
                    builder.append("&");
                }
                i++;
            }
        } else {
            builder.append("");
        }
        return builder.toString();
    }

}
