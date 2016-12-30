package com.pei.cleancachememory;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by 裴亮 on 16/12/29.
 */
public class CleanMyCache {
    // 查询缓存大小
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals
                (Environment.MEDIA_MOUNTED)){
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }
    // 清空缓存
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals
                (Environment.MEDIA_MOUNTED)){
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File cacheDir) {
        if (cacheDir != null && cacheDir.isDirectory() ){
            String[]children = cacheDir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(cacheDir,children[i]));
                if (!success){
                    return false;
                }
            }
        }
        return cacheDir.delete();
    }

    // 获取文件
    // Context.getExternalFilesDir() -> SDCard/Android/data/
    // 你的应用包名/files/目录 一般存放一些长时间保存的数据
    // Context.getExternalCacheDir() -> SDCard/Android/data/
    // 你的应用包名/cache/目录 一般存放临时缓存数据
    private static String getFormatSize(double cacheSize) {
        double kiloByte = cacheSize/1024;
        if (kiloByte < 1){
            return "0K";
        }
        double megaByte = kiloByte/1024;
        if (megaByte < 1){
            BigDecimal result1 = new BigDecimal
                    (Double.toString(kiloByte));
            return result1.setScale(2,BigDecimal.ROUND_HALF_UP)
                    .toPlainString()+"KB";
        }
        double gigaByte = megaByte/1024;
        if (gigaByte < 1){
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"MB";
        }
        double teraByte = gigaByte /1024;
        if (teraByte < 1){
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"GB";
        }
        BigDecimal result4 = new BigDecimal(teraByte);
        return result4.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"TB";

    }

    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }
}
