
package com.shrinkcom.expensemanagementapp.Pojo.syncPojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable, Parcelable
{

    @SerializedName("cat_id")
    @Expose
    private Integer catId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("backup_status")
    @Expose
    private Integer backupStatus;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    public final static Creator<Category> CREATOR = new Creator<Category>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return (new Category[size]);
        }

    }
    ;
    private final static long serialVersionUID = 8275437460021039900L;

    protected Category(Parcel in) {
        this.catId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.backupStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Category() {
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBackupStatus() {
        return backupStatus;
    }

    public void setBackupStatus(Integer backupStatus) {
        this.backupStatus = backupStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(catId);
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeValue(userId);
        dest.writeValue(backupStatus);
        dest.writeValue(type);
        dest.writeValue(categoryId);
    }

    public int describeContents() {
        return  0;
    }

}
