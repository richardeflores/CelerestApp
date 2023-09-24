package com.facebook.rebound;

import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Choreographer;

abstract class AndroidSpringLooperFactory {
    AndroidSpringLooperFactory() {
    }

    public static SpringLooper createSpringLooper() {
        if (Build.VERSION.SDK_INT >= 16) {
            return ChoreographerAndroidSpringLooper.create();
        }
        return LegacyAndroidSpringLooper.create();
    }

    private static class LegacyAndroidSpringLooper extends SpringLooper {
        /* access modifiers changed from: private */
        public final Handler mHandler;
        /* access modifiers changed from: private */
        public long mLastTime;
        /* access modifiers changed from: private */
        public final Runnable mLooperRunnable = new Runnable() {
            public void run() {
                if (LegacyAndroidSpringLooper.this.mStarted && LegacyAndroidSpringLooper.this.mSpringSystem != null) {
                    LegacyAndroidSpringLooper.this.mSpringSystem.loop((double) (SystemClock.uptimeMillis() - LegacyAndroidSpringLooper.this.mLastTime));
                    LegacyAndroidSpringLooper.this.mHandler.post(LegacyAndroidSpringLooper.this.mLooperRunnable);
                }
            }
        };
        /* access modifiers changed from: private */
        public boolean mStarted;

        public static SpringLooper create() {
            return new LegacyAndroidSpringLooper(new Handler());
        }

        public LegacyAndroidSpringLooper(Handler handler) {
            this.mHandler = handler;
        }

        public void start() {
            if (!this.mStarted) {
                this.mStarted = true;
                this.mLastTime = SystemClock.uptimeMillis();
                this.mHandler.removeCallbacks(this.mLooperRunnable);
                this.mHandler.post(this.mLooperRunnable);
            }
        }

        public void stop() {
            this.mStarted = false;
            this.mHandler.removeCallbacks(this.mLooperRunnable);
        }
    }

    private static class ChoreographerAndroidSpringLooper extends SpringLooper {
        /* access modifiers changed from: private */
        public final Choreographer mChoreographer;
        /* access modifiers changed from: private */
        public final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() {
            public void doFrame(long j) {
                if (ChoreographerAndroidSpringLooper.this.mStarted && ChoreographerAndroidSpringLooper.this.mSpringSystem != null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    ChoreographerAndroidSpringLooper.this.mSpringSystem.loop((double) (uptimeMillis - ChoreographerAndroidSpringLooper.this.mLastTime));
                    long unused = ChoreographerAndroidSpringLooper.this.mLastTime = uptimeMillis;
                    ChoreographerAndroidSpringLooper.this.mChoreographer.postFrameCallback(ChoreographerAndroidSpringLooper.this.mFrameCallback);
                }
            }
        };
        /* access modifiers changed from: private */
        public long mLastTime;
        /* access modifiers changed from: private */
        public boolean mStarted;

        public static ChoreographerAndroidSpringLooper create() {
            return new ChoreographerAndroidSpringLooper(Choreographer.getInstance());
        }

        public ChoreographerAndroidSpringLooper(Choreographer choreographer) {
            this.mChoreographer = choreographer;
        }

        public void start() {
            if (!this.mStarted) {
                this.mStarted = true;
                this.mLastTime = SystemClock.uptimeMillis();
                this.mChoreographer.removeFrameCallback(this.mFrameCallback);
                this.mChoreographer.postFrameCallback(this.mFrameCallback);
            }
        }

        public void stop() {
            this.mStarted = false;
            this.mChoreographer.removeFrameCallback(this.mFrameCallback);
        }
    }
}
