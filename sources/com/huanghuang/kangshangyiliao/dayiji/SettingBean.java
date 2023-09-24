package com.huanghuang.kangshangyiliao.dayiji;

public class SettingBean {
    private static SettingBean instance = new SettingBean();
    private int imageMaxWidth;
    private String[] supportedImgFiles;
    private String[] supportedPrintFiles;

    public String[] getSupportedPrintFiles() {
        return this.supportedPrintFiles;
    }

    public void setSupportedPrintFiles(String[] strArr) {
        this.supportedPrintFiles = strArr;
    }

    public String[] getSupportedImgFiles() {
        return this.supportedImgFiles;
    }

    public void setSupportedImgFiles(String[] strArr) {
        this.supportedImgFiles = strArr;
    }

    private SettingBean() {
    }

    public static SettingBean getInstance() {
        return instance;
    }

    public int getImageMaxWidth() {
        return this.imageMaxWidth;
    }

    public void setImageMaxWidth(int i) {
        this.imageMaxWidth = i;
    }

    public void clear() {
        this.imageMaxWidth = 0;
        this.supportedPrintFiles = null;
        this.supportedImgFiles = null;
    }
}
