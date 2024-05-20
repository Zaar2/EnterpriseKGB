package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmSettProductsEditBinding;
import com.zaar.meatkgb2_m.model.entity.Product;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_product;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Product_VM;

import java.util.HashMap;
import java.util.Objects;

public class EditProducts_fragment extends Fragment {
    private FragmSettProductsEditBinding binding;
    private Product_VM modelProduct;
    private String[] spinnerItems;
    private String configurationType = "",
            source = "",
            nameProduct_incoming;
    private String nameWorkshop_product = "";
    private Button btn_active;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettProductsEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        initVariables();
        initObserve();
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initVariables() {
        modelProduct = new ViewModelProvider(
                this,
                new SettingFactory_product(
                        requireActivity().getApplicationContext(),
                        "EditProducts"
                )
        ).get(Product_VM.class);
        spinnerItems = new String[]{"non"};
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String action = bundle.getString(getString(R.string._action), "non");
            if (!action.equals("non")) {
                if (Objects.equals(bundle.getString(getString(R.string._source)), getString(R.string._setting))) {
                    //source is settingActivity
                    source = getString(R.string._setting);
                    if (Objects.equals(action, getString(R.string._edit))) {
                        configurationType = getString(R.string._edit);
//                    nameWorkshop_product = bundle.getString(getString(R.string._nameshop), "non");
                        nameProduct_incoming = bundle.getString(getString(R.string._nameProduct), "non");
                    }
                    if (Objects.equals(action, getString(R.string._add))) {
                        configurationType = getString(R.string._add);
                    }
                }
            }
        }
    }

    private void initObserve() {
        initObserve_common();
        if (configurationType.equals(getString(R.string._add))) {
            initObserve_add();
        } else if (configurationType.equals(getString(R.string._edit))) {
            initObserveEdit();
        }
    }

    private void initObserve_common() {
        modelProduct.ld_sessionID.observe(
                getViewLifecycleOwner(),
                sID -> btn_active.performClick()
        );
        modelProduct.ldShopsNamesStrList().observe(
                getViewLifecycleOwner(),
                itemsShop -> {
                    if (itemsShop != null) {
                        spinnerItems = itemsShop.toArray(new String[0]);
                    } else {
                        spinnerItems = new String[0];
                        ((Button) binding.btnGroupProductAdd.getRoot()
                                .findViewById(R.id.btnAdd_groupAdd)).setTextAppearance(R.style.button_inactive);
                        binding.btnGroupProductAdd.getRoot()
                                .findViewById(R.id.btnAdd_groupAdd).setEnabled(false);
                    }
                    binding.spinnerShopForEditProduct.setAdapter(
                            new ArrayAdapter<>(
                                    requireActivity(),
                                    R.layout.item_spinner,
                                    spinnerItems
                            )
                    );
                }
        );
    }

    private void initView() {
        initView_common();
        if (configurationType.equals(getString(R.string._add))) {
            initViewAdd();
        }
        if (configurationType.equals(getString(R.string._edit))) {
            initViewEdit();
        }
    }

    private void initView_common() {
        binding.spinnerShopForEditProduct.setAdapter(
                new ArrayAdapter<>(
                        requireActivity(),
                        R.layout.item_spinner,
                        spinnerItems
                )
        );
        modelProduct.getAllManufactureShopNames();
    }

    private void initObserve_add() {
        initObserve_view_add();
        initObserveLivedata_add();
    }

    private void initObserveEdit() {
        initObserve_view_edit();
        initObserveLivedata_edit();
    }

    private void initViewAdd() {
        binding.btnGroupProductAdd.getRoot().setVisibility(View.VISIBLE);
        binding.btnGroupProductEdit.getRoot().setVisibility(View.GONE);
    }

    private void initViewEdit() {
        binding.btnGroupProductAdd.getRoot().setVisibility(View.GONE);
        binding.btnGroupProductEdit.getRoot().setVisibility(View.VISIBLE);
    }

    private void initObserve_view_add() {
        binding.btnGroupProductAdd.getRoot().findViewById(R.id.btnAdd_groupAdd)
                .setOnClickListener(
                        view -> {
                            btn_active = binding.btnGroupProductAdd.getRoot()
                                    .findViewById(R.id.btnAdd_groupAdd);
                            HashMap<String, String> product = new HashMap<>(5);
                            prepareValueUser(product);
                            binding.btnGroupProductAdd.getRoot()
                                    .findViewById(R.id.btnCancel_groupAdd)
                                    .setEnabled(false);
                            modelProduct.addProduct(product);
                        }
                );
        binding.btnGroupProductAdd.getRoot().findViewById(R.id.btnCancel_groupAdd)
                .setOnClickListener(
                        view -> {
                            btn_active = binding.btnGroupProductAdd.getRoot()
                                    .findViewById(R.id.btnCancel_groupAdd);
                            Bundle bundle = new Bundle();
                            bundle.putString(getString(R.string._action), getString(R.string._cancelAdd));
                            bundle.putString(getString(R.string._source), getString(R.string._addProduct));
                            if (getArguments() != null)
                                bundle.putString(
                                        getString(R.string._currentShop),
                                        getArguments().getString(getString(R.string._currentShop), ""));
                            fragmentBack(bundle);
                        }
                );
    }

    private void initObserveLivedata_add() {
        modelProduct.ld_id_insertResult_product.observe(
                getViewLifecycleOwner(),
                id -> {
                    binding.btnGroupProductAdd.getRoot()
                            .findViewById(R.id.btnCancel_groupAdd)
                            .setEnabled(true);
                    if (id >= 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString(getString(R.string._action), getString(R.string._add));
                        bundle.putString(getString(R.string._source), getString(R.string._addProduct));
                        if (getArguments() != null)
                            bundle.putString(
                                    getString(R.string._currentShop),
                                    getArguments().getString(getString(R.string._currentShop), ""));
                        fragmentBack(bundle);
                    }
                });
    }

    private void prepareValueUser(HashMap<String, String> product) {
        product.put(getString(R.string.productName_product), binding.etProductName.getText().toString().equals("") ?
                "non" : binding.etProductName.getText().toString());
        product.put(getString(R.string.vsd_support_product), binding.checkBVsdSupport.isChecked() ? "1" : "0");
        product.put(getString(R.string.me_product), binding.etMeProduct.getText().toString());
        product.put(getString(R.string.id_workshop_product), binding.spinnerShopForEditProduct.getSelectedItem().toString());
        product.put(getString(R.string.accuracy_product), binding.etAccuracy.getText().toString().equals("") ?
                        "0" : binding.etAccuracy.getText().toString());
        product.put(getString(R.string.idStatus_product), binding.checkBInactiveProduct.isChecked() ? "1" : "0");
        for (String key : product.keySet())
            product.computeIfPresent(key, (s, s2) -> s2.trim());
    }

    private void initObserve_view_edit() {
        if (!nameProduct_incoming.equals("non")) {
            modelProduct.getProductByName(nameProduct_incoming);
            modelProduct.getNameShopByProductName(nameProduct_incoming);
        }
        binding.btnGroupProductEdit.getRoot().findViewById(R.id.btnCancel_groupEdit)
                .setOnClickListener(
                        view -> {
                            btn_active = binding.btnGroupProductEdit.getRoot()
                                    .findViewById(R.id.btnCancel_groupEdit);
                            Bundle bundle = new Bundle();
                            bundle.putString(getString(R.string._action), getString(R.string._cancelUpd));
                            bundle.putString(getString(R.string._source), getString(R.string._updProduct));
                            if (getArguments() != null)
                                bundle.putString(
                                        getString(R.string._currentShop),
                                        getArguments().getString(getString(R.string._currentShop), ""));
                            fragmentBack(bundle);
                        }
                );
        binding.btnGroupProductEdit.getRoot().findViewById(R.id.btnUpd_groupEdit)
                .setOnClickListener(
                        view -> {
                            btn_active = binding.btnGroupProductEdit.getRoot()
                                    .findViewById(R.id.btnUpd_groupEdit);
                            HashMap<String, String> product = new HashMap<>(5);
                            prepareValueUser(product);
                            product.put(
                                    getString(R.string.id),
                                    binding.tvIdProduct.getText().toString()
                            );
                            binding.btnGroupProductAdd.getRoot()
                                    .findViewById(R.id.btnCancel_groupAdd)
                                    .setEnabled(false);
                            modelProduct.updProduct(product);
                        }
                );
        binding.btnGroupProductEdit.getRoot().findViewById(R.id.btnDel_groupEdit)
                .setOnClickListener(
                        view -> {
                            btn_active = binding.btnGroupProductEdit.getRoot()
                                    .findViewById(R.id.btnDel_groupEdit);
                            binding.btnGroupProductAdd.getRoot()
                                    .findViewById(R.id.btnCancel_groupAdd)
                                    .setEnabled(false);
                            modelProduct.delProduct(
                                    Long.parseLong(binding.tvIdProduct.getText().toString())
                            );
                        }
                );
    }

    private void initObserveLivedata_edit() {
        modelProduct.ld_product.observe(
                getViewLifecycleOwner(),
                product -> fillFieldsProduct(product)
        );
        modelProduct.ld_shopNameStr.observe(
                getViewLifecycleOwner(),
                nameShop -> {
                    nameWorkshop_product = nameShop;
                    if (nameWorkshop_product == null) {
                        String[] new_spinnerItems = new String[spinnerItems.length];
                        new_spinnerItems[0] = "non";
                        for (int i = 1; i < spinnerItems.length; i++) {
                            new_spinnerItems[i] = spinnerItems[i - 1];
                        }
                        binding.spinnerShopForEditProduct.setAdapter(
                                new ArrayAdapter<>(
                                        requireActivity(),
                                        R.layout.item_spinner,
                                        new_spinnerItems
                                ));
                    } else if (!nameWorkshop_product.equals("non")) {
                        binding.spinnerShopForEditProduct.setSelection(
                                ((ArrayAdapter<String>) binding.spinnerShopForEditProduct.getAdapter())
                                        .getPosition(nameWorkshop_product)
                        );
                    }
                });
        modelProduct.ld_id_insertUpd_product.observe(
                getViewLifecycleOwner(),
                idList -> {
                    binding.btnGroupProductAdd.getRoot()
                            .findViewById(R.id.btnCancel_groupAdd)
                            .setEnabled(true);
                    if (idList != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString(getString(R.string._action), getString(R.string._upd));
                        bundle.putString(getString(R.string._source), getString(R.string._updProduct));
                        if (getArguments() != null)
                            bundle.putString(
                                    getString(R.string._currentShop),
                                    getArguments().getString(getString(R.string._currentShop), ""));
                        fragmentBack(bundle);
                    }
                }
        );
        modelProduct.ld_id_deletedResult_product.observe(
                getViewLifecycleOwner(),
                idDel -> {
                    binding.btnGroupProductAdd.getRoot()
                            .findViewById(R.id.btnCancel_groupAdd)
                            .setEnabled(true);
                    if (idDel >= 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString(getString(R.string._action), getString(R.string._del));
                        bundle.putString(getString(R.string._source), getString(R.string._updProduct));
                        if (getArguments() != null)
                            bundle.putString(
                                    getString(R.string._currentShop),
                                    getArguments().getString(getString(R.string._currentShop), ""));
                        fragmentBack(bundle);
                    }
                }
        );
    }

    private void fillFieldsProduct(Product product) {
        binding.etProductName.setText(product.getProduct_name());
        binding.etMeProduct.setText(product.getMe());
        binding.etAccuracy.setText(String.valueOf(product.getAccuracy()));
        binding.checkBVsdSupport.setChecked(product.getVsd_support() == 1);
        binding.tvIdProduct.setText(String.valueOf(product.getId()));
        binding.checkBInactiveProduct.setChecked(product.getId_status() == 1);
    }

    private void fragmentBack(Bundle bundle) {
        Navigation.findNavController(binding.getRoot())
                .navigate(
                        R.id.action_editProducts_fragment_to_productsSetting_fragment,
                        bundle);
    }
}