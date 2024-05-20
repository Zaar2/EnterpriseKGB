package com.zaar.meatkgb2_m.viewModel.viewModels.setting;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zaar.meatkgb2_m.model.entity.Enterprise;
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl;
import com.zaar.meatkgb2_m.model.repository.RemoteRepositoryImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Enterprise_VM extends BaseVM_setting {

        public static String getName(){
            return Enterprise_VM.class.getSimpleName();
        }

    public Enterprise_VM(Context myContext, String initiator) {
        super(myContext, initiator);
    }

    //region-------------------BASE methods
    public void updEnterprise(String name, String inn, String email) {
        Enterprise enterprise = new LocalDbRepositoryImpl(getMyContext()).getEnterprise();
        enterprise.setName(name);
        enterprise.setInn(inn);
        enterprise.setEmail(email);

        Call<String> call = new RemoteRepositoryImpl().updEnterprise(enterprise);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (checkHttpCodeAndObtainingNewSessionId(
                            response.code(),
                            "SettingVM_enterprise.updEnterprise()",
                            Enterprise_VM.class.getSimpleName())
                    ) {
                        String enterpriseID_upd;
                        if (response.body() != null) {
                            enterpriseID_upd = response.body();
                        } else enterpriseID_upd = enterprise.getEnterpriseId();
                        int countUpd = new LocalDbRepositoryImpl(getMyContext()).updEnterprise(enterprise);
                        _ld_count_updResult_enterprise.postValue(countUpd);
                        _ld_id_updResult_enterprise.postValue(enterpriseID_upd);
                    }
                } else {
                    Log.d("ERROR RETROFIT: SettingVM_enterprise.updEnterprise()",
                            "response is not successful or body is empty");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t);
                Log.d("onFailureResponse", getDescriptionLineFromThrowable(t));
            }
        });
    }

    /** method code is not updated
    public void delEnterprise(String enterpriseID) {
        Call<String> call = new ServerRepositoryImpl().delEnterprise(enterpriseID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() &&
                        response.code() != HTTP_STATUS_SESSION_EXPIRED_EXISTS &&
                        response.body() != null
                ) {
                    String enterpriseID_del = response.body();
                    Enterprise enterprise_local = new LocalDbRepositoryImpl(getMyContext()).getEnterprise();
                    int countDel = new LocalDbRepositoryImpl(getMyContext()).delEnterprise(enterprise_local);
                    List<Integer> list = new ArrayList<>();
                    list.add(countDel);
                    _ld_count_delResult_enterprise.postValue(list);
                    _ld_id_deleteResult_enterprise.postValue(enterpriseID_del);
                } else {
                    Log.d("ERROR RETROFIT", "code is " + response.code());
                    if (response.code() == HTTP_STATUS_SESSION_EXPIRED_EXISTS) {
                        String enterpriseID_local = new LocalDbRepositoryImpl(getMyContext()).getCryptoIdEnterprise();
                        updateSession(response.code(), enterpriseID_local, false);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t);
                Log.d("onFailureResponse", getDescriptionLineFromThrowable(t));
            }
        });
    }
     */

    //endregion

    //region-------------------other methods
    public void getEnterprise() {
        Enterprise enterprise = new LocalDbRepositoryImpl(getMyContext()).getEnterprise();
        _ld_enterpriseData.setValue(enterprise);
    }

    //endregion
}