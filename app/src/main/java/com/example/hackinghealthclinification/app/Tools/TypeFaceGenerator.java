package com.example.hackinghealthclinification.app.Tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.example.hackinghealthclinification.app.R;

/**
 * @author Mark Iantorno
 */
public class TypeFaceGenerator {

    private static final String DEFAULT_FONT = "montserrat_regular.ttf";

    public static Typeface getCustomTypeface(Context context, AttributeSet attrs) {

        Typeface tf = null;
        String font = DEFAULT_FONT;

        if (attrs != null) {
            // Look up any layout-defined attributes
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextViewFont);
            font = a.getString(R.styleable.CustomTextViewFont_customFont);
        }

        try {
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + font);
        } catch (Exception e) {
            Log.e("Could not get typeface: ", e.getLocalizedMessage());
        }

        return tf;

    }
}
