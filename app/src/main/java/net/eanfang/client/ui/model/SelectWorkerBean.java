package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/12.
 */

public class SelectWorkerBean implements Serializable {
    private String bugOneUid;
    private String city;
    private String zone;

    public String getBugOneUid() {
        return bugOneUid;
    }

    public void setBugOneUid(String bugOneUid) {
        this.bugOneUid = bugOneUid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
