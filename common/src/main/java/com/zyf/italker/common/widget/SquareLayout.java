package com.zyf.italker.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquareLayout extends FrameLayout {
    public SquareLayout(@NonNull Context context) {
        super(context);
    }

    public SquareLayout(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(@NonNull Context context,@NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //给父类传递的测量值都为宽度，基于宽度的正方形
        //那么就是基于宽度的正方形控件了
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);


    }
}
