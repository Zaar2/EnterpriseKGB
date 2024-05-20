package com.zaar.meatkgb2_m.model.local.api_preferences;

import static com.zaar.meatkgb2_m.utilities.Const.APP_PREFERENCES_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Set;

public class myPreferences {
    private SharedPreferences preferences;

    public myPreferences(Context context) {
        MasterKey masterKeyAlias;
//        SharedPreferences preferences = context.getSharedPreferences(
//                APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        try {
            masterKeyAlias = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            preferences = EncryptedSharedPreferences.create(
                    context.getApplicationContext(),
                    APP_PREFERENCES_NAME,
                    masterKeyAlias,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException exception) {
            Log.e("to cryptoPreferences:", exception.toString());
        }
//        this.preferences = preferences;
    }

    /**
     * @param incomingBundle Strings(nameParam, valueParam)
     * @return if true -> list of inserted value; if false -> "false"
     */
    public String setPreferences(Bundle incomingBundle) {
        StringBuilder builder = new StringBuilder();
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> keys = incomingBundle.keySet();
        for (String key : keys) {
            editor.putString(key, incomingBundle.getString(key, "nonVal"));
            builder
                    .append(key).append(" : ").append(incomingBundle.getString(key, "nonVal"))
                    .append("\n");
        }
        boolean res = editor.commit();
        if (res) {
            return builder.toString();
        } else return "false";
    }

    /**
     * @return bundle containing the boolean value
     */
    public Bundle contains(String[] keys) {
        Bundle result = new Bundle();
        for (String key : keys)
            result.putBoolean(key, preferences.contains(key));
        return result;
    }

    public Bundle getPreferencesVal(String[] keys) {
        Bundle result = new Bundle();
        for (String key : keys)
            result.putString(key, preferences.getString(key, "non"));
        return result;
    }

    public void deleteKeys(String[] keys) {
        SharedPreferences.Editor editor = preferences.edit();
        for (String key : keys) {
            editor.remove(key).apply();
        }
    }

    public void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}