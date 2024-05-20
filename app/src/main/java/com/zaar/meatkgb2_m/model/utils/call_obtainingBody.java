package com.zaar.meatkgb2_m.model.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * сейчас не в рабочем состоянии
 * @param <T>
 */
public class call_obtainingBody<T> {
    Call<T> call;

    public call_obtainingBody(Call<T> call) {
        this.call = call;
    }

    public T obtaining() {
        T body = null;
        call.enqueue(new Callback<T>() {
            T body;

            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    body = response.body();
                } else {
                    Log.d("ERROR RETROFIT", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
        return body;
    }
}