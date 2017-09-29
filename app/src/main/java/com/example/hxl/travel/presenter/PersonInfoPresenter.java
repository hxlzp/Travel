package com.example.hxl.travel.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;

import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.presenter.contract.PersonInfoContract;
import com.example.hxl.travel.ui.activitys.GroupBuildActivity;
import com.example.hxl.travel.ui.activitys.PersonInfoActivity;
import com.example.hxl.travel.utils.ImageUtil;
import com.google.common.base.Preconditions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by hxl on 2017/1/5 at haiChou.
 */
public class PersonInfoPresenter extends RxPresenter implements PersonInfoContract.Presenter{
    PersonInfoContract.View mView;
    private final Context personInfoContext;
    private final RoundedImageView roundedImageView;

    public PersonInfoPresenter(PersonInfoContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        personInfoContext = mView.getPersonInfoContext();
        roundedImageView = mView.getRoundedImageView();
        getData();
    }

    @Override
    public void getData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1&&
                resultCode==((PersonInfoActivity)personInfoContext).RESULT_OK&&null!=data){//拍照
            //获得外部存储器的状态
            String externalStorageState = Environment.getExternalStorageState();
            if (!externalStorageState.equals(Environment.MEDIA_MOUNTED)){
                return;
            }
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            String name = DateFormat.format("yyyy_MM_dd_hh_mm_ss",calendar) + ".jpg";
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转化为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            FileOutputStream fout = null;
            String filename = null;
            try {
                filename = ImageUtil.showFileUrl(personInfoContext) + "/" + name;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fout = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fout);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            roundedImageView.setImageBitmap(bitmap);
            staffFileUpload(new File(filename));

        } else if (requestCode == 2&&
                resultCode==((PersonInfoActivity)personInfoContext).RESULT_OK&&null!=data){//本地
            Uri uri = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = personInfoContext
                    .getContentResolver().query(uri, filePathColumns, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
            String picturePath  = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            //获取图片并显示
            roundedImageView.setImageBitmap(bitmap);
            saveBitmapFile(bitmap,picturePath);
            String name = "picture.jpg";
            try {
                String filename = ImageUtil.showFileUrl(personInfoContext) + name;
                File file = new File(filename);
                staffFileUpload(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void saveBitmapFile(Bitmap bitmap, String path) {
        File file = new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void staffFileUpload(File file) {

    }
}
