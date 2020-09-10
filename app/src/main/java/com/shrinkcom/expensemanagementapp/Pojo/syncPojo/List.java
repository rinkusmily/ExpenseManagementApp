
package com.shrinkcom.expensemanagementapp.Pojo.syncPojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class List implements Serializable, Parcelable
{

    @SerializedName("lister_id")
    @Expose
    private Integer listerId;
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
    @SerializedName("backup_status")
    @Expose
    private Integer backupStatus;
    @SerializedName("list_id")
    @Expose
    private Integer listId;
    public final static Creator<List> CREATOR = new Creator<List>() {


        @SuppressWarnings({
            "unchecked"
        })
        public List createFromParcel(Parcel in) {
            return new List(in);
        }

        public List[] newArray(int size) {
            return (new List[size]);
        }

    }
    ;
    private final static long serialVersionUID = -4156741174952968019L;

    protected List(Parcel in) {
        this.listerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.listName = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.currency = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.backupStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.listId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public List() {
    }

    public Integer getListerId() {
        return listerId;
    }

    public void setListerId(Integer listerId) {
        this.listerId = listerId;
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

    public Integer getBackupStatus() {
        return backupStatus;
    }

    public void setBackupStatus(Integer backupStatus) {
        this.backupStatus = backupStatus;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(listerId);
        dest.writeValue(listName);
        dest.writeValue(userId);
        dest.writeValue(currency);
        dest.writeValue(date);
        dest.writeValue(backupStatus);
        dest.writeValue(listId);
    }

    public int describeContents() {
        return  0;
    }

}
