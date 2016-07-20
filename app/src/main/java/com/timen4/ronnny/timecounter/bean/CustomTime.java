package com.timen4.ronnny.timecounter.bean;

/**
 * Created by luore on 2016/7/20.
 */
public class CustomTime {
    public int hour;
    public int minites;
    public int seconds;

    public CustomTime(int hour, int minites, int seconds) {
        this.hour = hour;
        this.minites = minites;
        this.seconds = seconds;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinites() {
        return minites;
    }

    public void setMinites(int minites) {
        this.minites = minites;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
