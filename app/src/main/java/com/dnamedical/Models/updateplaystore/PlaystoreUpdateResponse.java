package com.dnamedical.Models.updateplaystore;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaystoreUpdateResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("detail")
@Expose
private List<Detail> detail = null;

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

public List<Detail> getDetail() {
return detail;
}

public void setDetail(List<Detail> detail) {
this.detail = detail;
}

}