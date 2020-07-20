package com.zhen.mvvm.app

import androidx.lifecycle.ViewModelProvider
import com.zhen.mvvm.base.ViewModelFactory

class GlobalConfig {
    var viewModelFactory: ViewModelProvider.NewInstanceFactory = ViewModelFactory()
}