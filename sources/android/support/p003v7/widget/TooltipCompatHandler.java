package android.support.p003v7.widget;

import android.support.p000v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;

/* renamed from: android.support.v7.widget.TooltipCompatHandler */
class TooltipCompatHandler implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {
    private static final long HOVER_HIDE_TIMEOUT_MS = 15000;
    private static final long HOVER_HIDE_TIMEOUT_SHORT_MS = 3000;
    private static final long LONG_CLICK_HIDE_TIMEOUT_MS = 2500;
    private static final String TAG = "TooltipCompatHandler";
    private static TooltipCompatHandler sActiveHandler;
    private final View mAnchor;
    private int mAnchorX;
    private int mAnchorY;
    private boolean mFromTouch;
    private final Runnable mHideRunnable = new Runnable() {
        public void run() {
            TooltipCompatHandler.this.hide();
        }
    };
    private TooltipPopup mPopup;
    private final Runnable mShowRunnable = new Runnable() {
        public void run() {
            TooltipCompatHandler.this.show(false);
        }
    };
    private final CharSequence mTooltipText;

    public void onViewAttachedToWindow(View view) {
    }

    public static void setTooltipText(View view, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            TooltipCompatHandler tooltipCompatHandler = sActiveHandler;
            if (tooltipCompatHandler != null && tooltipCompatHandler.mAnchor == view) {
                tooltipCompatHandler.hide();
            }
            view.setOnLongClickListener((View.OnLongClickListener) null);
            view.setLongClickable(false);
            view.setOnHoverListener((View.OnHoverListener) null);
            return;
        }
        new TooltipCompatHandler(view, charSequence);
    }

    private TooltipCompatHandler(View view, CharSequence charSequence) {
        this.mAnchor = view;
        this.mTooltipText = charSequence;
        this.mAnchor.setOnLongClickListener(this);
        this.mAnchor.setOnHoverListener(this);
    }

    public boolean onLongClick(View view) {
        this.mAnchorX = view.getWidth() / 2;
        this.mAnchorY = view.getHeight() / 2;
        show(true);
        return true;
    }

    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.mPopup != null && this.mFromTouch) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mAnchor.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                hide();
            }
        } else if (this.mAnchor.isEnabled() && this.mPopup == null) {
            this.mAnchorX = (int) motionEvent.getX();
            this.mAnchorY = (int) motionEvent.getY();
            this.mAnchor.removeCallbacks(this.mShowRunnable);
            this.mAnchor.postDelayed(this.mShowRunnable, (long) ViewConfiguration.getLongPressTimeout());
        }
        return false;
    }

    public void onViewDetachedFromWindow(View view) {
        hide();
    }

    /* access modifiers changed from: private */
    public void show(boolean z) {
        long j;
        int i;
        long j2;
        if (ViewCompat.isAttachedToWindow(this.mAnchor)) {
            TooltipCompatHandler tooltipCompatHandler = sActiveHandler;
            if (tooltipCompatHandler != null) {
                tooltipCompatHandler.hide();
            }
            sActiveHandler = this;
            this.mFromTouch = z;
            this.mPopup = new TooltipPopup(this.mAnchor.getContext());
            this.mPopup.show(this.mAnchor, this.mAnchorX, this.mAnchorY, this.mFromTouch, this.mTooltipText);
            this.mAnchor.addOnAttachStateChangeListener(this);
            if (this.mFromTouch) {
                j = LONG_CLICK_HIDE_TIMEOUT_MS;
            } else {
                if ((ViewCompat.getWindowSystemUiVisibility(this.mAnchor) & 1) == 1) {
                    j2 = HOVER_HIDE_TIMEOUT_SHORT_MS;
                    i = ViewConfiguration.getLongPressTimeout();
                } else {
                    j2 = HOVER_HIDE_TIMEOUT_MS;
                    i = ViewConfiguration.getLongPressTimeout();
                }
                j = j2 - ((long) i);
            }
            this.mAnchor.removeCallbacks(this.mHideRunnable);
            this.mAnchor.postDelayed(this.mHideRunnable, j);
        }
    }

    /* access modifiers changed from: private */
    public void hide() {
        if (sActiveHandler == this) {
            sActiveHandler = null;
            TooltipPopup tooltipPopup = this.mPopup;
            if (tooltipPopup != null) {
                tooltipPopup.hide();
                this.mPopup = null;
                this.mAnchor.removeOnAttachStateChangeListener(this);
            } else {
                Log.e(TAG, "sActiveHandler.mPopup == null");
            }
        }
        this.mAnchor.removeCallbacks(this.mShowRunnable);
        this.mAnchor.removeCallbacks(this.mHideRunnable);
    }
}
