package com.project.hyperfood.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontUtil {

    public static String LAMMOON = "lamoon_bold.ttf";

    public static Typeface getFont(AssetManager assetManager, String fontName){
        return Typeface.createFromAsset(assetManager,fontName);
    }

}
