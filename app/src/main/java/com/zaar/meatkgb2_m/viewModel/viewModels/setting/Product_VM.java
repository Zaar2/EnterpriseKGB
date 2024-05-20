package com.zaar.meatkgb2_m.viewModel.viewModels.setting;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zaar.meatkgb2_m.model.local.EnabledAppUser;
import com.zaar.meatkgb2_m.model.entity.Product;
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl;
import com.zaar.meatkgb2_m.model.repository.RemoteRepositoryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_VM extends BaseVM_setting {

    public static String getName(){
        return Product_VM.class.getSimpleName();
    }

    public Product_VM(Context myContext, String initiator) {
        super(myContext, initiator);
    }

//    public String currentShop="testViewModel";
    //region-------------------BASE methods
    public void addProduct(HashMap<String, String> product) {

        Product product_income = prepareProductClass(product);
        Call<List<Product>> call = new RemoteRepositoryImpl().addProduct(product_income);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (checkHttpCodeAndObtainingNewSessionId(
                            response.code(),
                            "SettingVM_product.addProduct()",
                            Product_VM.class.getSimpleName())
                    ) {
                        Product product = response.body().get(0);
                        long id = new LocalDbRepositoryImpl(getMyContext()).addProduct(product);
                        _ld_product.postValue(product);
                        _ld_id_insertResult_product.postValue(id);
                    } else {
                        Log.d("ERROR RETROFIT: SettingVM_product.addProduct()",
                                "response is not successful or body is empty");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t);
                Log.d("onFailureResponse", getDescriptionLineFromThrowable(t));
            }
        });
    }

    public void updProduct(HashMap<String, String> product) {
        Product product_income = prepareProductClass(product);

        Call<Long> call = new RemoteRepositoryImpl().updProduct(product_income);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if (response.isSuccessful()) {
                    if (checkHttpCodeAndObtainingNewSessionId(
                            response.code(),
                            "SettingVM_product.updProduct()",
                            Product_VM.class.getSimpleName())
                    ) {
                        long productID_upd;
                        if (response.body() != null) {
                            productID_upd = response.body();
                            product_income.setId(productID_upd);
                        } else productID_upd = product_income.getId();
                        int countUpd = new LocalDbRepositoryImpl(getMyContext()).updProduct(product_income);
                        List<Integer> countList = new ArrayList<>();
                        countList.add(countUpd);
                        List<Long> idList = new ArrayList<>();
                        idList.add(productID_upd);

                        _ld_count_updResult_product.postValue(countList);
                        _ld_id_insertUpd_product.postValue(idList);
                    } else {
                        Log.d("ERROR RETROFIT: SettingVM_product.updProduct()",
                                "response is not successful or body is empty");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Long> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t);
                Log.d("onFailureResponse", getDescriptionLineFromThrowable(t));
            }
        });
    }

    public void delProduct(long idProduct) {
        Call<Long> call = new RemoteRepositoryImpl().delProduct(idProduct);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (checkHttpCodeAndObtainingNewSessionId(
                            response.code(),
                            "SettingVM_product.delProduct()",
                            Product_VM.class.getSimpleName())
                    ) {
                        long ID_del = response.body();
                        int countDel = new LocalDbRepositoryImpl(getMyContext()).deleteById(ID_del);
                        List<Integer> list = new ArrayList<>();
                        list.add(countDel);
                        _ld_count_delResult_product.postValue(list);
                        _ld_id_deletedResult_product.postValue(ID_del);
                    } else {
                        Log.d("ERROR RETROFIT: SettingVM_product.updProduct()",
                                "response is not successful or body is empty");
                    }
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

//    public void getAllManufactureShop_names() {
//        _ld_shopsNamesStrList.setValue(
//                new LocalDbRepositoryImpl(getMyContext()).getAllManufactureShops()
//        );
//    }

    public void getAllNamesProductByShop(String nameShop) {
        long idShop = new LocalDbRepositoryImpl(getMyContext()).getIdShopByName(nameShop);
        List<String> productsOfShop =
                new LocalDbRepositoryImpl(getMyContext()).getAllNamesProductByShop((int) idShop);
        getMldNameProducts().setValue(productsOfShop);
    }

//    public void getAllNamesProduct() {
//        List<String> productsOfShop =
//                new LocalDbRepositoryImpl(getMyContext()).getAllNamesProduct();
//        getMldNameProducts().setValue(productsOfShop);
//    }

    public void getProductByName(String nameProduct) {
        Product product = new LocalDbRepositoryImpl(getMyContext()).getProductByName(nameProduct);
        _ld_product.setValue(product);
    }

    public void getNameShopById(int idShop) {
        String nameShop = new LocalDbRepositoryImpl(getMyContext()).getNameShopByID(idShop);
        _ld_shopNameStr.setValue(nameShop);
    }

    public void getNameShopByProductName(String nameProduct) {
        long idShop = new LocalDbRepositoryImpl(getMyContext()).getIdWorkshopByNameProduct(nameProduct);
        String nameShop = new LocalDbRepositoryImpl(getMyContext()).getNameShopByID(idShop);
        _ld_shopNameStr.setValue(nameShop);
    }

    private Product prepareProductClass(HashMap<String, String> product) {
        String enterpriseId = new LocalDbRepositoryImpl(getMyContext()).getCryptoIdEnterprise();
        long id_workshop = new LocalDbRepositoryImpl(getMyContext()).getIdShopByName(product.get("id_workshop"));
        long id_usrLogin = new LocalDbRepositoryImpl(getMyContext()).getIdUserByLogin(
                EnabledAppUser.getINSTANCE().getUsrLogin()
        );
        Product product_outcome = new Product(
                enterpriseId,
                product.get("product_name"),
                product.get("me"),
                (int) id_workshop,
                Integer.parseInt((Objects.requireNonNull(product.get("accuracy")))),
                Integer.parseInt((Objects.requireNonNull(product.get("vsd_support")))),
                "",
                (int) id_usrLogin,
                Integer.parseInt((Objects.requireNonNull(product.get("id_status"))))
        );
        product_outcome.setDateCreatedIsCurrent();
        if (product.get("id") != null)
            product_outcome.setId(Long.parseLong(Objects.requireNonNull(product.get("id"))));
        return product_outcome;
    }

    //endregion
}