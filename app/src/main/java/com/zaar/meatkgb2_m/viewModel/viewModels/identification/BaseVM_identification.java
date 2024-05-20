package com.zaar.meatkgb2_m.viewModel.viewModels.identification;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zaar.meatkgb2_m.model.entity.User;
import com.zaar.meatkgb2_m.viewModel.viewModels.BaseVM;

public class BaseVM_identification extends BaseVM {
    public BaseVM_identification(Context myContext) {
        super(myContext, "BaseViewModel_identification");
//        setMyContext(myContext);
    }

    //region-------------------RX liveData variables
    protected MutableLiveData<Boolean> _ld_rxUpdDataOK = new MutableLiveData<>();
    public LiveData<Boolean> ld_rxUpdDataOK = _ld_rxUpdDataOK;

    //endregion

    //region-------------------ENTERPRISE liveData variables
    protected MutableLiveData<String> _ld_cryptoId = new MutableLiveData<>();
    public LiveData<String> ld_cryptoId = _ld_cryptoId;

    //endregion

    //region-------------------USER liveData variables
    protected MutableLiveData<User> _ld_user = new MutableLiveData<>();
    public LiveData<User> ld_user = _ld_user;

    //endregion

    public void clearContext() {
        setMyContext(null);
    }
}