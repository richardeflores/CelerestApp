package android.support.p000v4.view.accessibility;

import android.os.Build;
import android.os.Bundle;
import android.support.p000v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean;
import android.support.p000v4.view.accessibility.AccessibilityNodeProviderCompatKitKat;
import java.util.ArrayList;
import java.util.List;

/* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat */
public class AccessibilityNodeProviderCompat {
    public static final int HOST_VIEW_ID = -1;
    private static final AccessibilityNodeProviderImpl IMPL;
    private final Object mProvider;

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderImpl */
    interface AccessibilityNodeProviderImpl {
        Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat);
    }

    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
        return null;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String str, int i) {
        return null;
    }

    public AccessibilityNodeInfoCompat findFocus(int i) {
        return null;
    }

    public boolean performAction(int i, int i2, Bundle bundle) {
        return false;
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderStubImpl */
    static class AccessibilityNodeProviderStubImpl implements AccessibilityNodeProviderImpl {
        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return null;
        }

        AccessibilityNodeProviderStubImpl() {
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderJellyBeanImpl */
    private static class AccessibilityNodeProviderJellyBeanImpl extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderJellyBeanImpl() {
        }

        public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge() {
                public boolean performAction(int i, int i2, Bundle bundle) {
                    return accessibilityNodeProviderCompat.performAction(i, i2, bundle);
                }

                public List<Object> findAccessibilityNodeInfosByText(String str, int i) {
                    List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText = accessibilityNodeProviderCompat.findAccessibilityNodeInfosByText(str, i);
                    if (findAccessibilityNodeInfosByText == null) {
                        return null;
                    }
                    ArrayList arrayList = new ArrayList();
                    int size = findAccessibilityNodeInfosByText.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        arrayList.add(findAccessibilityNodeInfosByText.get(i2).unwrap());
                    }
                    return arrayList;
                }

                public Object createAccessibilityNodeInfo(int i) {
                    AccessibilityNodeInfoCompat createAccessibilityNodeInfo = accessibilityNodeProviderCompat.createAccessibilityNodeInfo(i);
                    if (createAccessibilityNodeInfo == null) {
                        return null;
                    }
                    return createAccessibilityNodeInfo.unwrap();
                }
            });
        }
    }

    /* renamed from: android.support.v4.view.accessibility.AccessibilityNodeProviderCompat$AccessibilityNodeProviderKitKatImpl */
    private static class AccessibilityNodeProviderKitKatImpl extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderKitKatImpl() {
        }

        public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return AccessibilityNodeProviderCompatKitKat.newAccessibilityNodeProviderBridge(new AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge() {
                public boolean performAction(int i, int i2, Bundle bundle) {
                    return accessibilityNodeProviderCompat.performAction(i, i2, bundle);
                }

                public List<Object> findAccessibilityNodeInfosByText(String str, int i) {
                    List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText = accessibilityNodeProviderCompat.findAccessibilityNodeInfosByText(str, i);
                    if (findAccessibilityNodeInfosByText == null) {
                        return null;
                    }
                    ArrayList arrayList = new ArrayList();
                    int size = findAccessibilityNodeInfosByText.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        arrayList.add(findAccessibilityNodeInfosByText.get(i2).unwrap());
                    }
                    return arrayList;
                }

                public Object createAccessibilityNodeInfo(int i) {
                    AccessibilityNodeInfoCompat createAccessibilityNodeInfo = accessibilityNodeProviderCompat.createAccessibilityNodeInfo(i);
                    if (createAccessibilityNodeInfo == null) {
                        return null;
                    }
                    return createAccessibilityNodeInfo.unwrap();
                }

                public Object findFocus(int i) {
                    AccessibilityNodeInfoCompat findFocus = accessibilityNodeProviderCompat.findFocus(i);
                    if (findFocus == null) {
                        return null;
                    }
                    return findFocus.unwrap();
                }
            });
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeProviderKitKatImpl();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityNodeProviderJellyBeanImpl();
        } else {
            IMPL = new AccessibilityNodeProviderStubImpl();
        }
    }

    public AccessibilityNodeProviderCompat() {
        this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
    }

    public AccessibilityNodeProviderCompat(Object obj) {
        this.mProvider = obj;
    }

    public Object getProvider() {
        return this.mProvider;
    }
}
