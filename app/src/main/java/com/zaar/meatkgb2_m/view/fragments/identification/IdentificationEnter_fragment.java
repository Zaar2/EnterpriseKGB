package com.zaar.meatkgb2_m.view.fragments.identification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmIdentificationEnterBinding;
import com.zaar.meatkgb2_m.utilities.view.ViewUtilities;
import com.zaar.meatkgb2_m.viewModel.viewModels.identification.IdentificationVM;
import com.zaar.meatkgb2_m.viewModel.factory.identification.IdentificationFactory;

import java.util.Objects;

public class IdentificationEnter_fragment extends Fragment {
    private FragmIdentificationEnterBinding binding;
    private IdentificationVM modelIdentification;
    private String
            source = "",
            action = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmIdentificationEnterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initVariables();
        initObserve();
        initView();
    }

    private void initVariables() {
        modelIdentification = new ViewModelProvider(
                this,
                new IdentificationFactory(
                        requireActivity().getApplicationContext(),
                        getString(R.string._identification)
                )
        ).get(IdentificationVM.class);
        if (getArguments() != null) {
            source = getArguments().getString(getString(R.string._source), "non");
            action = getArguments().getString(getString(R.string._action), "non");
        }
    }

    private void initObserve() {
        initLiveDataObserve();
        initViewObserve();
    }

    private void initLiveDataObserve() {
        modelIdentification.ld_cryptoId.observe(
                getViewLifecycleOwner(),
                cryptoID -> binding.etIdEnterprise.setText(cryptoID)
        );
        modelIdentification.ld_user.observe(
                getViewLifecycleOwner(),
                user -> {
                    if (user != null) {
                        binding.etUsrLogin.setText(user.getUsr_login());
                        binding.etUsrPass.setText(user.getUsr_pass());
                    }
                }
        );
        modelIdentification.ldUserData().observe(
                getViewLifecycleOwner(),
                userData -> {
                    if (userData != null) {
                        binding.etUsrLogin.setText(userData.usrLogin);
                        binding.etUsrPass.setText(userData.usrPass);
                    }
                }
        );
        modelIdentification.ldSessionID().observe(
                getViewLifecycleOwner(),
                sessionID -> {
                    if (sessionID != null && !sessionID.equals("false") && !sessionID.equals("")) {
                        modelIdentification.getData_fromServ_rx(binding.etIdEnterprise.getText().toString(), false);
                    } else
                        new ViewUtilities().activeBtnOn(
                                binding.btnIdentificationEnterOk,
                                requireContext().getColorStateList(R.color.bg_selector),
                                requireContext().getColorStateList(R.color.milk_background)
                                );
                }
        );
        modelIdentification.ld_rxUpdDataOK.observe(
                getViewLifecycleOwner(),
                aBoolean -> {
                    if (aBoolean) {
                        //prepare the parameters for send to the main activity
                        Bundle bundle = new Bundle();
                        bundle.putString("idEnterprise", binding.etIdEnterprise.getText().toString());
                        bundle.putString("usrLogin", binding.etUsrLogin.getText().toString());
                        bundle.putString("usrPass", Objects.requireNonNull(binding.etUsrPass.getText()).toString());
                        //turn OFF the progressBar indicating the processing
                        //...codes...
                        //go to the MainActivity with Bundle

                        Navigation.findNavController(binding.getRoot()).navigate(
                                R.id.action_identificationEnter_fragment_to_mainActivity,
                                bundle
                        );
                    } else {
                        new ViewUtilities().activeBtnOn(
                                binding.btnIdentificationEnterOk,
                                requireContext().getColorStateList(R.color.bg_selector),
                                requireContext().getColorStateList(R.color.milk_background)
                        );
                    }
                }
        );
        modelIdentification.ldStageDescriptionForAppEntry().observe(
                getViewLifecycleOwner(),
                s -> binding.tvIdentificationDescriptionProcessUpdate.append(s + "\n")
        );
    }

    private void initViewObserve() {
        binding.btnIdentificationEnterOk.setOnClickListener(
                view -> {
                    new ViewUtilities().activeBtnOff(
                            (Button) view,
                            requireContext().getColorStateList(R.color.bg_selector),
                            requireContext().getColorStateList(R.color.milk_background)
                    );
                    modelIdentification.setStepProcessingUpd(1);
                    modelIdentification.obtainingValidSessionID(
                            binding.etIdEnterprise.getText().toString(),
                            binding.etUsrLogin.getText().toString(),
                            Objects.requireNonNull(binding.etUsrPass.getText()).toString(),
                            false
                    );
                    //turn ON the progressBar indicating the processing
                    //...
                });

        binding.btnIdentificationEnterBack.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_identificationEnter_fragment_to_identificationStart_fragment
                ));
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (Objects.equals(source, getString(R.string._identification))) {
                modelIdentification.getCryptoIdEnterprise();
                if (action.equals(getString(R.string._exists_enterprise)))
                    modelIdentification.getUserData();
            }
        }
    }
}