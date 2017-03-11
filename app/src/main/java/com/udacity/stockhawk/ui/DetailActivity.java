package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.udacity.stockhawk.ui.MainActivity.STOCK_HISTORY;
import static com.udacity.stockhawk.ui.MainActivity.STOCK_SYMBOL;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.line_chart)
    LineChart lineChart;
    private String stockHistory;
    private String stockSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        stockHistory = intent.getStringExtra(STOCK_HISTORY);
        stockSymbol = intent.getStringExtra(STOCK_SYMBOL);

        String history_ins[] = stockHistory.split("\\r?\\n");
        for (String raw : history_ins) {
            Timber.d(raw);
        }

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
//        lineChart.getax
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
//        dataSet.setValueTextColor(...); // styling, ...
    }

}