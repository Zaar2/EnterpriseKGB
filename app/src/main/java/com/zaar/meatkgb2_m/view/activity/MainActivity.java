package com.zaar.meatkgb2_m.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.ActivityMainBinding;
import com.zaar.meatkgb2_m.view.adapters.ChopsMainRecViewAdapter;
import com.zaar.meatkgb2_m.view.activity.reports.ProductionCertificatesActivity;
import com.zaar.meatkgb2_m.view.activity.reports.SearchActivity;
import com.zaar.meatkgb2_m.view.activity.reports.ShopReportsActivity;
import com.zaar.meatkgb2_m.viewModel.factory.MainFactory;
import com.zaar.meatkgb2_m.viewModel.viewModels.MainVM;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainVM model_mainView;
    private ChopsMainRecViewAdapter.OnShopProductClickListener onShopProductClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        initVariables();
        initObserve();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initVariables() {
        String message = "Main";
        model_mainView = new ViewModelProvider(
                this,
                new MainFactory(this.getApplicationContext(), message)
        ).get(MainVM.class);
        model_mainView.getAllManufactureShops();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String idEnterprise = bundle.getString("idEnterprise", "non");
            String usrLogin = bundle.getString("usrLogin", "non");
            if (!idEnterprise.equals("non") || !usrLogin.equals("non")) {
                model_mainView.getMainTitles(idEnterprise, usrLogin);
            }
        }
    }

    private void initObserve() {
        initObserveView();
        initObserveVM();
    }

    private void initObserveView(){
        btnSetting_onClick();
        btnSearch_onClick();
        btnVSD_onClick();
    }

    private void initObserveVM() {
        model_mainView.ld_shops.observe(
                this,
                strings -> initRecycleView(strings)
        );
        model_mainView.ld_appUser.observe(
                this,
                bundle -> {
                    if (bundle != null) {
                        binding.tvUserMain.setText(bundle.getString("userMain", "non"));
                        binding.tvEnterpriseNameMain.setText(bundle.getString("enterpriseName_main", "non"));
                    }else {

                    }
                });
        onShopProductClickListener = ((button, name, position) -> {
//            Intent intent = new Intent(this.getApplicationContext(), SlideReportActivity.class);
            Intent intent = new Intent(this.getApplicationContext(), ShopReportsActivity.class);
            intent.putExtra("shop", name);
            this.startActivity(intent);
        });
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL, false
        );
        binding.recViewMain.setLayoutManager(
                layoutManager
        );
        binding.recViewMain.addItemDecoration(new DividerItemDecoration(
                binding.recViewMain.getContext(),
                layoutManager.getOrientation()
        ));
    }

    private void initRecycleView(List<String> shopsList) {
        if (shopsList == null) shopsList = new ArrayList<>();
        ChopsMainRecViewAdapter chopsMainRecView_adapter = new ChopsMainRecViewAdapter(onShopProductClickListener, shopsList);
        binding.recViewMain.setAdapter(chopsMainRecView_adapter);
    }

    private void btnSetting_onClick() {
        binding.btnSetting.setOnClickListener(
                view -> viewActivity(SettingActivity.class, getString(R.string._setting))
        );
    }

    private void btnSearch_onClick() {
        binding.btnSearch.setOnClickListener(
                view -> viewActivity(SearchActivity.class, getString(R.string._activity_search))
        );
    }

    private void btnVSD_onClick() {
        binding.btnVsd.setOnClickListener(
                view -> viewActivity(ProductionCertificatesActivity.class, getString(R.string._activity_VSD))
        );
    }

    private void viewActivity(Class<?> cls, String valueExtra) {
        Intent intent = new Intent(getApplicationContext(), cls);
        if (valueExtra != null)
            intent.putExtra(getResources().getString(R.string._inputValExtras), valueExtra);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model_mainView.clearContext();
    }
}