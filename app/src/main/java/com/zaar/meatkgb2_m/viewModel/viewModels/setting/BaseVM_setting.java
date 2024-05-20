package com.zaar.meatkgb2_m.viewModel.viewModels.setting;

import static com.zaar.meatkgb2_m.utilities.Const.HTTP_ERR_RESPONSE_SERV_ERROR_DB;
import static com.zaar.meatkgb2_m.utilities.Const.HTTP_ERR_RESPONSE_SERV_NOT_FOUND;
import static com.zaar.meatkgb2_m.utilities.Const.HTTP_ERR_SESSION_EXPIRED_EXISTS;
import static com.zaar.meatkgb2_m.utilities.Const.HTTP_STATUS_ZERO_AFFECTED_ROWS;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zaar.meatkgb2_m.data.LogPass;
import com.zaar.meatkgb2_m.data.Workers_shortDescription;
import com.zaar.meatkgb2_m.model.entity.Enterprise;
import com.zaar.meatkgb2_m.model.entity.Product;
import com.zaar.meatkgb2_m.model.entity.Role;
import com.zaar.meatkgb2_m.model.entity.Shop;
import com.zaar.meatkgb2_m.model.entity.User;
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl;
import com.zaar.meatkgb2_m.viewModel.viewModels.BaseVM;

import java.util.ArrayList;
import java.util.List;

public class BaseVM_setting extends BaseVM {
    public BaseVM_setting(Context myContext, String initiator) {
        super(myContext, initiator);
    }

    //region-------------------app liveData variables
    protected MutableLiveData<String> _ld_sessionID = new MutableLiveData<>();
    public LiveData<String> ld_sessionID = _ld_sessionID;

    protected MutableLiveData<LogPass> _ld_userData = new MutableLiveData<>();
    public LiveData<LogPass> ld_userData = _ld_userData;
    //endregion

    //region-------------------ENTERPRISE liveData  variables
    protected MutableLiveData<Enterprise> _ld_enterpriseData = new MutableLiveData<>();
    public LiveData<Enterprise> ld_enterpriseData = _ld_enterpriseData;

    protected MutableLiveData<Integer> _ld_count_updResult_enterprise = new MutableLiveData<>();
    public LiveData<Integer> ld_count_updResult_enterprise = _ld_count_updResult_enterprise;

    protected MutableLiveData<String> _ld_id_updResult_enterprise = new MutableLiveData<>();
    public LiveData<String> ld_id_updResult_enterprise = _ld_id_updResult_enterprise;

    protected MutableLiveData<String> _ld_id_deleteResult_enterprise = new MutableLiveData<>();
    public LiveData<String> ld_id_deleteUpd_enterprise = _ld_id_deleteResult_enterprise;

    protected MutableLiveData<List<Integer>> _ld_count_delResult_enterprise = new MutableLiveData<>();
    public LiveData<List<Integer>> ld_count_delResult_enterprise = _ld_count_delResult_enterprise;
    //endregion

    //region-------------------USER liveData  variables
    protected MutableLiveData<Bundle> _ld_worker = new MutableLiveData<>();
    public LiveData<Bundle> ld_worker = _ld_worker;

    protected MutableLiveData<List<User>> _ld_usersClass = new MutableLiveData<>();
    public LiveData<List<User>> ld_usersClass = _ld_usersClass;

    protected MutableLiveData<Long> _ld_id_insertResult_user = new MutableLiveData<>();
    public LiveData<Long> ld_id_insertResult_user = _ld_id_insertResult_user;

    protected MutableLiveData<User> _ld_addUser = new MutableLiveData<>();
    public LiveData<User> ld_addUser = _ld_addUser;

    protected MutableLiveData<List<Long>> _ld_id_insertUpd_user = new MutableLiveData<>();
    public LiveData<List<Long>> ld_id_insertUpd_user = _ld_id_insertUpd_user;

    protected MutableLiveData<List<Workers_shortDescription>> _ld_usersDescription = new MutableLiveData<>();
    public LiveData<List<Workers_shortDescription>> ld_usersDescription = _ld_usersDescription;

    protected MutableLiveData<Integer> _ld_countUsers = new MutableLiveData<>();
    public LiveData<Integer> ld_countUsers = _ld_countUsers;

    protected MutableLiveData<List<Integer>> _ld_count_delResult_worker = new MutableLiveData<>();
    public LiveData<List<Integer>> ld_count_delResult_worker = _ld_count_delResult_worker;

