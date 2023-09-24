package android.support.v13.view.inputmethod;

import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

public final class EditorInfoCompat {
    /* access modifiers changed from: private */
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
    public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
    private static final EditorInfoCompatImpl IMPL;

    private interface EditorInfoCompatImpl {
        String[] getContentMimeTypes(EditorInfo editorInfo);

        void setContentMimeTypes(EditorInfo editorInfo, String[] strArr);
    }

    static {
        if (Build.VERSION.SDK_INT >= 25) {
            IMPL = new EditorInfoCompatApi25Impl();
        } else {
            IMPL = new EditorInfoCompatBaseImpl();
        }
    }

    private static final class EditorInfoCompatBaseImpl implements EditorInfoCompatImpl {
        private static String CONTENT_MIME_TYPES_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";

        private EditorInfoCompatBaseImpl() {
        }

        public void setContentMimeTypes(EditorInfo editorInfo, String[] strArr) {
            if (editorInfo.extras == null) {
                editorInfo.extras = new Bundle();
            }
            editorInfo.extras.putStringArray(CONTENT_MIME_TYPES_KEY, strArr);
        }

        public String[] getContentMimeTypes(EditorInfo editorInfo) {
            if (editorInfo.extras == null) {
                return EditorInfoCompat.EMPTY_STRING_ARRAY;
            }
            String[] stringArray = editorInfo.extras.getStringArray(CONTENT_MIME_TYPES_KEY);
            return stringArray != null ? stringArray : EditorInfoCompat.EMPTY_STRING_ARRAY;
        }
    }

    private static final class EditorInfoCompatApi25Impl implements EditorInfoCompatImpl {
        private EditorInfoCompatApi25Impl() {
        }

        public void setContentMimeTypes(EditorInfo editorInfo, String[] strArr) {
            editorInfo.contentMimeTypes = strArr;
        }

        public String[] getContentMimeTypes(EditorInfo editorInfo) {
            String[] strArr = editorInfo.contentMimeTypes;
            return strArr != null ? strArr : EditorInfoCompat.EMPTY_STRING_ARRAY;
        }
    }

    public static void setContentMimeTypes(EditorInfo editorInfo, String[] strArr) {
        IMPL.setContentMimeTypes(editorInfo, strArr);
    }

    public static String[] getContentMimeTypes(EditorInfo editorInfo) {
        return IMPL.getContentMimeTypes(editorInfo);
    }
}
