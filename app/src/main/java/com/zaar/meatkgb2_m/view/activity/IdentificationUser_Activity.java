package com.zaar.meatkgb2_m.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.zaar.meatkgb2_m.databinding.ActivityIdentificationUserBinding;
import com.zaar.meatkgb2_m.viewModel.viewModels.identification.IdentificationVM;
import com.zaar.meatkgb2_m.viewModel.factory.identification.IdentificationFactory;

public class IdentificationUser_Activity extends AppCompatActivity {
    private ActivityIdentificationUserBinding binding;
    public NavController navController;
    private IdentificationVM identificationViewModel;
    private String message = "Identification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdentificationUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initVariable();
        initObserve();
    }

    private void initVariable() {
        identificationViewModel = new ViewModelProvider(
                this,
                new IdentificationFactory(this.getApplicationContext(), message)
        ).get(IdentificationVM.class);
//        navController = Navigation.findNavController(this, R.id.identification_nav_host_graph);
    }

    private void initObserve() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        identificationViewModel.clearContext();
    }
}