    protected MutableLiveData<List<Integer>> _ld_count_updResult_worker = new MutableLiveData<>();
    public LiveData<List<Integer>> ld_count_updResult_worker = _ld_count_updResult_worker;

    protected MutableLiveData<Long> _ld_id_deletedResult_worker = new MutableLiveData<>();
    public LiveData<Long> ld_id_deletedResult_user = _ld_id_deletedResult_worker;
    //endregion

    //region-------------------SHOP liveData  variables
    protected MutableLiveData<Shop> _ld_shop = new MutableLiveData<>();
    public LiveData<Shop> ld_shop = _ld_shop;

//    protected MutableLiveData<List<String>> _ld_shopsNamesStrList = new MutableLiveData<>();
//    public LiveData<List<String>> ld_shopsNamesStrList = _ld_shopsNamesStrList;

    protected MutableLiveData<String> _ld_shopNameStr = new MutableLiveData<>();
    public LiveData<String> ld_shopNameStr = _ld_shopNameStr;

    protected MutableLiveData<List<Shop>> _ld_shopsClass = new MutableLiveData<>();
    public LiveData<List<Shop>> ld_shopsClass = _ld_shopsClass;

    protected MutableLiveData<Long> _ld_id_insertResult_shops = new MutableLiveData<>();
    public LiveData<Long> ld_id_insertResult_shops = _ld_id_insertResult_shops;

    protected MutableLiveData<Shop> _ld_addShops = new MutableLiveData<>();
    public LiveData<Shop> ld_addShops = _ld_addShops;

    protected MutableLiveData<List<Integer>> _ld_count_updResult_shops = new MutableLiveData<>();
    public LiveData<List<Integer>> ld_count_updResult_shops = _ld_count_updResult_shops;

    protected MutableLiveData<List<Integer>> _ld_count_delResult_shops = new MutableLiveData<>();
    public LiveData<List<Integer>> ld_count_delResult_shops = _ld_count_delResult_shops;

    protected MutableLiveData<Long> _ld_id_deletedResult_shops = new MutableLiveData<>();
    public LiveData<Long> ld_id_deletedResult_shops = _ld_id_deletedResult_shops;

    protected MutableLiveData<Long> _ld_id_updResult_shops = new MutableLiveData<>();
    public LiveData<Long> ld_id_updResult_shops = _ld_id_updResult_shops;

    //endregion

    //region-------------------SHOP liveData  variables

    protected MutableLiveData<List<String>> _ld_rolesNamesStrList = new MutableLiveData<>();
    public LiveData<List<String>> ld_rolesNamesStrList = _ld_rolesNamesStrList;

    //endregion

    //region-------------------PRODUCT liveData  variables
    protected MutableLiveData<Product> _ld_product = new MutableLiveData<>();
    public LiveData<Product> ld_product = _ld_product;

    protected MutableLiveData<Long> _ld_id_insertResult_product = new MutableLiveData<>();
    public LiveData<Long> ld_id_insertResult_product = _ld_id_insertResult_product;

    protected MutableLiveData<List<Long>> _ld_id_insertUpd_product = new MutableLiveData<>();
    public LiveData<List<Long>> ld_id_insertUpd_product = _ld_id_insertUpd_product;

    protected MutableLiveData<Long> _ld_id_deletedResult_product = new MutableLiveData<>();
    public LiveData<Long> ld_id_deletedResult_product = _ld_id_deletedResult_product;

//    protected MutableLiveData<List<String>> _ld_productsOfShop = new MutableLiveData<>();
//    public LiveData<List<String>> ld_productsOfShop = _ld_productsOfShop;

    protected MutableLiveData<User> _ld_addProduct = new MutableLiveData<>();
    public LiveData<User> ld_addProduct = _ld_addProduct;

    protected MutableLiveData<List<Integer>> _ld_count_updResult_product = new MutableLiveData<>();
    public LiveData<List<Integer>> ld_count_updResult_product = _ld_count_updResult_product;

