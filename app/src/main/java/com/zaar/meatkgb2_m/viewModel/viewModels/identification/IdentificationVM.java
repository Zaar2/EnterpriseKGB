package com.zaar.meatkgb2_m.viewModel.viewModels.identification;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zaar.meatkgb2_m.data.LogPass;
import com.zaar.meatkgb2_m.model.entity.Enterprise;
import com.zaar.meatkgb2_m.model.entity.Product;
import com.zaar.meatkgb2_m.model.entity.Role;
import com.zaar.meatkgb2_m.model.entity.Shop;
import com.zaar.meatkgb2_m.model.entity.User;
import com.zaar.meatkgb2_m.model.repository.SharedPreferencesImpl;
import com.zaar.meatkgb2_m.model.repository.RemoteRepositoryImpl;
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl;
import com.zaar.meatkgb2_m.viewModel.utils.TypeDataFromServ;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class IdentificationVM extends BaseVM_identification {

    private final MutableLiveData<Boolean> _ld_isInitExistsEnterprise = new MutableLiveData<>();
    public LiveData<Boolean> ld_isInitExistsEnterprise = _ld_isInitExistsEnterprise;

    private final HashMap<String, Boolean> isUpdateDataFromServer = new HashMap<>();

    private final MutableLiveData<Boolean> _ld_checkExistsUser = new MutableLiveData<>();
    public LiveData<Boolean> ld_checkExistsUser = _ld_checkExistsUser;

    public IdentificationVM(Context myContext) {
        super(myContext);
    }

    public void checkExistsUser(String usrLogin) {
//        getMldStageDescriptionForAppEntry().setValue("checkCurrentUserLogin - start");
        Boolean result = new LocalDbRepositoryImpl(getMyContext()).checkAppUser(usrLogin);
        getMldStageDescriptionForAppEntry().setValue("checkCurrentUserLogin - " + result.toString());
        _ld_checkExistsUser.setValue(result);
    }

    public void initExistsEnterprise() {
        String cryptoID = new LocalDbRepositoryImpl(getMyContext()).getCryptoIdEnterprise();
        if (!cryptoID.isEmpty()) {
            Bundle exists = new SharedPreferencesImpl(getMyContext())
                    .containsPreferences(new String[]{getKeyUsrLog(), getKeyUsrPass()});
            if ((exists.getBoolean(getKeyUsrLog())
                    && exists.getBoolean(getKeyUsrPass()))) {
                Bundle bundleLogPass = new SharedPreferencesImpl(getMyContext())
                        .getPreferencesVal(new String[]{getKeyUsrLog(), getKeyUsrPass()});
                if (!bundleLogPass.isEmpty()) {
                    LogPass logPass = new LogPass(
                            cryptoID,
                            bundleLogPass.getString(getKeyUsrLog()),
                            bundleLogPass.getString(getKeyUsrPass())
                    );
                    getMldUserData().setValue(logPass);
                    setStepProcessingUpd(1);
                    obtainingValidSessionID(
                            cryptoID,
                            bundleLogPass.getString(getKeyUsrLog(), ""),
                            bundleLogPass.getString(getKeyUsrPass(), ""),
                            false
                    );
                } else _ld_isInitExistsEnterprise.setValue(false);
            } else _ld_isInitExistsEnterprise.setValue(false);
        } else
            _ld_isInitExistsEnterprise.setValue(false);
    }

    public void getCryptoIdEnterprise() {
        String cryptoID = new LocalDbRepositoryImpl(getMyContext()).getCryptoIdEnterprise();
        _ld_cryptoId.setValue(cryptoID);
    }

    /**
     * <p>1.deleting all local record of legacy</p>
     * <p>2.create new enterprise on the server (response: enterpriseID of new enterprise)</p>
     * <p>3.launch obtaining valid sessionID</p>
     *
     * @param name  name of new enterprise
     * @param inn   inn of new enterprise
     * @param email email of new enterprise, for idEnterprise, password and login recovery
     */
    public void addEnterprise_rx(String name, String inn, String email) {
        new LocalDbRepositoryImpl(getMyContext()).clearDB();
        new SharedPreferencesImpl(getMyContext()).clear();
        Enterprise enterprise_new = new Enterprise(name, inn, email);
        new RemoteRepositoryImpl().addEnterprise(enterprise_new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(Objects::nonNull)
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String enterpriseID) {
                        if (!enterpriseID.equals("")) {
                            getMldStageDescriptionForAppEntry().setValue("Enterprise is created.\nEnterpriseID is received.");
                            _ld_cryptoId.postValue(enterpriseID);
                            obtainingValidSessionID(enterpriseID, "admin_" + enterpriseID, enterpriseID, true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "Response = " + e.toString());
                        getMldSessionID().postValue("");
                        getMldStageDescriptionForAppEntry().postValue("!!Adding enterprise is failed. Error on the server.");
                    }

                    @Override
                    public void onComplete() {
//                        _ld_stageDescription_forAppEntry.postValue("EnterpriseID: receiving - OK (onComplete())");
                    }
                })
        ;

    }

    public void isPartUpdateOK_dataFromServer_rx() {
        boolean result = true;
        for (TypeDataFromServ type : TypeDataFromServ.values()) {
            if (!isUpdateDataFromServer.containsKey(type.getTitle())) result = false;
        }
        if (result) {
            getMldStageDescriptionForAppEntry().setValue("updating local database from server - OK");
            setStepProcessingUpd(2);
            _ld_rxUpdDataOK.setValue(true);
        } else {
            if (isUpdateDataFromServer.size() == TypeDataFromServ.getEntries().size())
                _ld_rxUpdDataOK.setValue(false);
        }
    }

    public void getData_fromServ_rx(String enterpriseID, boolean isAdd) {
        if (getStepProcessingUpd() == 1) {
            getEnterprise_fromServer(enterpriseID);
            getShops_fromServer(enterpriseID);
            getWorkers_fromServer(enterpriseID);
            getProducts_fromServer(enterpriseID, isAdd);
            getRoles_fromServer();
        }
    }

    private void getEnterprise_fromServer(String enterpriseID) {
        new RemoteRepositoryImpl().getEnterprise(enterpriseID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Enterprise>>() {
                    @Override
                    public void onSuccess(List<Enterprise> enterprises) {
                        if (enterprises != null) {
                            new LocalDbRepositoryImpl(getMyContext()).addEnterprise_replaceData(enterprises.get(0));
                            isUpdateDataFromServer.put(TypeDataFromServ.ENTERPRISE.getTitle(), true);
                            isPartUpdateOK_dataFromServer_rx();
                            getMldStageDescriptionForAppEntry().setValue("getEnterprise (receiving) - OK");
                        } else
                            getMldStageDescriptionForAppEntry().setValue("getEnterprise (receiving) - false");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "Response = " + e.toString());
                        getMldStageDescriptionForAppEntry().setValue(
                                "getEnterprise (receiving) - onError()"
                                        + getDescriptionLineFromThrowable(e));

                    }
                })
        ;
    }

    private void getShops_fromServer(String enterpriseID) {
        new RemoteRepositoryImpl().getShops(enterpriseID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Shop>>() {
                    @Override
                    public void onSuccess(List<Shop> shops) {
                        if (shops != null) {
                            new LocalDbRepositoryImpl(getMyContext()).addShop_replaceData(shops);
                            isUpdateDataFromServer.put(TypeDataFromServ.SHOPS.getTitle(), true);
                            isPartUpdateOK_dataFromServer_rx();
                            getMldStageDescriptionForAppEntry().setValue("getShops(receiving) - OK");
                        } else
                            getMldStageDescriptionForAppEntry().setValue("getShops(receiving) - false");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "Response = " + e.toString());
                        getMldStageDescriptionForAppEntry().setValue(
                                "getShops(receiving) - onError()"
                                        + getDescriptionLineFromThrowable(e));
                    }
                });
    }

    private void getWorkers_fromServer(String enterpriseID) {
        new RemoteRepositoryImpl().getWorkers(enterpriseID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<User>>() {
                    @Override
                    public void onSuccess(List<User> users) {
                        if (users != null) {
                            new LocalDbRepositoryImpl(getMyContext()).addWorkers_replaceData(users);
                            isUpdateDataFromServer.put(TypeDataFromServ.WORKERS.getTitle(), true);
                            isPartUpdateOK_dataFromServer_rx();
                            getMldStageDescriptionForAppEntry().setValue("getUsers(receiving) - OK");
                        } else
                            getMldStageDescriptionForAppEntry().setValue("getUsers(receiving) - false");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "Response = " + e.toString());
                        getMldStageDescriptionForAppEntry().setValue(
                                "getUsers(receiving) - onError()"
                                        + getDescriptionLineFromThrowable(e));
                    }
                });
    }

    private void getRoles_fromServer() {
        new RemoteRepositoryImpl().getRoles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Role>>() {
                    @Override
                    public void onSuccess(List<Role> roles) {
                        if (roles != null) {
                            new LocalDbRepositoryImpl(getMyContext()).addRoles_replaceData(roles);
                            isUpdateDataFromServer.put(TypeDataFromServ.ROLES.getTitle(), true);
                            isPartUpdateOK_dataFromServer_rx();
                            getMldStageDescriptionForAppEntry().setValue("getRoles(receiving) - OK");
                        } else
                            getMldStageDescriptionForAppEntry().setValue("getRoles(receiving) - false");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "Response = " + e.toString());
                        getMldStageDescriptionForAppEntry().setValue(
                                "getRoles(receiving) - onError()"
                                        + getDescriptionLineFromThrowable(e));
                    }
                });
    }

    private void getProducts_fromServer(String enterpriseID, boolean isAdd) {
        if (isAdd) {
            isUpdateDataFromServer.put(TypeDataFromServ.PRODUCT.getTitle(), true);
            getMldStageDescriptionForAppEntry().setValue("getProducts(receiving) - no content");
            isPartUpdateOK_dataFromServer_rx();
        } else {
            new RemoteRepositoryImpl().getProduct(enterpriseID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<List<Product>>() {
                        @Override
                        public void onSuccess(List<Product> products) {
                            if (products != null) {
                                new LocalDbRepositoryImpl(getMyContext()).addProduct_replaceData(products);
                                isUpdateDataFromServer.put(TypeDataFromServ.PRODUCT.getTitle(), true);
                                isPartUpdateOK_dataFromServer_rx();
                                getMldStageDescriptionForAppEntry().setValue("getProducts(receiving) - OK");
                            } else
                                getMldStageDescriptionForAppEntry().setValue("getProducts(receiving) - false");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("TAG", "Response = " + e.toString());
                            getMldStageDescriptionForAppEntry().setValue(
                                    "getProducts(receiving) - onError()"
                                            + getDescriptionLineFromThrowable(e));
                        }
                    });
        }
    }
}