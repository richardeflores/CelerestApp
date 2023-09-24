package com.yzq.zxinglibrary.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.p000v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.google.zxing.ResultPoint;
import com.yzq.zxinglibrary.C0598R;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.camera.CameraManager;
import java.util.ArrayList;
import java.util.List;

public final class ViewfinderView extends View {
    private static final long ANIMATION_DELAY = 80;
    private static final int CURRENT_POINT_OPACITY = 160;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 6;
    private CameraManager cameraManager;
    private ZxingConfig config;
    private Rect frame;
    private int frameLineColor;
    private Paint frameLinePaint;
    private List<ResultPoint> lastPossibleResultPoints;
    private int maskColor;
    private Paint paint;
    private List<ResultPoint> possibleResultPoints;
    private int reactColor;
    private Paint reactPaint;
    private Bitmap resultBitmap;
    private int resultColor;
    private int resultPointColor;
    private int scanLineColor;
    private Paint scanLinePaint;
    /* access modifiers changed from: private */
    public int scanLineTop;
    private ValueAnimator valueAnimator;

    public ViewfinderView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ViewfinderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setZxingConfig(ZxingConfig zxingConfig) {
        this.config = zxingConfig;
        this.reactColor = ContextCompat.getColor(getContext(), zxingConfig.getReactColor());
        if (zxingConfig.getFrameLineColor() != -1) {
            this.frameLineColor = ContextCompat.getColor(getContext(), zxingConfig.getFrameLineColor());
        }
        this.scanLineColor = ContextCompat.getColor(getContext(), zxingConfig.getScanLineColor());
        initPaint();
    }

