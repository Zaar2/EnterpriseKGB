package com.zaar.meatkgb2_m.view.fragments.setting;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmSettProductsBinding;
import com.zaar.meatkgb2_m.utilities.view.ViewUtilities;
import com.zaar.meatkgb2_m.view.adapters.setting.ShopsForProductSetting_adapter;
import com.zaar.meatkgb2_m.view.adapters.setting.ShopsRecView_adapter;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_product;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Product_VM;

import java.util.ArrayList;
import java.util.List;

public class ProductsSetting_fragment extends Fragment {
    private FragmSettProductsBinding binding;
    private Product_VM modelProduct;
    private ShopsForProductSetting_adapter.OnShopProductClickListener onShopProductClickListener;
    /**
     * <p>it is interface from 'ShopsForProductSetting_adapter'</p><P></P>
     * <p>may be started once, if there was a return from the product editing fragment and the button is
     * belongs to the recycler view</p>
     */
    private ShopsForProductSetting_adapter.getItemPressed getItemPressed;
    private ShopsRecView_adapter.OnShopClickListener onShopClickListener;
    private String currentShop = "";
    private Button pressedButton = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettProductsBinding.inflate(inflater, container, false);
        modelProduct = new ViewModelProvider(
                this,
                new SettingFactory_product(
                        requireActivity().getApplicationContext(), "ProductsSetting"
                )
        ).get(Product_VM.class);
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
            Bundle bundle = getArguments();
            currentShop = bundle.getString(getString(R.string._currentShop), "");
        }
        if (currentShop.equals("all")) {
            pressedButton = binding.btnAllProducts;
        }
    }

    private void initObserve() {
        initObserveLiveData();
        initObserveView();
    }

    private void initObserveLiveData() {
        modelProduct.ldShopsNamesStrList().observe(
                getViewLifecycleOwner(),
                namesShops -> initRecView_shops(namesShops)
        );
        modelProduct.ldNameProducts().observe(
                getViewLifecycleOwner(),
                namesProducts -> initRecView_product(namesProducts)
        );
    }

    private void initObserveView() {
        binding.btnAllProducts.setOnClickListener(
                view -> {
                    btn_onPressed((Button) view, pressedButton);
                    updateRecViewProduct_all();
                }
        );

        binding.btnAddProduct.setOnClickListener(
                view -> {
                    //add
                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string._action), getString(R.string._add));
                    bundle.putString(getString(R.string._source), getString(R.string._setting));
                    bundle.putString(getString(R.string._currentShop), currentShop);
                    fragment_toEdit(bundle);
                }
        );

        onShopProductClickListener = (button, name) -> {
            btn_onPressed(button, pressedButton);
            itemRecViewShops_onClick(name);
        };

        onShopClickListener = (name, position) -> itemRecViewProduct_onClick(name, position);

        getItemPressed = button -> pressedButton = button;

        binding.btnBackProduct.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_productsSetting_fragment_to_setting_fragment
                ));
    }

    private void initView() {
        initView_recView(binding.recViewProductsShop, getString(R.string.workshop));
        initView_recView(binding.recViewProducts, "product");
        updateRecViewShops();
        if (currentShop.equals("") || currentShop.equals("all")) {
            updateRecViewProduct_all();
            btn_onPressed(binding.btnAllProducts, null);
        } else itemRecViewShops_onClick(currentShop);
    }

    private void initView_recView(RecyclerView recyclerView, String type) {
        final int orientation;
        if (type.equals(getString(R.string.workshop))) orientation = LinearLayoutManager.HORIZONTAL;
        else orientation = LinearLayoutManager.VERTICAL;

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                requireActivity().getApplicationContext(),
                orientation, false
        );
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void updateRecViewShops() {
        modelProduct.getAllManufactureShopNames();
    }

    private void updateRecViewProduct_all() {
        currentShop = "all";
        modelProduct.getAllNamesProduct();
    }

    private void initRecView_shops(List<String> namesShops) {
        if (namesShops == null) namesShops = new ArrayList<>();
        ColorStateList colorStateList_pressed = this.requireActivity().getColorStateList(R.color.bg_selector_pressed);
        ShopsForProductSetting_adapter shopsForProductSettingAdapter =
                new ShopsForProductSetting_adapter(
                        onShopProductClickListener,
                        getItemPressed,
                        namesShops,
                        currentShop,
                        colorStateList_pressed
                );
        binding.recViewProductsShop.setAdapter(shopsForProductSettingAdapter);
    }

    private void initRecView_product(List<String> productList) {
        if (productList == null) productList = new ArrayList<>();
        ShopsRecView_adapter shopsRecView_adapter = new ShopsRecView_adapter(
                productList,
                onShopClickListener
        );
        binding.tvCountItemsProduct.setText(String.valueOf(productList.size()));
        binding.recViewProducts.setAdapter(shopsRecView_adapter);
    }

    private void itemRecViewShops_onClick(String name) {
        currentShop = name;
        modelProduct.getAllNamesProductByShop(name);
    }

    private void itemRecViewProduct_onClick(String name, int position) {
        //edit
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string._source), getString(R.string._setting));
        bundle.putString(getString(R.string._action), getString(R.string._edit));
        bundle.putString(getString(R.string._nameProduct), name);
        bundle.putInt(getString(R.string._position), position);
        bundle.putString(getString(R.string._currentShop), currentShop);
        fragment_toEdit(bundle);
    }

    private void fragment_toEdit(Bundle bundle) {
        Navigation.findNavController(binding.getRoot()).navigate(
                R.id.action_productsSetting_fragment_to_editProducts_fragment,
                bundle
        );
    }

    private void btn_onPressed(Button willBeBtnPressed, Button wasBtnPressed) {
        ViewUtilities viewUtilities = new ViewUtilities();
        viewUtilities.activeBtnOff(
                willBeBtnPressed,
                this.requireActivity().getColorStateList(R.color.bg_selector_pressed),
                this.requireContext().getColorStateList(R.color.black));
        if (wasBtnPressed != null)
            viewUtilities.activeBtnOn(
                    wasBtnPressed,
                    this.requireActivity().getColorStateList(R.color.bg_selector),
                    this.requireContext().getColorStateList(R.color.milk_background));
        pressedButton = willBeBtnPressed;
    }
}