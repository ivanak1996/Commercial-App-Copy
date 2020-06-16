package com.example.commercialapp.roomDatabase.orders;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "orders_table")
public class Order {

    public static final int STATUS_OPEN = 0;
    public static final int STATUS_SENT = 1;
    public static final int STATUS_COMPLETE = 2;

    @PrimaryKey(autoGenerate = true)
    private long rowId;

    private int status;
    private Date date;
    private int orderNumber;

    public Order(int status, Date date) {
        this.status = status;
        this.date = date;
    }

    // region setters

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    // endregion

    // region getters

    public long getRowId() {
        return rowId;
    }

    public int getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    // endregion
}
