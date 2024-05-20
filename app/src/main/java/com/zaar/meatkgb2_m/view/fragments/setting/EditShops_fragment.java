package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmSettShopsEditBinding;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_shop;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Shop_VM;

public class EditShops_fragment extends Fragment {
    private FragmSettShopsEditBinding binding;
    private String action = "",
            source = "",
            nameShopIncoming = "";
    private Shop_VM modelSettingShop;
    private Button btn_active;
    private String[] spinnerItems;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettShopsEditBinding.inflate(inflater, container, false);
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
        if (getArguments() != null) {
            source = getArguments().getString(getString(R.string._source), "def");
            action = getArguments().getString(getString(R.string._action), "def");
            nameShopIncoming = getArguments().getString(getString(R.string._nameShop), "");
        } else {
            action = "non";
            source = "non";
            nameShopIncoming = "";
        }
        if (!source.equals("non"))
            modelSettingShop = new ViewModelProvider(
                    this,
                    new SettingFactory_shop(requireActivity().getApplicationContext(), source)
            ).get(Shop_VM.class);
        spinnerItems = new String[]{"non"};
    }

    private void initObserve() {
        initObserveCommon();
        if (action.equals(getString(R.string._add))) {
            initObserveAdd();
        }
        if (action.equals(getString(R.string._upd))) {
            initObserveEdit();
        }
    }

    private void initView() {
        initViewCommon();
        if (action.equals(getString(R.string._add))) {
            initViewAdd();
        }
        if (action.equals(getString(R.string._upd))) {
            initViewEdit();
        }
    }

    //region------------------ADD

    private void initObserveAdd() {
        initObserveLiveData_add();
        btnAdd_onClick();
        btnCancelAdd_onClick();
    }

    private void initObserveLiveData_add() {
        modelSettingShop.ld_id_insertResult_shops.observe(
                getViewLifecycleOwner(),
                aLong -> {
                    if (aLong >= 0) {
                        fragmentOk();
                    }
                }
        );
    }

    private void initViewAdd() {
        binding.btnGroupShopEdit.getRoot().setVisibility(View.GONE);
        binding.btnGroupShopAdd.getRoot().setVisibility(View.VISIBLE);
    }

    private void btnAdd_onClick() {
        binding.btnGroupShopAdd.getRoot()
                .findViewById(R.id.btnAdd_groupAdd).setOnClickListener(
                        view -> {
                            btn_active = binding.btnGroupShopAdd.getRoot().
                                    findViewById(R.id.btnAdd_groupAdd);
                            int isNonManufacture = binding.checkBNonManufacture.isChecked() ? 1 : 0;
                            modelSettingShop.addShop(
                                    (binding.etNameShop.getText().toString()).trim(),
                                    (binding.etShortNameShop.getText().toString()).trim(),
                                    isNonManufacture,
                                    binding.spinnerTypeOfShop.getSelectedItem().toString(),
                                    binding.checkBInactiveShop.isChecked() ? 1 : 0
                            );
                        }
                );
    }

    private void btnCancelAdd_onClick() {
        binding.btnGroupShopAdd.getRoot()
                .findViewById(R.id.btnCancel_groupAdd).setOnClickListener(
                        view -> fragmentCancel()
                );
    }
    //endregion

    //region------------------EDIT

    private void initObserveEdit() {
        initObserveLiveData_edit();
        btnUpd_onClick();
        btnDel_onClick();
        btnCancelEdit_onClick();
        et_ChangedListener();
    }

    private void initViewEdit() {
        binding.btnGroupShopEdit.getRoot().setVisibility(View.VISIBLE);
        binding.btnGroupShopAdd.getRoot().setVisibility(View.GONE);
        ((Button) binding.btnGroupShopEdit.getRoot().findViewById(R.id.btnUpd_groupEdit))
                .setTextAppearance(R.style.button_inactive);
        binding.btnGroupShopEdit.getRoot().findViewById(R.id.btnUpd_groupEdit)
                .setEnabled(false);
        binding.etNameShop.setText(nameShopIncoming);
        //получить из БД данные по цеху и внести в соответствующие поля
        modelSettingShop.getShopByName(nameShopIncoming);
    }

    private void initObserveLiveData_edit() {
        modelSettingShop.ld_shop.observe(
                getViewLifecycleOwner(),
                shop -> {
                    binding.etNameShop.setText(shop.getName());
                    binding.etShortNameShop.setText(shop.getShort_name());
                    binding.checkBNonManufacture.setChecked(
                            shop.getNonManufacture_isChecked() == 1
                    );
                    binding.spinnerTypeOfShop.setSelection(
                            ((ArrayAdapter<String>) binding.spinnerTypeOfShop.getAdapter())
                                    .getPosition(shop.getNameRole())
                    );
                    binding.checkBInactiveShop.setChecked(shop.getId_status() == 1);
                });
        modelSettingShop.ld_count_delResult_shops.observe(
                getViewLifecycleOwner(),
                integers -> {
                    int count = integers.get(0);
                    if (count >= 1) fragmentOk();
                }
        );
        modelSettingShop.ld_count_updResult_shops.observe(
                getViewLifecycleOwner(),
                integers -> {
                    int count = integers.get(0);
                    if (count >= 1) fragmentOk();
                }
        );
    }

    private void btnUpd_onClick() {
        binding.btnGroupShopEdit.getRoot().findViewById(R.id.btnUpd_groupEdit).setOnClickListener(
                view -> {
                    btn_active = binding.btnGroupShopEdit.getRoot().
                            findViewById(R.id.btnUpd_groupEdit);
                    int nonM = -1;
                    nonM = binding.checkBNonManufacture.isChecked() ? 1 : 0;
                    if (getArguments() != null) {
                        modelSettingShop.updShop(
                                nameShopIncoming,
                                (binding.etNameShop.getText().toString()).trim(),
                                nonM,
                                binding.spinnerTypeOfShop.getSelectedItem().toString(),
                                binding.checkBInactiveShop.isChecked() ? 1 : 0
                        );
                    }
                });
    }

    private void btnDel_onClick() {
        binding.btnGroupShopEdit.getRoot().findViewById(R.id.btnDel_groupEdit).setOnClickListener(
                view -> {
                    btn_active = binding.btnGroupShopEdit.getRoot().
                            findViewById(R.id.btnDel_groupEdit);
                    //здесь нужно диалоговое окно для подтверждения удаления, с указанием имени цеха
                    //...dialog...
                    //если -> ДА
                    modelSettingShop.delShop(nameShopIncoming);
                }
        );
    }

    private void btnCancelEdit_onClick() {
        binding.btnGroupShopEdit.getRoot().
                findViewById(R.id.btnCancel_groupEdit).setOnClickListener(
                        view -> fragmentCancel()
                );
    }

    private void et_ChangedListener() {
        binding.etNameShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((Button) binding.btnGroupShopEdit.getRoot().findViewById(R.id.btnUpd_groupEdit))
                        .setTextAppearance(R.style.button);
                binding.btnGroupShopEdit.getRoot().findViewById(R.id.btnUpd_groupEdit)
                        .setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    //endregion

    //region------------------COMMON METHODS

    private void initViewCommon() {
        initSpinner_roles();
        modelSettingShop.getAllRoles();
    }

    private void initSpinner_roles() {
        binding.spinnerTypeOfShop.setAdapter(
                new ArrayAdapter<>(
                        requireActivity(),
                        R.layout.item_spinner,
                        spinnerItems
                )
        );
    }

    private void initObserveCommon() {
        modelSettingShop.ld_sessionID.observe(
                getViewLifecycleOwner(),
                sID -> btn_active.performClick()
        );
        modelSettingShop.ld_rolesNamesStrList.observe(
                getViewLifecycleOwner(),
                strings -> {
                    if (strings != null) {
                        spinnerItems = strings.toArray(new String[0]);
                    } else {
                        spinnerItems = new String[0];
                        ((Button) binding.btnGroupShopAdd.getRoot()
                                .findViewById(R.id.btnAdd_groupAdd)).setTextAppearance(R.style.button_inactive);
                        binding.btnGroupShopAdd.getRoot()
                                .findViewById(R.id.btnAdd_groupAdd).setEnabled(false);
                        binding.tvAlertMessage.setText(getString(R.string.alert_nonShops_fromUsers));
                    }
                    initSpinner_roles();
                }
        );
    }

    private void fragmentOk() {
        int action;
        Bundle bundle = new Bundle();
        if (source.equals(getString(R.string._setting))) {
            action = R.id.action_editShops_fragment_to_shopsSetting_fragment;
        } else if (source.equals(getString(R.string._identification))) {
            action = R.id.action_editShops_fragment2_to_editWorkers_fragment2;
            bundle.putString(getString(R.string._action), getString(R.string._adduser));
        } else action = 0;
        actionTo(action, bundle);
    }

    private void fragmentCancel() {
        int action;
        Bundle bundle = new Bundle();
        if (source.equals(getString(R.string._setting))) {
            action = R.id.action_editShops_fragment_to_shopsSetting_fragment;
        } else if (source.equals(getString(R.string._identification))) {
            bundle.putString(getString(R.string._action), getString(R.string._new_enterprise));
            action = R.id.action_editShops_fragment2_to_identificationEnter_fragment;
        } else action = 0;
        actionTo(action, bundle);
    }

    private void actionTo(int action, Bundle bundle) {
        if (action != 0) {
            bundle.putString(getString(R.string._source), source);
            Navigation.findNavController(binding.getRoot()).navigate(action, bundle);
        }
    }
    //endregion
}