package com.store.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val compnents: LiveData<VisualComponent> get() = _components

    private var _components =
        MutableLiveData<VisualComponent>().also {
            it.value = hasComponents
        }

    var hasComponents = VisualComponent()
        set(value) {
            field = value
            _components.value = value
        }


}

class VisualComponent(
    val appBar: Boolean = false,
    val bottomNavigation: Boolean = false
)
