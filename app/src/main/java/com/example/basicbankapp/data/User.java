package com.example.basicbankapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.versionedparcelable.VersionedParcelize;

public class User  implements Parcelable{
    private String name;
    private int balance;
    private int accountNumber;
    private String phoneNumber;
    private String ifscCode;
    private String email;

    public User(String name, int balance, int accountNumber, String phoneNumber, String ifscCode, String email) {
        this.name = name;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.phoneNumber = phoneNumber;
        this.ifscCode = ifscCode;
        this.email = email;
    }

    public User(String name, int balance, int accountNumber) {
        this.name = name;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    protected User(Parcel in) {
        name = in.readString();
        balance = in.readInt();
        accountNumber = in.readInt();
        phoneNumber = in.readString();
        ifscCode = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(balance);
        parcel.writeInt(accountNumber);
        parcel.writeString(phoneNumber);
        parcel.writeString(ifscCode);
        parcel.writeString(email);
    }
}
