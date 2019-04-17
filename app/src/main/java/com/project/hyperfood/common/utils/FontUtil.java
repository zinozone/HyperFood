package com.project.hyperfood.common.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class FontUtil {

    public static String LAMMOON_BOLD = "lamoon_bold.ttf";
    public static String LAMMOON_REGULAR = "lamoon_regular.ttf";

    public static Typeface getFont(AssetManager assetManager, String fontName){
        return Typeface.createFromAsset(assetManager,fontName);
    }

}
