package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.databinding.FragmSettWorkersEditBinding;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_worker;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Worker_VM;

import java.util.Objects;

public class EditWorkers_fragment extends Fragment {
    private FragmSettWorkersEditBinding binding;
    private Worker_VM modelSettingWorker;
    private String[] spinnerItems;
    private String configurationType = "", source = "";
    private long idUser_incoming = -1;
    /**
     * active button for perform click if the session ID has expired
     * during code execution after pressing this button (contacting the server)
     */
    private Button btn_active;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettWorkersEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        initVariables();
        initObserve();
        initView();
    }

    private void initVariables() {
        modelSettingWorker = new ViewModelProvider(
                this,
                new SettingFactory_worker(
                        requireActivity().getApplicationContext(),
                        "editWorkers")
        ).get(Worker_VM.class);
        spinnerItems = new String[]{"non"};
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            if (Objects.equals(bundle.getString(getString(R.string._source)), getString(R.string._identification))) {
                source = getString(R.string._identification);
                if (Objects.equals(bundle.getString(getString(R.string._action)), getString(R.string._adduser))) {
                    configurationType = getString(R.string._add);
                }
            } else if (Objects.equals(bundle.getString(getString(R.string._source)), getString(R.string._setting))) {
                source = getString(R.string._setting);
                if (Objects.equals(bundle.getString(getString(R.string._action)), getString(R.string._edit))) {
                    configurationType = getString(R.string._edit);
                    idUser_incoming = bundle.getLong("userId", -1L);
                }
                if (Objects.equals(bundle.getString(getString(R.string._action)), getString(R.string._add))) {
                    configurationType = getString(R.string._add);
                }
            }
        }
    }

    private void initObserve() {
        initObserveCommon();
        if (configurationType.equals(getString(R.string._edit)))
            initObserveEdit(source);
        if (configurationType.equals(getString(R.string._add)))
            initObserveAdd();
    }

    private void initView() {
        initViewCommon();
        if (configurationType.equals(getString(R.string._add)))
            initViewAdd();
        if (configurationType.equals(getString(R.string._edit)))
            initViewEdit();
    }

    //region------------------ADD
    private void initObserveAdd() {
        btnAdd_groupAdd_onClick();
        btnCancel_groupAdd_onClick();
        initObserveLiveData_add();
    }

    private void initViewAdd() {
        binding.btnGroupWorkerAdd.getRoot().setVisibility(View.VISIBLE);
        binding.btnGroupWorkersEdit.getRoot().setVisibility(View.GONE);
    }

    private void btnAdd_groupAdd_onClick() {
        binding.btnGroupWorkerAdd.getRoot().findViewById(R.id.btnAdd_groupAdd)
                .setOnClickListener(view -> {
                    btn_active = binding.btnGroupWorkerAdd.getRoot().findViewById(R.id.btnAdd_groupAdd);
                    String[][] alert;
                    Bundle user = new Bundle();
                    prepareValueUser(user);
                    alert = checkElements(user);
                    if (alert[0][0].equals("ok")) {
                        binding.btnGroupWorkerAdd.getRoot().findViewById(R.id.btnCancel_groupAdd)
                                .setEnabled(false);
                        modelSettingWorker.addWorker(user);
                    } else viewingAlertMessage(alert);
                });
    }

    private void btnCancel_groupAdd_onClick() {
        binding.btnGroupWorkerAdd.getRoot()
                .findViewById(R.id.btnCancel_groupAdd).setOnClickListener(
                        view -> {
                            btn_active = binding.btnGroupWorkerAdd.getRoot().
                                    findViewById(R.id.btnCancel_groupAdd);
                            fragmentCancel();
                        });
    }

    private void initObserveLiveData_add() {
        modelSettingWorker.ld_id_insertResult_user.observe(
                getViewLifecycleOwner(),
                aLong -> {
                    if (aLong >= 0) {
                        binding.btnGroupWorkerAdd.getRoot().findViewById(R.id.btnCancel_groupAdd)
                                .setEnabled(true);
                        fragmentOk();
                    }
                });
    }
    //endregion


    //region------------------EDIT
    private void initViewEdit() {
        binding.btnGroupWorkerAdd.getRoot().setVisibility(View.GONE);
        binding.btnGroupWorkersEdit.getRoot().setVisibility(View.VISIBLE);
        if (idUser_incoming >= 0)
            modelSettingWorker.getWorkerById(idUser_incoming);
    }

    private void initObserveEdit(String source) {
        binding.btnGroupWorkersEdit.getRoot().findViewById(R.id.btnUpd_groupEdit)
                .setOnClickListener(view -> {
                            btn_active = binding.btnGroupWorkersEdit.getRoot()
                                    .findViewById(R.id.btnUpd_groupEdit);
                            String[][] alert;
                            Bundle user = new Bundle();
                            user.putString(getString(R.string.id), String.valueOf(idUser_incoming));
                            prepareValueUser(user);
                            alert = checkElements(user);
                            if (alert[0][0].equals("ok")) {
                                modelSettingWorker.updWorker(user);
                            } else viewingAlertMessage(alert);
                        }
                );
        binding.btnGroupWorkersEdit.getRoot().findViewById(R.id.btnCancel_groupEdit)
                .setOnClickListener(view -> {
                            btn_active = binding.btnGroupWorkersEdit.getRoot().
                                    findViewById(R.id.btnCancel_groupEdit);
                            Bundle bundle = new Bundle();
                            bundle.putString(getString(R.string._source), source);
                            fragmentCancel();
                        }
                );
        binding.btnGroupWorkersEdit.getRoot().findViewById(R.id.btnDel_groupEdit)
                .setOnClickListener(view -> {
                            btn_active = binding.btnGroupWorkersEdit.getRoot().
                                    findViewById(R.id.btnDel_groupEdit);
                            //здесь нужно диалоговое окно для подтверждения удаления,
                            // с указанием имени цеха
                            //...dialog...
                            //если -> ДА
                            modelSettingWorker.delWorker(idUser_incoming);
                        }
                );
        modelSettingWorker.ld_worker.observe(
                getViewLifecycleOwner(),
                worker -> fillingViews(worker)
        );
        modelSettingWorker.ld_count_delResult_worker.observe(
                getViewLifecycleOwner(),
                integers -> {
                    int count = integers.get(0);
                    if (count >= 1) fragmentOk();
                }
        );
        modelSettingWorker.ld_count_updResult_worker.observe(
                getViewLifecycleOwner(),
                integers -> {
                    int count = integers.get(0);
                    if (count >= 1) fragmentOk();
                }
        );
    }

    private void fillingViews(Bundle user_income) {
        binding.etFullNameWorker.setText(user_income.getString("fullName", "non"));
        binding.etShortNameWorker.setText(user_income.getString("shortName", "non"));
        binding.etAppointmentWorker.setText(user_income.getString("appointment", "non"));
        binding.etUsrLoginWorker.setText(user_income.getString("login", "non"));
        binding.etUsrPassWorker.setText(user_income.getString("pass", "non"));
        binding.checkBInactiveWorker.setChecked(user_income.getInt("id_status", 0) == 1);
        String nameOneMoreWorkshop_worker = user_income.getString("oneMoreWorkshop", "non");
        String nameWorkshop_worker = user_income.getString("shop", "non");
        if (!nameOneMoreWorkshop_worker.equals("non"))
            getPositionOfSpinnerShops(binding.spinnerOneMoreShopForEditUser, nameOneMoreWorkshop_worker);
        else
            initSpinner_shops(binding.spinnerOneMoreShopForEditUser, addToBeginOfSpinnerItems("non"));
        if (!nameWorkshop_worker.equals("non"))
            getPositionOfSpinnerShops(binding.spinnerShopForEditUser, nameWorkshop_worker);
        else initSpinner_shops(binding.spinnerShopForEditUser, addToBeginOfSpinnerItems("non"));
    }
    //endregion

    //region------------------COMMON METHODS
    private void initObserveCommon() {
        initObserveCommonVM();
        initObserveCommonView();
    }

    private void initObserveCommonVM(){
        modelSettingWorker.ld_sessionID.observe(
                getViewLifecycleOwner(),
                sID -> btn_active.performClick()
        );
        modelSettingWorker.ldShopsNamesStrList().observe(
                getViewLifecycleOwner(),
                strings -> {
                    if (strings != null) {
                        spinnerItems = strings.toArray(new String[0]);
                    } else {
                        spinnerItems = new String[0];
                        ((Button) binding.btnGroupWorkerAdd.getRoot().findViewById(R.id.btnAdd_groupAdd))
                                .setTextAppearance(R.style.button_inactive);
                        binding.btnGroupWorkerAdd.getRoot().findViewById(R.id.btnAdd_groupAdd)
                                .setEnabled(false);
                        binding.tvAlertMessage.setText(getString(R.string.alert_nonShops_fromUsers));
                    }
                    initSpinner_shops(binding.spinnerShopForEditUser, spinnerItems);
                    initSpinner_shops(binding.spinnerOneMoreShopForEditUser, addToBeginOfSpinnerItems("non"));
                });
        modelSettingWorker.ldVisibilitySpinnerMoreWorkshop.observe(
                getViewLifecycleOwner(),
                aBoolean -> {
                    if (aBoolean) binding.layoutOneMoreWorkshop.setVisibility(View.VISIBLE);
                    else binding.layoutOneMoreWorkshop.setVisibility(View.GONE);
                }
        );
    }

    private void initObserveCommonView(){
        spinnerShopOnSelectedItem();
    }

    private void initViewCommon() {
        modelSettingWorker.getAllShops();
    }

    private void initSpinner_shops(Spinner spinner, String[] spinnerItems) {
        spinner.setAdapter(
                new ArrayAdapter<>(
                        requireActivity(),
                        R.layout.item_spinner,
                        spinnerItems
                ));
    }

    private void prepareValueUser(Bundle user) {
        user.putString(getString(R.string.fullName_user), binding.etFullNameWorker.getText().toString());
        user.putString(getString(R.string.shortName_user), binding.etShortNameWorker.getText().toString());
        user.putString(getString(R.string.workshop), binding.spinnerShopForEditUser.getSelectedItem().toString());
        user.putString(getString(R.string.appointment_user), binding.etAppointmentWorker.getText().toString());
        user.putString(getString(R.string.login_user), binding.etUsrLoginWorker.getText().toString());
        user.putString(getString(R.string.pass_user), Objects.requireNonNull(binding.etUsrPassWorker.getText()).toString());
        user.putString(getString(R.string.idStatus_product), binding.checkBInactiveWorker.isChecked() ? "1" : "0");
        user.putString(getString(R.string.one_more_workshop), binding.spinnerOneMoreShopForEditUser.getSelectedItem().toString());
        for (String key : user.keySet())
            user.putString(key, Objects.requireNonNull(user.getString(key)).trim());
    }

    private String[][] checkElements(Bundle user) {
        String[][] result = null;
        //check spaces
        String[] keys = new String[]{
                getString(R.string.login_user), getString(R.string.pass_user)
        };
        result = new String[keys.length][2];
        result[0][0] = "ok";
        for (int i = 0; i < keys.length; i++) {
            if (Objects.requireNonNull(user.getString(keys[i])).contains(" ")) {
                result[i] = new String[]{
                        keys[i],
                        getString(R.string.alert_contains_space)
                };
            }
        }
        return result;
    }

    private void viewingAlertMessage(String[][] alert) {
        StringBuilder builder = new StringBuilder();
        for (String[] message : alert) {
            if (message[1] != null) {
                builder
                        .append(message[0]).append(" : ")
                        .append(message[1]).append("\n");
            }
        }
        binding.tvAlertMessage.setText(builder);
    }

    private void fragmentOk() {
        int action;
        Bundle bundle = new Bundle();
        if (source.equals(getString(R.string._setting))) {
            action = R.id.action_editWorkers_fragment_to_workersSetting_fragment;
        } else if (source.equals(getString(R.string._identification))) {
            bundle.putString(getString(R.string._action), getString(R.string._new_enterprise));
            action = R.id.action_editWorkers_fragment2_to_identificationEnter_fragment;
        } else action = 0;
        actionTo(action, bundle);
    }

    private void fragmentCancel() {
        int action;
        Bundle bundle = new Bundle();
        if (source.equals(getString(R.string._setting))) {
            action = R.id.action_editWorkers_fragment_to_workersSetting_fragment;
        } else if (source.equals(getString(R.string._identification))) {
            bundle.putString(getString(R.string._action), getString(R.string._new_enterprise));
            action = R.id.action_editWorkers_fragment2_to_identificationEnter_fragment;
        } else action = 0;
        actionTo(action, bundle);
    }

    private void actionTo(int action, Bundle bundle) {
        if (action != 0) {
            bundle.putString(getString(R.string._source), source);
            Navigation.findNavController(binding.getRoot()).navigate(action, bundle);
        }
    }

    /**
     * sets spinner to the specified value from the list
     *
     * @param spinner android.widget.AbsSpinner
     * @param shopStr the specified value
     */
    private void getPositionOfSpinnerShops(Spinner spinner, String shopStr) {
        spinner.setSelection(
                ((ArrayAdapter<String>) spinner.getAdapter()).getPosition(shopStr));
    }

    /**
     * insert element as first in 'spinnerItem'
     *
     * @param element
     * @return
     */
    private String[] addToBeginOfSpinnerItems(String element) {
        String[] temp = new String[spinnerItems.length + 1];
        temp[0] = element;
        System.arraycopy(spinnerItems, 0, temp, 1, spinnerItems.length);
        return temp;
    }

    private void spinnerShopOnSelectedItem(){
        binding.spinnerShopForEditUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelSettingWorker.getVisibilitySpinnerMoreWorkshop(((TextView)view).getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //endregion
}