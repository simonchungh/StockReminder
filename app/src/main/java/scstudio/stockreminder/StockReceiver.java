package scstudio.stockreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import stockservice.StockInfoService;

/**
 * Created by simonchung on 26/11/15.
 */
public class StockReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Stock", "recieve");
        Intent service = new Intent(context, StockInfoService.class);
        context.startService(service);
    }
}
