package com.huanghuang.kangshangyiliao.view.wheel;

import java.util.Timer;
import java.util.TimerTask;

final class LoopTimerTask extends TimerTask {

    /* renamed from: a */
    float f94a = 2.1474839E9f;

    /* renamed from: b */
    final float f95b;
    final LoopView loopView;
    final Timer timer;

    LoopTimerTask(LoopView loopView2, float f, Timer timer2) {
        this.loopView = loopView2;
        this.f95b = f;
        this.timer = timer2;
    }

    public final void run() {
        if (this.f94a == 2.1474839E9f) {
            if (Math.abs(this.f95b) <= 2000.0f) {
                this.f94a = this.f95b;
            } else if (this.f95b > 0.0f) {
                this.f94a = 2000.0f;
            } else {
                this.f94a = -2000.0f;
            }
        }
        if (Math.abs(this.f94a) < 0.0f || Math.abs(this.f94a) > 20.0f) {
            this.loopView.totalScrollY -= (int) ((this.f94a * 10.0f) / 1000.0f);
            if (!this.loopView.isLoop) {
                if (this.loopView.totalScrollY <= ((int) (((float) (-this.loopView.positon)) * this.loopView.f98l * ((float) this.loopView.f97h)))) {
                    this.f94a = 40.0f;
                    LoopView loopView2 = this.loopView;
                    loopView2.totalScrollY = (int) (((float) (-loopView2.positon)) * this.loopView.f98l * ((float) this.loopView.f97h));
                } else if (this.loopView.totalScrollY >= ((int) (((float) ((this.loopView.arrayList.size() - 1) - this.loopView.positon)) * this.loopView.f98l * ((float) this.loopView.f97h)))) {
                    LoopView loopView3 = this.loopView;
                    loopView3.totalScrollY = (int) (((float) ((loopView3.arrayList.size() - 1) - this.loopView.positon)) * this.loopView.f98l * ((float) this.loopView.f97h));
                    this.f94a = -40.0f;
                }
            }
            float f = this.f94a;
            if (f < 0.0f) {
                this.f94a = f + 20.0f;
            } else {
                this.f94a = f - 20.0f;
            }
            this.loopView.handler.sendEmptyMessage(1000);
            return;
        }
        this.timer.cancel();
        this.loopView.handler.sendEmptyMessage(2000);
    }
}
