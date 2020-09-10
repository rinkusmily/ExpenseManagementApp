package com.shrinkcom.expensemanagementapp.model;

public class PaymentListModel {

    String  tv_date=""  ,tv_vehicle_name="" , note="" ,tv_paid_out="" , payment_status ="";


    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_date = tv_date;
    }

    public String getTv_vehicle_name() {
        return tv_vehicle_name;
    }

    public void setTv_vehicle_name(String tv_vehicle_name) {
        this.tv_vehicle_name = tv_vehicle_name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTv_paid_out() {
        return tv_paid_out;
    }

    public void setTv_paid_out(String tv_paid_out) {
        this.tv_paid_out = tv_paid_out;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}
