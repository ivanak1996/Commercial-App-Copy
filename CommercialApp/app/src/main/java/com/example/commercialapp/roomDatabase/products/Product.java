package com.example.commercialapp.roomDatabase.products;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

@Entity(tableName = "products_table", indices = {@Index(value = {"orderRowId", "b"}, unique = true)})
public class Product {
    @PrimaryKey(autoGenerate = true)
    private long rowId;

    private double quantity;
    private long orderRowId;

    private String a; //id,
    private String b; //sifra prozivda
    private String c; //naziv
    private String d; //grupa proizvoda
    private String e; //jedinica mere
    private String f; //barkod
    private String v; //pdv stopa
    private String p; //cena
    private String r; //rabat
    private String i; //url do slike

    public Product(long orderRowId, double quantity, String a, String b, String c, String d, String e, String f, String v, String p, String r, String i) {
        this.orderRowId = orderRowId;
        this.quantity = quantity;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.v = v;
        this.p = p;
        this.r = r;
        this.i = i;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public void setOrderRowId(long orderRowId) {
        this.orderRowId = orderRowId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // region getters


    public long getRowId() {
        return rowId;
    }

    public long getOrderRowId() {
        return orderRowId;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getQuantityInt() {
        return (int) quantity;
    }

    public String getQuantityAsString() {
        double quantity = getQuantity();
        int quantityDecimal = getQuantityInt();
        if (quantity - quantityDecimal > 0.00) {
            return "" + round(quantity, 2);
        } else {
            return "" + getQuantityInt();
        }
    }

    public double calcPriceWithRabat() {
        double price = Double.parseDouble(p);
        double rabat = Double.parseDouble(r);
        double pdv = Double.parseDouble(v);
        return (price * (1 - rabat / 100)) * (1 + pdv / 100) * quantity;
    }

    public String calcPriceWithRabatAsString() {
        double price = Double.parseDouble(p);
        double rabat = Double.parseDouble(r);
        double pdv = Double.parseDouble(v);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return "" + formatter.format(round((price * (1 - rabat / 100)) * (1 + pdv / 100) * quantity, 2)) + " RSD";
    }

    public double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public String getE() {
        return e;
    }

    public String getF() {
        return f;
    }

    public String getV() {
        return v;
    }

    public String getP() {
        return p;
    }

    public String getR() {
        return r;
    }

    public String getI() {
        return i;
    }

    // endregion
}
