package com.huanghuang.kangshangyiliao.view.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import com.huanghuang.kangshangyiliao.dao.ProteinDao;
import com.huanghuang.kangshangyiliao.dao.bean.Protein;
import com.huanghuang.kangshangyiliao.util.DisplayUtil;
import com.huanghuang.kangshangyiliao.util.HourItem;
import com.huanghuang.kangshangyiliao.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class ProteinTrendChartView extends View {
    private static final int MARGIN_LEFT_ITEM = 20;
    private static final int MARGIN_RIGHT_ITEM = 0;
    private static final int bottomTextHeight = 20;
    private int ITEM_SIZE;
    private int ITEM_WIDTH;
    private Paint dashLinePaint;
    private Paint lineNormalPaint;
    private Paint linePaint;
    private List<HourItem> listItems;
    private int mHeight;
    private int mWidth;
    private int maxTemp;
    private int minTemp;
    private Paint pointHighPaint;
    private Paint pointLowPaint;
    private Paint pointNormalPaint;
    private Paint pointPaint;
    private ProteinDao proteinDao;
    private Paint rectanglePaint;
    private int tempBaseBottom;
    private int tempBaseTop;
    private TextPaint textPaint;

    public ProteinTrendChartView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ProteinTrendChartView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ProteinTrendChartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        WBCmax_small();
    }

    private void WBCmax_small() {
        this.proteinDao = new ProteinDao();
        List<Protein> queryAll = this.proteinDao.queryAll();
        int[] iArr = new int[queryAll.size()];
        int size = queryAll.size();
        if (size > 30) {
            int i = 29;
            while (i >= 0) {
                String replace = Utils.float2Point(Utils.parsePOCTData(Utils.toByteArray(queryAll.get(i).data)).finalValues[0]).replace(".", "");
                iArr[i] = Integer.parseInt(replace.substring(0, replace.length() - 1));
                i--;
                size = 30;
            }
        } else {
            for (int size2 = queryAll.size() - 1; size2 >= 0; size2--) {
                String replace2 = Utils.float2Point(Utils.parsePOCTData(Utils.toByteArray(queryAll.get(size2).data)).finalValues[0]).replace(".", "");
                iArr[size2] = Integer.parseInt(replace2.substring(0, replace2.length() - 1));
            }
        }
        if (size != 0) {
            IntMaxSmall(iArr, size);
        }
    }

    private void IntMaxSmall(int[] iArr, int i) {
        int i2 = iArr[0];
        for (int i3 = 1; i3 < iArr.length; i3++) {
            if (iArr[i3] > i2) {
                i2 = iArr[i3];
            }
        }
        int i4 = iArr[0];
        for (int i5 = 1; i5 < iArr.length; i5++) {
            if (iArr[i5] < i4) {
                i4 = iArr[i5];
            }
        }
        init(i2, i4, i);
    }

    private void init(int i, int i2, int i3) {
        this.maxTemp = i + 10;
        this.minTemp = i2;
        this.ITEM_SIZE = i3;
        int width = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getWidth();
        if (i3 > 10) {
            this.ITEM_WIDTH = ((width - DisplayUtil.dip2px(getContext(), 0.0f)) - DisplayUtil.dip2px(getContext(), 20.0f)) / i3;
        } else {
            this.ITEM_WIDTH = ((width - DisplayUtil.dip2px(getContext(), 0.0f)) - DisplayUtil.dip2px(getContext(), 20.0f)) / 10;
        }
        this.mWidth = (this.ITEM_SIZE * this.ITEM_WIDTH) + 20;
        this.mHeight = DisplayUtil.dip2px(getContext(), 150.0f) + 20;
        this.tempBaseTop = DisplayUtil.dip2px(getContext(), 10.0f);
        this.tempBaseBottom = this.mHeight - 20;
        initHourItems();
        initPaint();
    }

    private void initPaint() {
        this.rectanglePaint = new Paint();
        this.rectanglePaint.setStyle(Paint.Style.FILL);
        this.rectanglePaint.setColor(-655375);
        this.rectanglePaint.setAntiAlias(true);
        this.rectanglePaint.setTextSize((float) DisplayUtil.dip2px(getContext(), 2.0f));
        this.pointNormalPaint = new Paint();
        this.pointNormalPaint.setStyle(Paint.Style.FILL);
        this.pointNormalPaint.setColor(-9709249);
        this.pointNormalPaint.setAntiAlias(true);
        this.pointNormalPaint.setTextSize(2.0f);
        this.pointHighPaint = new Paint();
        this.pointHighPaint.setStyle(Paint.Style.FILL);
        this.pointHighPaint.setColor(-31744);
        this.pointHighPaint.setAntiAlias(true);
        this.pointHighPaint.setTextSize(2.0f);
        this.pointLowPaint = new Paint();
        this.pointLowPaint.setStyle(Paint.Style.FILL);
        this.pointLowPaint.setColor(-16737025);
        this.pointLowPaint.setAntiAlias(true);
        this.pointLowPaint.setTextSize(2.0f);
        this.textPaint = new TextPaint();
        this.textPaint.setTextSize((float) DisplayUtil.sp2px(getContext(), 12.0f));
        this.textPaint.setColor(-8750470);
        this.textPaint.setAntiAlias(true);
        this.lineNormalPaint = new Paint();
        this.lineNormalPaint.setColor(-9709249);
        this.lineNormalPaint.setAntiAlias(true);
        this.lineNormalPaint.setStyle(Paint.Style.STROKE);
        this.lineNormalPaint.setStrokeWidth(3.0f);
        this.linePaint = new Paint();
        this.linePaint.setColor(-3355444);
        this.linePaint.setAntiAlias(true);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setStrokeWidth(1.0f);
        this.dashLinePaint = new Paint();
        this.dashLinePaint.setColor(-3355444);
        this.dashLinePaint.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f, 5.0f, 5.0f}, 1.0f));
        this.dashLinePaint.setStrokeWidth(1.0f);
        this.dashLinePaint.setAntiAlias(true);
        this.dashLinePaint.setStyle(Paint.Style.STROKE);
    }

    private void initHourItems() {
        this.proteinDao = new ProteinDao();
        List<Protein> queryAll = this.proteinDao.queryAll();
        this.listItems = new ArrayList();
        int size = queryAll.size();
        if (size > 30) {
            for (int i = 29; i >= 0; i--) {
                String replace = Utils.float2Point(Utils.parsePOCTData(Utils.toByteArray(queryAll.get(i).data)).finalValues[0]).replace(".", "");
                String substring = replace.substring(0, replace.length() - 1);
                int parseInt = Integer.parseInt(replace);
                int parseInt2 = Integer.parseInt(substring);
                String valueOf = String.valueOf(30 - i);
                int i2 = this.ITEM_WIDTH;
                int i3 = ((29 - i) * i2) + 20;
                Point calculateTempPoint = calculateTempPoint(i3, (i2 + i3) - 1, parseInt2);
                HourItem hourItem = new HourItem();
                hourItem.WBC_Id = valueOf;
                hourItem.WBC = parseInt2;
                hourItem.WBC_double = parseInt;
                hourItem.WBC_Point = calculateTempPoint;
                this.listItems.add(hourItem);
            }
            return;
        }
        for (int i4 = size - 1; i4 >= 0; i4--) {
            String replace2 = Utils.float2Point(Utils.parsePOCTData(Utils.toByteArray(queryAll.get(i4).data)).finalValues[0]).replace(".", "");
            String substring2 = replace2.substring(0, replace2.length() - 1);
            int parseInt3 = Integer.parseInt(replace2);
            int parseInt4 = Integer.parseInt(substring2);
            String valueOf2 = String.valueOf(queryAll.size() - i4);
            int i5 = this.ITEM_WIDTH;
            int size2 = (((queryAll.size() - 1) - i4) * i5) + 20;
            Point calculateTempPoint2 = calculateTempPoint(size2, (i5 + size2) - 1, parseInt4);
            HourItem hourItem2 = new HourItem();
            hourItem2.WBC_Id = valueOf2;
            hourItem2.WBC = parseInt4;
            hourItem2.WBC_double = parseInt3;
            hourItem2.WBC_Point = calculateTempPoint2;
            this.listItems.add(hourItem2);
        }
    }

    private Point calculateTempPoint(int i, int i2, int i3) {
        if (i3 < 35) {
            double dip2px = (double) DisplayUtil.dip2px(getContext(), 113.0f);
            double dip2px2 = (double) DisplayUtil.dip2px(getContext(), 150.0f);
            Double.isNaN(dip2px2);
            Double.isNaN(dip2px);
            double d = (double) (35 - i3);
            Double.isNaN(d);
            Double.isNaN(dip2px);
            return new Point((i + i2) / 2, (int) (dip2px + (((dip2px2 - dip2px) / 35.0d) * d)));
        } else if (i3 > 100) {
            double d2 = (double) this.tempBaseTop;
            double dip2px3 = (double) DisplayUtil.dip2px(getContext(), 53.0f);
            Double.isNaN(dip2px3);
            Double.isNaN(d2);
            double d3 = (double) (this.maxTemp - 100);
            Double.isNaN(d3);
            double d4 = (double) (i3 - 100);
            Double.isNaN(d4);
            Double.isNaN(dip2px3);
            return new Point((i + i2) / 2, (int) (dip2px3 - (((dip2px3 - d2) / d3) * d4)));
        } else {
            double dip2px4 = (double) DisplayUtil.dip2px(getContext(), 53.0f);
            double dip2px5 = (double) DisplayUtil.dip2px(getContext(), 113.0f);
            Double.isNaN(dip2px5);
            Double.isNaN(dip2px4);
            double d5 = (double) (65 - (i3 - 35));
            Double.isNaN(d5);
            Double.isNaN(dip2px4);
            return new Point((i + i2) / 2, (int) (dip2px4 + (((dip2px5 - dip2px4) / 65.0d) * d5)));
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onRectanglePaint(canvas);
        for (int i = 0; i < this.listItems.size(); i++) {
            String valueOf = String.valueOf(i);
            String substring = valueOf.substring(valueOf.length() - 1, valueOf.length());
            if (i == 0) {
                onVerticalLine(canvas, i);
            }
            if (i == 9) {
                onVerticalLine(canvas, i);
            }
            if (valueOf.length() > 1 && substring.equals("9")) {
                onVerticalLine(canvas, i);
            }
        }
        onLowLine(canvas);
        onHighLine(canvas);
        for (int i2 = 0; i2 < this.listItems.size(); i2++) {
            onDrawLine(canvas, i2);
        }
        for (int i3 = 0; i3 < this.listItems.size(); i3++) {
            onDrawTemp(canvas, i3);
        }
    }

    private void onRectanglePaint(Canvas canvas) {
        int i = this.ITEM_WIDTH;
        canvas.drawRect((float) ((i / 2) + 20), (float) ((int) ((double) DisplayUtil.dip2px(getContext(), 53.0f))), (float) ((this.mWidth + 0) - (i / 2)), (float) ((int) ((double) DisplayUtil.dip2px(getContext(), 113.0f))), this.rectanglePaint);
    }

    private void onLowLine(Canvas canvas) {
        Path path = new Path();
        int dip2px = (int) ((double) DisplayUtil.dip2px(getContext(), 113.0f));
        float f = (float) dip2px;
        path.moveTo((float) ((this.ITEM_WIDTH / 2) + 20), f);
        int i = this.mWidth;
        path.quadTo((float) (i + 0), f, (float) ((i + 0) - (this.ITEM_WIDTH / 2)), f);
        canvas.drawPath(path, this.linePaint);
        canvas.drawText("3.5", 16.0f, (float) (dip2px + 8), this.textPaint);
        canvas.drawText("0", 17.0f, (float) ((this.mHeight - 20) + 9), this.textPaint);
    }

    private void onHighLine(Canvas canvas) {
        Path path = new Path();
        int dip2px = (int) ((double) DisplayUtil.dip2px(getContext(), 53.0f));
        float f = (float) dip2px;
        path.moveTo((float) ((this.ITEM_WIDTH / 2) + 20), f);
        int i = this.mWidth;
        path.quadTo((float) (i + 0), f, (float) ((i + 0) - (this.ITEM_WIDTH / 2)), f);
        canvas.drawPath(path, this.linePaint);
        canvas.drawText("10", 15.0f, (float) (dip2px + 8), this.textPaint);
    }

    private void onVerticalLine(Canvas canvas, int i) {
        int i2 = this.ITEM_WIDTH;
        Path path = new Path();
        float f = (float) ((((i2 * i) + ((i2 * (i + 1)) - 1)) / 2) + 20);
        path.moveTo(f, 0.0f);
        path.quadTo(f, 0.0f, f, (float) this.mHeight);
        canvas.drawPath(path, this.dashLinePaint);
    }

    private void onDrawTemp(Canvas canvas, int i) {
        HourItem hourItem = this.listItems.get(i);
        Point point = hourItem.WBC_Point;
        int i2 = hourItem.WBC;
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        if (i2 > 100) {
            canvas.drawCircle((float) point.x, (float) point.y, (float) DisplayUtil.dip2px(getContext(), 2.0f), this.pointHighPaint);
        } else if (i2 < 35) {
            canvas.drawCircle((float) point.x, (float) point.y, (float) DisplayUtil.dip2px(getContext(), 2.0f), this.pointLowPaint);
        } else {
            canvas.drawCircle((float) point.x, (float) point.y, (float) DisplayUtil.dip2px(getContext(), 2.0f), this.pointNormalPaint);
        }
    }

    private void onDrawLine(Canvas canvas, int i) {
        if (i != 0) {
            int i2 = this.listItems.get(i).WBC;
            Point point = this.listItems.get(i).WBC_Point;
            Point point2 = this.listItems.get(i - 1).WBC_Point;
            Path path = new Path();
            path.moveTo((float) point2.x, (float) point2.y);
            path.cubicTo((float) ((point2.x + point.x) / 2), (float) ((point2.y + point.y) / 2), (float) ((point2.x + point.x) / 2), (float) ((point2.y + point.y) / 2), (float) point.x, (float) point.y);
            canvas.drawPath(path, this.lineNormalPaint);
        }
    }

    public void setPostInvalidate() {
        WBCmax_small();
        postInvalidate();
    }
}
