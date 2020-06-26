package com.example.commercialapp.models.orderHistoryModels;

import java.text.DecimalFormat;

public class ProductHistoryModel {

    private String acIdent;
    private String anPrice;
    private String acName;
    private String anNo;
    private String anQty;
    private String anVat;
    private String acUM;
    private String acKey;
    private String anRebate;

    private String total;

    public ProductHistoryModel(String acIdent, String anPrice, String anNo, String acName, String anQty, String anVat, String acUM, String acKey, String anRebate) {
        this.acIdent = acIdent;
        this.anPrice = anPrice;
        this.anNo = anNo;
        this.acName = acName;
        this.anQty = anQty;
        this.anVat = anVat;
        this.acUM = acUM;
        this.acKey = acKey;
        this.anRebate = anRebate;

        calculateTotal();
        formatDecimals();
    }


    public String getAcIdent() {
        return acIdent;
    }

    public String getAnPrice() {
        return anPrice;
    }

    public String getAcName() {
        return acName;
    }

    public String getAnNo() {
        return anNo;
    }

    public String getAnQty() {
        return anQty;
    }

    public String getAnVat() {
        return anVat;
    }

    public String getAcUM() {
        return acUM;
    }

    public String getTotal() {
        return total;
    }

    private void calculateTotal() {
        //double totalPrice = kolcina*cena*(1-anrebat/100)*(1+anVat/100);
        double kolcina = Double.parseDouble(anQty);
        double cena = Double.parseDouble(anPrice);
        double anrebat = Double.parseDouble(anRebate);
        double anVatt = Double.parseDouble(anVat);
        double totalPrice = kolcina * cena * (1 - anrebat / 100) * (1 + anVatt / 100);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        total = "" + formatter.format(totalPrice);
    }

    private void formatDecimals() {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        anPrice = "" + formatter.format(Double.parseDouble(anPrice));
        anQty = "" + formatter.format(Double.parseDouble(anQty));
    }
}
