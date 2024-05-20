package com.zaar.meatkgb2_m.view.adapters.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaar.meatkgb2_m.R;

import java.util.List;

public class ShopsRecView_adapter
        extends RecyclerView.Adapter<ShopsRecView_adapter.ShopsRecViewHolder> {
    public interface OnShopClickListener {
        void onShopClick(String name, int position);
    }

    private final OnShopClickListener onClickListener;
    private List<String> shopList;

    public ShopsRecView_adapter(List<String> shopList, OnShopClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ShopsRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_rec_view_one_tv_setting, parent, false);
        view.setClickable(true);
        return new ShopsRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsRecViewHolder holder, int position) {
        holder.tv_nameShop.setText(shopList.get(position));
        holder.tv_nameShop.setOnClickListener(
                view -> onClickListener.onShopClick(
                        ((TextView) view).getText().toString(), position
                )
        );
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public static class ShopsRecViewHolder extends RecyclerView.ViewHolder {
        public final TextView tv_nameShop;

        public ShopsRecViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameShop = itemView.findViewById(R.id.tv_itemRecView);
        }
    }
}