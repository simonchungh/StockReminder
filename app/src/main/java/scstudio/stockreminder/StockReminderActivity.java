package scstudio.stockreminder;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import StockDate.Stock;
import stockdbprovider.StockDataSource;
import stockservice.StockInfoService;

public class StockReminderActivity extends AppCompatActivity implements AddStockDialog.AddStockListener{

    static StockDataSource mSource;
    static StockArrayAdapter mAdapter;
    private SwipeDetector mSwipeDetector = new SwipeDetector();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stock_reminder);
        /* init database for read*/
        mSource = new StockDataSource(this);
        mSource.open();
        /* restart stock service */
        Intent service = new Intent(this, StockInfoService.class);
        startService(service);
        ArrayList<Stock> stocks = mSource.getAllItems();
        ListView list = (ListView)findViewById(R.id.stock_list);
        mAdapter = new StockArrayAdapter(this, stocks);
        mAdapter.setListView(list);
        list.setAdapter(mAdapter);
        ImageView add = (ImageView)findViewById(R.id.stock_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_stock_reminder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void showAddDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment newFragment = AddStockDialog.newInstance();
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onCompleteAddStock(Stock stock) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSource.close();
    }

    static public StockDataSource getStockDateSource() {
        return mSource;
    }
    static public StockArrayAdapter getStockArrayAdapter(){
        return mAdapter;
    }
}
