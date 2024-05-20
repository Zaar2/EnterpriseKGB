package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmSettingBinding;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_base;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.BaseVM_setting;

public class Setting_fragment extends Fragment {

    private FragmSettingBinding binding;
    private BaseVM_setting model_setting;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initVariables();
        initObserve();
    }

    private void initVariables() {
        model_setting = new ViewModelProvider(
                this,
                new SettingFactory_base(
                        requireActivity().getApplicationContext(), "setting_main")
        ).get(BaseVM_setting.class);
    }

    private void initObserve() {
        binding.btnProducts.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_setting_fragment_to_productsSetting_fragment
                ));
        binding.btnWorkers.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_setting_fragment_to_workersSetting_fragment
                ));
        binding.btnStructure.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_setting_fragment_to_structureSetting_fragment
                ));
        binding.btnResetLogin.setOnClickListener(view -> {
            model_setting.dbClear();
            model_setting.preferencesClear();
            Navigation.findNavController(binding.getRoot()).navigate(
                    R.id.action_setting_fragment_to_identificationUser_Activity);
        });
        binding.btnSettingBackToMain.setOnClickListener(view ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );
    }
}