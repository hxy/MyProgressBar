package hy.myprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by huangyue on 2017/5/9.
 */

public class MyProgressBar extends View {
    /*已到达进度条的默认颜色*/
    private static final int DEFAULT_ACHIEVED_COLOR = 0xffDA70D6;
    /*未到达进度条的默认颜色*/
    private static final int DEFAULT_NOACHIEVED_COLOR = 0xffD6D6D6;
    /*默认字体颜色*/
    private static final int DEFAULT_TEXT_COLOR = 0xff6959CD;
    /*已到达进度条矩形*/
    private RectF achieved;
    /*未到达进度条矩形*/
    private RectF noAchieved;
    /*进度条画笔*/
    private Paint paint;
    /*字体画笔*/
    private TextPaint textPaint;
    /*已到达的百分比*/
    private float currentPercent = 0.1f;
    /*已到达进度条的高度*/
    private float achievedHeight = 0;
    /*未到达进度条的高度*/
    private float noAchievedHeight = 0;
    private int achievedBarColor = DEFAULT_ACHIEVED_COLOR;
    private int noAchievedBarColor = DEFAULT_NOACHIEVED_COLOR;
    //    private int textColor = DEFAULT_TEXT_COLOR;
    /*记录文字的起画位置*/
    private float textStartX;
    /*view的宽高*/
    private int width;
    private int height;

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        /*解析attrs*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        achievedHeight = typedArray.getDimension(R.styleable.MyProgressBar_achievedHeight, 0);
        noAchievedHeight = typedArray.getDimension(R.styleable.MyProgressBar_noachievedHeight, 0);
        achievedBarColor = typedArray.getColor(R.styleable.MyProgressBar_achievedColor, DEFAULT_ACHIEVED_COLOR);
        noAchievedBarColor = typedArray.getColor(R.styleable.MyProgressBar_noachievedColor, DEFAULT_NOACHIEVED_COLOR);
        currentPercent = typedArray.getDimension(R.styleable.MyProgressBar_percent, 0.1f);
        int textColor = typedArray.getColor(R.styleable.MyProgressBar_textColor, DEFAULT_TEXT_COLOR);
        textPaint.setColor(textColor);
        float textSize = typedArray.getDimension(R.styleable.MyProgressBar_textSize, 0);
        textPaint.setTextSize(textSize);
    }

    private void init() {
        achieved = new RectF();
        noAchieved = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*----------画到达的进度条----------*/
        /*到达进度条的长度*/
        float achievedWidth = width / 100f * currentPercent;
        paint.setColor(achievedBarColor);
        achieved.set(0, (height - achievedHeight) / 2f, achievedWidth, (height - achievedHeight) / 2f + achievedHeight);
        canvas.drawRect(achieved, paint);

        if (currentPercent != 100) {
            /*----------画百分比文字----------*/
            float textWidth = textPaint.measureText((int)currentPercent + "%");
            float textOffsetY = (textPaint.descent() + textPaint.ascent()) / 2;
            if (achievedWidth + textWidth < width) {
                textStartX = achievedWidth;
            }
            canvas.drawText((int)currentPercent + "%", textStartX, height / 2 - textOffsetY, textPaint);
            /*----------画未到达的进度条----------*/
            paint.setColor(noAchievedBarColor);
            noAchieved.set(achievedWidth + textWidth, (height - noAchievedHeight) / 2f, width, (height - noAchievedHeight) / 2f + noAchievedHeight);
            canvas.drawRect(noAchieved, paint);
        }
    }


    public void setAchievedBarHeight(float height) {
        achievedHeight = height;
    }

    public void setNoAchievedBarHeight(float height) {
        noAchievedHeight = height;
    }

    public void setAchievedBarColor(int color) {
        achievedBarColor = color;
    }

    public void setNoAchievedBarColor(int color) {
        noAchievedBarColor = color;
    }

    public void setPercent(int percent) {
        currentPercent = percent;
        invalidate();
    }


    public void setTextSize(int textSize) {
        textPaint.setTextSize(textSize);
    }

    public void setTextColor(int color) {
//        textColor = color;
        textPaint.setColor(color);
    }
}
