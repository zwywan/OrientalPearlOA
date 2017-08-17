package com.geely.app.geelyapprove.datamanage;

/**zhy
 * Created on 2016/7/23.
 */

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 统一缓存文件管理类
 */
public class CacheManger
{
    
    private static String cacheDir;
    
    private CacheManger()
    {
        if (cacheDir == null)
        {
            // /sdcard/包名/cache/
            // File.separator跨平台/,window / linux \
            cacheDir = Environment.getExternalStorageDirectory().getPath() + File.separator
                + File.separator + "cache";
            File fileDir = new File(cacheDir);
            if (!fileDir.exists())
            {
                // 如果不存在创建目录
                fileDir.mkdirs();

            }
        }
    }
    
    private static CacheManger sCacheManger = new CacheManger();
    
    public static CacheManger getInstance()
    {
        return sCacheManger;
    }
    
    // 保存缓存文件
    public void saveData(String url, String content)
    {
        // 1.保存的位置
        // 2. md5加密文件名,参数是url
        // sdcard/包名/cache/
        try
        {
            String fileName = getMd5Name(url);
            //System.out.println("当前的文件路径:"+cacheDir);
            File saveFile = new File(cacheDir, fileName);
            
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            // 2.保存的内容写入到文件中
            fileOutputStream.write(content.getBytes());
            
            fileOutputStream.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 生成一个md5文件名
     * 
     * @param url
     * @return
     */
    private String getMd5Name(String url)
    {
        StringBuffer stringBuffer = new StringBuffer();
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            byte[] digest = messageDigest.digest();
            // 把byte转成16进制
            for (int i = 0; i < digest.length; i++)
            {
                //System.out.println("md5" + Integer.toHexString(digest[i] & 0xff));
                stringBuffer.append(Integer.toHexString(digest[i] & 0xff));
            }
            
            return stringBuffer.toString();
            
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public String getData(String url)
    {
        //记录内容
        StringBuffer stringBuffer = new StringBuffer();
        try
        {
            File readFile = new File(cacheDir, getMd5Name(url));
            
            FileInputStream fileInputStream = new FileInputStream(readFile);

            int len = -1;

            byte[] buffer = new byte[1024];

            while ((len = fileInputStream.read(buffer)) != -1) {
                stringBuffer.append(new String(buffer, 0, len));
            }

            fileInputStream.close();

            return stringBuffer.toString();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        
    }
    
}
