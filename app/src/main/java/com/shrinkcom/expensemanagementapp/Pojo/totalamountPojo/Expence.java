
package com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expence implements Serializable
{

    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = 8052690262619101067L;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
