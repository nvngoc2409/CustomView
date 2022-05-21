package com.example.learncustomview.custom;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class AVLoadingIndicatorView extends View {

    //Sizes (with defaults in DP)
    public static final int DEFAULT_SIZE = 45;
    //attrs
    int mIndicatorColor;
    Paint mPaint;
    float[] lengths = new float[]{
            1f,
            1f,
            1,
            1,
            1,
            1,
            1,
            1,
    };
    Animator mAnimator;


    public AVLoadingIndicatorView(Context context) {
        super(context);
        init(null, 0);
    }


    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    private void init(AttributeSet attrs, int defStyle) {
        mIndicatorColor = 0x88000000;
        mPaint = new Paint();
        mPaint.setColor(mIndicatorColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(dp2px(DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(defaultSize, specSize);
                break;
            default:
                result = defaultSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        float radius = width / 2f;
        mPaint.setStrokeWidth(radius / 8);
        for (int i = 0; i < lengths.length; i++) {
            canvas.save();
            canvas.translate(width / 2f, width / 2f);
            canvas.rotate(135f + i * 360f / lengths.length);
            if (lengths[i] > 0) {
                float startX = -radius + radius / 16 + (radius - radius / 16 - radius / 1.7f) * (1 - lengths[i]);
                canvas.drawLine(startX, 0, -radius / 1.7f, 0, mPaint);
            }
            canvas.restore();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimation();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        createAnimation();
    }

    @SuppressWarnings("SameParameterValue")
    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }

    public void setPercent(float percent) {
        percent = percent % 100;
        float index = Math.round(percent / 5.25f);
        for (int i = 0; i < lengths.length; i++) {
            if (i * 2 < 16 - index) {
                lengths[i] = 0f;
            } else if (i * 2 == 16 - index) {
                lengths[i] = 0.5f;
            } else {
                lengths[i] = 1f;
            }
        }
        postInvalidate();
    }

    public void createAnimation() {
        cancelAnimation();
        final ValueAnimator alphaAnim = ValueAnimator.ofFloat(100, 0);
        alphaAnim.setDuration(1000);
        alphaAnim.setInterpolator(new LinearInterpolator());
        alphaAnim.setRepeatCount(ValueAnimator.INFINITE);
        alphaAnim.addUpdateListener(animation -> setPercent((float) animation.getAnimatedValue()));
        alphaAnim.start();
        mAnimator = alphaAnim;
    }

    private void cancelAnimation() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }
}
