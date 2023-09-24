package com.huanghuang.kangshangyiliao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.huanghuang.kangshangyiliao.C0418R;

public class CircleSportView extends View {
    private Drawable backgroundDrawable;
    private Drawable centerDrawable;
    private int centerX;
    private int centerY;
    private float sportAngle;
    private boolean sportRunning;
    private float sportSlop;
    private long sportStartTime;

    public CircleSportView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CircleSportView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0418R.styleable.CircleSportView);
        this.backgroundDrawable = obtainStyledAttributes.getDrawable(0);
        this.centerDrawable = obtainStyledAttributes.getDrawable(1);
        this.sportSlop = obtainStyledAttributes.getFloat(3, 0.12f);
        this.sportRunning = obtainStyledAttributes.getBoolean(2, true);
        obtainStyledAttributes.recycle();
        this.sportAngle = 0.0f;
        this.sportStartTime = -1;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.backgroundDrawable != null && this.centerDrawable != null) {
            this.centerX = getPaddingLeft() + (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2);
            this.centerY = getPaddingTop() + (((getHeight() - getPaddingTop()) - getPaddingBottom()) / 2);
            int intrinsicWidth = this.backgroundDrawable.getIntrinsicWidth() / 2;
            int intrinsicHeight = this.backgroundDrawable.getIntrinsicHeight() / 2;
            this.backgroundDrawable.setBounds(-intrinsicWidth, -intrinsicHeight, intrinsicWidth, intrinsicHeight);
            int intrinsicWidth2 = this.centerDrawable.getIntrinsicWidth() / 2;
            int intrinsicHeight2 = this.centerDrawable.getIntrinsicHeight() / 2;
            this.centerDrawable.setBounds(-intrinsicWidth2, -intrinsicHeight2, intrinsicWidth2, intrinsicHeight2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        Drawable drawable = this.backgroundDrawable;
        if (drawable == null || this.centerDrawable == null) {
            super.onMeasure(i, i2);
            return;
        }
        setMeasuredDimension(measureSize(i, getPaddingLeft() + getPaddingRight() + Math.max(drawable.getIntrinsicWidth(), this.centerDrawable.getIntrinsicWidth())), measureSize(i2, getPaddingTop() + getPaddingBottom() + Math.max(this.backgroundDrawable.getIntrinsicHeight(), this.centerDrawable.getIntrinsicHeight())));
    }

    private int measureSize(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (mode != Integer.MIN_VALUE) {
            return mode != 1073741824 ? i2 : size;
        }
        return Math.min(size, i2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.backgroundDrawable != null && this.centerDrawable != null) {
            if (this.sportRunning) {
                long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                if (this.sportStartTime == -1) {
                    this.sportStartTime = currentAnimationTimeMillis;
                }
                this.sportAngle += this.sportSlop * ((float) (currentAnimationTimeMillis - this.sportStartTime));
                this.sportStartTime = currentAnimationTimeMillis;
                this.sportAngle %= 360.0f;
            }
            canvas.translate((float) this.centerX, (float) this.centerY);
            canvas.rotate(this.sportAngle);
            this.backgroundDrawable.draw(canvas);
            canvas.rotate(-this.sportAngle);
            this.centerDrawable.draw(canvas);
            canvas.translate((float) (-this.centerX), (float) (-this.centerY));
            if (this.sportRunning) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }
}
