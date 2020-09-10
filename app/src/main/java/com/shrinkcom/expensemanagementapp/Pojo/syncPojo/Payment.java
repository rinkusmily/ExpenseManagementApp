
package com.shrinkcom.expensemanagementapp.Pojo.syncPojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payment implements Serializable, Parcelable
{

    @SerializedName("pay_id")
    @Expose
    private Integer payId;
    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("plate_no")
    @Expose
    private String plateNo;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("finished_status")
    @Expose
    private Integer finishedStatus;
    @SerializedName("paidout_status")
    @Expose
    private Integer paidoutStatus;
    @SerializedName("invoice_status")
    @Expose
    private Integer invoiceStatus;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("list_id")
    @Expose
    private Integer listId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("company")
    @Expose
    private String company;
    public final static Creator<Payment> CREATOR = new Creator<Payment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        public Payment[] newArray(int size) {
            return (new Payment[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6553949929404050745L;

    protected Payment(Parcel in) {
        this.payId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.paymentId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.note = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.plateNo = ((String) in.readValue((String.class.getClassLoader())));
        this.model = ((String) in.readValue((String.class.getClassLoader())));
        this.finishedStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.paidoutStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.invoiceStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.icon = ((String) in.readValue((String.class.getClassLoader())));
        this.listId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.company = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Payment() {
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getFinishedStatus() {
        return finishedStatus;
    }

    public void setFinishedStatus(Integer finishedStatus) {
        this.finishedStatus = finishedStatus;
    }

    public Integer getPaidoutStatus() {
        return paidoutStatus;
    }

    public void setPaidoutStatus(Integer paidoutStatus) {
        this.paidoutStatus = paidoutStatus;
    }

    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(payId);
        dest.writeValue(paymentId);
        dest.writeValue(price);
        dest.writeValue(date);
        dest.writeValue(categoryId);
        dest.writeValue(note);
        dest.writeValue(userId);
        dest.writeValue(type);
        dest.writeValue(createdAt);
        dest.writeValue(plateNo);
        dest.writeValue(model);
        dest.writeValue(finishedStatus);
        dest.writeValue(paidoutStatus);
        dest.writeValue(invoiceStatus);
        dest.writeValue(icon);
        dest.writeValue(listId);
        dest.writeValue(name);
        dest.writeValue(company);
    }

    public int describeContents() {
        return  0;
    }

}
