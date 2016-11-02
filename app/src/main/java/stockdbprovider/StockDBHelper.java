package stockdbprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by simonchung on 18/11/15.
 */
public class StockDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "StockDB.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_STOCK = "stock";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STOCK_NUM = "num";
    public static final String COLUMN_STOCK_NAME = "name";
    public static final String COLUMN_STOCK_CURRENT_PRICE = "current";
    public static final String COLUMN_STOCK_MIN = "min";
    public static final String COLUMN_STOCK_MAX = "max";
    public static final String COLUMN_STOCK_STATUS = "status";
    public static final String COLUMN_STOCK_LIMIT_OPTION = "option"; //greater or less limit price
    public static final String COLUMN_STOCK_LIMIT_PRICE = "price";
    public static final String COLUMN_STOCK_UPDATE_TIME = "update_time";
    public static final String CREATE_TABLE = "create table " + TABLE_STOCK + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_STOCK_NUM + " text not null,"
            + COLUMN_STOCK_NAME + " text not null, "
            + COLUMN_STOCK_CURRENT_PRICE + " real not null, "
            + COLUMN_STOCK_MIN + " real not null, "
            + COLUMN_STOCK_MAX + " real not null, "
            + COLUMN_STOCK_STATUS + " integer not null, "
            + COLUMN_STOCK_LIMIT_OPTION + " integer not null, "
            + COLUMN_STOCK_LIMIT_PRICE + " real not null, "
            + COLUMN_STOCK_UPDATE_TIME + " text not null )";

    public StockDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);
        onCreate(db);
    }
}
