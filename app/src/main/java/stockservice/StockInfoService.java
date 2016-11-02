package stockservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import StockDate.Stock;
import scstudio.stockreminder.R;
import scstudio.stockreminder.StockReminderActivity;
import stockapi.StockApi;
import stockdbprovider.StockDataSource;

/**
 * Created by simonchung on 25/11/15.
 */
public class StockInfoService extends Service {
    private static String TAG = "Stock";
    private NotificationManager mNM;
    private int NOTIFICATION = R.string.app_name;
    private Timer mTimer = new Timer(true);
    private StockDataSource mStockDataSource;

    private TimerTask mGetStockInfoTask = new TimerTask() {
        @Override
        public void run() {
            Log.d(TAG, "timeout");
            ArrayList<Stock> list = mStockDataSource.getAllItems();
            for (Stock stock: list) {
                /* Get latest stock price */
                Log.d(TAG, stock.getNum() + ":" + stock.getCurrent() + ":" + stock.getStatus());
                try {
                    StockApi.getStockInfo(stock);
                } catch (StockApi.StockApiException exception) {
                    Log.d(TAG, "Not found stock:" + stock.getNum());
                }
                Log.d(TAG, stock.getNum() + ":" + stock.getCurrent() + ":" + stock.getStatus());
                if (stock.getLimitOption() && (stock.getCurrent() > stock.getLimtPrices())) {
                    if (stock.getStatus() == true) {
                        showNotification(stock.getName() + "(" + stock.getNum() + ")" + "\n"
                                            + getResources().getString(R.string.stock_price) + " "
                                            + getResources().getString(R.string.stock_greater) + " "
                                            + stock.getCurrent());
                        stock.setStatus(false);
                        stock.setUpdateTime(System.currentTimeMillis());
                        mStockDataSource.updateItem(stock);
                    }
                } else if (!stock.getLimitOption() &&
                        (stock.getCurrent() < stock.getLimtPrices())) {
                    if (stock.getStatus() == true) {
                        showNotification(stock.getName() + "(" + stock.getNum() + ")" + "\n"
                                + getResources().getString(R.string.stock_price) + " "
                                + getResources().getString(R.string.stock_less) + " "
                                + stock.getCurrent());
                        stock.setStatus(false);
                        stock.setUpdateTime(System.currentTimeMillis());
                        mStockDataSource.updateItem(stock);
                    }
                }
            }
        }
    };
    @Override
    public void onCreate() {
        Context context = getApplicationContext();
        mStockDataSource = new StockDataSource(context);
        mStockDataSource.open();
        mTimer.schedule(mGetStockInfoTask, 0, 10000);//every 10s
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Log.d(TAG, "start service");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void showNotification(String msg) {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        //CharSequence text = getText(R.string.app_name);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, StockReminderActivity.class), 0);

        // Set Audio
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)  // the status icon
                .setTicker(getResources().getText(R.string.app_name))  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getText(R.string.app_name))  // the label of the entry
                .setContentText(msg)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .setSound(alarmSound) // Set notification sound
                .build();

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }
}
