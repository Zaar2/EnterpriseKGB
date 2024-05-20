package com.zaar.meatkgb2_m.viewModel.factory.settings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Product_VM;

public class SettingFactory_product extends ViewModelProvider.NewInstanceFactory{
    private Context context;
    private String initiator;

    public SettingFactory_product(Context context, String initiator) {
        this.context = context;
        this.initiator = initiator;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new Product_VM(context, initiator);
    }
}
