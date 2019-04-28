package com.dnamedical.Models.QbankSubCat;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QbankSubResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("details")
@Expose
private List<Detail> details = null;

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

public List<Detail> getDetails() {
return details;
}

public void setDetails(List<Detail> details) {
this.details = details;
}

}