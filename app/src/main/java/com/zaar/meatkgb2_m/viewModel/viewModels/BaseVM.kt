package com.zaar.meatkgb2_m.viewModel.viewModels

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaar.meatkgb2_m.data.LogPass
import com.zaar.meatkgb2_m.model.repository.LocalDbRepositoryImpl
import com.zaar.meatkgb2_m.model.repository.SharedPreferencesImpl
import com.zaar.meatkgb2_m.model.repository.RemoteRepositoryImpl
import com.zaar.meatkgb2_w.utilities.types.TypeKeyForStore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.Objects

open class BaseVM(
    var myContext: Context,
    val initiator: String
): ViewModel() {
//    protected var sessionID = ""

    protected val keyUsrLog = TypeKeyForStore.KEY_USR_LOG.value
    protected val keyUsrPass = TypeKeyForStore.KEY_USR_PASS.value
    private val strSessionID = TypeKeyForStore.KEY_SESSION_ID.value

    /**
     *
     * 0-initialise fragment
     *
     * 1-processingUpd
     *
     * 2-processingUpd is end
     */
    var stepProcessingUpd = 0

    //region-------------------RX LIVEDATA VARIABLES----------------------
    protected var mldStageDescriptionForAppEntry = MutableLiveData<String>()
    fun ldStageDescriptionForAppEntry(): LiveData<String> = mldStageDescriptionForAppEntry

    //endregion

    //region-------------------USER LIVEDATA VARIABLES----------------------
    protected var mldUserData = MutableLiveData<LogPass?>()
    fun ldUserData(): LiveData<LogPass?> = mldUserData

    //endregion

    //region-------------------APP LIVEDATA VARIABLES----------------------
    protected var mldSessionID = MutableLiveData<String>()
    fun ldSessionID(): LiveData<String> = mldSessionID

    //endregion

    //region-------------------COMMON LIVEDATA VARIABLES----------------------
    protected var mldShopsNamesStrList = MutableLiveData<List<String>>()
    fun ldShopsNamesStrList(): LiveData<List<String>> = mldShopsNamesStrList

    protected var mldNameProducts = MutableLiveData<List<String>>()
    fun ldNameProducts(): LiveData<List<String>> = mldNameProducts

    //endregion

    open fun preferencesClear() {
//        new SaveAppSettingsImpl(myContext).clear();
        SharedPreferencesImpl(myContext).deleteKeys(
            arrayOf<String>(
                keyUsrLog,
                keyUsrPass
            )
        )
    }

    open fun dbClear() {
        val repository = LocalDbRepositoryImpl(myContext)
        repository.clearDB()
    }

    /**
     *
     * check the existence of a login and pass in the SharedPreferences
     *
     * if them exist, an them values is assigned to the 'UserData' class
     *
     * and pasting it to the LiveData
     */
    fun getUserData() {
        val keys = arrayOf(keyUsrLog, keyUsrPass)
        val isContains = SharedPreferencesImpl(
            myContext
        ).containsPreferences(keys)
        var b = true
        for (key in keys) {
            if (java.lang.Boolean.FALSE == isContains.getBoolean(key)) b = false
        }
        if (b) {
            val bundle = SharedPreferencesImpl(
                myContext
            ).getPreferencesVal(keys)
            val logPass = LogPass()
            logPass.usrLogin = bundle.getString(keyUsrLog)
            logPass.usrPass = bundle.getString(keyUsrPass)
            mldUserData.setValue(logPass)
        } else mldUserData.setValue(null)
    }

    /**
     * save in the sharedPreference
     */
    private fun saveUserData(login: String, pass: String): String {
        val bundle = Bundle()
        bundle.putString(keyUsrLog, login)
        bundle.putString(keyUsrPass, pass)
        return SharedPreferencesImpl(
            myContext
        ).setPreferences(bundle) ?: ""
    }

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
    /**
     *
     * Preparing parameters for method **obtainingValidSessionIDRx()** and running it
     *
     * Inside of method of **obtainingValidSessionIDRx()**
     * LiveData ld_sessionID.postValue(received new sessionID)
     */
    protected open fun updateSession(
        responseCode: Int,
        enterpriseId: String,
        isAddNewEnterprise: Boolean
    ) {
        val logPass = SharedPreferencesImpl(
            myContext
        ).getPreferencesVal(
            arrayOf(keyUsrLog, keyUsrPass)
        )
        obtainingValidSessionID(
            enterpriseId,
            logPass.getString(keyUsrLog)!!,
            logPass.getString(keyUsrPass)!!,
            isAddNewEnterprise,
        )
    }

    /**
     *
     * 1.exists logPass in the localData,
     *
     * if true -> verification,
     *
     * if false -> step2
     *
     * 2.send income registry data to server and verification on server
     *
     * 3.exists sessionID in the localData,
     *
     * if true -> step4,
     *
     * if false -> step4 (but sessionID = "")
     *
     * 4.verification user on server and receiving sessionId
     */
    fun obtainingValidSessionID(
        idEnterprise: String,
        usrLog: String,
        usrPass: String,
        isAddNew: Boolean,
    ) {
        val regDataExistsLocal: Bundle =
            checkExistsLocalVerifyRegData(idEnterprise, usrLog, usrPass, isAddNew)
        val logPass = LogPass(idEnterprise, usrLog, usrPass)
        /** redundant code
        var sessionIDExistsLocal = false
        if (regDataExistsLocal.getBoolean("exists", false)
            && regDataExistsLocal.getBoolean("locallyVerify", false)
        ) {
            sessionIDExistsLocal = checkExistsSessionIdRx()
        }
        if (sessionIDExistsLocal) {
            //verification sessionID and identification user (if sessionID not verified)
            verificationObtainSessionIDRx(sessionID, logPass, regDataExistsLocal)
        } else {
        identification user
            verificationObtainSessionIDRx("", logPass, regDataExistsLocal)
        }
        */
        val sessionID: String = checkExistsAndObtainLocalSessionId()
        remoteVerificationObtainSessionID(sessionID, logPass, regDataExistsLocal)
    }

    /**
     * @return
     *
     *true - exists and verificated - locally,
     *
     * false - not exists or failed verification
     */
    private fun checkExistsLocalVerifyRegData(
        idEnterprise: String,
        usrLog: String,
        usrPass: String,
        isAddNewEnterprise: Boolean
    ): Bundle {
        val result = Bundle()
        val exists = SharedPreferencesImpl(
            myContext
        )
            .containsPreferences(arrayOf(keyUsrLog, keyUsrPass))
        if ((exists.getBoolean(keyUsrLog) && exists.getBoolean(keyUsrPass))
            && !isAddNewEnterprise
        ) {
            val logPass = SharedPreferencesImpl(
                myContext
            )
                .getPreferencesVal(arrayOf(keyUsrLog, keyUsrPass))
            val cryptoId = LocalDbRepositoryImpl(myContext).getCryptoIdEnterprise()
            val locallyVerification = logPass.getString(keyUsrLog, "non") == usrLog
                    && logPass.getString(keyUsrPass, "non") == usrPass
                    && idEnterprise == cryptoId
            if (locallyVerification) {
                mldStageDescriptionForAppEntry.value =
                    "Registry data is exists in local data and locally verificated"
                result.putBoolean("exists", true)
                result.putBoolean("locallyVerify", true)
            } else {
                mldStageDescriptionForAppEntry.value =
                    "!!Registry data is exists in local data and failed verification"
                result.putBoolean("exists", true)
                result.putBoolean("locallyVerify", false)
            }
        } else {
            mldStageDescriptionForAppEntry.value = "!!Registry data is not exists in local data"
            result.putBoolean("exists", false)
            result.putBoolean("locallyVerify", false)
        }
        return result
    }

    /**
     * @return
     *
     *true - exists
     *
     * false - not exists in local data
     */
    private fun checkExistsAndObtainLocalSessionId(): String {
        val existsSessionID = SharedPreferencesImpl(
            myContext
        ).containsPreferences(
            arrayOf(strSessionID))
        return if (!existsSessionID.getBoolean(strSessionID)) {
            mldStageDescriptionForAppEntry.value = "!!SessionID is not exists in local data"
            ""
        } else {
            mldStageDescriptionForAppEntry.value = "SessionID is exists in local data"
            Objects.requireNonNull(
                SharedPreferencesImpl(
                    myContext
                ).getPreferencesVal(arrayOf(strSessionID))
                    .getString(strSessionID)
                    ?: ""
            )
        }
    }

    /**
     *
     * send sessionID to server
     *
     * if response true (sessionID - verified), LiveData _ld_sessionID = current SessionID
     *
     * if response false (sessionID - not verified), send to server registration details
     * for obtaining new sessionID
     */
    private fun remoteVerificationObtainSessionID(
        sessionID: String,
        logPass: LogPass,
        regDataExistsLocal: Bundle
    ) {
        //=============================================================================
        val identificationUserSubscribe: DisposableObserver<String?> =
            object : DisposableObserver<String?>() {
                override fun onNext(newSessionID: String) {
                    mldStageDescriptionForAppEntry.postValue("sessionID - received ($newSessionID)")
                    SharedPreferencesImpl(
                        myContext
                    ).setSessionID(newSessionID)
                    mldStageDescriptionForAppEntry.postValue("sessionID - saved ($newSessionID)")
                    if (!regDataExistsLocal.getBoolean("locallyVerify", false)) {
                        val description: String = saveUserData(logPass.usrLogin, logPass.usrPass)
                        mldStageDescriptionForAppEntry.postValue(
                            "registration details ->\n $description \nsaving log and pass - OK!"
                        )
                    }
                    mldSessionID.postValue(newSessionID)
                }

                override fun onError(e: Throwable) {
                    Log.d("TAG", "Response = $e")
                    mldSessionID.postValue("")
                    mldStageDescriptionForAppEntry.postValue(
                        "!!Obtaining new sessionID is failed.\n" +
                                "Please check your registration details." +
                                getDescriptionLineFromThrowable(e)
                    )
                }

                override fun onComplete() {
                    mldStageDescriptionForAppEntry.postValue("SessionID: receiving - OK (onComplete()-)");
                }
            }
        //=============================================================================

        if (sessionID == "") {
            RemoteRepositoryImpl().identificationUser_rx(logPass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(identificationUserSubscribe)
        } else {
            RemoteRepositoryImpl().identificationSession(sessionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { obj: String? ->
                    Objects.nonNull(obj)
                }
                .concatMap(Function { strBool: String ->
                    try {
                        if (strBool != "1") {
                            mldStageDescriptionForAppEntry.postValue("!!sessionID - not verified")
                            return@Function RemoteRepositoryImpl()
                                .identificationUser_rx(logPass)
                                .subscribeOn(Schedulers.io())
                        } else {
                            mldStageDescriptionForAppEntry.postValue("sessionID - verified ($sessionID)")
                            return@Function Observable.just<String>(sessionID)
                        }
                    } catch (e: Exception) {
                        mldStageDescriptionForAppEntry.postValue("!!Obtaining new sessionID is failed")
                        return@Function Observable.just<String>("")
                    }
                } as Function<String?, Observable<String>>)
                .subscribe(identificationUserSubscribe)
        }
    }

    /**
     * only if **Throwable** extends **HttpException**
     */
    protected fun getDescriptionLineFromThrowable(e: Throwable): String {
        return if (e is HttpException)
            """HTTP code is ${e.code()}"""
        else ""
    }

    fun getAllNamesProduct() {
        val productsOfShop = LocalDbRepositoryImpl(myContext).getAllNamesProduct()
        mldNameProducts.value = productsOfShop
    }

    fun getAllManufactureShopNames() {
        mldShopsNamesStrList.value =
            LocalDbRepositoryImpl(myContext).getAllManufactureShops()
    }
}