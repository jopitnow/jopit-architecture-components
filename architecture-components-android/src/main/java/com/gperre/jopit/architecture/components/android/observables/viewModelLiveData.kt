package com.gperre.jopit.architecture.components.android.observables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.reflect.KProperty

class GetLiveData<T>(private val data: MutableLiveData<T>) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): LiveData<T> {
        return data
    }
}

fun <T> getLiveData(data: MutableLiveData<T>): GetLiveData<T> {
    return GetLiveData(data)
}
