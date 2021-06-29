package com.interview.task.widgets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    var showMessage: MutableLiveData<Int> = MutableLiveData()

}