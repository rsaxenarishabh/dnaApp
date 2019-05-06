package com.dnamedical.Models.updateplaystore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

@SerializedName("id")
@Expose
private String id;
@SerializedName("appName")
@Expose
private String appName;
@SerializedName("appVersion")
@Expose
private String appVersion;
@SerializedName("forceUpgrade")
@Expose
private String forceUpgrade;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getAppName() {
return appName;
}

public void setAppName(String appName) {
this.appName = appName;
}

public String getAppVersion() {
return appVersion;
}

public void setAppVersion(String appVersion) {
this.appVersion = appVersion;
}

public String getForceUpgrade() {
return forceUpgrade;
}

public void setForceUpgrade(String forceUpgrade) {
this.forceUpgrade = forceUpgrade;
}

}