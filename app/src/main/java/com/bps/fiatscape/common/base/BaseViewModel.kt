package com.bps.fiatscape.common.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(@ApplicationContext app: Context): ViewModel() {
    private val _navigationCommand = MutableLiveData<Int>()
    val navigationCommand: LiveData<Int> = _navigationCommand

    internal val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    internal val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

}
