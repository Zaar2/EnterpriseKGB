package com.zaar.meatkgb2_m.viewModel.factory.identification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zaar.meatkgb2_m.viewModel.viewModels.identification.IdentificationVM;

public class IdentificationFactory extends ViewModelProvider.NewInstanceFactory{
    private Context context;
    private String initiator;

    public IdentificationFactory(Context context, String initiator) {
        this.context = context;
        this.initiator = initiator;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new IdentificationVM(context);
    }
}
