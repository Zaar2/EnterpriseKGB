package com.zaar.meatkgb2_m.viewModel.viewModels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zaar.meatkgb2_m.data.AppUser;
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl;

import java.util.List;

public class MainVM extends ViewModel {
    @SuppressLint("StaticFieldLeak")
    private Context myContext;
    private String message;
    private MutableLiveData<List<String>> _ld_shops = new MutableLiveData<>();
    public LiveData<List<String>> ld_shops = _ld_shops;

    private MutableLiveData<Bundle> _ld_appUser = new MutableLiveData<>();
    public LiveData<Bundle> ld_appUser = _ld_appUser;


    public MainVM(@NonNull Context context) {
        this.myContext = context;
//        this.message = message;
    }

    public void getAllManufactureShops() {
        List<String> names = new LocalDbRepositoryImpl(myContext).getAllManufactureShops();
        if (names.size() > 0) {
            _ld_shops.setValue(names);
        } else _ld_shops.setValue(null);
    }

    public void getMainTitles(String idEnterprise, String usrLogin) {
        Bundle mainTitles = new Bundle();
        AppUser appUser;
        appUser = new LocalDbRepositoryImpl(myContext).getAppUser(usrLogin);
        if (appUser != null) {
            appUser.usrLogin = usrLogin;
            appUser.enterpriseName = new LocalDbRepositoryImpl(myContext).getNameEnterprise(idEnterprise);
            boolean isUsrEnabled = new LocalDbRepositoryImpl(myContext).enablingAppUser(appUser);
            if (isUsrEnabled) {
                mainTitles.putString("enterpriseName_main", appUser.enterpriseName);
                String userTitle = appUser.appointment + " : " + appUser.usrFullName;
                mainTitles.putString("userMain", userTitle);
                _ld_appUser.setValue(mainTitles);
            } else _ld_appUser.setValue(null);
        }
    }

    public void clearContext() {
        this.myContext = null;
    }
}