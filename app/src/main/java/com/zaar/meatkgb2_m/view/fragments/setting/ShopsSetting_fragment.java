package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmSettShopsBinding;
import com.zaar.meatkgb2_m.view.adapters.setting.ShopsRecView_adapter;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_shop;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Shop_VM;

import java.util.ArrayList;
import java.util.List;

public class ShopsSetting_fragment extends Fragment {
    private FragmSettShopsBinding binding;
    private Shop_VM modelSettingShop;
    private ShopsRecView_adapter.OnShopClickListener onShopClickListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettShopsBinding.inflate(inflater, container, false);
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
        modelSettingShop = new ViewModelProvider(
                this,
                new SettingFactory_shop(requireActivity().getApplicationContext(), "ShopSetting")
        ).get(Shop_VM.class);
    }

    private void initObserve() {
        btnAdd_onClick();
        btnBack_onClick();
        onShopClickListener = (name, position) -> itemRecView_onClick(name, position);
        modelSettingShop.ldShopsNamesStrList().observe(getViewLifecycleOwner(), strings -> initRecView(strings));
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                requireActivity().getApplicationContext(),
                LinearLayoutManager.VERTICAL, false
        );
        binding.recViewStructure.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.recViewStructure.getContext(),
                layoutManager.getOrientation()
        );
        binding.recViewStructure.addItemDecoration(dividerItemDecoration);
        updateRecView();
    }

    private void updateRecView() {
        modelSettingShop.getAllShops();
    }

    private void initRecView(List<String> shopsList) {
        if (shopsList == null) shopsList = new ArrayList<>();
        ShopsRecView_adapter shopsRecView_adapter = new ShopsRecView_adapter(
                shopsList,
                onShopClickListener
        );
        binding.recViewStructure.setAdapter(shopsRecView_adapter);
    }

    private void btnAdd_onClick() {
        binding.btnAddShops.setOnClickListener(
                view -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string._source), getString(R.string._setting));
                    bundle.putString(getString(R.string._action), getString(R.string._add));
                    fragment_toEdit(bundle);
                }
        );
    }

    private void itemRecView_onClick(String name, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string._source), getString(R.string._setting));
        bundle.putString(getString(R.string._action), getString(R.string._upd));
        bundle.putString(getString(R.string._nameShop), name);
        bundle.putInt(getString(R.string._position), position);
        fragment_toEdit(bundle);
    }

    private void btnBack_onClick() {
        binding.btnBackShops.setOnClickListener(
                view -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string._source), getString(R.string._setting));
                    bundle.putString(getString(R.string._action), getString(R.string._back));
                    fragment_back(bundle);
                }
        );
    }

    private void fragment_toEdit(Bundle bundle) {
        Navigation.findNavController(binding.getRoot()).navigate(
                R.id.action_shopsSetting_fragment_to_editShops_fragment,
                bundle
        );
    }

    private void fragment_back(Bundle bundle) {
        Navigation.findNavController(binding.getRoot()).navigate(
                R.id.action_shopsSetting_fragment_to_structureSetting_fragment,
                bundle
        );
    }
}