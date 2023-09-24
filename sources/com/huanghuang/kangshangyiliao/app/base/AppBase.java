package com.huanghuang.kangshangyiliao.app.base;

import android.app.Application;
import android.content.Context;

public class AppBase {
    private Application app;
    private Context mCtx;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final AppBase INSTANCE = new AppBase();

        private Loader() {
        }
    }

    private AppBase() {
        init();
    }

    public static AppBase getInstance() {
        return Loader.INSTANCE;
    }

    private void init() {
        try {
            this.mCtx = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke((Object) null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContext(Application application) {
        this.app = application;
    }

    public Context getContext() {
        Context context = this.mCtx;
        if (context != null) {
            return context;
        }
        return this.app;
    }
}
