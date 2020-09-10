
package com.shrinkcom.expensemanagementapp.Pojo.fileListPojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable
{

    @SerializedName("list_id")
    @Expose
    private Integer listId;
    @SerializedName("list_name")
    @Expose
    private String listName;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("date")
    @Expose
    private String date;
    private final static long serialVersionUID = -5874350163176608506L;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
