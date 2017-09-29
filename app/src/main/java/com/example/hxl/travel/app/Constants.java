package com.example.hxl.travel.app;

import java.io.File;

/**
 * Created by hxl on 2017/1/21 at haiChou.
 */
public class Constants {
    //================= PATH ====================
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() +
            File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";
}
