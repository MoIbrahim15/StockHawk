package com.udacity.stockhawk.widget;

/**
 * Created by Mohamed Ibrahim
 * on 3/23/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

/**
 * StockWidgetFactory acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class StockWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor mCursor;
    private Context mContext;

    private final String[] QUOTE_COLUMNS = {
            Contract.Quote._ID,
            Contract.Quote.COLUMN_SYMBOL,
            Contract.Quote.COLUMN_PRICE,
            Contract.Quote.COLUMN_ABSOLUTE_CHANGE,
            Contract.Quote.COLUMN_PERCENTAGE_CHANGE,
            Contract.Quote.COLUMN_HISTORY
    };

    //    public static final int POSITION_ID = 0;
    private static final int POSITION_SYMBOL = 1;
    private static final int POSITION_PRICE = 2;
    //    public static final int POSITION_ABSOLUTE_CHANGE = 3;
    private static final int POSITION_PERCENTAGE_CHANGE = 4;
//    public static final int POSITION_HISTORY = 5;


    public StockWidgetFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        mCursor.moveToPosition(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);
        String symbol = mCursor.getString(POSITION_SYMBOL);
        Double price = 0.1 * mCursor.getDouble(POSITION_PRICE);
//        Double abs_change = 0.1 * mCursor.getDouble(POSITION_ABSOLUTE_CHANGE);
        Double per_change = 0.1 * mCursor.getDouble(POSITION_PERCENTAGE_CHANGE);
        views.setTextViewText(R.id.symbol, symbol);
        views.setTextViewText(R.id.price, "$" + String.format("%.2f", price));
        if (per_change < 0) {
            views.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
            views.setTextViewText(R.id.change, String.format("%.2f", per_change) + "%");
        } else {
            views.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
            views.setTextViewText(R.id.change, "+" + String.format("%.2f", per_change) + "%");
        }


        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(Contract.Quote.URI, QUOTE_COLUMNS, null, null, null);
    }

}