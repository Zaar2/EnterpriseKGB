package com.zaar.meatkgb2_m.model.repository;

import android.content.Context;
import android.os.Bundle;

import com.zaar.meatkgb2_m.model.local.EnabledAppUser;
import com.zaar.meatkgb2_m.model.local.api_preferences.myPreferences;

public class SharedPreferencesImpl implements SharedPreferences {
    myPreferences myPreferences;

    public SharedPreferencesImpl(Context context) {
        this.myPreferences = new myPreferences(context);
    }

    @Override
    public String setPreferences(Bundle incomingBundle) {
        return myPreferences.setPreferences(incomingBundle);
    }

    /**
     *
     * @return bundle containing the boolean value
     */
    @Override
    public Bundle containsPreferences(String[] keys) {
        return myPreferences.contains(keys);
    }

    @Override
    public Bundle getPreferencesVal(String[] keys) {
        return myPreferences.getPreferencesVal(keys);
    }

    @Override
    public void deleteKeys(String[] keys) {
        myPreferences.deleteKeys(keys);
    }

    @Override
    public void clear() {
        myPreferences.clear();
    }

    @Override
    public void setSessionID(String sessionID) {
        EnabledAppUser.getINSTANCE().setSessionID(sessionID);
        Bundle bundle = new Bundle();
        bundle.putString("sessionID", sessionID);
        String response = setPreferences(bundle);
    }
}