    protected MutableLiveData<List<Integer>> _ld_count_delResult_product = new MutableLiveData<>();
    public LiveData<List<Integer>> ld_count_delResult_product = _ld_count_delResult_product;
    //endregion

//    public void preferencesClear() {
////        new SaveAppSettingsImpl(myContext).clear();
//        new SaveAppSettingsImpl(getMyContext()).deleteKeys(new String[]{
//                key_usrLog,
//                key_usrPass
//        });
//    }
//
//    public void db_clear() {
//        LocalDbRepositoryImpl repository = new LocalDbRepositoryImpl(getMyContext());
//        repository.clearDB();
//    }


//    public void getSessionID(String enterpriseCryptoId, String usrLogin, String usrPass) {
//        LogPass logPass = new LogPass();
//        logPass.enterpriseId = enterpriseCryptoId;
//        logPass.usr_login = usrLogin;
//        logPass.usr_pass = usrPass;
//        Call<String> call = new ServerRepositoryImpl().identificationUser(logPass);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    String sessionID = response.body();
//                    if (sessionID != null && !sessionID.equals("false")) {
//                        new SaveAppSettingsImpl(getMyContext()).setSessionID(sessionID);
//                        _ld_sessionID.postValue(sessionID);
//                    }
//                } else {
//                    Log.d("ERROR RETROFIT", String.valueOf(response.code()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("TAG", "Response = " + t.toString());
//            }
//        });
//    }

//    /**
//     * <p>Preparing parameters for method <b>getSessionID()</b> and running it</p>
//     * <p>Inside of method of <b>getSessionID()</b>
//     * LiveData ld_sessionID.postValue(received new sessionID)</p>
//     */
//    protected <T extends Response<?>>
//    void updateSession(T response, String enterpriseId, String method) {
//        notSuccessfulResponse = new NotSuccessful_response(
//                response.code(),
//                "sessionID_hasExpired",
//                method,
//                "trying to get data in Callback() from Call<List<User>>"
//        );
//        Bundle logPass = new SaveAppSettingsImpl(getMyContext()).
//                getPreferencesVal(new String[]{getKeyUsrLog(), getKeyUsrPass()});
//        obtainingValidSessionIDRx(
//                enterpriseId,
//                Objects.requireNonNull(logPass.getString(getKeyUsrLog())),
//                Objects.requireNonNull(logPass.getString(getKeyUsrPass())),
//                false
//        );
//    }

    public void getAllShops() {
        List<Shop> shops = new LocalDbRepositoryImpl(getMyContext()).getAllShops();
        List<String> names = new ArrayList<>();
        if (shops != null) {
            for (int i = 0; i < shops.size(); i++) {
                names.add(shops.get(i).getName());
            }
            getMldShopsNamesStrList().setValue(names);
        } else getMldShopsNamesStrList().setValue(null);
    }

    public void getAllRoles() {
        List<Role> roles = new LocalDbRepositoryImpl(getMyContext()).getAllRolesClass();
        List<String> names = new ArrayList<>();
        if (roles != null) {
            for (int i = 0; i < roles.size(); i++) {
                names.add(roles.get(i).getName());
            }
            _ld_rolesNamesStrList.setValue(names);
        } else _ld_rolesNamesStrList.setValue(null);
    }

    protected boolean checkHttpCodeAndObtainingNewSessionId(
            int responseHttpCode,
            String pathFuncInitiator,
            String classInitiator
    ) {
        /** with enum
//        boolean result=false;
//        if (classInitiator.equals(ClassesSetting.ENTERPRISE.getName())){
//
//        }else if (classInitiator.equals(ClassesSetting.SHOP.getName())){
//
//        } else if (classInitiator.equals(ClassesSetting.PRODUCT.getName())) {
//
//        } else if (classInitiator.equals(ClassesSetting.WORKER.getName())) {
//
//        }else result=false;
        */
        if (responseHttpCode == HTTP_ERR_SESSION_EXPIRED_EXISTS
                || responseHttpCode == HTTP_ERR_RESPONSE_SERV_ERROR_DB
//                || responseHttpCode == HTTP_ERR_RESPONSE_SERV_NO_CONTENT
//                || responseHttpCode == HTTP_ERR_RESPONSE_SERV_HEADER_TEXT
                || responseHttpCode == HTTP_ERR_RESPONSE_SERV_NOT_FOUND
                || responseHttpCode == HTTP_STATUS_ZERO_AFFECTED_ROWS
        ) {
            Log.d("ERROR RETROFIT in " + pathFuncInitiator, "code is " + responseHttpCode);
            if (responseHttpCode == HTTP_ERR_SESSION_EXPIRED_EXISTS) {
                String enterpriseID_local = new LocalDbRepositoryImpl(getMyContext()).getCryptoIdEnterprise();
                updateSession(responseHttpCode, enterpriseID_local, false);
            }
            return false;
        } else
            return true;
    }

    public void clearContext() {
//        setMyContext(null);
    }
}