package com.huanghuang.kangshangyiliao.util;

public class Session {
    private String BloodFat_Device;
    private String ImmunofluorescencecurrentDevice;
    private String POCTcurrentDevice;
    private String Printer_Device;
    private String Protein_currentDevice;
    private String Urinalys_currentDevice;
    private int deviceType;
    public int size;
    private int viewType;

    private static class Loader {
        /* access modifiers changed from: private */
        public static final Session INSTANCE = new Session();

        private Loader() {
        }
    }

    public static Session getInstance() {
        return Loader.INSTANCE;
    }

    private Session() {
        this.deviceType = 0;
        this.viewType = 0;
    }

    public void setPOCTCurrentDevice(String str) {
        this.POCTcurrentDevice = str;
    }

    public String getPOCTCurrentDevice() {
        return this.POCTcurrentDevice;
    }

    public void setUrinalysCurrentDevice(String str) {
        this.Urinalys_currentDevice = str;
    }

    public String getUrinalysCurrentDevice() {
        return this.Urinalys_currentDevice;
    }

    public void setProteinCurrentDevice(String str) {
        this.Protein_currentDevice = str;
    }

    public String getProteinCurrentDevice() {
        return this.Protein_currentDevice;
    }

    public void setBloodFat_Device(String str) {
        this.BloodFat_Device = str;
    }

    public String getBloodFat_Device() {
        return this.BloodFat_Device;
    }

    public void setImmunofluorescenceCurrentDevice(String str) {
        this.ImmunofluorescencecurrentDevice = str;
    }

    public String getImmunofluorescenceCurrentDevice() {
        return this.ImmunofluorescencecurrentDevice;
    }

    public void setPrinterDevice(String str) {
        this.Printer_Device = str;
    }

    public String getPrinterDevice() {
        return this.Printer_Device;
    }

    public void setDeviceType(int i) {
        this.deviceType = i;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setViewType(int i) {
        this.viewType = i;
    }

    public int getViewType() {
        return this.viewType;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }
}
