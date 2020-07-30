package com.example.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class PersonCustomView extends ViewGroup {
    private int defaultWidth = 70;
    private int defaultHeight = 150;

    public PersonCustomView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childCount = getChildCount();
        for (int v = 0;i<childCount;i++){
            View childAt = getChildAt(v);
            int height = childAt.getHeight();
            childAt.layout(10,10,10,10);
        }
    }

    public PersonCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode==MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY){
            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        }else if (widthMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,heightMeasureSpec);
        }else if(heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMode,heightSize);
        }
    }

}
