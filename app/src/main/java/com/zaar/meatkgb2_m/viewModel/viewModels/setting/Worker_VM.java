package com.zaar.meatkgb2_m.viewModel.viewModels.setting;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.data.Workers_shortDescription;
import com.zaar.meatkgb2_m.model.entity.User;
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl;
import com.zaar.meatkgb2_m.model.repository.SharedPreferencesImpl;
import com.zaar.meatkgb2_m.model.repository.RemoteRepositoryImpl;
import com.zaar.meatkgb2_w.utilities.types.TypeKeyForStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Worker_VM extends BaseVM_setting {

    public static String getName(){
        return Worker_VM.class.getSimpleName();
    }

    public Worker_VM(Context myContext, String initiator) {
        super(myContext, initiator);
    }

    private MutableLiveData<Boolean> mldVisibilitySpinnerMoreWorkshop = new MutableLiveData<>();
    public LiveData<Boolean> ldVisibilitySpinnerMoreWorkshop = mldVisibilitySpinnerMoreWorkshop;

    //region-------------------BASE methods
    public void addWorker(Bundle user) {
        String enterpriseId = new LocalDbRepositoryImpl(getMyContext()).getCryptoIdEnterprise();
        user.putString("enterpriseId", enterpriseId);
        user.putLong("id_workshop", new LocalDbRepositoryImpl(getMyContext()).getIdShopByName(user.getString("shop")));
        user.putLong("id_one_more_workshop", new LocalDbRepositoryImpl(getMyContext()).getIdShopByName(user.getString("oneMoreWorkshop")));
        user.putString(
                "login_user_created",
                new SharedPreferencesImpl(getMyContext()).getPreferencesVal(new String[]{TypeKeyForStore.KEY_USR_LOG.getValue()})
                        .getString(TypeKeyForStore.KEY_USR_LOG.getValue(), "non")
        );
        User user_income = bundleToUserObject(user);

        Call<List<User>> call = new RemoteRepositoryImpl().addWorker(user_income);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (checkHttpCodeAndObtainingNewSessionId(
                        response.code(),
                        "SettingVM_worker.addWorker()",
                        Worker_VM.class.getSimpleName())
                ) {
                    User user_serv = response.body().get(0);
                    long id = new LocalDbRepositoryImpl(getMyContext()).addWorker(user_serv);
                    _ld_addUser.postValue(user_serv);
                    _ld_id_insertResult_user.postValue(id);
                } else {
                    Log.d("ERROR RETROFIT: SettingVM_product.updProduct()",
                            "response is not successful or body is empty");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d("TAG", "Response = " + t);
                Log.d("onFailureResponse", getDescriptionLineFromThrowable(t));
            }
        });
    }

    public void delWorker(long idWorker) {
        Call<Long> call = new RemoteRepositoryImpl().delWorker(idWorker);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if (checkHttpCodeAndObtainingNewSessionId(
                        response.code(),
                        "SettingVM_worker.delWorker()",
                        Worker_VM.class.getSimpleName())
                ) {
                    long workerID_del = response.body();
                    User worker_local = new LocalDbRepositoryImpl(getMyContext()).getWorkerById(workerID_del);
                    int countDel = new LocalDbRepositoryImpl(getMyContext()).delWorker(worker_local);
                    List<Integer> list = new ArrayList<>();
                    list.add(countDel);
                    _ld_count_delResult_worker.postValue(list);
                    _ld_id_deletedResult_worker.postValue(workerID_del);
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

    public void updWorker(Bundle user) {
        user.putString("enterpriseId", new LocalDbRepositoryImpl(getMyContext()).getCryptoIdEnterprise());
        user.putLong("id_workshop", new LocalDbRepositoryImpl(getMyContext()).getIdShopByName(
                user.getString(Objects.requireNonNull(getMyContext()).getString(R.string.workshop))));
        user.putLong("id_one_more_workshop", new LocalDbRepositoryImpl(getMyContext()).getIdShopByName(
                user.getString(getMyContext().getString(R.string.one_more_workshop))));
        User userClass = bundleToUserObject(user);
        userClass.setId(Long.parseLong(user.getString("id", "-1")));

        Call<Long> call = new RemoteRepositoryImpl().updWorker(userClass);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                if (checkHttpCodeAndObtainingNewSessionId(
                        response.code(),
                        "SettingVM_worker.updWorker()",
                        Worker_VM.class.getSimpleName())
                ) {
                    long workerID_upd;
                    if (response.body() != null) {
                        workerID_upd = response.body();
                        userClass.setId(workerID_upd);
                    } else workerID_upd = userClass.getId();
                    int countUpd = new LocalDbRepositoryImpl(getMyContext()).updWorker(userClass);
                    List<Integer> countList = new ArrayList<>();
                    countList.add(countUpd);
                    List<Long> idList = new ArrayList<>();
                    idList.add(workerID_upd);

                    _ld_count_updResult_worker.postValue(countList);
                    _ld_id_insertUpd_user.postValue(idList);
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
    public void getCountWorkers() {
        int count = new LocalDbRepositoryImpl(getMyContext()).getCountUsers();
        _ld_countUsers.setValue(count);
    }

    public void getWorkerById(long id) {
        User user = new LocalDbRepositoryImpl(getMyContext()).getWorkerById(id);
//        _ld_worker.setValue(user);
        String workshop = new LocalDbRepositoryImpl(getMyContext()).getNameShopByID(user.getId_workshop());
        String oneMoreWorkshop = new LocalDbRepositoryImpl(getMyContext()).getNameShopByID(user.getId_one_more_workshop());
        Bundle userBundle = userObjToBundle(user);
        userBundle.putString("shop", workshop);
        userBundle.putString("oneMoreWorkshop", oneMoreWorkshop);
        _ld_worker.setValue(userBundle);
    }


    public void getAllWorkersDescription() {
        List<Workers_shortDescription> workersShortDescriptions =
                new LocalDbRepositoryImpl(getMyContext()).getWorkersShortDescription();
        for (int i = 0; i < workersShortDescriptions.size(); i++) {
            long id_workshop = workersShortDescriptions.get(i).id_workshop;
            workersShortDescriptions.get(i).nameWorkshop =
                    new LocalDbRepositoryImpl(getMyContext()).getNameShopByID(id_workshop);
        }
        this._ld_usersDescription.setValue(workersShortDescriptions);
    }

    public void getAllWorkersClass() {
        List<User> users = new LocalDbRepositoryImpl(getMyContext()).getAllUsersClass();
        this._ld_usersClass.setValue(users);
    }

    public void getVisibilitySpinnerMoreWorkshop(String nameShop){
        int role = new LocalDbRepositoryImpl(getMyContext()).getRoleShop(nameShop);
        if (role==3) mldVisibilitySpinnerMoreWorkshop.setValue(true);
        else mldVisibilitySpinnerMoreWorkshop.setValue(false);
    }

    private User bundleToUserObject(Bundle user_income) {
        User user = new User(
                user_income.getString("fullName", "non"),
                user_income.getString("shortName", "non"),
                user_income.getString("login", "non"),
                user_income.getLong("id_workshop", -1L),
                user_income.getString("appointment", "non"),
                user_income.getString("pass", "non"),
                user_income.getString("enterpriseId", "non"),
                Integer.parseInt(Objects.requireNonNull(user_income.getString("id_status", "-1"))),
                user_income.getLong("id_one_more_workshop", -1L)
        );
        user.setLogin_user_created(user_income.getString("login_user_created", "non"));
        return user;
    }

    private Bundle userObjToBundle(User userIncome) {
        Bundle userOutcome = new Bundle();
        userOutcome.putString(Objects.requireNonNull(getMyContext()).getString(R.string.fullName_user), userIncome.getFull_name());
        userOutcome.putString(getMyContext().getString(R.string.shortName_user), userIncome.getShort_name());
        userOutcome.putString(getMyContext().getString(R.string.workshop), String.valueOf(userIncome.getId_workshop()));
        userOutcome.putString(getMyContext().getString(R.string.appointment_user), userIncome.getAppointment());
        userOutcome.putString(getMyContext().getString(R.string.login_user), userIncome.getUsr_login());
        userOutcome.putString(getMyContext().getString(R.string.pass_user), userIncome.getUsr_pass());
        userOutcome.putInt(getMyContext().getString(R.string.idStatus_product), userIncome.getId_status());
        userOutcome.putString(getMyContext().getString(R.string.one_more_workshop), String.valueOf(userIncome.getId_one_more_workshop()));
        return userOutcome;
    }
    //endregion
}