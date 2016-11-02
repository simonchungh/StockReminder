package stockdbprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import StockDate.Stock;

/**
 * Created by simonchung on 18/11/15.
 */
public class StockDataSource {

    private StockDBHelper mStockDb;
    private SQLiteDatabase mDb;
    private String[] allColumns = {
            StockDBHelper.COLUMN_ID,
            StockDBHelper.COLUMN_STOCK_NAME,
            StockDBHelper.COLUMN_STOCK_CURRENT_PRICE,
            StockDBHelper.COLUMN_STOCK_MIN,
            StockDBHelper.COLUMN_STOCK_MAX,
            StockDBHelper.COLUMN_STOCK_STATUS,
            StockDBHelper.COLUMN_STOCK_LIMIT_OPTION,
            StockDBHelper.COLUMN_STOCK_LIMIT_PRICE,
            StockDBHelper.COLUMN_STOCK_UPDATE_TIME};
    public StockDataSource(Context context) {
        mStockDb = new StockDBHelper(context);
    }
    public void open() {
        mDb =mStockDb.getWritableDatabase();
        Log.d("Stock", "mDb=" + mDb);
    }
    public void close() {
        mStockDb.close();
    }
    public Stock addItem(String num, String name, float current, float min, float max,
                         boolean status, boolean option, float price, long date) {
        ContentValues value = new ContentValues();
        value.put(StockDBHelper.COLUMN_STOCK_NUM, num);
        value.put(StockDBHelper.COLUMN_STOCK_NAME, name);
        value.put(StockDBHelper.COLUMN_STOCK_CURRENT_PRICE, current);
        value.put(StockDBHelper.COLUMN_STOCK_MIN, min);
        value.put(StockDBHelper.COLUMN_STOCK_MAX, max);
        value.put(StockDBHelper.COLUMN_STOCK_STATUS, status);
        value.put(StockDBHelper.COLUMN_STOCK_LIMIT_OPTION, option);
        value.put(StockDBHelper.COLUMN_STOCK_LIMIT_PRICE, price);
        value.put(StockDBHelper.COLUMN_STOCK_UPDATE_TIME, date);
        long id = mDb.insert(StockDBHelper.TABLE_STOCK, null, value);
        Stock stock = new Stock();
        stock.setId(id);
        stock.setNum(num);
        stock.setName(name);
        stock.setMax(max);
        stock.setMin(min);
        stock.setStatus(status);
        stock.setCurrent(current);
        stock.setLimitOption(option);
        stock.setLimtePrice(price);
        stock.setUpdateTime(date);
        return stock;
    }
    public void deleteItem(Stock stock) {
        mDb.delete(StockDBHelper.TABLE_STOCK, StockDBHelper.COLUMN_ID + "=" + stock.getId(), null);
    }
    public boolean updateItem(Stock stock) {
        ContentValues value = new ContentValues();
        value.put(StockDBHelper.COLUMN_STOCK_NUM, stock.getNum());
        value.put(StockDBHelper.COLUMN_STOCK_NAME, stock.getName());
        value.put(StockDBHelper.COLUMN_STOCK_CURRENT_PRICE, stock.getCurrent());
        value.put(StockDBHelper.COLUMN_STOCK_MIN, stock.getMin());
        value.put(StockDBHelper.COLUMN_STOCK_MAX, stock.getMax());
        value.put(StockDBHelper.COLUMN_STOCK_STATUS, stock.getStatus());
        value.put(StockDBHelper.COLUMN_STOCK_LIMIT_OPTION, stock.getLimitOption());
        value.put(StockDBHelper.COLUMN_STOCK_LIMIT_PRICE, stock.getLimtPrices());
        value.put(StockDBHelper.COLUMN_STOCK_UPDATE_TIME, stock.getUpdateTime());
        return mDb.update(StockDBHelper.TABLE_STOCK,value, StockDBHelper.COLUMN_ID + "=" + stock.getId(),
                null) > 0;
    }
    public ArrayList getAllItems() {
        Log.d("Stock", "mDb=" + mDb);

        ArrayList list = new ArrayList<Stock>();
        Cursor cursor = mDb.query(StockDBHelper.TABLE_STOCK, null, null, null, null, null,
                null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Stock stock = new Stock();
            int index = cursor.getColumnIndex(StockDBHelper.COLUMN_ID);
            stock.setId(cursor.getLong(index));
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_NUM);
            Log.d("Stock", "index=" + index);
            stock.setNum(cursor.getString(index));
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_NAME);
            stock.setName(cursor.getString(index));
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_CURRENT_PRICE);
            stock.setCurrent(cursor.getFloat(index));
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_MIN);
            stock.setMin(cursor.getFloat(index));
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_MAX);
            stock.setMax(cursor.getFloat(index));
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_STATUS);
            stock.setStatus(cursor.getInt(index) != 0);
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_LIMIT_OPTION);
            stock.setLimitOption(cursor.getInt(index) != 0);
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_LIMIT_PRICE);
            stock.setLimtePrice(cursor.getFloat(index));
            index = cursor.getColumnIndex(StockDBHelper.COLUMN_STOCK_UPDATE_TIME);
            stock.setUpdateTime(cursor.getLong(index));
            list.add(stock);
            cursor.moveToNext();
        }
        return list;
    }
}
