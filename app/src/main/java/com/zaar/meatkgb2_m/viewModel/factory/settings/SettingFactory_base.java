package com.zaar.meatkgb2_m.viewModel.factory.settings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zaar.meatkgb2_m.viewModel.viewModels.setting.BaseVM_setting;

public class SettingFactory_base extends ViewModelProvider.NewInstanceFactory{
    private Context context;
    private String initiator;

    public SettingFactory_base(Context context, String initiator) {
        this.context = context;
        this.initiator = initiator;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BaseVM_setting(context, initiator);
    }
}
