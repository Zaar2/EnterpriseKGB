package com.zaar.meatkgb2_m.viewModel.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zaar.meatkgb2_m.viewModel.viewModels.MainVM;

public class MainFactory extends ViewModelProvider.NewInstanceFactory {
    private Context context;
    private String message;

    public MainFactory(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainVM(context);
    }
}