package com.dnamedical.Models.EditProfileResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateDetail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("username")
@Expose
private String username;
@SerializedName("mobile_no")
@Expose
private String mobileNo;
@SerializedName("state")
@Expose
private String state;
@SerializedName("college")
@Expose
private String college;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getMobileNo() {
return mobileNo;
}

public void setMobileNo(String mobileNo) {
this.mobileNo = mobileNo;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public String getCollege() {
return college;
}

public void setCollege(String college) {
this.college = college;
}

}