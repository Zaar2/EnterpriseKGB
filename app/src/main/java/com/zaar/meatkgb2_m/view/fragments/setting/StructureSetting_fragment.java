package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmSettStructureBinding;
import com.zaar.meatkgb2_m.view.activity.SettingActivity;

public class StructureSetting_fragment extends Fragment {
    private FragmSettStructureBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettStructureBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.btnStructEnterprise.setOnClickListener(
                view -> Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_structureSetting_fragment_to_enterpriseSetting_fragment)
        );
        binding.btnStructShops.setOnClickListener(
                view -> Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_structureSetting_fragment_to_shopsSetting_fragment)
        );
        binding.btnBackStruct.setOnClickListener(
                view -> Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_structureSetting_fragment_to_setting_fragment)
        );
    }
}