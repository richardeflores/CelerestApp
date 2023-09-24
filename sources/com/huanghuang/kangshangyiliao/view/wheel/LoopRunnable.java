package com.huanghuang.kangshangyiliao.view.wheel;

final class LoopRunnable implements Runnable {
    final LoopView loopView;

    LoopRunnable(LoopView loopView2) {
        this.loopView = loopView2;
    }

    public final void run() {
        this.loopView.loopListener.onItemSelect(LoopView.getSelectItem(this.loopView));
    }
}
