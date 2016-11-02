package scstudio.stockreminder;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import StockDate.Stock;
import stockapi.StockApi;
import stockdbprovider.StockDataSource;
import utils.Utils;

/**UAT
 * Created by simonchung on 30/11/15.
 */
public class AddStockDialog extends DialogFragment {

    AutoCompleteTextView mNumber;
    EditText mPrice;
    TextView mName;
    TextView mOptionText;
    boolean mOption = true; //true is grater, false is less
    Handler mHandler = new Handler();
    StockDataSource mSource;
    StockArrayAdapter mAdapter;
    String[] mStockName = {"000001 上证指数", "000001 平安银行"};

    public interface AddStockListener {
        void onCompleteAddStock(Stock stock);
    }
    static AddStockDialog newInstance(){
        AddStockDialog dialog = new AddStockDialog();
        return dialog;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
        mSource = StockReminderActivity.getStockDateSource();
        mAdapter = StockReminderActivity.getStockArrayAdapter();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        View view = inflater.inflate(R.layout.fragment_dailog_stock_add, container, false);

        RelativeLayout add = (RelativeLayout)view.findViewById(R.id.stock_add_save);
        mNumber = (AutoCompleteTextView)view.findViewById(R.id.stock_add_number);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,mStockName);
        mNumber.setAdapter(adapter);
        mPrice = (EditText)view.findViewById(R.id.stock_add_price);
        mName = (TextView)view.findViewById(R.id.stock_name);
        mOptionText = (TextView)view.findViewById(R.id.stock_limit_option);
        mNumber.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("Stock", "hasFocus:" + hasFocus);
                if (!hasFocus) {
                    if(!Utils.isValidPrice(mNumber.getText().toString())) {
                        Toast.makeText(getActivity(), R.string.stock_add_invalid_input,
                                Toast.LENGTH_LONG).show();
                        return;
                    } //else if (!mName.getText().toString().equals("")){
                    new GetStockName().execute(mNumber.getText().toString());
                }
            }
        });
        RelativeLayout cancel = (RelativeLayout)view.findViewById(R.id.stock_add_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String num = mNumber.getText().toString();
                String price = mPrice.getText().toString();
                if (num.equals("") || price.equals("")) {
                    Toast.makeText(getActivity(), R.string.stock_add_invalid_input,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Utils.isValidPrice(num) && Utils.isValidPrice(price)) {
                    Toast.makeText(getActivity(), R.string.stock_add_invalid_input,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Stock stock = mSource.addItem(mNumber.getText().toString(),
                        mName.getText().toString(), 0, 0, 0, true, mOption,
                        Float.parseFloat(mPrice.getText().toString()),System.currentTimeMillis());
                mAdapter.add(stock);
                mAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        RelativeLayout limitOption = (RelativeLayout)view.findViewById(R.id.stock_limit_setting);
        limitOption.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOption){
                    mOption = false;
                    mOptionText.setText(R.string.stock_less_short);
                } else {
                    mOptionText.setText(R.string.stock_greater_short);
                    mOption = true;
                }
            }
        });
        return view;
    }
    private class GetStockName extends AsyncTask <String, Integer, Long>{

        private Stock mStock;
        private StockApi.StockApiException exception;
        @Override
        protected Long doInBackground(String... params) {
            mStock = new Stock(params[0]);
            try {
                StockApi.getStockInfo(mStock);
            }
            catch (StockApi.StockApiException excep) {
                exception = excep;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            mName.setText(mStock.getName());
            if (exception != null) {
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                mNumber.setText("");
            }
        }
    }

}
