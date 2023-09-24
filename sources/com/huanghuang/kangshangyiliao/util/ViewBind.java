package com.huanghuang.kangshangyiliao.util;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ViewBind {

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Bind {
        boolean click() default false;

        /* renamed from: id */
        int mo7926id();
    }

    public static void bind(Object obj, View view) {
        try {
            HashMap hashMap = new HashMap();
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (View.class.isAssignableFrom(field.getType())) {
                    try {
                        Bind bind = (Bind) field.getAnnotation(Bind.class);
                        if (bind != null) {
                            hashMap.put(bind.mo7926id() + "", field);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            setValue(obj, view, hashMap);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void setValue(Object obj, View view, Map<String, Field> map) {
        if (view instanceof ViewGroup) {
            try {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                boolean z = obj instanceof View.OnClickListener;
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    Field field = map.get("" + childAt.getId());
                    if (field != null) {
                        field.setAccessible(true);
                        field.set(obj, childAt);
                        map.remove("" + childAt.getId());
                    }
                    if (z && childAt.isClickable() && childAt.getId() != -1 && !(childAt instanceof AdapterView) && !childAt.hasOnClickListeners()) {
                        childAt.setOnClickListener((View.OnClickListener) obj);
                    }
                    if (childAt instanceof ViewGroup) {
                        setValue(obj, childAt, map);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void bind(Activity activity) {
        bind(activity, activity.getWindow().getDecorView());
    }

    public static void bind(View view) {
        bind(view, view);
    }

    public static void bind(Fragment fragment) {
        bind(fragment, fragment.getView());
    }

    public static void bind(android.support.p000v4.app.Fragment fragment) {
        bind(fragment, fragment.getView());
    }
}
