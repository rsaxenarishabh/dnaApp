package com.dnamedical.Models.verifyid;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyDetail {

@SerializedName("v_id")
@Expose
private String vId;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("v_title")
@Expose
private String vTitle;
@SerializedName("v_image")
@Expose
private String vImage;

public String getVId() {
return vId;
}

public void setVId(String vId) {
this.vId = vId;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getVTitle() {
return vTitle;
}

public void setVTitle(String vTitle) {
this.vTitle = vTitle;
}

public String getVImage() {
return vImage;
}

public void setVImage(String vImage) {
this.vImage = vImage;
}

}
