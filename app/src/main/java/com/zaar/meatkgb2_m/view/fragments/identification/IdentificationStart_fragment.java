package com.zaar.meatkgb2_m.view.fragments.identification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.data.LogPass;
import com.zaar.meatkgb2_m.databinding.FragmIdentificationStartBinding;
import com.zaar.meatkgb2_m.viewModel.factory.identification.IdentificationFactory;
import com.zaar.meatkgb2_m.viewModel.viewModels.identification.IdentificationVM;

public class IdentificationStart_fragment extends Fragment {
    private FragmIdentificationStartBinding binding;
    private IdentificationVM modelIdentification;
    private LogPass logPass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmIdentificationStartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        initVariables();
        initObserve();
        initView();
        initExistsEnterprise();
    }

    private void initVariables() {
        modelIdentification = new ViewModelProvider(
                this,
                new IdentificationFactory(
                        requireActivity().getApplicationContext(),
                        getString(R.string._fragment_main)
                )
        ).get(IdentificationVM.class);
    }

    private void initObserve() {
        initObserveLiveData();
        initObserveView();
    }

    private void initObserveLiveData() {
        modelIdentification.ldStageDescriptionForAppEntry().observe(
                getViewLifecycleOwner(),
                s -> binding.tvDescriptionMain.append(s + "\n")
        );
        modelIdentification.ld_isInitExistsEnterprise.observe(
                getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean)
                        btnVisible();
                });
        modelIdentification.ldUserData().observe(
                getViewLifecycleOwner(),
                logPass ->
                        this.logPass = logPass
        );
        modelIdentification.ldSessionID().observe(
                getViewLifecycleOwner(),
                sessionID -> {
                    if (sessionID != null && !sessionID.equals("false") && !sessionID.equals("")
                            && !logPass.isEmpty()) {
                        modelIdentification.getData_fromServ_rx(logPass.enterpriseId, false);
                    } else
                        btnVisible();
                }
        );
        modelIdentification.ld_rxUpdDataOK.observe(
                getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean)
                        btnVisible();
                    else {
                        modelIdentification.checkExistsUser(logPass.usrLogin);
                    }
                }
        );
        modelIdentification.ld_checkExistsUser.observe(
                getViewLifecycleOwner(),
                aBoolean -> {
                    if (aBoolean) {
                        if (!logPass.isEmpty()) {
                            Bundle bundle = new Bundle();
                            bundle.putString("idEnterprise", logPass.enterpriseId);
                            bundle.putString("usrLogin", logPass.usrLogin);
                            bundle.putString("usrPass", logPass.usrPass);
                            Navigation.findNavController(binding.getRoot()).navigate(
                                    R.id.action_identificationStart_fragment_to_mainActivity,
                                    bundle
                            );
                        } else btnVisible();
                    } else btnVisible();
                }
        );
    }

    private void initObserveView() {
        binding.btnStartEntryEnterprise.setOnClickListener(
                view -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string._source), getString(R.string._identification));
                    bundle.putString(getString(R.string._action), getString(R.string._exists_enterprise));
                    Navigation.findNavController(binding.getRoot()).navigate(
                            R.id.action_identificationStart_fragment_to_identificationEnter_fragment,
                            bundle
                    );
                }
        );
        binding.btnStartNewEnterprise.setOnClickListener(
                view -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string._source), getString(R.string._identification));
                    bundle.putString(getString(R.string._action), getString(R.string._new_enterprise));
                    Navigation.findNavController(binding.getRoot()).navigate(
                            R.id.action_identificationStart_fragment_to_editEnterprise_fragment2,
                            bundle
                    );
                }
        );
    }

    private void initView() {
        btnGone();
    }

    private void initExistsEnterprise() {
        modelIdentification.initExistsEnterprise();
    }

    private void btnGone() {
        binding.btnStartEntryEnterprise.setVisibility(View.GONE);
        binding.btnStartNewEnterprise.setVisibility(View.GONE);
    }

    private void btnVisible() {
        binding.btnStartEntryEnterprise.setVisibility(View.VISIBLE);
        binding.btnStartNewEnterprise.setVisibility(View.VISIBLE);
    }
}