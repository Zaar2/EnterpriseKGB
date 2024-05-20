package com.zaar.meatkgb2_m.viewModel.viewModels.tmp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zaar.meatkgb2_m.viewModel.viewModels.setting.BaseVM_setting;

public class FactoryViewModel<M extends BaseVM_setting> extends ViewModelProvider.NewInstanceFactory {
    private final M viewModel;
    private Context context;

    public FactoryViewModel(M viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) viewModel;
    }
}