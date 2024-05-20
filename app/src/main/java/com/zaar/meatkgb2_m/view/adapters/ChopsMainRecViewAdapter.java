package com.zaar.meatkgb2_m.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaar.meatkgb2_m.R;

import java.util.List;

public class ChopsMainRecViewAdapter extends RecyclerView.Adapter<ChopsMainRecViewAdapter.ShopsRecViewHolder> {
    public interface OnShopProductClickListener{
        void onShopClick(Button button, String name, int position);
    }
    private List<String> shopList;
    private OnShopProductClickListener onShopProductClickListener;
    public ChopsMainRecViewAdapter
            (OnShopProductClickListener onShopProductClickListener, List<String> shopList) {
        this.shopList = shopList;
        this.onShopProductClickListener = onShopProductClickListener;
    }

    @NonNull
    @Override
    public ShopsRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_rec_v_button_main, parent, false);
        return new ShopsRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsRecViewHolder holder, int position) {
        holder.btn_nameShop.setText(shopList.get(position));
        holder.btn_nameShop.setOnClickListener(
                view -> {
                    onShopProductClickListener.onShopClick(
                            ((Button) view),
                            ((Button) view).getText().toString(),
                            position
                    );
                }
        );
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public static class ShopsRecViewHolder extends RecyclerView.ViewHolder {
        public final Button btn_nameShop;

        public ShopsRecViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_nameShop = itemView.findViewById(R.id.btnMain_shop);
        }
    }
}