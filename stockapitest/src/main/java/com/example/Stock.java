package com.example;

/**
 * Created by simonchung on 18/11/15.
 */
public class Stock {
    private long mId;
    private String mNum;
    private String mName;
    private float mMin;
    private float mMax;
    private float mCurrent;
    private float mAlertLess;
    private float mAlertGreater;

    public Stock() {
    }
    public Stock(String num) {
        mNum = num;
    }
    public Stock(long id, String num, String name, float min, float max) {
        mId = id;
        mNum = num;
        mName = name;
        mMin = min;
        mMax = max;
    }
    void setMin(float min) {
        mMin = min;
    }
    float getMin() {
        return mMin;
    }
    void setMax(float max) {
        mMax = max;
    }
    float getMax() {
        return mMax;
    }
    void setId(int id) {
        mId = id;
    }
    void setName(String name) {
        mName = name;
    }
    String getName() {
        return mName;
    }
    long getId() {
        return mId;
    }
    void setNum(String num) {
        mNum = num;
    }
    String getNum() {
        return mNum;
    }
    void setCurrent(float current) {
        mCurrent = current;
    }
    float getCurrent() {
        return mCurrent;
    }
    void setAlertLess(float less) {
        mAlertLess = less;
    }
    float getAlertLess() {
        return mAlertLess;
    }
    void setmAlertGreater(float greater) {
        mAlertGreater = greater;
    }
    float getmAlertGreater() {
        return mAlertGreater;
    }
}
