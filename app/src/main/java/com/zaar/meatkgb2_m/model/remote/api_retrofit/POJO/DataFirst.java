package com.zaar.meatkgb2_m.model.remote.api_retrofit.POJO;

import com.google.gson.annotations.SerializedName;

public class DataFirst {
    @SerializedName("id")
    private int responseId;

    @SerializedName("str")
    private String responseStr;

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }
}
