package com.huanghuang.kangshangyiliao.view.wheel;

import android.support.graphics.drawable.PathInterpolatorCompat;
import android.support.p003v7.widget.ActivityChooserView;
import java.util.Timer;
import java.util.TimerTask;

final class MTimer extends TimerTask {

    /* renamed from: a */
    int f110a = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

    /* renamed from: b */
    int f111b = 0;

    /* renamed from: c */
    final int f112c;
    final LoopView loopView;
    final Timer timer;

    MTimer(LoopView loopView2, int i, Timer timer2) {
        this.loopView = loopView2;
        this.f112c = i;
        this.timer = timer2;
    }

    public final void run() {
        if (this.f110a == Integer.MAX_VALUE) {
            int i = this.f112c;
            if (i < 0) {
                if (((float) (-i)) > (this.loopView.f98l * ((float) this.loopView.f97h)) / 2.0f) {
                    this.f110a = (int) (((-this.loopView.f98l) * ((float) this.loopView.f97h)) - ((float) this.f112c));
                } else {
                    this.f110a = -this.f112c;
                }
            } else if (((float) i) > (this.loopView.f98l * ((float) this.loopView.f97h)) / 2.0f) {
                this.f110a = (int) ((this.loopView.f98l * ((float) this.loopView.f97h)) - ((float) this.f112c));
            } else {
                this.f110a = -this.f112c;
            }
        }
        int i2 = this.f110a;
        this.f111b = (int) (((float) i2) * 0.1f);
        if (this.f111b == 0) {
            if (i2 < 0) {
                this.f111b = -1;
            } else {
                this.f111b = 1;
            }
        }
        if (Math.abs(this.f110a) <= 0) {
            this.timer.cancel();
            this.loopView.handler.sendEmptyMessage(PathInterpolatorCompat.MAX_NUM_POINTS);
            return;
        }
        this.loopView.totalScrollY += this.f111b;
        this.loopView.handler.sendEmptyMessage(1000);
        this.f110a -= this.f111b;
    }
}
