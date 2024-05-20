package com.zaar.meatkgb2_m.viewModel.viewModels.setting;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zaar.meatkgb2_m.model.entity.Shop;
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl;
import com.zaar.meatkgb2_m.model.repository.RemoteRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shop_VM extends BaseVM_setting {

    public static String getName(){
        return Shop_VM.class.getSimpleName();
    }

    public Shop_VM(Context myContext, String initiator) {
        super(myContext, initiator);
    }

    //region-------------------BASE methods
    public void addShop(String nameShop,
                        String shortNameShop,
                        int nonManufacture_isChecked,
                        String roleName,
                        int id_status) {
        String enterpriseId = new LocalDbRepositoryImpl(getMyContext()).getCryptoIdEnterprise();
        long idRole = new LocalDbRepositoryImpl(getMyContext()).getIdRoleByName(roleName);
        Shop shop_income = new Shop(
                nameShop,
                shortNameShop,
                enterpriseId,
                nonManufacture_isChecked,
                "",
                idRole,
                id_status
        );
        Call<List<Shop>> call = new RemoteRepositoryImpl().addShop(shop_income);
        call.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(@NonNull Call<List<Shop>> call, @NonNull Response<List<Shop>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (checkHttpCodeAndObtainingNewSessionId(
                            response.code(),
                            "SettingVM_shop.addShop()",
                            Shop_VM.class.getSimpleName())
                    ) {
                        Shop shop_serv = response.body().get(0);
                        long id = new LocalDbRepositoryImpl(getMyContext()).addShop(shop_serv);
                        _ld_id_insertResult_shops.postValue(id);
                        _ld_addShops.postValue(shop_serv);
                    } else {
                        Log.d("ERROR RETROFIT: SettingVM_product.updProduct()",
                                "response is not successful or body is empty");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Shop>> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t);
                Log.d("onFailureResponse", getDescriptionLineFromThrowable(t));
            }
        });
//        getAllShops();
    }

    public void delShop(String nameShop) {
        long shopID = new LocalDbRepositoryImpl(getMyContext()).getIdShopByName(nameShop);
        Call<Long> call = new RemoteRepositoryImpl().delShop(shopID);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if (checkHttpCodeAndObtainingNewSessionId(
                        response.code(),
                        "SettingVM_shop.delShop()",
                        Shop_VM.class.getSimpleName())
                ) {
                    long shopID_del = response.body();
                    Shop shop_local = new LocalDbRepositoryImpl(getMyContext()).getShopByID(shopID_del);
                    int countDel = new LocalDbRepositoryImpl(getMyContext()).delShop(shop_local);
                    List<Integer> list = new ArrayList<>();
                    list.add(countDel);
                    _ld_count_delResult_shops.setValue(list);
                    _ld_id_deletedResult_shops.postValue(shopID_del);
//                    getAllShops();
                } else {
                    Log.d("ERROR RETROFIT: SettingVM_product.updProduct()",
                            "response is not successful or body is empty");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Long> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t);
                Log.d("onFailureResponse", getDescriptionLineFromThrowable(t));
            }
        });
    }

    public void updShop(String old_nameShop,
                        String new_nameShop,
                        int new_checkboxIsChecked,
                        String roleName,
                        int id_status
    ) {
        long idRole = new LocalDbRepositoryImpl(getMyContext()).getIdRoleByName(roleName);
        Shop shop = new LocalDbRepositoryImpl(getMyContext()).getShopByName(old_nameShop);
        shop.setName(new_nameShop);
        shop.setNonManufacture_isChecked(new_checkboxIsChecked);
        shop.setId_type_role(idRole);
        shop.setId_status(id_status);

        Call<Long> call = new RemoteRepositoryImpl().updShop(shop);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if (checkHttpCodeAndObtainingNewSessionId(
                        response.code(),
                        "SettingVM_shop.updShop()",
                        Shop_VM.class.getSimpleName())
                ) {
                    long shopID_upd;
                    if (response.body() != null) {
                        shopID_upd = response.body();
                    } else shopID_upd = shop.getId();
                    int countUpd = new LocalDbRepositoryImpl(getMyContext()).updShop(shop);
                    List<Integer> list = new ArrayList<>();
                    list.add(countUpd);
                    _ld_count_updResult_shops.setValue(list);
                    _ld_id_updResult_shops.postValue(shopID_upd);
                } else {
                    Log.d("ERROR RETROFIT: SettingVM_product.updProduct()",
                            "response is not successful or body is empty");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Long> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t);
                Log.d("onFailureResponse", getDescriptionLineFromThrowable(t));
            }
        });
    }

    //endregion

    //region-------------------other methods
    public void getShopByName(String nameShop) {
        Shop shop = new LocalDbRepositoryImpl(getMyContext()).getShopByName(nameShop);
        shop.setNameRole(new LocalDbRepositoryImpl(getMyContext()).getNameRoleById(shop.getId_type_role()));
        _ld_shop.setValue(shop);
    }
    //endregion
}
