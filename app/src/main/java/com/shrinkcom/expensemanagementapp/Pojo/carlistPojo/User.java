
package com.shrinkcom.expensemanagementapp.Pojo.carlistPojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{

    @SerializedName("payment_id")
    @Expose
    private Integer carId;

    @SerializedName("category_id")
    @Expose
    private Integer category_id;



    @SerializedName("list_id")
    @Expose
    private Integer list_id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("plate_no")
    @Expose
    private String plateNo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("finished_status")
    @Expose
    private Integer finishedStatus;
    @SerializedName("paidout_status")
    @Expose
    private Integer paidoutStatus;
    @SerializedName("invoice_status")
    @Expose
    private Integer invoiceStatus;


    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("category_id")
    @Expose
    private String category;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("type_name")
    @Expose
    private String type_name;

    private final static long serialVersionUID = 8671569371284122624L;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
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


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getList_id() {
        return list_id;
    }

    public void setList_id(Integer list_id) {
        this.list_id = list_id;
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
