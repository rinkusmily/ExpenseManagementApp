
package com.shrinkcom.expensemanagementapp.Pojo.syncPojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable, Parcelable
{

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("verify_otp")
    @Expose
    private Integer verifyOtp;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("decrypt_pass")
    @Expose
    private Object decryptPass;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("username")
    @Expose
    private Object username;
    @SerializedName("login_by")
    @Expose
    private Object loginBy;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_word")
    @Expose
    private String currencyWord;
    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
            "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
    ;
    private final static long serialVersionUID = -1208841192481142806L;

    protected User(Parcel in) {
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.otp = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.verifyOtp = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.decryptPass = ((Object) in.readValue((Object.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((Object) in.readValue((Object.class.getClassLoader())));
        this.image = ((Object) in.readValue((Object.class.getClassLoader())));
        this.pin = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((Object) in.readValue((Object.class.getClassLoader())));
        this.loginBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.currency = ((String) in.readValue((String.class.getClassLoader())));
        this.currencyWord = ((String) in.readValue((String.class.getClassLoader())));
    }

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Integer getVerifyOtp() {
        return verifyOtp;
    }

    public void setVerifyOtp(Integer verifyOtp) {
        this.verifyOtp = verifyOtp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getDecryptPass() {
        return decryptPass;
    }

    public void setDecryptPass(Object decryptPass) {
        this.decryptPass = decryptPass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(Object loginBy) {
        this.loginBy = loginBy;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyWord() {
        return currencyWord;
    }

    public void setCurrencyWord(String currencyWord) {
        this.currencyWord = currencyWord;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(name);
        dest.writeValue(email);
        dest.writeValue(otp);
        dest.writeValue(verifyOtp);
        dest.writeValue(status);
        dest.writeValue(createdAt);
        dest.writeValue(decryptPass);
        dest.writeValue(phone);
        dest.writeValue(address);
        dest.writeValue(image);
        dest.writeValue(pin);
        dest.writeValue(username);
        dest.writeValue(loginBy);
        dest.writeValue(currency);
        dest.writeValue(currencyWord);
    }

    public int describeContents() {
        return  0;
    }

}
