
package com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Total implements Serializable
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
    private Integer total;
    private final static long serialVersionUID = 5165317912295813964L;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
