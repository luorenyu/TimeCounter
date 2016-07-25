package com.timen4.ronnny.timecounter.bean;

/**
 * Created by luore on 2016/7/22.
 */
public class CustomTime {
    int Hours, Minutes,Seconds;

    public CustomTime(int hours, int minites, int seconds) {
        Hours = hours;
        Minutes = minites;
        Seconds = seconds;
    }

    public int getHours() {
        return Hours;
    }

    public int getMinutes() {
        return Minutes;
    }

    public int getSeconds() {
        return Seconds;
    }

    @Override
    public String toString() {
        return  Hours +
                "h " + Minutes +
                "m " + Seconds +
                's';
    }
}
