package com.zaar.meatkgb2_m.model.repository;

import android.os.Bundle;

public interface SharedPreferences {

    String setPreferences(Bundle bundle);
    Bundle containsPreferences(String[] keys);
    Bundle getPreferencesVal(String[] keys);
//    String savePreferenceVal(Bundle values);
    void deleteKeys(String[] keys);
    void clear();
    public void setSessionID(String sessionID);
}
