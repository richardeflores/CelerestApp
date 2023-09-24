package com.huanghuang.kangshangyiliao.view.wheel;

import java.util.Timer;
import java.util.TimerTask;

final class MyTimerTask extends TimerTask {

    /* renamed from: a */
    float f114a = 2.1474839E9f;

    /* renamed from: b */
    float f115b = 0.0f;

    /* renamed from: c */
    final int f116c;
    final LoopView loopView;
    final Timer timer;

    MyTimerTask(LoopView loopView2, int i, Timer timer2) {
        this.loopView = loopView2;
        this.f116c = i;
        this.timer = timer2;
    }

    public final void run() {
        if (this.f114a == 2.1474839E9f) {
            this.f114a = ((float) (this.f116c - LoopView.getSelectItem(this.loopView))) * this.loopView.f98l * ((float) this.loopView.f97h);
            if (this.f116c > LoopView.getSelectItem(this.loopView)) {
                this.f115b = -1000.0f;
            } else {
                this.f115b = 1000.0f;
            }
        }
        if (Math.abs(this.f114a) < 1.0f) {
            this.timer.cancel();
            this.loopView.handler.sendEmptyMessage(2000);
            return;
        }
        int i = (int) ((this.f115b * 10.0f) / 1000.0f);
        if (Math.abs(this.f114a) < ((float) Math.abs(i))) {
            i = (int) (-this.f114a);
        }
        this.loopView.totalScrollY -= i;
        this.f114a = ((float) i) + this.f114a;
        this.loopView.handler.sendEmptyMessage(1000);
    }
}
