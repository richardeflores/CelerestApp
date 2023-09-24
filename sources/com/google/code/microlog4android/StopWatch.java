package com.google.code.microlog4android;

class StopWatch {
    private long currentTime;
    private long startTime;
    private boolean started;
    private long stopTime;

    StopWatch() {
    }

    public synchronized void start() {
        this.startTime = System.currentTimeMillis();
        this.started = true;
    }

    public synchronized long getCurrentTime() {
        if (this.started) {
            this.currentTime = System.currentTimeMillis() - this.startTime;
        }
        return this.currentTime;
    }

    public synchronized long stop() {
        this.stopTime = System.currentTimeMillis();
        this.currentTime = this.stopTime - this.startTime;
        this.started = false;
        return this.currentTime;
    }

    public synchronized void reset() {
        if (this.started) {
            this.startTime = System.currentTimeMillis();
        } else {
            this.startTime = 0;
        }
        this.stopTime = 0;
        this.currentTime = 0;
    }

    public String toString() {
        return String.valueOf(getCurrentTime());
    }
}
