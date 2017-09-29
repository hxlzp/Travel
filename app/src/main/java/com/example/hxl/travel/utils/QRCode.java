package com.example.hxl.travel.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hxl on 2017/6/12 at haiChou.
 * 二维码
 */
public class QRCode {
    /*屏幕的宽度*/
    private static int mScreenWidth = 0;
    /*生成二维码*/
    public static Bitmap Create2QR2(Context context ,String uri,int logoId){
        try{
            /**
             * 获得屏幕信息
             * 只有Activity可以使用WindowManager,否则使用Context.getResources().getDisplayMetrics()
             * Context.getResources().getDisplayMetrics()依赖于手机系统，获取到的是屏幕信息
             * WindowManage.getDefaultDisplay().getMetrics(dm)获取到的是实际屏幕信息
             */
            DisplayMetrics displayMetrics = new DisplayMetrics() ;
            WindowManager windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            mScreenWidth = displayMetrics.widthPixels;
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), logoId);
            Bitmap bitmap = createQRImage(uri, mScreenWidth, bm);
            if (bitmap!=null){
                return bitmap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    //带图片的二维码
    public static Bitmap createQRImage(String url, int widthPix, Bitmap logoBm) {
        try {
            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            //指定字符编码使用适用的地方
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别 指定使用什么程度的误差修正
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            // hints.put(EncodeHintType.MARGIN, 2); //default is 4

            // 图像数据转换，使用了矩阵转换 QRCode二维码
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, widthPix, widthPix, hints);
            int[] pixels = new int[widthPix * widthPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < widthPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, widthPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, widthPix);

            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }

            //必须使用compress方法将bitmap保存到文件中再进行读取。
            // 直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            compressScale(bitmap);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }
    /*质量压缩*/
    private static Bitmap compressImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        /* 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到byteArrayOutputStream中*/
        /*PNG是一种无损lossess压缩方式，compress方法中quality字段的设置是无效的，
        JPEG是不透明opaque压缩。*/
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        int options = 90;
        /* 循环判断如果压缩后图片是否大于100kb,大于继续压缩  */
        while (byteArrayOutputStream.toByteArray().length/1024>100){
            /*重置byteArrayOutputStream即清空byteArrayOutputStream */
            byteArrayOutputStream.reset();
            /*这里压缩options%，把压缩后的数据存放到byteArrayOutputStream中*/
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,byteArrayOutputStream);
            //每次都减少10
            options -= 10;
        }
        /*把压缩后的数据byteArrayOutputStream存放到ByteArrayInputStream中*/
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Bitmap decodeStream = BitmapFactory.decodeStream(byteArrayInputStream);
        return decodeStream;
    }
    /*按比例大小压缩*/
    public static Bitmap compressScale(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        /*100表示不压缩，存到byteArrayOutputStream*/
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        /*判断如果图片大于1M,进行压缩避免再次生成图片（BitmapFactory.decodeStream）时溢出  */
        while (byteArrayOutputStream.toByteArray().length/1024>1024){
            /*清空byteArrayOutputStream*/
            byteArrayOutputStream.reset();
            /* 这里压缩50%，把压缩后的数据存放到byteArrayOutputStream中*/
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        }
        ByteArrayInputStream byteArrayInputStream =
                new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        //配置Bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        /*开始读入图片，此时把options.inJustDecodeBounds 设置true 只解码边界  */
        options.inJustDecodeBounds = true;
        Bitmap decodeStream = BitmapFactory.decodeStream(byteArrayInputStream,null,options);
        options.inJustDecodeBounds = false;
        //获得宽高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //现在主流手机比较多是1920*1080分辨率，所以高和宽我们设置为
        float hh = 512f;
        float ww = 512f;
        /*缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  */
        int be = 1;// be=1表示不缩放
        if (outWidth > outHeight && outWidth > ww){// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (outWidth/ww);
        }else if(outHeight > outWidth&&outHeight>hh){//如果高度高的话根据高度固定大小缩放
            be = (int) (outHeight/hh);
        }
        if (be <= 0)
            be = 1;
        /*设置缩放比例*/
        options.inSampleSize = be;
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        /*重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  */
        byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        decodeStream = BitmapFactory.decodeStream(byteArrayInputStream);
        return  compressImage(decodeStream);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/4
        float scaleFactor = srcWidth * 1.0f / 4 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }
}
