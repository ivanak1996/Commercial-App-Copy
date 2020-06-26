package com.example.commercialapp.models.orderHistoryModels;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderHistoryModel {
    private String acReceiver;
    private String adDate;
    private String anForPay;
    private String acKey;

    public OrderHistoryModel(String acReceiver, String adDate, String anForPay, String acKey) {
        this.acReceiver = acReceiver;
        this.adDate = adDate;
        this.anForPay = anForPay;
        this.acKey = acKey;

        formatDate();
        formatForPay();
    }

    private void formatDate() {
        String pattern = "dd.MM.yyyy.";

        String strDate = adDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS.ss");
        try {
            Date convertedDate = dateFormat.parse(strDate);
            SimpleDateFormat sdfnewformat = new SimpleDateFormat(pattern);
            adDate = sdfnewformat.format(convertedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getAcReceiver() {
        return acReceiver;
    }

    public String getAdDate() {
        return adDate;
    }

    public String getAnForPay() {
        return anForPay;
    }

    public String getAcKey() {
        return acKey;
    }

    private void formatForPay() {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        anForPay = "" + formatter.format(Double.parseDouble(anForPay));
    }
}
