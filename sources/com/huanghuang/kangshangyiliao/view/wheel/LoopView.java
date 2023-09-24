package com.huanghuang.kangshangyiliao.view.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.util.List;
import java.util.Timer;

public class LoopView extends View {
    List arrayList;
    int colorBlack;
    int colorGray;
    int colorGrayLight;
    Context context;

    /* renamed from: g */
    int f96g;
    private GestureDetector gestureDetector;

    /* renamed from: h */
    int f97h;
    Handler handler;
    boolean isLoop;

    /* renamed from: l */
    float f98l;
    LoopListener loopListener;
    int mCurrentItem;
    private int mSelectItem;
    Timer mTimer;

    /* renamed from: n */
    int f99n;

    /* renamed from: o */
    int f100o;
    Paint paintA;
    Paint paintB;
    Paint paintC;
    int positon;

    /* renamed from: r */
    int f101r;

    /* renamed from: s */
    int f102s;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;

    /* renamed from: t */
    int f103t;
    int textSize;
    int totalScrollY;

    /* renamed from: u */
    int f104u;

    /* renamed from: v */
    int f105v;

    /* renamed from: w */
    int f106w;

    /* renamed from: x */
    float f107x;

    /* renamed from: y */
    float f108y;

    /* renamed from: z */
    float f109z;

    public LoopView(Context context2) {
        super(context2);
        initLoopView(context2);
    }

