package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.udacity.stockhawk.ui.HomeActivity.STOCK_ABS_CHANGE;
import static com.udacity.stockhawk.ui.HomeActivity.STOCK_HISTORY;
import static com.udacity.stockhawk.ui.HomeActivity.STOCK_PER_CHANGE;
import static com.udacity.stockhawk.ui.HomeActivity.STOCK_PRICE;
import static com.udacity.stockhawk.ui.HomeActivity.STOCK_SYMBOL;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_abs_change)
    TextView tvAbsChange;
    @BindView(R.id.tv_per_change)
    TextView tvPerChange;

    private String stockHistory;
    private String stockSymbol;
    private Float stockPrice;
    private Float stockAbsChange;
    private Float stockPerChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        fillViews();
    }

    private void fillViews() {
        Intent intent = getIntent();
        stockHistory = intent.getStringExtra(STOCK_HISTORY);
        stockSymbol = intent.getStringExtra(STOCK_SYMBOL);
        stockPrice = intent.getFloatExtra(STOCK_PRICE, 0f);
        stockAbsChange = intent.getFloatExtra(STOCK_ABS_CHANGE, 0f);
        stockPerChange = intent.getFloatExtra(STOCK_PER_CHANGE, 0f);

        if (stockAbsChange > 0) {
            tvAbsChange.setBackgroundResource(R.drawable.percent_change_pill_green);
        } else {
            tvAbsChange.setBackgroundResource(R.drawable.percent_change_pill_red);
        }
        if (stockPerChange > 0) {
            tvPerChange.setBackgroundResource(R.drawable.percent_change_pill_green);
        } else {
            tvPerChange.setBackgroundResource(R.drawable.percent_change_pill_red);
        }
        tvAbsChange.setText(Utils.getDollarFormatWithPlus().format(stockAbsChange));
        tvPerChange.setText(Utils.getPercentageFormat().format(stockPerChange));
        tvPrice.setText(Utils.getDollarFormat().format(stockPrice));


        String history_ins[] = stockHistory.split("\\r?\\n");
//        for (String raw : history_ins) {
//            Timber.d(raw);
//        }
        initChart(history_ins);
    }


    private void initChart(String[] history) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < history.length; i++) {
            String pair[] = history[i].split(", ");
            float price = Float.parseFloat(pair[1]);
            entries.add(new Entry(i, price));
        }

        LineDataSet dataSet = new LineDataSet(entries, stockSymbol);
        dataSet.setColor(ContextCompat.getColor(this, android.R.color.white));

        LineData lineData = new LineData(dataSet);
        lineData.setValueTextColor(android.R.color.white);

        Description description = new Description();
        description.setText(getString(R.string.history_desc));
        lineChart.setDescription(description);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
    }

}