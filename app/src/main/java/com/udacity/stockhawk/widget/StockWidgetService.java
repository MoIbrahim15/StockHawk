package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Mohamed Ibrahim on 3/13/2017.
 **/

public class StockWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockWidgetFactory(this);
    }
}
