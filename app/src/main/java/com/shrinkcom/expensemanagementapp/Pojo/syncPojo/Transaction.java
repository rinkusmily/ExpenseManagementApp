
package com.shrinkcom.expensemanagementapp.Pojo.syncPojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaction implements Serializable, Parcelable
{

    @SerializedName("trans_id")
    @Expose
    private Integer transId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("list_id")
    @Expose
    private Integer listId;
    public final static Creator<Transaction> CREATOR = new Creator<Transaction>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        public Transaction[] newArray(int size) {
            return (new Transaction[size]);
        }

    }
    ;
    private final static long serialVersionUID = 4582255590239625630L;

    protected Transaction(Parcel in) {
        this.transId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentId = ((String) in.readValue((String.class.getClassLoader())));
        this.listId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Transaction() {
    }

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(transId);
        dest.writeValue(userId);
        dest.writeValue(price);
        dest.writeValue(type);
        dest.writeValue(createdAt);
        dest.writeValue(paymentId);
        dest.writeValue(listId);
    }

    public int describeContents() {
        return  0;
    }

}
