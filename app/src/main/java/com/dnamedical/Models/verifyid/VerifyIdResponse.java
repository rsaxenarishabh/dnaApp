package com.dnamedical.Models.verifyid;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyIdResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("Verify_detail")
@Expose
private List<VerifyDetail> verifyDetail = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<VerifyDetail> getVerifyDetail() {
return verifyDetail;
}

public void setVerifyDetail(List<VerifyDetail> verifyDetail) {
this.verifyDetail = verifyDetail;
}

}