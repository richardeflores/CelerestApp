package com.huanghuang.kangshangyiliao.event;

public class MessageEvent {
    private int currentFragment;
    private String message;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public MessageEvent(String str) {
        this.message = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public int getCurrentFragment() {
        return this.currentFragment;
    }

    public void setCurrentFragment(int i) {
        this.currentFragment = i;
    }
}
