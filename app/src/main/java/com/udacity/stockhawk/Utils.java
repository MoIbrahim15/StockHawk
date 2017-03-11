package com.udacity.stockhawk;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Mohamed Ibrahim on 3/11/2017.
 **/

public class Utils {

    public static DecimalFormat getDollarFormat() {
        return (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    }

    public static DecimalFormat getDollarFormatWithPlus() {
        DecimalFormat dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        return dollarFormatWithPlus;
    }

    public static DecimalFormat getPercentageFormat() {
        DecimalFormat percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
        return percentageFormat;
    }

}
