
package com.shrinkcom.expensemanagementapp.Pojo.totalamountPojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment implements Serializable
{

    @SerializedName("total")
    @Expose
    private Integer total;
    private final static long serialVersionUID = 7041013356102494339L;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
