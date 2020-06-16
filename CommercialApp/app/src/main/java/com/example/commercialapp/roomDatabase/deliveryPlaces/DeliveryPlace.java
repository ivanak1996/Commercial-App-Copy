package com.example.commercialapp.roomDatabase.deliveryPlaces;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "deliveryPlace_table")
public class DeliveryPlace {

    @PrimaryKey(autoGenerate = true)
    private long rowId;
    private long userRowId;

    private String acName2;
    private String acAddress;
    private String acCity;
    private String anQId;
    private String acPost;
    private String acSubject;

    public DeliveryPlace(long userRowId, String acName2, String acAddress, String acCity, String anQId, String acPost, String acSubject) {
        this.userRowId = userRowId;
        this.acName2 = acName2;
        this.acAddress = acAddress;
        this.acCity = acCity;
        this.anQId = anQId;
        this.acPost = acPost;
        this.acSubject = acSubject;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    // region getters

    public long getRowId() {
        return rowId;
    }

    public long getUserRowId() {
        return userRowId;
    }

    public String getAcName2() {
        return acName2;
    }

    public String getAcAddress() {
        return acAddress;
    }

    public String getAcCity() {
        return acCity;
    }

    public String getAnQId() {
        return anQId;
    }

    public String getAcPost() {
        return acPost;
    }

    public String getAcSubject() {
        return acSubject;
    }

    // endregion


    @NonNull
    @Override
    public String toString() {
        return acName2;
    }
}