    public LoopView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        initLoopView(context2);
    }

    public LoopView(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        initLoopView(context2);
    }

    private void initLoopView(Context context2) {
        this.textSize = 0;
        this.colorGray = -5789785;
        this.colorBlack = -13421773;
        this.colorGrayLight = -2302756;
        this.f98l = 2.0f;
        this.isLoop = true;
        this.positon = -1;
        this.f101r = 9;
        this.f107x = 0.0f;
        this.f108y = 0.0f;
        this.f109z = 0.0f;
        this.totalScrollY = 0;
        this.simpleOnGestureListener = new LoopViewGestureListener(this);
        this.handler = new MessageHandler(this);
        this.context = context2;
        setTextSize(18.0f);
    }

    static int getSelectItem(LoopView loopView) {
        return loopView.getCurrentItem();
    }

    /* renamed from: b */
    static void m8b(LoopView loopView) {
        loopView.m11f();
    }

    /* renamed from: d */
    private void m9d() {
        if (this.arrayList != null) {
            this.paintA = new Paint();
            this.paintA.setColor(this.colorGray);
            this.paintA.setAntiAlias(true);
            this.paintA.setTypeface(Typeface.MONOSPACE);
            this.paintA.setTextSize((float) this.textSize);
            this.paintB = new Paint();
            this.paintB.setColor(this.colorBlack);
            this.paintB.setAntiAlias(true);
            this.paintB.setTextScaleX(1.05f);
            this.paintB.setTypeface(Typeface.MONOSPACE);
            this.paintB.setTextSize((float) this.textSize);
            this.paintC = new Paint();
            this.paintC.setColor(this.colorGrayLight);
            this.paintC.setAntiAlias(true);
            this.paintC.setTypeface(Typeface.MONOSPACE);
            this.paintC.setTextSize((float) this.textSize);
            if (Build.VERSION.SDK_INT >= 11) {
                setLayerType(1, (Paint) null);
            }
            this.gestureDetector = new GestureDetector(this.context, this.simpleOnGestureListener);
            this.gestureDetector.setIsLongpressEnabled(false);
            m10e();
            int i = this.f97h;
            float f = this.f98l;
            this.f103t = (int) (((float) i) * f * ((float) (this.f101r - 1)));
            int i2 = this.f103t;
            double d = (double) (i2 * 2);
            Double.isNaN(d);
            this.f102s = (int) (d / 3.141592653589793d);
            double d2 = (double) i2;
            Double.isNaN(d2);
            this.f104u = (int) (d2 / 3.141592653589793d);
            this.f105v = this.f96g + this.textSize;
            int i3 = this.f102s;
            this.f99n = (int) ((((float) i3) - (((float) i) * f)) / 2.0f);
            this.f100o = (int) ((((float) i3) + (f * ((float) i))) / 2.0f);
            if (this.positon == -1) {
                if (this.isLoop) {
                    this.positon = (this.arrayList.size() + 1) / 2;
                } else {
                    this.positon = 0;
                }
            }
            this.mCurrentItem = this.positon;
        }
    }

    /* renamed from: e */
    private void m10e() {
        Rect rect = new Rect();
        for (int i = 0; i < this.arrayList.size(); i++) {
            this.paintB.getTextBounds("0000", 0, 4, rect);
            int width = (int) (((float) rect.width()) * 3.0f);
            if (width > this.f96g) {
                this.f96g = width;
            }
            this.paintB.getTextBounds("星期", 0, 2, rect);
            int height = rect.height();
            if (height > this.f97h) {
                this.f97h = height;
            }
        }
    }

    /* renamed from: f */
    private void m11f() {
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new MTimer(this, (int) (((float) this.totalScrollY) % (this.f98l * ((float) this.f97h))), timer), 0, 10);
    }

    public final void setNotLoop() {
        this.isLoop = false;
    }

    public final void setCyclic(boolean z) {
        this.isLoop = z;
    }

    public final void setTextSize(float f) {
        if (f > 0.0f) {
            this.textSize = (int) (this.context.getResources().getDisplayMetrics().density * f);
        }
    }

    public final void setCurrentItem(int i) {
        this.positon = i;
        this.totalScrollY = 0;
        m11f();
        invalidate();
    }

    public final void setListener(LoopListener loopListener2) {
        this.loopListener = loopListener2;
    }

    public final void setArrayList(List list) {
        this.arrayList = list;
        m9d();
        invalidate();
    }

    public final int getCurrentItem() {
        int i = this.mCurrentItem;
        if (i <= 0) {
            return 0;
        }
        return i;
    }

    public final String getCurrentItemValue() {
        return String.valueOf(this.arrayList.get(getCurrentItem())).trim();
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo7930b(float f) {
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new LoopTimerTask(this, f, timer), 0, 20);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo7931b(int i) {
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new MyTimerTask(this, i, timer), 0, 20);
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public final void mo7932c() {
        if (this.loopListener != null) {
            new Handler().postDelayed(new LoopRunnable(this), 200);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        List list = this.arrayList;
        if (list == null) {
            super.onDraw(canvas);
            return;
        }
        String[] strArr = new String[this.f101r];
        this.f106w = (int) (((float) this.totalScrollY) / (this.f98l * ((float) this.f97h)));
        this.mCurrentItem = this.positon + (this.f106w % list.size());
        if (!this.isLoop) {
            if (this.mCurrentItem < 0) {
                this.mCurrentItem = 0;
            }
            if (this.mCurrentItem > this.arrayList.size() - 1) {
                this.mCurrentItem = this.arrayList.size() - 1;
            }
        } else {
            if (this.mCurrentItem < 0) {
                this.mCurrentItem = this.arrayList.size() + this.mCurrentItem;
            }
            if (this.mCurrentItem > this.arrayList.size() - 1) {
                this.mCurrentItem -= this.arrayList.size();
            }
        }
        int i = (int) (((float) this.totalScrollY) % (this.f98l * ((float) this.f97h)));
        for (int i2 = 0; i2 < this.f101r; i2++) {
            int i3 = this.mCurrentItem - (4 - i2);
            if (this.isLoop) {
                if (i3 < 0) {
                    i3 += this.arrayList.size();
                }
                if (i3 > this.arrayList.size() - 1) {
                    i3 -= this.arrayList.size();
                }
                strArr[i2] = (String) this.arrayList.get(i3);
            } else if (i3 < 0) {
                strArr[i2] = "";
            } else if (i3 > this.arrayList.size() - 1) {
                strArr[i2] = "";
            } else {
                strArr[i2] = (String) this.arrayList.get(i3);
            }
        }
        int i4 = this.f105v;
        int i5 = (i4 - this.f96g) / 2;
        int i6 = this.f99n;
        canvas.drawLine(0.0f, (float) i6, (float) i4, (float) i6, this.paintC);
        int i7 = this.f100o;
        canvas.drawLine(0.0f, (float) i7, (float) this.f105v, (float) i7, this.paintC);
        for (int i8 = 0; i8 < this.f101r; i8++) {
            canvas.save();
            double d = (double) ((((float) (this.f97h * i8)) * this.f98l) - ((float) i));
            Double.isNaN(d);
            double d2 = (double) this.f103t;
            Double.isNaN(d2);
            double d3 = (d * 3.141592653589793d) / d2;
            float f = (float) (90.0d - ((d3 / 3.141592653589793d) * 180.0d));
            if (f >= 90.0f || f <= -90.0f) {
                canvas.restore();
            } else {
                double d4 = (double) this.f104u;
                double cos = Math.cos(d3);
                double d5 = (double) this.f104u;
                Double.isNaN(d5);
                Double.isNaN(d4);
                double d6 = d4 - (cos * d5);
                double sin = Math.sin(d3);
                double d7 = (double) this.f97h;
                Double.isNaN(d7);
                int i9 = (int) (d6 - ((sin * d7) / 2.0d));
                canvas2.translate(0.0f, (float) i9);
                canvas2.scale(1.0f, (float) Math.sin(d3));
                String str = strArr[i8];
                double d8 = (double) this.textSize;
                double length = (double) (str.length() * 2);
                Double.isNaN(d8);
                Double.isNaN(length);
                double d9 = d8 - length;
                int i10 = this.textSize;
                double d10 = (double) i10;
                Double.isNaN(d10);
                double d11 = (double) i10;
                Double.isNaN(d11);
                int i11 = (int) (d11 * (d9 / d10) * 1.2d);
                if (i11 < 10) {
                    i11 = 10;
                }
                float f2 = (float) i11;
                this.paintA.setTextSize(f2);
                this.paintB.setTextSize(f2);
                double d12 = (double) this.f99n;
                double left = (double) getLeft();
                Double.isNaN(left);
                Double.isNaN(d12);
                int i12 = (int) (d12 + (left * 0.5d));
                Rect rect = new Rect();
                this.paintB.getTextBounds(str, 0, str.length(), rect);
                int width = rect.width();
                int width2 = getWidth() - (i12 * 2);
                if (width > 0) {
                    double d13 = (double) i12;
                    double d14 = (double) (width2 - width);
                    Double.isNaN(d14);
                    Double.isNaN(d13);
                    i12 = (int) (d13 + (d14 * 0.5d));
                }
                int i13 = this.f99n;
                if (i9 > i13 || this.f97h + i9 < i13) {
                    int i14 = this.f100o;
                    if (i9 > i14 || this.f97h + i9 < i14) {
                        if (i9 >= this.f99n) {
                            int i15 = this.f97h;
                            if (i9 + i15 <= this.f100o) {
                                canvas2.clipRect(0, 0, this.f105v, (int) (((float) i15) * this.f98l));
                                canvas2.drawText(strArr[i8], (float) i12, (float) this.f97h, this.paintB);
                                this.mSelectItem = this.arrayList.indexOf(strArr[i8]);
                            }
                        }
                        canvas2.clipRect(0, 0, this.f105v, (int) (((float) this.f97h) * this.f98l));
                        canvas2.drawText(strArr[i8], (float) i12, (float) this.f97h, this.paintA);
                    } else {
                        canvas.save();
                        canvas2.clipRect(0, 0, this.f105v, this.f100o - i9);
                        float f3 = (float) i12;
                        canvas2.drawText(strArr[i8], f3, (float) this.f97h, this.paintB);
                        canvas.restore();
                        canvas.save();
                        canvas2.clipRect(0, this.f100o - i9, this.f105v, (int) (((float) this.f97h) * this.f98l));
                        canvas2.drawText(strArr[i8], f3, (float) this.f97h, this.paintA);
                        canvas.restore();
                    }
                } else {
                    canvas.save();
                    canvas2.clipRect(0, 0, this.f105v, this.f99n - i9);
                    float f4 = (float) i12;
                    canvas2.drawText(strArr[i8], f4, (float) this.f97h, this.paintA);
                    canvas.restore();
                    canvas.save();
                    canvas2.clipRect(0, this.f99n - i9, this.f105v, (int) (((float) this.f97h) * this.f98l));
                    canvas2.drawText(strArr[i8], f4, (float) this.f97h, this.paintB);
                    canvas.restore();
                }
                canvas.restore();
            }
        }
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        m9d();
        setMeasuredDimension(i, this.f102s);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.f107x = motionEvent.getRawY();
        } else if (action != 2) {
            if (!this.gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1) {
                m11f();
            }
            return true;
        } else {
            this.f108y = motionEvent.getRawY();
            float f = this.f107x;
            float f2 = this.f108y;
            this.f109z = f - f2;
            this.f107x = f2;
            this.totalScrollY = (int) (((float) this.totalScrollY) + this.f109z);
            if (!this.isLoop) {
                int i = this.totalScrollY;
                int i2 = this.positon;
                float f3 = this.f98l;
                int i3 = this.f97h;
                if (i <= ((int) (((float) (-i2)) * ((float) i3) * f3))) {
                    this.totalScrollY = (int) (((float) (-i2)) * f3 * ((float) i3));
                }
            }
        }
        if (this.totalScrollY < ((int) (((float) ((this.arrayList.size() - 1) - this.positon)) * this.f98l * ((float) this.f97h)))) {
            invalidate();
        } else {
            this.totalScrollY = (int) (((float) ((this.arrayList.size() - 1) - this.positon)) * this.f98l * ((float) this.f97h));
            invalidate();
        }
        if (!this.gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1) {
            m11f();
        }
        return true;
    }
}
