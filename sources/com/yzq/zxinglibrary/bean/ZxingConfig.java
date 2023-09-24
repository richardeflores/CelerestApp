package com.yzq.zxinglibrary.bean;

import com.yzq.zxinglibrary.C0598R;
import java.io.Serializable;

public class ZxingConfig implements Serializable {
    private int frameLineColor = -1;
    private boolean isDecodeBarCode = true;
    private boolean isFullScreenScan = true;
    private boolean isPlayBeep = true;
    private boolean isShake = true;
    private boolean isShowAlbum = true;
    private boolean isShowFlashLight = true;
    private boolean isShowbottomLayout = true;
    private int reactColor = C0598R.color.react;
    private int scanLineColor = C0598R.color.scanLineColor;

    public int getFrameLineColor() {
        return this.frameLineColor;
    }

    public void setFrameLineColor(int i) {
        this.frameLineColor = i;
    }

    public int getScanLineColor() {
        return this.scanLineColor;
    }

    public void setScanLineColor(int i) {
        this.scanLineColor = i;
    }

    public int getReactColor() {
        return this.reactColor;
    }

    public void setReactColor(int i) {
        this.reactColor = i;
    }

    public boolean isFullScreenScan() {
        return this.isFullScreenScan;
    }

    public void setFullScreenScan(boolean z) {
        this.isFullScreenScan = z;
    }

    public boolean isDecodeBarCode() {
        return this.isDecodeBarCode;
    }

    public void setDecodeBarCode(boolean z) {
        this.isDecodeBarCode = z;
    }

    public boolean isPlayBeep() {
        return this.isPlayBeep;
    }

    public void setPlayBeep(boolean z) {
        this.isPlayBeep = z;
    }

    public boolean isShake() {
        return this.isShake;
    }

    public void setShake(boolean z) {
        this.isShake = z;
    }

    public boolean isShowbottomLayout() {
        return this.isShowbottomLayout;
    }

    public void setShowbottomLayout(boolean z) {
        this.isShowbottomLayout = z;
    }

    public boolean isShowFlashLight() {
        return this.isShowFlashLight;
    }

    public void setShowFlashLight(boolean z) {
        this.isShowFlashLight = z;
    }

    public boolean isShowAlbum() {
        return this.isShowAlbum;
    }

    public void setShowAlbum(boolean z) {
        this.isShowAlbum = z;
    }
}
