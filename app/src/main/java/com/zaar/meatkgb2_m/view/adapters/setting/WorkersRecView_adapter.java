package com.zaar.meatkgb2_m.view.adapters.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaar.meatkgb2_m.R;
import com.zaar.meatkgb2_m.data.Workers_shortDescription;

import java.util.List;

public class WorkersRecView_adapter extends RecyclerView.Adapter<WorkersRecView_adapter.WorkersRecViewHolder> {
    public interface OnWorkerClickListener {
        void onWorkerClick(long id, int position, String nameWorkshop);
    }

    private final OnWorkerClickListener onClickListener;
    private List<Workers_shortDescription> workersDescrList;
    private Context context;

    public WorkersRecView_adapter(List<Workers_shortDescription> workersDescrList, Context context, OnWorkerClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.workersDescrList = workersDescrList;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkersRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_rec_view_one_tv_setting, parent, false);
        view.setClickable(true);
        return new WorkersRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkersRecViewHolder holder, int position) {
        String builder = workersDescrList.get(position).fullName +
                "(" +
                workersDescrList.get(position).nameWorkshop +
                ", " +
                workersDescrList.get(position).appointment +
                ")";
        holder.tv_descriptionWorker.setText(builder);
        holder.tv_descriptionWorker.setOnClickListener(
                view -> onClickListener.onWorkerClick(
                        workersDescrList.get(position).id,
                        position,
                        workersDescrList.get(position).nameWorkshop
                )
        );
    }

    @Override
    public int getItemCount() {
        return workersDescrList.size();
    }

    public static class WorkersRecViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_descriptionWorker;

        public WorkersRecViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_descriptionWorker = itemView.findViewById(R.id.tv_itemRecView);
        }
    }
}