package com.zaar.meatkgb2_m.viewModel.factory.settings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Enterprise_VM;

public class SettingFactory_enterprise extends ViewModelProvider.NewInstanceFactory{
    private Context context;
    private String initiator;

    public SettingFactory_enterprise(Context context, String initiator) {
        this.context = context;
        this.initiator = initiator;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new Enterprise_VM(context, initiator);
    }
}