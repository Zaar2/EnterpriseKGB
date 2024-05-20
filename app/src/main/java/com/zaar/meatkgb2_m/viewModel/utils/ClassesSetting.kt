package com.zaar.meatkgb2_m.viewModel.utils

import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Enterprise_VM
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Product_VM
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Shop_VM
import com.zaar.meatkgb2_m.viewModel.viewModels.setting.Worker_VM

enum class ClassesSetting {

    ENTERPRISE {
        override fun getName(): String = Enterprise_VM.getName()
    },
    PRODUCT {
        override fun getName(): String = Product_VM.getName()
    },
    WORKER {
        override fun getName(): String = Worker_VM.getName()
    },
    SHOP {
        override fun getName(): String = Shop_VM.getName()
    };

    abstract fun getName(): String
}