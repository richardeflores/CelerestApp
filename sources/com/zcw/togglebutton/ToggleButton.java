package com.zcw.togglebutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.huanghuang.kangshangyiliao.C0418R;

public class ToggleButton extends View {
    private int borderColor = this.offBorderColor;
    private int borderWidth = 2;
    private float centerY;
    /* access modifiers changed from: private */
    public boolean defaultAnimate = true;
    private float endX;
    private boolean isDefaultOn = false;
    private OnToggleChanged listener;
    private int offBorderColor = Color.parseColor("#dadbda");
    private int offColor = Color.parseColor("#ffffff");
    private float offLineWidth;
    private int onColor = Color.parseColor("#00D93E");
    private Paint paint;
    private float radius;
    private RectF rect = new RectF();
    private int spotColor = Color.parseColor("#ffffff");
    private float spotMaxX;
    private float spotMinX;
    private int spotSize;
    private float spotX;
    private Spring spring;
    SimpleSpringListener springListener = new SimpleSpringListener() {
        public void onSpringUpdate(Spring spring) {
            ToggleButton.this.calculateEffect(spring.getCurrentValue());
        }
    };
    private SpringSystem springSystem;
    private float startX;
    private boolean toggleOn = false;

    public interface OnToggleChanged {
        void onToggle(boolean z);
    }

    private ToggleButton(Context context) {
        super(context);
    }

