package com.example.hackinghealthclinification.app.CustomViewClasses;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.hackinghealthclinification.app.Tools.TypeFaceGenerator;

/**
 * @author Mark Iantorno
 */
public class CustomTextView extends TextView {

    /**
     * Note that when generating the class from code, you will need
     * to call setCustomFont() manually.
     */
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(TypeFaceGenerator.getCustomTypeface(context, attrs));
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(TypeFaceGenerator.getCustomTypeface(context, attrs));
    }

}