
package com.shrinkcom.expensemanagementapp.Pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleLoginPojo implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user")
    @Expose
    private List<UserGoogle> user = new ArrayList<UserGoogle>();
    private final static long serialVersionUID = -2432053551215444129L;

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

    public List<UserGoogle> getUser() {
        return user;
    }

    public void setUser(List<UserGoogle> user) {
        this.user = user;
    }

}
