
package com.shrinkcom.expensemanagementapp.Pojo.syncPojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable, Parcelable
{

    @SerializedName("category")
    @Expose
    private java.util.List<Category> category = null;
    @SerializedName("payment")
    @Expose
    private java.util.List<Payment> payment = null;
    @SerializedName("transaction")
    @Expose
    private java.util.List<Transaction> transaction = null;
    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;
    @SerializedName("user")
    @Expose
    private java.util.List<User> user = null;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = -7712112878890352926L;

    protected Data(Parcel in) {
        in.readList(this.category, (Category.class.getClassLoader()));
        in.readList(this.payment, (Payment.class.getClassLoader()));
        in.readList(this.transaction, (Transaction.class.getClassLoader()));
        in.readList(this.list, (List.class.getClassLoader()));
        in.readList(this.user, (User.class.getClassLoader()));
    }

    public Data() {
    }

    public java.util.List<Category> getCategory() {
        return category;
    }

    public void setCategory(java.util.List<Category> category) {
        this.category = category;
    }

    public java.util.List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(java.util.List<Payment> payment) {
        this.payment = payment;
    }

    public java.util.List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(java.util.List<Transaction> transaction) {
        this.transaction = transaction;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public java.util.List<User> getUser() {
        return user;
    }

    public void setUser(java.util.List<User> user) {
        this.user = user;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(category);
        dest.writeList(payment);
        dest.writeList(transaction);
        dest.writeList(list);
        dest.writeList(user);
    }

    public int describeContents() {
        return  0;
    }

}