    public ToggleButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setup(attributeSet);
    }

    public ToggleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setup(attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.spring.removeListener(this.springListener);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.spring.addListener(this.springListener);
    }

    public void setup(AttributeSet attributeSet) {
        this.paint = new Paint(1);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.springSystem = SpringSystem.create();
        this.spring = this.springSystem.createSpring();
        this.spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(50.0d, 10.0d));
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ToggleButton toggleButton = ToggleButton.this;
                toggleButton.toggle(toggleButton.defaultAnimate);
            }
        });
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C0418R.styleable.ToggleButton);
        this.offBorderColor = obtainStyledAttributes.getColor(3, this.offBorderColor);
        this.onColor = obtainStyledAttributes.getColor(5, this.onColor);
        this.spotColor = obtainStyledAttributes.getColor(6, this.spotColor);
        this.offColor = obtainStyledAttributes.getColor(4, this.offColor);
        this.borderWidth = obtainStyledAttributes.getDimensionPixelSize(2, this.borderWidth);
        this.defaultAnimate = obtainStyledAttributes.getBoolean(0, this.defaultAnimate);
        this.isDefaultOn = obtainStyledAttributes.getBoolean(1, this.isDefaultOn);
        obtainStyledAttributes.recycle();
        this.borderColor = this.offBorderColor;
        if (this.isDefaultOn) {
            toggleOn();
        }
    }

    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean z) {
        this.toggleOn = !this.toggleOn;
        takeEffect(z);
        OnToggleChanged onToggleChanged = this.listener;
        if (onToggleChanged != null) {
            onToggleChanged.onToggle(this.toggleOn);
        }
    }

    public void toggleOn() {
        setToggleOn();
        OnToggleChanged onToggleChanged = this.listener;
        if (onToggleChanged != null) {
            onToggleChanged.onToggle(this.toggleOn);
        }
    }

    public void toggleOff() {
        setToggleOff();
        OnToggleChanged onToggleChanged = this.listener;
        if (onToggleChanged != null) {
            onToggleChanged.onToggle(this.toggleOn);
        }
    }

    public boolean isToggleOn() {
        return this.toggleOn;
    }

    public void setToggleOn() {
        setToggleOn(true);
    }

    public void setToggleOn(boolean z) {
        this.toggleOn = true;
        takeEffect(z);
    }

    public void setToggleOff() {
        setToggleOff(true);
    }

    public void setToggleOff(boolean z) {
        this.toggleOn = false;
        takeEffect(z);
    }

    private void takeEffect(boolean z) {
        double d = 1.0d;
        if (z) {
            Spring spring2 = this.spring;
            if (!this.toggleOn) {
                d = 0.0d;
            }
            spring2.setEndValue(d);
            return;
        }
        this.spring.setCurrentValue(this.toggleOn ? 1.0d : 0.0d);
        if (!this.toggleOn) {
            d = 0.0d;
        }
        calculateEffect(d);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        View.MeasureSpec.getSize(i);
        int size = View.MeasureSpec.getSize(i2);
        Resources system = Resources.getSystem();
        if (mode == 0 || mode == Integer.MIN_VALUE) {
            i = View.MeasureSpec.makeMeasureSpec((int) TypedValue.applyDimension(1, 50.0f, system.getDisplayMetrics()), 1073741824);
        }
        if (mode2 == 0 || size == Integer.MIN_VALUE) {
            i2 = View.MeasureSpec.makeMeasureSpec((int) TypedValue.applyDimension(1, 30.0f, system.getDisplayMetrics()), 1073741824);
        }
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int width = getWidth();
        int height = getHeight();
        this.radius = ((float) Math.min(width, height)) * 0.5f;
        float f = this.radius;
        this.centerY = f;
        this.startX = f;
        this.endX = ((float) width) - f;
        float f2 = this.startX;
        int i5 = this.borderWidth;
        this.spotMinX = f2 + ((float) i5);
        this.spotMaxX = this.endX - ((float) i5);
        this.spotSize = height - (i5 * 4);
        this.spotX = this.toggleOn ? this.spotMaxX : this.spotMinX;
        this.offLineWidth = 0.0f;
    }

    private int clamp(int i, int i2, int i3) {
        return Math.min(Math.max(i, i2), i3);
    }

    public void draw(Canvas canvas) {
        this.rect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        this.paint.setColor(this.borderColor);
        RectF rectF = this.rect;
        float f = this.radius;
        canvas.drawRoundRect(rectF, f, f, this.paint);
        float f2 = this.offLineWidth;
        if (f2 > 0.0f) {
            float f3 = f2 * 0.5f;
            float f4 = this.centerY;
            this.rect.set(this.spotX - f3, f4 - f3, this.endX + f3, f4 + f3);
            this.paint.setColor(this.offColor);
            canvas.drawRoundRect(this.rect, f3, f3, this.paint);
        }
        RectF rectF2 = this.rect;
        float f5 = this.spotX;
        float f6 = this.radius;
        float f7 = this.centerY;
        rectF2.set((f5 - 1.0f) - f6, f7 - f6, f5 + 1.1f + f6, f7 + f6);
        this.paint.setColor(this.borderColor);
        RectF rectF3 = this.rect;
        float f8 = this.radius;
        canvas.drawRoundRect(rectF3, f8, f8, this.paint);
        float f9 = ((float) this.spotSize) * 0.5f;
        RectF rectF4 = this.rect;
        float f10 = this.spotX;
        float f11 = this.centerY;
        rectF4.set(f10 - f9, f11 - f9, f10 + f9, f11 + f9);
        this.paint.setColor(this.spotColor);
        canvas.drawRoundRect(this.rect, f9, f9, this.paint);
    }

    /* access modifiers changed from: private */
    public void calculateEffect(double d) {
        this.spotX = (float) SpringUtil.mapValueFromRangeToRange(d, 0.0d, 1.0d, (double) this.spotMinX, (double) this.spotMaxX);
        double d2 = 1.0d - d;
        this.offLineWidth = (float) SpringUtil.mapValueFromRangeToRange(d2, 0.0d, 1.0d, 10.0d, (double) this.spotSize);
        int blue = Color.blue(this.onColor);
        int red = Color.red(this.onColor);
        int green = Color.green(this.onColor);
        int blue2 = Color.blue(this.offBorderColor);
        int red2 = Color.red(this.offBorderColor);
        int green2 = Color.green(this.offBorderColor);
        double d3 = (double) red2;
        int clamp = clamp((int) SpringUtil.mapValueFromRangeToRange(d2, 0.0d, 1.0d, (double) blue, (double) blue2), 0, 255);
        this.borderColor = Color.rgb(clamp((int) SpringUtil.mapValueFromRangeToRange(d2, 0.0d, 1.0d, (double) red, d3), 0, 255), clamp((int) SpringUtil.mapValueFromRangeToRange(d2, 0.0d, 1.0d, (double) green, (double) green2), 0, 255), clamp);
        postInvalidate();
    }

    public void setOnToggleChanged(OnToggleChanged onToggleChanged) {
        this.listener = onToggleChanged;
    }

    public boolean isAnimate() {
        return this.defaultAnimate;
    }

    public void setAnimate(boolean z) {
        this.defaultAnimate = z;
    }
}
