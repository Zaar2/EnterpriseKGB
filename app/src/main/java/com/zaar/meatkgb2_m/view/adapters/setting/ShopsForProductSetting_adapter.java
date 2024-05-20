package com.zaar.meatkgb2_m.view.adapters.setting;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zaar.meatkgb2_m.R;

import java.util.List;

public class ShopsForProductSetting_adapter
        extends RecyclerView.Adapter<ShopsForProductSetting_adapter.ShopsForProductSettingHolder> {

    private final OnShopProductClickListener onShopProductClickListener;
    private final getItemPressed getItemPressed;
    private List<String> shopList;
    private String namePressedButton;
    private ColorStateList colorStateListPressed;

    public ShopsForProductSetting_adapter(
            OnShopProductClickListener onShopProductClickListener,
            getItemPressed getItemPressed,
            List<String> shopList,
            String namePressedButton,
            ColorStateList colorStateListPressed
    ) {
        this.onShopProductClickListener = onShopProductClickListener;
        this.shopList = shopList;
        this.namePressedButton = namePressedButton;
        this.colorStateListPressed = colorStateListPressed;
        this.getItemPressed = getItemPressed;
    }

    //region -------OVERRIDE--------------------------
    public static class ShopsForProductSettingHolder extends RecyclerView.ViewHolder {
        public final Button btn_nameShop;

        public ShopsForProductSettingHolder(@NonNull View itemView) {
            super(itemView);
            btn_nameShop = itemView.findViewById(R.id.btnProduct_shop);
        }
    }

    @NonNull
    @Override
    public ShopsForProductSettingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_rec_v_button_shops_product, parent, false);
        ShopsForProductSettingHolder holder = new ShopsForProductSettingHolder(view);
        view.setOnClickListener(
                it -> onShopProductClickListener.onShopClick(
                        ((Button) it),
                        ((Button) it).getText().toString()
                )
        );
        view.setClickable(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsForProductSettingHolder holder, int position) {
        String nameButton = shopList.get(position);
        holder.btn_nameShop.setText(nameButton);
        if (nameButton.equals(namePressedButton)) {
            holder.btn_nameShop.setBackgroundTintList(
                    colorStateListPressed
            );
            getItemPressed.getButton(holder.btn_nameShop);
        }
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    //endregion ---------------------------------------------------------------

    public interface OnShopProductClickListener {
        void onShopClick(Button button, String name);
    }

    public interface getItemPressed {
        void getButton(Button button);
    }

//    public int getPositionButtonPressed(String name) {
//        int position = -1;
//        for (int i = 0; i < shopList.size(); i++) {
//            if (shopList.get(i).equals(name)) {
//                position = i;
//                break;
//            }
//        }
//        int item=this.getItemViewType(position);
//        return position;
//    }
}