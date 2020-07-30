package com.example.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TimeCustom extends View {
    private Paint xian;
    private Paint yuan;
    private int color;
    private float size;
    private int radius;

    public TimeCustom(Context context) {
        super(context);
    }

    public TimeCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        doCustomPro(context,attrs);
        initPaint();
    }

    private void doCustomPro(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeCustom);
         color = typedArray.getColor(R.styleable.TimeCustom_color, Color.WHITE);
         size = typedArray.getDimension(R.styleable.TimeCustom_size, 18);
         radius = typedArray.getInteger(R.styleable.TimeCustom_radius, 10);
    }

    private void initPaint() {
        xian = new Paint();
        yuan = new Paint();

        xian.setColor(color);
        xian.setAntiAlias(true);
        xian.setStyle(Paint.Style.STROKE);
        xian.setStrokeWidth(size);
        yuan.setColor(color);
        yuan.setAntiAlias(true);
        yuan.setStyle(Paint.Style.FILL);
        yuan.setStrokeWidth(size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.drawLine(width/2,0,width/2,height/2-radius,xian);
        canvas.drawArc(width/2-radius,height/2-radius,width/2+radius,height/2+radius,
                0,360,false,yuan);
        canvas.drawLine(width/2,height/2+radius,width/2,height,xian);
    }
}
