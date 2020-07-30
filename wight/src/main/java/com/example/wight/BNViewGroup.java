package com.example.wight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BNViewGroup extends ViewGroup {

    //中间控件的宽度
    private int widthMeddle;
    private float widthMeddleProportion = 0.3f;
    //log开关
    private boolean logs = true;

    //存储子控件排列位置
    private List<Rect> list_rect = null;
    //中间控件位置下标
    private int indexMeddle = 2;
    //左移次数
    private int orderMoveLeft = 0;

    private OnViewClickListener listener;

    public void setListener(OnViewClickListener listener) {
        this.listener = listener;
    }

    public BNViewGroup(Context context) {
        super(context);
    }

    public BNViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        list_rect = new ArrayList<>();
    }

    public BNViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        widthMeddle = (int) (getMeasuredWidth()*widthMeddleProportion);
        if (mode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSpec,widthMeddle);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        log("onMeasure");
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        log("onLayout");

        //遍历所有子控件
        for (int i = 0; i < getChildCount(); i++) {
            int width = getWidth();
            int layoutLeft = 0;
            int layoutTop = 0;
            int layoutRight = 0;
            int layoutBottom = 0;
            int widthSmall = (width - widthMeddle) / 4;

            final View childView = getChildAt(i);
            if (i < list_rect.size()) {
                Rect rect = list_rect.get(i);
                layoutLeft = rect.left;
                layoutTop = rect.top;
                layoutRight = rect.right;
                layoutBottom = rect.bottom;
            } else {
                layoutLeft = i * widthSmall + (i >= 3 ? (widthMeddle - widthSmall) : 0);
                layoutRight = layoutLeft + (i == 2 ? widthMeddle : widthSmall);
                layoutTop = i == 2 ? 0 : (widthMeddle - widthSmall) / 2;
                layoutBottom = i == 2 ? widthMeddle : widthSmall + layoutTop;
                list_rect.add(i, new Rect(layoutLeft, layoutTop, layoutRight, layoutBottom));

                final int finalI = i;
                childView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //showSwap(finalI);
                        showMove(finalI);
                        showView(view);
                        if (listener != null){
                            listener.onViewClick(view,finalI);
                        }
                    }
                });
            }
            layoutLog(layoutLeft, layoutTop, layoutRight, layoutBottom, widthSmall);
            childView.layout(layoutLeft, layoutTop, layoutRight, layoutBottom);

        }
    }

    private void layoutLog(int layoutLeft, int layoutTop, int layoutRight, int layoutBottom, int widthSmall) {
        if (logs) {
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        log("onDraw");
        showView(getChildAt(2));
    }

    private void log(String name) {
        if (logs) {
//            ZLog.i("————————————————");
//            //除了measure都能用
//            int width = getWidth();
//            int height = getHeight();
//            //onMeasure专用
//            int measuredWidth = getMeasuredWidth();
//            int measuredHeight = getMeasuredHeight();
//            ZLog.i("get宽高" + width + "--" + height);
//            ZLog.i("getMeasured宽高" + measuredWidth + "--" + measuredHeight);
//            int childCount = getChildCount();
//            ZLog.i("子控件数量：" + childCount);
//            measureLog();
        }
    }

    private void measureLog() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getWidth();
            int height = child.getHeight();
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
        }
    }

    //交换式显示
    private void showSwap(int position) {
        Collections.swap(list_rect, position, indexMeddle);
        indexMeddle = position;
        requestLayout();
    }

    //平移式显示
    private void showMove(int position) {
        position = (orderMoveLeft - position + 4) % 5;
//        int space = Math.abs(2 - position);
//        ZLog.i("Postion:"+position+"，Space："+space);
//        if (position == 3) {
//            space += 3;
//        }else if (position ==4){
//            space += 1;
//        }

        //优化写法
        int space = (4-position+3)%5;

        for (int i = 0; i < space; i++) {
            listMoveRight();
        }
        requestLayout();
    }

    //坐标右移，等于控件左移
    private void listMoveRight() {
        Rect rect = list_rect.get(list_rect.size() - 1);
        list_rect.remove(list_rect.size() - 1);
        list_rect.add(0, rect);
        orderMoveLeft++;
        requestLayout();
    }

    //变色等
    public void showView(View view){
        for (int i = 0; i < getChildCount(); i++) {
            ImageView child = (ImageView) getChildAt(i);
            child.setColorFilter(Color.WHITE);
        }
        ImageView imageView = (ImageView) view;
        imageView.setColorFilter(0xff46A1A1);
    }
}
