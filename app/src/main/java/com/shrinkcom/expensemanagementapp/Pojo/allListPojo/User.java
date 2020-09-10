
package com.shrinkcom.expensemanagementapp.Pojo.allListPojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{

    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("list_id")
    @Expose
    private Integer list_id;

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
    private Integer userId;
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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose

    private String company;
    @SerializedName("company")
    @Expose

    private String type_name;
    @SerializedName("type_name")
    @Expose
    private String image;
    private final static long serialVersionUID = -764729421607599489L;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getList_id() {
        return list_id;
    }

    public void setList_id(Integer list_id) {
        this.list_id = list_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}


