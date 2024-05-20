package com.zaar.meatkgb2_m.view.fragments.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.data.Workers_shortDescription;
import com.zaar.meatkgb2_m.databinding.FragmSettWorkersBinding;
import com.zaar.meatkgb2_m.view.adapters.setting.WorkersRecView_adapter;
import com.zaar.meatkgb2_m.viewModel.factory.settings.SettingFactory_worker;
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Worker_VM;

import java.util.List;

public class WorkersSetting_fragment extends Fragment {
    private FragmSettWorkersBinding binding;
    private Worker_VM modelWorker;
    private WorkersRecView_adapter.OnWorkerClickListener onWorkerClickListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmSettWorkersBinding.inflate(inflater, container, false);
        modelWorker = new ViewModelProvider(
                this,
                new SettingFactory_worker(
                        requireActivity().getApplicationContext(), "WorkersSetting"
                )
        ).get(Worker_VM.class);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initObserve();
        initView();
    }

    private void initObserve() {
        //add
        binding.btnAddWorkers.setOnClickListener(
                view -> addWorker_onClick()
        );
        //back
        binding.btnBackWorkers.setOnClickListener(
                view -> Navigation.findNavController(binding.getRoot())
                        .navigate(R.id.action_workersSetting_fragment_to_setting_fragment)
        );
        //filling recycle view on start
        modelWorker.ld_usersDescription.observe(
                getViewLifecycleOwner(),
                workersShortDescriptions -> initRecView(workersShortDescriptions)
        );

        onWorkerClickListener = (id, position, nameWorkshop) -> itemRecView_onClick(id, nameWorkshop);
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                requireActivity().getApplicationContext(),
                LinearLayoutManager.VERTICAL, false
        );
        binding.recViewWorkers.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.recViewWorkers.getContext(),
                layoutManager.getOrientation()
        );
        binding.recViewWorkers.addItemDecoration(dividerItemDecoration);
        updateRecView();
    }

    private void updateRecView() {
        modelWorker.getAllWorkersDescription();
    }

    private void initRecView(List<Workers_shortDescription> workersShortDescriptions) {
        WorkersRecView_adapter workersRecViewAdapter = new WorkersRecView_adapter(
                workersShortDescriptions,
                requireActivity(),
                onWorkerClickListener
        );
        binding.recViewWorkers.setAdapter(workersRecViewAdapter);
    }

    private void itemRecView_onClick(long id, String nameWorkshop) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string._action), getString(R.string._edit));
        bundle.putLong("userId", id);
        bundle.putString("nameWorkshop", nameWorkshop);
        actionTo(R.id.action_workersSetting_fragment_to_editWorkers_fragment, bundle);
    }

    private void addWorker_onClick() {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string._action), getString(R.string._add));
        actionTo(R.id.action_workersSetting_fragment_to_editWorkers_fragment, bundle);
    }

    private void actionTo(int action, Bundle bundle) {
        bundle.putString(getString(R.string._source), getString(R.string._setting));
        Navigation.findNavController(binding.getRoot())
                .navigate(action, bundle);
    }
}