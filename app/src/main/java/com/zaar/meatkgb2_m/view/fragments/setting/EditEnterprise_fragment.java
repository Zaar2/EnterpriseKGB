package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.data.LogPass;
import com.zaar.meatkgb2_m.databinding.FragmSettEnterpriseEditBinding;
import com.zaar.meatkgb2_m.viewModel.factory.identification.IdentificationFactory;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_enterprise;
import com.zaar.meatkgb2_m.viewModel.viewModels.identification.IdentificationVM;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Enterprise_VM;

public class EditEnterprise_fragment extends Fragment {
    private FragmSettEnterpriseEditBinding binding;
    private NavController navController;
    private Enterprise_VM model_settingEnterprise;
    private IdentificationVM model_identification;
    private String
            source = "",
            action = "";
//    private boolean sessionID_received = false;
    private LogPass logPass;
    private Button btn_active;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmSettEnterpriseEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        initVariables();
        initObserve();
        initView();
    }

    private void initVariables() {
        navController = Navigation.findNavController(binding.getRoot());
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            source = bundle.getString(getString(R.string._source));
            action = bundle.getString(getString(R.string._action));
        } else {
            source = "non";
            action = "non";
        }
        if (source.equals(getString(R.string._identification))) {
            model_identification = new ViewModelProvider(
                    this,
                    new IdentificationFactory(
                            requireActivity().getApplicationContext(),
                            source
                    )
            ).get(IdentificationVM.class);
        } else if (source.equals(getString(R.string._setting))) {
            model_settingEnterprise = new ViewModelProvider(
                    this,
                    new SettingFactory_enterprise(
                            requireActivity().getApplicationContext(),
                            source
                    )
            ).get(Enterprise_VM.class);
        }
        logPass = new LogPass();
    }

    private void initView() {
        if (source.equals(getString(R.string._identification))
                && action.equals(getString(R.string._new_enterprise)))
            //add new enterprise, source is identificationActivity
            initView_add();
        else if (source.equals(getString(R.string._setting)))
            //edit, source is settingActivity
            initView_edit();
    }

    private void initObserve() {
        if (source.equals(getString(R.string._identification))
                && action.equals(getString(R.string._new_enterprise)))
            //add new enterprise, source is identificationActivity
            initObserveAdd();
        else if (source.equals(getString(R.string._setting)))
            //edit, source is settingActivity
            initObserveEdit();
    }

    private void initView_add() {
        binding.btnGroupEnterpriseEdit.getRoot().setVisibility(View.GONE);
        if (model_identification.getStepProcessingUpd() == 0) {
            //start fragment
            binding.btnGroupEnterpriseAdd.getRoot().setVisibility(View.VISIBLE);
            binding.btnGroupEnterpriseInfo.getRoot().setVisibility(View.GONE);
        } else if (model_identification.getStepProcessingUpd() == 1) {
            //processing update
            binding.btnGroupEnterpriseAdd.getRoot().setVisibility(View.GONE);
            binding.btnGroupEnterpriseInfo.getRoot().setVisibility(View.INVISIBLE);
        } else if (model_identification.getStepProcessingUpd() == 2) {
            //processing update is end
            binding.btnGroupEnterpriseAdd.getRoot().setVisibility(View.GONE);
            binding.btnGroupEnterpriseInfo.getRoot().setVisibility(View.VISIBLE);
        }
    }

    private void initView_edit() {
        binding.btnGroupEnterpriseEdit.getRoot().setVisibility(View.VISIBLE);
        binding.btnGroupEnterpriseAdd.getRoot().setVisibility(View.GONE);
        binding.btnGroupEnterpriseInfo.getRoot().setVisibility(View.GONE);
        binding.btnGroupEnterpriseEdit.getRoot().findViewById(R.id.btnDel_groupEdit)
                .setVisibility(View.GONE);
        binding.btnEnterpriseGenerateId.setVisibility(View.INVISIBLE);
        model_settingEnterprise.getEnterprise();
    }

    private void initObserveEdit() {
        binding.btnGroupEnterpriseEdit.getRoot()
                .findViewById(R.id.btnCancel_groupEdit).setOnClickListener(
                        view -> btnCancelEdit_onClick()
                );
        binding.btnGroupEnterpriseEdit.getRoot()
                .findViewById(R.id.btnUpd_groupEdit).setOnClickListener(
                        view -> btnUpdate_onClick()
                );
        binding.btnGroupEnterpriseEdit.getRoot()
                .findViewById(R.id.btnDel_groupEdit).setOnClickListener(
                        view -> btnDelete_onClick()
                );
        model_settingEnterprise.ld_enterpriseData.observe(
                getViewLifecycleOwner(),
                enterprise -> {
                    binding.tvIdEnterprise.setText(enterprise.getEnterpriseId());
                    binding.etNameEnterprise.setText(enterprise.getName());
                    binding.etInnEnterprise.setText(enterprise.getInn());
                    binding.etEmailEnterprise.setText(enterprise.getEmail());
                }
        );
        model_settingEnterprise.ld_count_updResult_enterprise.observe(
                getViewLifecycleOwner(),
                count -> {
                    if (count >= 1) {
                        fragmentEditEnt_Back();
                    }
                }
        );
        model_settingEnterprise.ld_sessionID.observe(
                getViewLifecycleOwner(),
                sID -> btn_active.performClick()
        );
    }


    private void initObserveAdd() {
        initObserveAdd_liveData();
        initObserveAdd_view();
    }

    private void initObserveAdd_liveData() {
        model_identification.ld_cryptoId.observe(
                getViewLifecycleOwner(),
                cryptoID -> {
                    binding.tvIdEnterprise.setText(cryptoID);
                    binding.tvAddEnterpriseExplanation.setVisibility(View.VISIBLE);
                }
        );

        model_identification.ldSessionID().observe(
                getViewLifecycleOwner(),
                sessionID -> {
                    if (!sessionID.isEmpty()) {
                        model_identification.getData_fromServ_rx(binding.tvIdEnterprise.getText().toString(), true);
                        model_identification.getUserData();
                    }
                }
        );

        model_identification.ld_rxUpdDataOK.observe(
                getViewLifecycleOwner(),
                aBoolean -> {
                    if (aBoolean) {
                        binding.btnGroupEnterpriseAdd.getRoot().setVisibility(View.GONE);
                        binding.btnGroupEnterpriseInfo.getRoot().setVisibility(View.VISIBLE);
                    } else {
                        binding.btnGroupEnterpriseAdd.getRoot().setVisibility(View.VISIBLE);
                        binding.btnGroupEnterpriseInfo.getRoot().setVisibility(View.GONE);
                        active_btn_on(binding.btnGroupEnterpriseAdd.getRoot().findViewById(R.id.btnAdd_groupAdd));
                    }
                }
        );

        model_identification.ldStageDescriptionForAppEntry().observe(
                getViewLifecycleOwner(),
                strDescription -> binding.tvAddEnterpriseDescriptionProcess.append(strDescription + "\n")
        );

        model_identification.ldUserData().observe(
                getViewLifecycleOwner(),
                logPass_ld -> this.logPass = logPass_ld
        );
    }

    private void initObserveAdd_view() {
        binding.btnGroupEnterpriseAdd.getRoot()
                .findViewById(R.id.btnAdd_groupAdd).setOnClickListener(
                        view -> btnAdd_onClick()
                );
        binding.btnGroupEnterpriseAdd.getRoot()
                .findViewById(R.id.btnCancel_groupAdd).setOnClickListener(
                        view -> btnCancelAdd_onClick()
                );
        binding.btnGroupEnterpriseInfo.getRoot()
                .findViewById(R.id.btnGoOn_groupInfo).setOnClickListener(
                        view -> btnGoOn_onClick()
                );
    }

    private void btnCancelEdit_onClick() {
        fragmentEditEnt_Back();
    }

    private void btnUpdate_onClick() {
        btn_active = binding.btnGroupEnterpriseEdit.getRoot()
                .findViewById(R.id.btnUpd_groupEdit);
        model_settingEnterprise.updEnterprise(
                binding.etNameEnterprise.getText().toString(),
                binding.etInnEnterprise.getText().toString(),
                binding.etEmailEnterprise.getText().toString()
        );
    }

    private void btnDelete_onClick() {
        btn_active = binding.btnGroupEnterpriseEdit.getRoot()
                .findViewById(R.id.btnDel_groupEdit);
        fragmentEditEnt_Back();
    }

    private void btnAdd_onClick() {
        active_btn_off(binding.btnGroupEnterpriseAdd.getRoot().findViewById(R.id.btnAdd_groupAdd));

        binding.etInnEnterprise.setKeyListener(null);
        binding.etNameEnterprise.setKeyListener(null);
        binding.etEmailEnterprise.setKeyListener(null);

        model_identification.setStepProcessingUpd(1);
        model_identification.addEnterprise_rx(
                binding.etNameEnterprise.getText().toString(),
                binding.etInnEnterprise.getText().toString(),
                binding.etEmailEnterprise.getText().toString()
        );
        binding.btnGroupEnterpriseEdit.getRoot().setVisibility(View.GONE);
        binding.btnGroupEnterpriseInfo.getRoot().setVisibility(View.INVISIBLE);
    }

    private void btnGoOn_onClick() {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string._source), source);
        bundle.putString(getString(R.string._action), getString(R.string._add));
        bundle.putString("idEnterprise", binding.tvIdEnterprise.getText().toString());
        bundle.putString("usrLogin", logPass.usrLogin);
        bundle.putString("usrPass", logPass.usrPass);
        navController.navigate(
                R.id.action_editEnterprise_fragment2_to_mainActivity,
                bundle
        );
    }

    private void btnCancelAdd_onClick() {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string._action), getString(R.string._cancelAddEnterprise));
        navController.navigate(
                R.id.action_editEnterprise_fragment2_to_identificationStart_fragment,
                bundle
        );
    }

    private void fragmentEditEnt_Back() {
        navController.navigate(
                R.id.action_editEnterprise_fragment_to_enterpriseSetting_fragment
        );
    }

    private void active_btn_off(Button button) {
        button.setEnabled(false);
        button.setBackgroundTintList(this.requireActivity().getColorStateList(R.color.button_blocked));
    }

    private void active_btn_on(Button button) {
        button.setEnabled(true);
        button.setBackgroundTintList(this.requireActivity().getColorStateList(R.color.bg_selector));
    }
}