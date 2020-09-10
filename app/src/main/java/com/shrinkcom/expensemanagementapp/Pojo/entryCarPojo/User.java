
package com.shrinkcom.expensemanagementapp.Pojo.entryCarPojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{

    @SerializedName("car_id")
    @Expose
    private Integer carId;
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

}
