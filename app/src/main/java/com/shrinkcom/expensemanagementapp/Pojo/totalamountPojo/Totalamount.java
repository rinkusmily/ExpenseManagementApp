
package com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Totalamount implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("taking")
    @Expose
    private List<Taking> taking = new ArrayList<Taking>();
    @SerializedName("expences")
    @Expose
    private List<Expence> expences = new ArrayList<Expence>();
    @SerializedName("payment")
    @Expose
    private List<Payment> payment = new ArrayList<Payment>();
    @SerializedName("total")
    @Expose
    private List<Total> total = new ArrayList<Total>();
    @SerializedName("total_repaired_car")
    @Expose
    private Integer totalRepairedCar;
    private final static long serialVersionUID = -44304432574680720L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Taking> getTaking() {
        return taking;
    }

    public void setTaking(List<Taking> taking) {
        this.taking = taking;
    }

    public List<Expence> getExpences() {
        return expences;
    }

    public void setExpences(List<Expence> expences) {
        this.expences = expences;
    }

    public List<Payment> getPayment() {
        return payment;
    }

    public void setPayment(List<Payment> payment) {
        this.payment = payment;
    }

    public List<Total> getTotal() {
        return total;
    }

    public void setTotal(List<Total> total) {
        this.total = total;
    }

    public Integer getTotalRepairedCar() {
        return totalRepairedCar;
    }

    public void setTotalRepairedCar(Integer totalRepairedCar) {
        this.totalRepairedCar = totalRepairedCar;
    }

}






