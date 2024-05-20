package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmSettEnterpriseBinding;
import com.zaar.meatkgb2_m.model.entity.Enterprise;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_enterprise;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Enterprise_VM;

public class EnterpriseSetting_fragment extends Fragment {
    private FragmSettEnterpriseBinding binding;
    private Enterprise_VM modelSettingEnterprise;
    private Button btn_active;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettEnterpriseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        modelSettingEnterprise = new ViewModelProvider(
                this,
                new SettingFactory_enterprise(requireActivity().getApplicationContext(), "structureSetting")
        ).get(Enterprise_VM.class);
        modelSettingEnterprise.getEnterprise();
        initObserve();
    }

    private void initObserve() {
        initObserveButton();
        initObserveData();
    }

    private void initObserveButton() {
        binding.btnEnterpriseBack.setOnClickListener(
                view -> Navigation.findNavController(binding.getRoot()).navigate(
                        R.id.action_enterpriseSetting_fragment_to_structureSetting_fragment
                )
        );
        binding.btnEnterpriseEdit.setOnClickListener(
                view -> {
                    btn_active = binding.btnEnterpriseEdit;
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string._source), getString(R.string._setting));
                    bundle.putString(getString(R.string._action), getString(R.string._upd));
                    Navigation.findNavController(binding.getRoot()).navigate(
                            R.id.action_enterpriseSetting_fragment_to_editEnterprise_fragment,
                            bundle
                    );
                }
        );
    }

    private void initObserveData() {
        modelSettingEnterprise.ld_enterpriseData.observe(
                getViewLifecycleOwner(),
                enterprise -> {
                    updateViewEnterprise(enterprise);
                }
        );
//        modelSettingEnterprise.ld_count_updResult_enterprise.observe(
//                getViewLifecycleOwner(),
//                count -> {
//                    if (count >= 1) {
//                        modelSettingEnterprise.getEnterprise();
//                    }
//                }
//        );
        modelSettingEnterprise.ld_sessionID.observe(
                getViewLifecycleOwner(),
                sID -> btn_active.performClick()
        );
    }

    private void updateViewEnterprise(Enterprise enterprise) {
        binding.tvIdEnterprise.setText(enterprise.getEnterpriseId());
        binding.tvNameEnterprise.setText(enterprise.getName());
        binding.tvInnEnterprise.setText(enterprise.getInn());
        binding.tvEmailEnterprise.setText(enterprise.getEmail());
    }
}