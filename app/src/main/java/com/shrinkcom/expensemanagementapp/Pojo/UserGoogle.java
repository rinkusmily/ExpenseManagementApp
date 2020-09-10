
package com.shrinkcom.expensemanagementapp.Pojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserGoogle implements Serializable
{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("verify_otp")
    @Expose
    private String verifyOtp;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("decrypt_pass")
    @Expose
    private String decryptPass;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    private final static long serialVersionUID = 8188751024641258508L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getVerifyOtp() {
        return verifyOtp;
    }

    public void setVerifyOtp(String verifyOtp) {
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

    public void setDecryptPass(String decryptPass) {
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

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

}
