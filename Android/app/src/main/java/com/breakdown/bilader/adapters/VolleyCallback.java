package com.breakdown.bilader.adapters;

import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject object);
    void onFail(String message);
}