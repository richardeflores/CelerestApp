package com.huanghuang.kangshangyiliao.view.wheel;

import android.os.Handler;
import android.os.Message;

final class MessageHandler extends Handler {

    /* renamed from: a */
    final LoopView f113a;

    MessageHandler(LoopView loopView) {
        this.f113a = loopView;
    }

    public final void handleMessage(Message message) {
        if (message.what == 1000) {
            this.f113a.invalidate();
        }
        if (message.what == 2000) {
            LoopView.m8b(this.f113a);
        } else if (message.what == 3000) {
            this.f113a.mo7932c();
        }
        super.handleMessage(message);
    }
}
