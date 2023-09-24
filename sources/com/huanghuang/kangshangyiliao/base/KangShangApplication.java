package com.huanghuang.kangshangyiliao.base;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import com.huanghuang.kangshangyiliao.app.base.AppBase;
import com.huanghuang.kangshangyiliao.util.Session;
import java.util.Locale;

public class KangShangApplication extends Application {
    public static int defaultNumber;
    private Session session = Session.getInstance();

    public void onCreate() {
        super.onCreate();
        AppBase.getInstance().setContext(this);
        language(getResources().getConfiguration().locale.getLanguage());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        this.session.setUrinalysCurrentDevice((String) null);
        this.session.setPOCTCurrentDevice((String) null);
        this.session.setProteinCurrentDevice((String) null);
        this.session.setBloodFat_Device((String) null);
        this.session.setImmunofluorescenceCurrentDevice((String) null);
        this.session.setPrinterDevice((String) null);
        this.session.setDeviceType(-1);
    }

    private void language(String str) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (str.equals("zh")) {
            configuration.locale = Locale.CHINESE;
            resources.updateConfiguration(configuration, displayMetrics);
        } else if (str.equals("en")) {
            configuration.locale = Locale.ENGLISH;
            resources.updateConfiguration(configuration, displayMetrics);
        } else if (str.equals("fr")) {
            configuration.locale = Locale.ENGLISH;
            resources.updateConfiguration(configuration, displayMetrics);
        } else if (str.equals("de")) {
            configuration.locale = Locale.ENGLISH;
            resources.updateConfiguration(configuration, displayMetrics);
        } else if (str.equals("it")) {
            configuration.locale = Locale.ENGLISH;
            resources.updateConfiguration(configuration, displayMetrics);
        } else if (str.equals("ja")) {
            configuration.locale = Locale.ENGLISH;
            resources.updateConfiguration(configuration, displayMetrics);
        } else if (str.equals("ko")) {
            configuration.locale = Locale.ENGLISH;
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }
}