    public ViewfinderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.frameLineColor = -1;
        this.maskColor = ContextCompat.getColor(getContext(), C0598R.color.viewfinder_mask);
        this.resultColor = ContextCompat.getColor(getContext(), C0598R.color.result_view);
        this.resultPointColor = ContextCompat.getColor(getContext(), C0598R.color.possible_result_points);
        this.possibleResultPoints = new ArrayList(10);
        this.lastPossibleResultPoints = null;
    }

    private void initPaint() {
        this.paint = new Paint(1);
        this.reactPaint = new Paint(1);
        this.reactPaint.setColor(this.reactColor);
        this.reactPaint.setStyle(Paint.Style.FILL);
        this.reactPaint.setStrokeWidth((float) dp2px(1));
        if (this.frameLineColor != -1) {
            this.frameLinePaint = new Paint(1);
            this.frameLinePaint.setColor(ContextCompat.getColor(getContext(), this.config.getFrameLineColor()));
            this.frameLinePaint.setStrokeWidth((float) dp2px(1));
            this.frameLinePaint.setStyle(Paint.Style.STROKE);
        }
        this.scanLinePaint = new Paint(1);
        this.scanLinePaint.setStrokeWidth((float) dp2px(2));
        this.scanLinePaint.setStyle(Paint.Style.FILL);
        this.scanLinePaint.setDither(true);
        this.scanLinePaint.setColor(this.scanLineColor);
    }

    private void initAnimator() {
        if (this.valueAnimator == null) {
            this.valueAnimator = ValueAnimator.ofInt(new int[]{this.frame.top, this.frame.bottom});
            this.valueAnimator.setDuration(3000);
            this.valueAnimator.setInterpolator(new DecelerateInterpolator());
            this.valueAnimator.setRepeatMode(1);
            this.valueAnimator.setRepeatCount(-1);
            this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int unused = ViewfinderView.this.scanLineTop = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    ViewfinderView.this.invalidate();
                }
            });
            this.valueAnimator.start();
        }
    }

    public void setCameraManager(CameraManager cameraManager2) {
        this.cameraManager = cameraManager2;
    }

    public void onDraw(Canvas canvas) {
        CameraManager cameraManager2 = this.cameraManager;
        if (cameraManager2 != null) {
            this.frame = cameraManager2.getFramingRect();
            Rect framingRectInPreview = this.cameraManager.getFramingRectInPreview();
            if (this.frame != null && framingRectInPreview != null) {
                initAnimator();
                drawMaskView(canvas, this.frame, canvas.getWidth(), canvas.getHeight());
                drawFrameBounds(canvas, this.frame);
                if (this.resultBitmap != null) {
                    this.paint.setAlpha(CURRENT_POINT_OPACITY);
                    canvas.drawBitmap(this.resultBitmap, (Rect) null, this.frame, this.paint);
                    return;
                }
                drawScanLight(canvas, this.frame);
            }
        }
    }

    private void drawPoint(Canvas canvas, Rect rect, Rect rect2) {
        float width = ((float) rect.width()) / ((float) rect2.width());
        float height = ((float) rect.height()) / ((float) rect2.height());
        List<ResultPoint> list = this.possibleResultPoints;
        List<ResultPoint> list2 = this.lastPossibleResultPoints;
        int i = rect.left;
        int i2 = rect.top;
        if (list.isEmpty()) {
            this.lastPossibleResultPoints = null;
        } else {
            this.possibleResultPoints = new ArrayList(5);
            this.lastPossibleResultPoints = list;
            this.paint.setAlpha(CURRENT_POINT_OPACITY);
            this.paint.setColor(this.resultPointColor);
            synchronized (list) {
                for (ResultPoint next : list) {
                    canvas.drawCircle((float) (((int) (next.getX() * width)) + i), (float) (((int) (next.getY() * height)) + i2), 6.0f, this.paint);
                }
            }
        }
        if (list2 != null) {
            this.paint.setAlpha(80);
            this.paint.setColor(this.resultPointColor);
            synchronized (list2) {
                for (ResultPoint next2 : list2) {
                    canvas.drawCircle((float) (((int) (next2.getX() * width)) + i), (float) (((int) (next2.getY() * height)) + i2), 3.0f, this.paint);
                }
            }
        }
        postInvalidateDelayed(ANIMATION_DELAY, rect.left - 6, rect.top - 6, rect.right + 6, rect.bottom + 6);
    }

    private void drawMaskView(Canvas canvas, Rect rect, int i, int i2) {
        Rect rect2 = rect;
        this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
        float f = (float) i;
        canvas.drawRect(0.0f, 0.0f, f, (float) rect2.top, this.paint);
        canvas.drawRect(0.0f, (float) rect2.top, (float) rect2.left, (float) (rect2.bottom + 1), this.paint);
        Canvas canvas2 = canvas;
        float f2 = f;
        canvas2.drawRect((float) (rect2.right + 1), (float) rect2.top, f2, (float) (rect2.bottom + 1), this.paint);
        canvas2.drawRect(0.0f, (float) (rect2.bottom + 1), f2, (float) i2, this.paint);
    }

    private void drawFrameBounds(Canvas canvas, Rect rect) {
        if (this.frameLineColor != -1) {
            canvas.drawRect(rect, this.frameLinePaint);
        }
        double width = (double) rect.width();
        Double.isNaN(width);
        int i = (int) (width * 0.07d);
        double d = (double) i;
        Double.isNaN(d);
        int i2 = (int) (d * 0.2d);
        if (i2 > 15) {
            i2 = 15;
        }
        Canvas canvas2 = canvas;
        canvas2.drawRect((float) (rect.left - i2), (float) rect.top, (float) rect.left, (float) (rect.top + i), this.reactPaint);
        canvas2.drawRect((float) (rect.left - i2), (float) (rect.top - i2), (float) (rect.left + i), (float) rect.top, this.reactPaint);
        canvas2.drawRect((float) rect.right, (float) rect.top, (float) (rect.right + i2), (float) (rect.top + i), this.reactPaint);
        canvas2.drawRect((float) (rect.right - i), (float) (rect.top - i2), (float) (rect.right + i2), (float) rect.top, this.reactPaint);
        canvas2.drawRect((float) (rect.left - i2), (float) (rect.bottom - i), (float) rect.left, (float) rect.bottom, this.reactPaint);
        canvas2.drawRect((float) (rect.left - i2), (float) rect.bottom, (float) (rect.left + i), (float) (rect.bottom + i2), this.reactPaint);
        canvas2.drawRect((float) rect.right, (float) (rect.bottom - i), (float) (rect.right + i2), (float) rect.bottom, this.reactPaint);
        canvas2.drawRect((float) (rect.right - i), (float) rect.bottom, (float) (rect.right + i2), (float) (rect.bottom + i2), this.reactPaint);
    }

    private void drawScanLight(Canvas canvas, Rect rect) {
        canvas.drawLine((float) rect.left, (float) this.scanLineTop, (float) rect.right, (float) this.scanLineTop, this.scanLinePaint);
    }

    public void drawViewfinder() {
        Bitmap bitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (bitmap != null) {
            bitmap.recycle();
        }
        invalidate();
    }

    public void drawResultBitmap(Bitmap bitmap) {
        this.resultBitmap = bitmap;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint resultPoint) {
        List<ResultPoint> list = this.possibleResultPoints;
        synchronized (list) {
            list.add(resultPoint);
            int size = list.size();
            if (size > 20) {
                list.subList(0, size - 10).clear();
            }
        }
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }
}
