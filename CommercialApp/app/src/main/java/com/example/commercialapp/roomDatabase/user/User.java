package com.example.commercialapp.roomDatabase.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user_table")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long rowId;

    private String acName2;
    private String acAddress;
    private String acRegNo;
    private String acSubject;
    private String password;
    private String anDoeplo;
    private String acCity;
    private String anNedospelo;
    private String acCode;
    private String id;
    private String acClerk;
    private String acPost;
    private String email;
    private String anLimit;

    public User(String acName2, String acAddress, String acRegNo, String acSubject, String password, String anDoeplo, String acCity, String anNedospelo, String acCode, String id, String acClerk, String acPost, String email, String anLimit) {
        this.acName2 = acName2;
        this.acAddress = acAddress;
        this.acRegNo = acRegNo;
        this.acSubject = acSubject;
        this.password = password;
        this.anDoeplo = anDoeplo;
        this.acCity = acCity;
        this.anNedospelo = anNedospelo;
        this.acCode = acCode;
        this.id = id;
        this.acClerk = acClerk;
        this.acPost = acPost;
        this.email = email;
        this.anLimit = anLimit;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    // region getters

    public long getRowId() {
        return rowId;
    }

    public String getAcName2() {
        return acName2;
    }

    public String getAcAddress() {
        return acAddress;
    }

    public String getAcRegNo() {
        return acRegNo;
    }

    public String getAcSubject() {
        return acSubject;
    }

    public String getPassword() {
        return password;
    }

    public String getAnDoeplo() {
        return anDoeplo;
    }

    public String getAcCity() {
        return acCity;
    }

    public String getAnNedospelo() {
        return anNedospelo;
    }

    public String getAcCode() {
        return acCode;
    }

    public String getId() {
        return id;
    }

    public String getAcClerk() {
        return acClerk;
    }

    public String getAcPost() {
        return acPost;
    }

    public String getEmail() {
        return email;
    }

    public String getAnLimit() {
        return anLimit;
    }

    // endregion
}
