package StockDate;

import java.util.Date;

/**
 * Created by simonchung on 18/11/15.
 */
public class Stock {
    private long mId;
    private String mNum;
    private String mName;
    private float mMin;
    private float mMax;
    private float mCurrent; //Current stock price
    private boolean mStatus;
    private boolean mLimitLess;//alert price less or greater
    private float mLimitPrice;
    private long mUpdateDate; //last update time

    public Stock() {
    }
    public Stock(String num) {
        mNum = num;
    }
    public Stock(String num, float limitPrice) {
        mNum = num;
        mLimitPrice = limitPrice;
    }
    public Stock(long id, String num, String name, float min, float max) {
        mId = id;
        mNum = num;
        mName = name;
        mMin = min;
        mMax = max;
    }
    public void setMin(float min) {
        mMin = min;
    }
    public float getMin() {
        return mMin;
    }
    public void setMax(float max) {
        mMax = max;
    }
    public float getMax() {
        return mMax;
    }
    public void setId(long id) {
        mId = id;
    }
    public void setName(String name) {
        mName = name;
    }
    public String getName() {
        return mName;
    }
    public long getId() {
        return mId;
    }
    public void setNum(String num) {
        mNum = num;
    }
    public String getNum() {
        return mNum;
    }
    public void setCurrent(float current) {
        mCurrent = current;
    }
    public float getCurrent() {
        return mCurrent;
    }
    public void setLimtePrice(float price) {
        mLimitPrice = price;
    }
    public float getLimtPrices() {
        return mLimitPrice;
    }
    public void setStatus(boolean on) {
        mStatus = on;
    }
    public boolean getStatus() {
        return mStatus;
    }
    public void setLimitOption(boolean greater) {
        mLimitLess = greater;
    }
    public boolean getLimitOption() {
        return mLimitLess;
    }
    public void setUpdateTime(long date) {
        mUpdateDate = date;
    }
    public long getUpdateTime() {
        return mUpdateDate;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n mId :" + mId );
        sb.append("\n mNum :" + mNum);
        sb.append("\n name :" + mName);
        sb.append("\n current :" + mCurrent);
        return sb.toString();
    }
}
