package android.support.p000v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompatKitKat;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat */
public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    static final AccessibilityNodeInfoBaseImpl IMPL;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private final AccessibilityNodeInfo mInfo;
    public int mParentVirtualDescendantId = -1;

    private static String getActionSymbolicName(int i) {
        if (i == 1) {
            return "ACTION_FOCUS";
        }
        if (i == 2) {
            return "ACTION_CLEAR_FOCUS";
        }
        switch (i) {
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat */
    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLICK = new AccessibilityActionCompat(16, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_COLLAPSE = new AccessibilityActionCompat(524288, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionContextClick());
        public static final AccessibilityActionCompat ACTION_COPY = new AccessibilityActionCompat(16384, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CUT = new AccessibilityActionCompat(65536, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_DISMISS = new AccessibilityActionCompat(1048576, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_EXPAND = new AccessibilityActionCompat(262144, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_LONG_CLICK = new AccessibilityActionCompat(32, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_PASTE = new AccessibilityActionCompat(32768, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_DOWN = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollDown());
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_LEFT = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollLeft());
        public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollRight());
        public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollToPosition());
        public static final AccessibilityActionCompat ACTION_SCROLL_UP = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionScrollUp());
        public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SET_PROGRESS = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionSetProgress());
        public static final AccessibilityActionCompat ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(AccessibilityNodeInfoCompat.IMPL.getActionShowOnScreen());
        final Object mAction;

        public AccessibilityActionCompat(int i, CharSequence charSequence) {
            this(AccessibilityNodeInfoCompat.IMPL.newAccessibilityAction(i, charSequence));
        }

        AccessibilityActionCompat(Object obj) {
            this.mAction = obj;
        }

        public int getId() {
            return AccessibilityNodeInfoCompat.IMPL.getAccessibilityActionId(this.mAction);
        }

        public CharSequence getLabel() {
            return AccessibilityNodeInfoCompat.IMPL.getAccessibilityActionLabel(this.mAction);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$CollectionInfoCompat */
    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        public static CollectionInfoCompat obtain(int i, int i2, boolean z, int i3) {
            return new CollectionInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionInfo(i, i2, z, i3));
        }

        public static CollectionInfoCompat obtain(int i, int i2, boolean z) {
            return new CollectionInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionInfo(i, i2, z));
        }

        CollectionInfoCompat(Object obj) {
            this.mInfo = obj;
        }

        public int getColumnCount() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoColumnCount(this.mInfo);
        }

        public int getRowCount() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoRowCount(this.mInfo);
        }

        public boolean isHierarchical() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionInfoHierarchical(this.mInfo);
        }

        public int getSelectionMode() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionInfoSelectionMode(this.mInfo);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$CollectionItemInfoCompat */
    public static class CollectionItemInfoCompat {
        final Object mInfo;

        public static CollectionItemInfoCompat obtain(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionItemInfo(i, i2, i3, i4, z, z2));
        }

        public static CollectionItemInfoCompat obtain(int i, int i2, int i3, int i4, boolean z) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainCollectionItemInfo(i, i2, i3, i4, z));
        }

        CollectionItemInfoCompat(Object obj) {
            this.mInfo = obj;
        }

        public int getColumnIndex() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemColumnIndex(this.mInfo);
        }

        public int getColumnSpan() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemColumnSpan(this.mInfo);
        }

        public int getRowIndex() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemRowIndex(this.mInfo);
        }

        public int getRowSpan() {
            return AccessibilityNodeInfoCompat.IMPL.getCollectionItemRowSpan(this.mInfo);
        }

        public boolean isHeading() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionItemHeading(this.mInfo);
        }

        public boolean isSelected() {
            return AccessibilityNodeInfoCompat.IMPL.isCollectionItemSelected(this.mInfo);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$RangeInfoCompat */
    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        final Object mInfo;

        public static RangeInfoCompat obtain(int i, float f, float f2, float f3) {
            return new RangeInfoCompat(AccessibilityNodeInfoCompat.IMPL.obtainRangeInfo(i, f, f2, f3));
        }

        RangeInfoCompat(Object obj) {
            this.mInfo = obj;
        }

        public float getCurrent() {
            return AccessibilityNodeInfoCompatKitKat.RangeInfo.getCurrent(this.mInfo);
        }

        public float getMax() {
            return AccessibilityNodeInfoCompatKitKat.RangeInfo.getMax(this.mInfo);
        }

        public float getMin() {
            return AccessibilityNodeInfoCompatKitKat.RangeInfo.getMin(this.mInfo);
        }

        public int getType() {
            return AccessibilityNodeInfoCompatKitKat.RangeInfo.getType(this.mInfo);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoBaseImpl */
    static class AccessibilityNodeInfoBaseImpl {
        public void addAction(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
        }

        public void addChild(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
        }

        public boolean canOpenPopup(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public Object findFocus(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            return null;
        }

        public Object focusSearch(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            return null;
        }

        public int getAccessibilityActionId(Object obj) {
            return 0;
        }

        public CharSequence getAccessibilityActionLabel(Object obj) {
            return null;
        }

        public Object getActionContextClick() {
            return null;
        }

        public List<Object> getActionList(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public Object getActionScrollDown() {
            return null;
        }

        public Object getActionScrollLeft() {
            return null;
        }

        public Object getActionScrollRight() {
            return null;
        }

        public Object getActionScrollToPosition() {
            return null;
        }

        public Object getActionScrollUp() {
            return null;
        }

        public Object getActionSetProgress() {
            return null;
        }

        public Object getActionShowOnScreen() {
            return null;
        }

        public Object getCollectionInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public int getCollectionInfoColumnCount(Object obj) {
            return 0;
        }

        public int getCollectionInfoRowCount(Object obj) {
            return 0;
        }

        public int getCollectionInfoSelectionMode(Object obj) {
            return 0;
        }

        public int getCollectionItemColumnIndex(Object obj) {
            return 0;
        }

        public int getCollectionItemColumnSpan(Object obj) {
            return 0;
        }

        public Object getCollectionItemInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public int getCollectionItemRowIndex(Object obj) {
            return 0;
        }

        public int getCollectionItemRowSpan(Object obj) {
            return 0;
        }

        public int getDrawingOrder(AccessibilityNodeInfo accessibilityNodeInfo) {
            return 0;
        }

        public CharSequence getError(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public int getInputType(AccessibilityNodeInfo accessibilityNodeInfo) {
            return 0;
        }

        public Object getLabelFor(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public Object getLabeledBy(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public int getLiveRegion(AccessibilityNodeInfo accessibilityNodeInfo) {
            return 0;
        }

        public int getMaxTextLength(AccessibilityNodeInfo accessibilityNodeInfo) {
            return -1;
        }

        public int getMovementGranularities(AccessibilityNodeInfo accessibilityNodeInfo) {
            return 0;
        }

        public Object getRangeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public CharSequence getRoleDescription(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public int getTextSelectionEnd(AccessibilityNodeInfo accessibilityNodeInfo) {
            return -1;
        }

        public int getTextSelectionStart(AccessibilityNodeInfo accessibilityNodeInfo) {
            return -1;
        }

        public Object getTraversalAfter(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public Object getTraversalBefore(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public String getViewIdResourceName(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public Object getWindow(AccessibilityNodeInfo accessibilityNodeInfo) {
            return null;
        }

        public boolean isAccessibilityFocused(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public boolean isCollectionInfoHierarchical(Object obj) {
            return false;
        }

        public boolean isCollectionItemHeading(Object obj) {
            return false;
        }

        public boolean isCollectionItemSelected(Object obj) {
            return false;
        }

        public boolean isContentInvalid(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public boolean isContextClickable(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public boolean isDismissable(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public boolean isEditable(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public boolean isImportantForAccessibility(AccessibilityNodeInfo accessibilityNodeInfo) {
            return true;
        }

        public boolean isMultiLine(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public boolean isVisibleToUser(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public Object newAccessibilityAction(int i, CharSequence charSequence) {
            return null;
        }

        public AccessibilityNodeInfo obtain(View view, int i) {
            return null;
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z) {
            return null;
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
            return null;
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z) {
            return null;
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return null;
        }

        public Object obtainRangeInfo(int i, float f, float f2, float f3) {
            return null;
        }

        public boolean performAction(AccessibilityNodeInfo accessibilityNodeInfo, int i, Bundle bundle) {
            return false;
        }

        public boolean refresh(AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public boolean removeAction(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
            return false;
        }

        public boolean removeChild(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
            return false;
        }

        public boolean removeChild(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            return false;
        }

        public void setAccessibilityFocused(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        public void setCanOpenPopup(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        public void setCollectionInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
        }

        public void setCollectionItemInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
        }

        public void setContentInvalid(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        public void setContextClickable(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        public void setDismissable(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        public void setDrawingOrder(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        }

        public void setEditable(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        public void setError(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
        }

        public void setImportantForAccessibility(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        public void setInputType(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        }

        public void setLabelFor(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
        }

        public void setLabelFor(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
        }

        public void setLabeledBy(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
        }

        public void setLabeledBy(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
        }

        public void setLiveRegion(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        }

        public void setMaxTextLength(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        }

        public void setMovementGranularities(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        }

        public void setMultiLine(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        public void setParent(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
        }

        public void setRangeInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
        }

        public void setRoleDescription(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
        }

        public void setSource(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
        }

        public void setTextSelection(AccessibilityNodeInfo accessibilityNodeInfo, int i, int i2) {
        }

        public void setTraversalAfter(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
        }

        public void setTraversalAfter(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
        }

        public void setTraversalBefore(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
        }

        public void setTraversalBefore(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
        }

        public void setViewIdResourceName(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        }

        public void setVisibleToUser(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        }

        AccessibilityNodeInfoBaseImpl() {
        }

        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
            return Collections.emptyList();
        }

        public Bundle getExtras(AccessibilityNodeInfo accessibilityNodeInfo) {
            return new Bundle();
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi16Impl */
    static class AccessibilityNodeInfoApi16Impl extends AccessibilityNodeInfoBaseImpl {
        AccessibilityNodeInfoApi16Impl() {
        }

        public AccessibilityNodeInfo obtain(View view, int i) {
            return AccessibilityNodeInfo.obtain(view, i);
        }

        public Object findFocus(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            return accessibilityNodeInfo.findFocus(i);
        }

        public Object focusSearch(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            return accessibilityNodeInfo.focusSearch(i);
        }

        public void addChild(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            accessibilityNodeInfo.addChild(view, i);
        }

        public void setSource(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            accessibilityNodeInfo.setSource(view, i);
        }

        public boolean isVisibleToUser(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isVisibleToUser();
        }

        public void setVisibleToUser(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setVisibleToUser(z);
        }

        public boolean isAccessibilityFocused(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isAccessibilityFocused();
        }

        public void setAccessibilityFocused(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setAccessibilityFocused(z);
        }

        public boolean performAction(AccessibilityNodeInfo accessibilityNodeInfo, int i, Bundle bundle) {
            return accessibilityNodeInfo.performAction(i, bundle);
        }

        public void setMovementGranularities(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            accessibilityNodeInfo.setMovementGranularities(i);
        }

        public int getMovementGranularities(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getMovementGranularities();
        }

        public void setParent(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            accessibilityNodeInfo.setParent(view, i);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi17Impl */
    static class AccessibilityNodeInfoApi17Impl extends AccessibilityNodeInfoApi16Impl {
        AccessibilityNodeInfoApi17Impl() {
        }

        public void setLabelFor(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
            accessibilityNodeInfo.setLabelFor(view);
        }

        public void setLabelFor(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            accessibilityNodeInfo.setLabelFor(view, i);
        }

        public Object getLabelFor(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getLabelFor();
        }

        public void setLabeledBy(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
            accessibilityNodeInfo.setLabeledBy(view);
        }

        public void setLabeledBy(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            accessibilityNodeInfo.setLabeledBy(view, i);
        }

        public Object getLabeledBy(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getLabeledBy();
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi18Impl */
    static class AccessibilityNodeInfoApi18Impl extends AccessibilityNodeInfoApi17Impl {
        AccessibilityNodeInfoApi18Impl() {
        }

        public String getViewIdResourceName(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getViewIdResourceName();
        }

        public void setViewIdResourceName(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
            accessibilityNodeInfo.setViewIdResourceName(str);
        }

        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
            return accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str);
        }

        public void setTextSelection(AccessibilityNodeInfo accessibilityNodeInfo, int i, int i2) {
            accessibilityNodeInfo.setTextSelection(i, i2);
        }

        public int getTextSelectionStart(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getTextSelectionStart();
        }

        public int getTextSelectionEnd(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getTextSelectionEnd();
        }

        public boolean isEditable(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isEditable();
        }

        public void setEditable(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setEditable(z);
        }

        public boolean refresh(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.refresh();
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi19Impl */
    static class AccessibilityNodeInfoApi19Impl extends AccessibilityNodeInfoApi18Impl {
        private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";

        AccessibilityNodeInfoApi19Impl() {
        }

        public int getLiveRegion(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getLiveRegion();
        }

        public void setLiveRegion(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            accessibilityNodeInfo.setLiveRegion(i);
        }

        public Object getCollectionInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getCollectionInfo();
        }

        public void setCollectionInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
            accessibilityNodeInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo) obj);
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
            return AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z);
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z) {
            return AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z);
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z);
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z) {
            return AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z);
        }

        public int getCollectionInfoColumnCount(Object obj) {
            return ((AccessibilityNodeInfo.CollectionInfo) obj).getColumnCount();
        }

        public int getCollectionInfoRowCount(Object obj) {
            return ((AccessibilityNodeInfo.CollectionInfo) obj).getRowCount();
        }

        public boolean isCollectionInfoHierarchical(Object obj) {
            return ((AccessibilityNodeInfo.CollectionInfo) obj).isHierarchical();
        }

        public Object getCollectionItemInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getCollectionItemInfo();
        }

        public Object getRangeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getRangeInfo();
        }

        public void setRangeInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
            accessibilityNodeInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo) obj);
        }

        public int getCollectionItemColumnIndex(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).getColumnIndex();
        }

        public int getCollectionItemColumnSpan(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).getColumnSpan();
        }

        public int getCollectionItemRowIndex(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).getRowIndex();
        }

        public int getCollectionItemRowSpan(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).getRowSpan();
        }

        public boolean isCollectionItemHeading(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).isHeading();
        }

        public void setCollectionItemInfo(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
            accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) obj);
        }

        public Object obtainRangeInfo(int i, float f, float f2, float f3) {
            return AccessibilityNodeInfo.RangeInfo.obtain(i, f, f2, f3);
        }

        public void setContentInvalid(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setContentInvalid(z);
        }

        public boolean isContentInvalid(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isContentInvalid();
        }

        public boolean canOpenPopup(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.canOpenPopup();
        }

        public void setCanOpenPopup(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setCanOpenPopup(z);
        }

        public Bundle getExtras(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getExtras();
        }

        public int getInputType(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getInputType();
        }

        public void setInputType(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            accessibilityNodeInfo.setInputType(i);
        }

        public boolean isDismissable(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isDismissable();
        }

        public void setDismissable(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setDismissable(z);
        }

        public boolean isMultiLine(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isMultiLine();
        }

        public void setMultiLine(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setMultiLine(z);
        }

        public CharSequence getRoleDescription(AccessibilityNodeInfo accessibilityNodeInfo) {
            return getExtras(accessibilityNodeInfo).getCharSequence(ROLE_DESCRIPTION_KEY);
        }

        public void setRoleDescription(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
            getExtras(accessibilityNodeInfo).putCharSequence(ROLE_DESCRIPTION_KEY, charSequence);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi21Impl */
    static class AccessibilityNodeInfoApi21Impl extends AccessibilityNodeInfoApi19Impl {
        AccessibilityNodeInfoApi21Impl() {
        }

        public Object newAccessibilityAction(int i, CharSequence charSequence) {
            return new AccessibilityNodeInfo.AccessibilityAction(i, charSequence);
        }

        public List<Object> getActionList(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getActionList();
        }

        public Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
            return AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z, i3);
        }

        public void addAction(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
            accessibilityNodeInfo.addAction((AccessibilityNodeInfo.AccessibilityAction) obj);
        }

        public boolean removeAction(AccessibilityNodeInfo accessibilityNodeInfo, Object obj) {
            return accessibilityNodeInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) obj);
        }

        public int getAccessibilityActionId(Object obj) {
            return ((AccessibilityNodeInfo.AccessibilityAction) obj).getId();
        }

        public CharSequence getAccessibilityActionLabel(Object obj) {
            return ((AccessibilityNodeInfo.AccessibilityAction) obj).getLabel();
        }

        public Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z, z2);
        }

        public boolean isCollectionItemSelected(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).isSelected();
        }

        public CharSequence getError(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getError();
        }

        public void setError(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
            accessibilityNodeInfo.setError(charSequence);
        }

        public void setMaxTextLength(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            accessibilityNodeInfo.setMaxTextLength(i);
        }

        public int getMaxTextLength(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getMaxTextLength();
        }

        public Object getWindow(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getWindow();
        }

        public boolean removeChild(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
            return accessibilityNodeInfo.removeChild(view);
        }

        public boolean removeChild(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            return accessibilityNodeInfo.removeChild(view, i);
        }

        public int getCollectionInfoSelectionMode(Object obj) {
            return ((AccessibilityNodeInfo.CollectionInfo) obj).getSelectionMode();
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi22Impl */
    static class AccessibilityNodeInfoApi22Impl extends AccessibilityNodeInfoApi21Impl {
        AccessibilityNodeInfoApi22Impl() {
        }

        public Object getTraversalBefore(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getTraversalBefore();
        }

        public void setTraversalBefore(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
            accessibilityNodeInfo.setTraversalBefore(view);
        }

        public void setTraversalBefore(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            accessibilityNodeInfo.setTraversalBefore(view, i);
        }

        public Object getTraversalAfter(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getTraversalAfter();
        }

        public void setTraversalAfter(AccessibilityNodeInfo accessibilityNodeInfo, View view) {
            accessibilityNodeInfo.setTraversalAfter(view);
        }

        public void setTraversalAfter(AccessibilityNodeInfo accessibilityNodeInfo, View view, int i) {
            accessibilityNodeInfo.setTraversalAfter(view, i);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi23Impl */
    static class AccessibilityNodeInfoApi23Impl extends AccessibilityNodeInfoApi22Impl {
        AccessibilityNodeInfoApi23Impl() {
        }

        public Object getActionScrollToPosition() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION;
        }

        public Object getActionShowOnScreen() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN;
        }

        public Object getActionScrollUp() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP;
        }

        public Object getActionScrollDown() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN;
        }

        public Object getActionScrollLeft() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT;
        }

        public Object getActionScrollRight() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT;
        }

        public Object getActionContextClick() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK;
        }

        public boolean isContextClickable(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isContextClickable();
        }

        public void setContextClickable(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setContextClickable(z);
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityNodeInfoApi24Impl */
    static class AccessibilityNodeInfoApi24Impl extends AccessibilityNodeInfoApi23Impl {
        AccessibilityNodeInfoApi24Impl() {
        }

        public Object getActionSetProgress() {
            return AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS;
        }

        public int getDrawingOrder(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.getDrawingOrder();
        }

        public void setDrawingOrder(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
            accessibilityNodeInfo.setDrawingOrder(i);
        }

        public boolean isImportantForAccessibility(AccessibilityNodeInfo accessibilityNodeInfo) {
            return accessibilityNodeInfo.isImportantForAccessibility();
        }

        public void setImportantForAccessibility(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
            accessibilityNodeInfo.setImportantForAccessibility(z);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            IMPL = new AccessibilityNodeInfoApi24Impl();
        } else if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new AccessibilityNodeInfoApi23Impl();
        } else if (Build.VERSION.SDK_INT >= 22) {
            IMPL = new AccessibilityNodeInfoApi22Impl();
        } else if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new AccessibilityNodeInfoApi21Impl();
        } else if (Build.VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeInfoApi19Impl();
        } else if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityNodeInfoApi18Impl();
        } else if (Build.VERSION.SDK_INT >= 17) {
            IMPL = new AccessibilityNodeInfoApi17Impl();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityNodeInfoApi16Impl();
        } else {
            IMPL = new AccessibilityNodeInfoBaseImpl();
        }
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object obj) {
        if (obj != null) {
            return new AccessibilityNodeInfoCompat(obj);
        }
        return null;
    }

    @Deprecated
    public AccessibilityNodeInfoCompat(Object obj) {
        this.mInfo = (AccessibilityNodeInfo) obj;
    }

    private AccessibilityNodeInfoCompat(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mInfo = accessibilityNodeInfo;
    }

    public static AccessibilityNodeInfoCompat wrap(AccessibilityNodeInfo accessibilityNodeInfo) {
        return new AccessibilityNodeInfoCompat(accessibilityNodeInfo);
    }

    public AccessibilityNodeInfo unwrap() {
        return this.mInfo;
    }

    @Deprecated
    public Object getInfo() {
        return this.mInfo;
    }

    public static AccessibilityNodeInfoCompat obtain(View view) {
        return wrap(AccessibilityNodeInfo.obtain(view));
    }

    public static AccessibilityNodeInfoCompat obtain(View view, int i) {
        return wrapNonNullInstance(IMPL.obtain(view, i));
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return wrap(AccessibilityNodeInfo.obtain());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return wrap(AccessibilityNodeInfo.obtain(accessibilityNodeInfoCompat.mInfo));
    }

    public void setSource(View view) {
        this.mInfo.setSource(view);
    }

    public void setSource(View view, int i) {
        IMPL.setSource(this.mInfo, view, i);
    }

    public AccessibilityNodeInfoCompat findFocus(int i) {
        return wrapNonNullInstance(IMPL.findFocus(this.mInfo, i));
    }

    public AccessibilityNodeInfoCompat focusSearch(int i) {
        return wrapNonNullInstance(IMPL.focusSearch(this.mInfo, i));
    }

    public int getWindowId() {
        return this.mInfo.getWindowId();
    }

    public int getChildCount() {
        return this.mInfo.getChildCount();
    }

    public AccessibilityNodeInfoCompat getChild(int i) {
        return wrapNonNullInstance(this.mInfo.getChild(i));
    }

    public void addChild(View view) {
        this.mInfo.addChild(view);
    }

    public void addChild(View view, int i) {
        IMPL.addChild(this.mInfo, view, i);
    }

    public boolean removeChild(View view) {
        return IMPL.removeChild(this.mInfo, view);
    }

    public boolean removeChild(View view, int i) {
        return IMPL.removeChild(this.mInfo, view, i);
    }

    public int getActions() {
        return this.mInfo.getActions();
    }

    public void addAction(int i) {
        this.mInfo.addAction(i);
    }

    public void addAction(AccessibilityActionCompat accessibilityActionCompat) {
        IMPL.addAction(this.mInfo, accessibilityActionCompat.mAction);
    }

    public boolean removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        return IMPL.removeAction(this.mInfo, accessibilityActionCompat.mAction);
    }

    public boolean performAction(int i) {
        return this.mInfo.performAction(i);
    }

    public boolean performAction(int i, Bundle bundle) {
        return IMPL.performAction(this.mInfo, i, bundle);
    }

    public void setMovementGranularities(int i) {
        IMPL.setMovementGranularities(this.mInfo, i);
    }

    public int getMovementGranularities() {
        return IMPL.getMovementGranularities(this.mInfo);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String str) {
        ArrayList arrayList = new ArrayList();
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = this.mInfo.findAccessibilityNodeInfosByText(str);
        int size = findAccessibilityNodeInfosByText.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(wrap(findAccessibilityNodeInfosByText.get(i)));
        }
        return arrayList;
    }

    public AccessibilityNodeInfoCompat getParent() {
        return wrapNonNullInstance(this.mInfo.getParent());
    }

    public void setParent(View view) {
        this.mInfo.setParent(view);
    }

    public void setParent(View view, int i) {
        this.mParentVirtualDescendantId = i;
        IMPL.setParent(this.mInfo, view, i);
    }

    public void getBoundsInParent(Rect rect) {
        this.mInfo.getBoundsInParent(rect);
    }

    public void setBoundsInParent(Rect rect) {
        this.mInfo.setBoundsInParent(rect);
    }

    public void getBoundsInScreen(Rect rect) {
        this.mInfo.getBoundsInScreen(rect);
    }

    public void setBoundsInScreen(Rect rect) {
        this.mInfo.setBoundsInScreen(rect);
    }

    public boolean isCheckable() {
        return this.mInfo.isCheckable();
    }

    public void setCheckable(boolean z) {
        this.mInfo.setCheckable(z);
    }

    public boolean isChecked() {
        return this.mInfo.isChecked();
    }

    public void setChecked(boolean z) {
        this.mInfo.setChecked(z);
    }

    public boolean isFocusable() {
        return this.mInfo.isFocusable();
    }

    public void setFocusable(boolean z) {
        this.mInfo.setFocusable(z);
    }

    public boolean isFocused() {
        return this.mInfo.isFocused();
    }

    public void setFocused(boolean z) {
        this.mInfo.setFocused(z);
    }

    public boolean isVisibleToUser() {
        return IMPL.isVisibleToUser(this.mInfo);
    }

    public void setVisibleToUser(boolean z) {
        IMPL.setVisibleToUser(this.mInfo, z);
    }

    public boolean isAccessibilityFocused() {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public void setAccessibilityFocused(boolean z) {
        IMPL.setAccessibilityFocused(this.mInfo, z);
    }

    public boolean isSelected() {
        return this.mInfo.isSelected();
    }

    public void setSelected(boolean z) {
        this.mInfo.setSelected(z);
    }

    public boolean isClickable() {
        return this.mInfo.isClickable();
    }

    public void setClickable(boolean z) {
        this.mInfo.setClickable(z);
    }

    public boolean isLongClickable() {
        return this.mInfo.isLongClickable();
    }

    public void setLongClickable(boolean z) {
        this.mInfo.setLongClickable(z);
    }

    public boolean isEnabled() {
        return this.mInfo.isEnabled();
    }

    public void setEnabled(boolean z) {
        this.mInfo.setEnabled(z);
    }

    public boolean isPassword() {
        return this.mInfo.isPassword();
    }

    public void setPassword(boolean z) {
        this.mInfo.setPassword(z);
    }

    public boolean isScrollable() {
        return this.mInfo.isScrollable();
    }

    public void setScrollable(boolean z) {
        this.mInfo.setScrollable(z);
    }

    public boolean isImportantForAccessibility() {
        return IMPL.isImportantForAccessibility(this.mInfo);
    }

    public void setImportantForAccessibility(boolean z) {
        IMPL.setImportantForAccessibility(this.mInfo, z);
    }

    public CharSequence getPackageName() {
        return this.mInfo.getPackageName();
    }

    public void setPackageName(CharSequence charSequence) {
        this.mInfo.setPackageName(charSequence);
    }

    public CharSequence getClassName() {
        return this.mInfo.getClassName();
    }

    public void setClassName(CharSequence charSequence) {
        this.mInfo.setClassName(charSequence);
    }

    public CharSequence getText() {
        return this.mInfo.getText();
    }

    public void setText(CharSequence charSequence) {
        this.mInfo.setText(charSequence);
    }

    public CharSequence getContentDescription() {
        return this.mInfo.getContentDescription();
    }

    public void setContentDescription(CharSequence charSequence) {
        this.mInfo.setContentDescription(charSequence);
    }

    public void recycle() {
        this.mInfo.recycle();
    }

    public void setViewIdResourceName(String str) {
        IMPL.setViewIdResourceName(this.mInfo, str);
    }

    public String getViewIdResourceName() {
        return IMPL.getViewIdResourceName(this.mInfo);
    }

    public int getLiveRegion() {
        return IMPL.getLiveRegion(this.mInfo);
    }

    public void setLiveRegion(int i) {
        IMPL.setLiveRegion(this.mInfo, i);
    }

    public int getDrawingOrder() {
        return IMPL.getDrawingOrder(this.mInfo);
    }

    public void setDrawingOrder(int i) {
        IMPL.setDrawingOrder(this.mInfo, i);
    }

    public CollectionInfoCompat getCollectionInfo() {
        Object collectionInfo = IMPL.getCollectionInfo(this.mInfo);
        if (collectionInfo == null) {
            return null;
        }
        return new CollectionInfoCompat(collectionInfo);
    }

    public void setCollectionInfo(Object obj) {
        IMPL.setCollectionInfo(this.mInfo, ((CollectionInfoCompat) obj).mInfo);
    }

    public void setCollectionItemInfo(Object obj) {
        IMPL.setCollectionItemInfo(this.mInfo, ((CollectionItemInfoCompat) obj).mInfo);
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        Object collectionItemInfo = IMPL.getCollectionItemInfo(this.mInfo);
        if (collectionItemInfo == null) {
            return null;
        }
        return new CollectionItemInfoCompat(collectionItemInfo);
    }

    public RangeInfoCompat getRangeInfo() {
        Object rangeInfo = IMPL.getRangeInfo(this.mInfo);
        if (rangeInfo == null) {
            return null;
        }
        return new RangeInfoCompat(rangeInfo);
    }

    public void setRangeInfo(RangeInfoCompat rangeInfoCompat) {
        IMPL.setRangeInfo(this.mInfo, rangeInfoCompat.mInfo);
    }

    public List<AccessibilityActionCompat> getActionList() {
        List<Object> actionList = IMPL.getActionList(this.mInfo);
        if (actionList == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = actionList.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(new AccessibilityActionCompat(actionList.get(i)));
        }
        return arrayList;
    }

    public void setContentInvalid(boolean z) {
        IMPL.setContentInvalid(this.mInfo, z);
    }

    public boolean isContentInvalid() {
        return IMPL.isContentInvalid(this.mInfo);
    }

    public boolean isContextClickable() {
        return IMPL.isContextClickable(this.mInfo);
    }

    public void setContextClickable(boolean z) {
        IMPL.setContextClickable(this.mInfo, z);
    }

    public void setError(CharSequence charSequence) {
        IMPL.setError(this.mInfo, charSequence);
    }

    public CharSequence getError() {
        return IMPL.getError(this.mInfo);
    }

    public void setLabelFor(View view) {
        IMPL.setLabelFor(this.mInfo, view);
    }

    public void setLabelFor(View view, int i) {
        IMPL.setLabelFor(this.mInfo, view, i);
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        return wrapNonNullInstance(IMPL.getLabelFor(this.mInfo));
    }

    public void setLabeledBy(View view) {
        IMPL.setLabeledBy(this.mInfo, view);
    }

    public void setLabeledBy(View view, int i) {
        IMPL.setLabeledBy(this.mInfo, view, i);
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        return wrapNonNullInstance(IMPL.getLabeledBy(this.mInfo));
    }

    public boolean canOpenPopup() {
        return IMPL.canOpenPopup(this.mInfo);
    }

    public void setCanOpenPopup(boolean z) {
        IMPL.setCanOpenPopup(this.mInfo, z);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String str) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId = IMPL.findAccessibilityNodeInfosByViewId(this.mInfo, str);
        if (findAccessibilityNodeInfosByViewId == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (AccessibilityNodeInfo wrap : findAccessibilityNodeInfosByViewId) {
            arrayList.add(wrap(wrap));
        }
        return arrayList;
    }

    public Bundle getExtras() {
        return IMPL.getExtras(this.mInfo);
    }

    public int getInputType() {
        return IMPL.getInputType(this.mInfo);
    }

    public void setInputType(int i) {
        IMPL.setInputType(this.mInfo, i);
    }

    public void setMaxTextLength(int i) {
        IMPL.setMaxTextLength(this.mInfo, i);
    }

    public int getMaxTextLength() {
        return IMPL.getMaxTextLength(this.mInfo);
    }

    public void setTextSelection(int i, int i2) {
        IMPL.setTextSelection(this.mInfo, i, i2);
    }

    public int getTextSelectionStart() {
        return IMPL.getTextSelectionStart(this.mInfo);
    }

    public int getTextSelectionEnd() {
        return IMPL.getTextSelectionEnd(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        return wrapNonNullInstance(IMPL.getTraversalBefore(this.mInfo));
    }

    public void setTraversalBefore(View view) {
        IMPL.setTraversalBefore(this.mInfo, view);
    }

    public void setTraversalBefore(View view, int i) {
        IMPL.setTraversalBefore(this.mInfo, view, i);
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        return wrapNonNullInstance(IMPL.getTraversalAfter(this.mInfo));
    }

    public void setTraversalAfter(View view) {
        IMPL.setTraversalAfter(this.mInfo, view);
    }

    public void setTraversalAfter(View view, int i) {
        IMPL.setTraversalAfter(this.mInfo, view, i);
    }

    public AccessibilityWindowInfoCompat getWindow() {
        return AccessibilityWindowInfoCompat.wrapNonNullInstance(IMPL.getWindow(this.mInfo));
    }

    public boolean isDismissable() {
        return IMPL.isDismissable(this.mInfo);
    }

    public void setDismissable(boolean z) {
        IMPL.setDismissable(this.mInfo, z);
    }

    public boolean isEditable() {
        return IMPL.isEditable(this.mInfo);
    }

    public void setEditable(boolean z) {
        IMPL.setEditable(this.mInfo, z);
    }

    public boolean isMultiLine() {
        return IMPL.isMultiLine(this.mInfo);
    }

    public void setMultiLine(boolean z) {
        IMPL.setMultiLine(this.mInfo, z);
    }

    public boolean refresh() {
        return IMPL.refresh(this.mInfo);
    }

    public CharSequence getRoleDescription() {
        return IMPL.getRoleDescription(this.mInfo);
    }

    public void setRoleDescription(CharSequence charSequence) {
        IMPL.setRoleDescription(this.mInfo, charSequence);
    }

    public int hashCode() {
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        return accessibilityNodeInfo.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) obj;
        AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
        if (accessibilityNodeInfo == null) {
            if (accessibilityNodeInfoCompat.mInfo != null) {
                return false;
            }
        } else if (!accessibilityNodeInfo.equals(accessibilityNodeInfoCompat.mInfo)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        Rect rect = new Rect();
        getBoundsInParent(rect);
        sb.append("; boundsInParent: " + rect);
        getBoundsInScreen(rect);
        sb.append("; boundsInScreen: " + rect);
        sb.append("; packageName: ");
        sb.append(getPackageName());
        sb.append("; className: ");
        sb.append(getClassName());
        sb.append("; text: ");
        sb.append(getText());
        sb.append("; contentDescription: ");
        sb.append(getContentDescription());
        sb.append("; viewId: ");
        sb.append(getViewIdResourceName());
        sb.append("; checkable: ");
        sb.append(isCheckable());
        sb.append("; checked: ");
        sb.append(isChecked());
        sb.append("; focusable: ");
        sb.append(isFocusable());
        sb.append("; focused: ");
        sb.append(isFocused());
        sb.append("; selected: ");
        sb.append(isSelected());
        sb.append("; clickable: ");
        sb.append(isClickable());
        sb.append("; longClickable: ");
        sb.append(isLongClickable());
        sb.append("; enabled: ");
        sb.append(isEnabled());
        sb.append("; password: ");
        sb.append(isPassword());
        sb.append("; scrollable: " + isScrollable());
        sb.append("; [");
        int actions = getActions();
        while (actions != 0) {
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(actions);
            actions &= numberOfTrailingZeros ^ -1;
            sb.append(getActionSymbolicName(numberOfTrailingZeros));
            if (actions != 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
