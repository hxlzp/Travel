package com.example.hxl.travel.utils;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;
/**
 * Created by hxl on 2017/1/6 at haiChou.
 * 清除缓存
 */
public class DataCleanManager {
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public static void clearAllCache(Context context) {
        //清除内存
        deleteDir(context.getCacheDir());
        //外部存储状态
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //清除sd卡
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * isDirectory()是检查一个对象是否是文件夹
     * list()方法 ： 获取该目录下的所有文件  会返回一个字符数组，
     * 将制定路径下的文件或文件夹名字存储到String数组中
     * list()方法是返回某个目录下的所有文件和目录的文件名，返回的是String数组
     * 递归删除
     * 检查目录下的文件和子目录，如果是文件，马上删除；如果是子目录，依次进行删除。
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * listFiles是获取该目录下所有文件和目录的绝对路径
     * listFiles()方法是返回某个目录下所有文件和目录的绝对路径，返回的是File数组
     * 获取文件
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，
     * 一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，
     * 一般存放临时缓存数据
     */

    public static long getFolderSize(File file) throws Exception {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
