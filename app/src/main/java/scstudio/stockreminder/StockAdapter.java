package scstudio.stockreminder;

import android.app.ActionBar;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import StockDate.Stock;

import static android.view.ViewGroup.*;

/**
 * Created by simonchung on 28/11/15.
 */
class StockArrayAdapter extends ArrayAdapter<Stock>{
    private ArrayList<Stock> mStocks;
    private Context mContext;
    private ListView mListView;

    public StockArrayAdapter(Context context, ArrayList<Stock> stocks) {
        super(context, -1, stocks);
        mContext = context;
        mStocks = stocks;
    }
    public void setListView(ListView listView) {
        mListView = listView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("Stock", "position=" + position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.
                LAYOUT_INFLATER_SERVICE);
        View stockView;
        Stock stock = mStocks.get(position);
        //if (convertView == null) {
            stockView = inflater.inflate(R.layout.stock_item, parent, false);

        //} else {
        //    stockView = convertView;
        //}
        LinearLayout info = (LinearLayout)stockView.findViewById(R.id.stock_info);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)info.getLayoutParams();
        params.rightMargin = 0;
        params.leftMargin = 0;
        info.setLayoutParams(params);
        stockView.setOnTouchListener(new SwipeDetector(stockView,position));
        TextView name = (TextView)stockView.findViewById(R.id.stock_name);
        TextView option = (TextView)stockView.findViewById(R.id.stock_limit_option);
        TextView price = (TextView)stockView.findViewById(R.id.stock_limit_price);
        TextView date = (TextView)stockView.findViewById(R.id.stock_date);
        ImageView status = (ImageView)stockView.findViewById(R.id.stock_status);
        name.setText(stock.getName() + " (" + stock.getNum() + ")");
        if (stock.getLimitOption()) {
            option.setText(R.string.stock_greater);
        } else {
            option.setText(R.string.stock_less);
        }
        if(stock.getStatus()){
            status.setImageResource(R.drawable.ic_alarm_on_black_24dp);
        } else {
            status.setImageResource(R.drawable.ic_alarm_off_black_24dp);

        }
        price.setText(String.valueOf(stock.getLimtPrices()));
        Date time = new Date(stock.getUpdateTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date.setText(format.format(time));
        return stockView;
    }

    @Override
    public int getCount() {

        return mStocks.size();
    }

    public class SwipeDetector implements View.OnTouchListener {

        private static final int MIN_DISTANCE = 300;
        private static final int MIN_LOCK_DISTANCE = 30; // disallow motion intercept
        private boolean motionInterceptDisallowed = false;
        private float downX, upX;
        private View mainView;
        private int position;

        public SwipeDetector(View main, int pos) {
            mainView = main;
            position = pos;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RelativeLayout delete = (RelativeLayout)mainView.findViewById(R.id.stock_delete);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    return true; // allow other events like Click to be processed
                }

                case MotionEvent.ACTION_MOVE: {
                    upX = event.getX();
                    float deltaX = downX - upX;

                    if (Math.abs(deltaX) > MIN_LOCK_DISTANCE && mListView != null &&
                            !motionInterceptDisallowed) {
                        mListView.requestDisallowInterceptTouchEvent(true);
                        motionInterceptDisallowed = true;
                    }

                    if (deltaX > 0) {
                        delete.setVisibility(View.GONE);
                    } else {
                        // if first swiped left and then swiped right
                        delete.setVisibility(View.VISIBLE);
                    }
                //}
                    swipe(-(int) deltaX);
                    return true;
                }

                case MotionEvent.ACTION_UP:
                    upX = event.getX();
                    float deltaX = upX - downX;
                    Log.i("Stock", "deltax=" + deltaX);
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // left or right
                        swipeRemove();
                        delete.setVisibility(View.VISIBLE);
                    } else {
                        swipe(0);
                       // swipe(delete.getWidth());
                        delete.setVisibility(View.INVISIBLE);

                    }

                    if (mListView != null) {
                        mListView.requestDisallowInterceptTouchEvent(false);
                        motionInterceptDisallowed = false;
                    }

                    //delete.setVisibility(View.VISIBLE);
                    return true;

                case MotionEvent.ACTION_CANCEL:
                    //delete.setVisibility(View.VISIBLE);
                    return false;
            }

            return true;
        }

        private void swipe(int distance) {
            LinearLayout info = (LinearLayout) mainView.findViewById(R.id.stock_info);
            FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) info.getLayoutParams();
            //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) info.getLayoutParams();
            params.rightMargin = -distance;
            params.leftMargin = distance;
            info.setLayoutParams(params);
        }

        private void swipeRemove() {
            Log.i("Stock", "remove " + position);
            //mStocks.remove(position);
            StockReminderActivity.getStockDateSource().deleteItem(mStocks.get(position));
            remove(getItem(position));
            notifyDataSetChanged();
        }
    }
}

