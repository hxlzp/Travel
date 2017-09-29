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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;

import com.example.hxl.travel.R;
import com.example.hxl.travel.base.RxPresenter;
import com.example.hxl.travel.model.bean.GroupBuild;
import com.example.hxl.travel.model.bean.VisitorGroups;
import com.example.hxl.travel.model.net.RetrofitHelper;
import com.example.hxl.travel.presenter.contract.GroupBuildContract;
import com.example.hxl.travel.ui.activitys.GroupBuildActivity;
import com.example.hxl.travel.utils.ImageUtil;
import com.example.hxl.travel.utils.SharedPreferencesUtil;
import com.google.common.base.Preconditions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hxl on 2017/6/8 at haiChou.
 * 创建群组
 */
public class GroupBuildPresenter extends RxPresenter implements GroupBuildContract.Presenter{
    GroupBuildContract.View mView;
    private  Context groupBuildContext;
    private  String user_id;
    private  String sessionId;
    private final RoundedImageView roundedImageView;

    public GroupBuildPresenter(GroupBuildContract.View view){
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        groupBuildContext = mView.getGroupBuildContext();
        user_id = SharedPreferencesUtil.getUserId(groupBuildContext);
        sessionId = SharedPreferencesUtil.getSessionId(groupBuildContext);
        roundedImageView = mView.getRoundedImageView();
        getData();
    }
    @Override
    public void getData() {
        mView.showData();
    }

    @Override
    public void submit(final Button button) {
        //获取用户录入的信息
        final String groupNick = mView.getGroupNick();
        if (TextUtils.isEmpty(groupNick)){
            mView.showMassage(groupBuildContext.getResources().getString(R.string.groupNicknameHint));
            return;
        }
        Subscription subscription = RetrofitHelper.groupBuildObservable(user_id,
                groupNick, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupBuild>() {
                    @Override
                    public void onCompleted() {
                        Log.e("complete", "onCompleted: "+"completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "onError: "+e);
                    }

                    @Override
                    public void onNext(GroupBuild groupBuild) {
                        String type = groupBuild.getType();
                        if (type.equals("1")){
                            button.setClickable(false);
                            button.setFocusable(false);
                            button.setEnabled(false);
                            mView.backPage();
                            //EventBus.getDefault().post(new Group(R.mipmap.photo,groupNick));
                            EventBus.getDefault().post(new VisitorGroups(groupNick));
                            mView.showMassage(groupBuildContext.getResources().getString(R.string.groupBuildSuccess));
                        }else if (type.equals("2")){
                            mView.showMassage(groupBuildContext.getResources().getString(R.string.groupBuildFail));
                        }else if (type.equals("3")){
                            mView.showMassage(groupBuildContext.getResources().getString(R.string.groupBuildExist));
                        }
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1&&
                resultCode==((GroupBuildActivity)groupBuildContext).RESULT_OK&&null!=data){//拍照
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
                filename = ImageUtil.showFileUrl(groupBuildContext) + "/" + name;
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
                resultCode==((GroupBuildActivity)groupBuildContext).RESULT_OK&&null!=data){//本地
            Uri uri = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = groupBuildContext.getContentResolver().query(uri, filePathColumns,
                    null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
            String picturePath  = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            saveBitmapFile(bitmap,picturePath);
            //获取图片并显示
            roundedImageView.setImageBitmap(bitmap);
            String name = "picture.jpg";
            try {
                String filename = ImageUtil.showFileUrl(groupBuildContext) + name;
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
