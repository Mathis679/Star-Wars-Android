package com.example.presentation

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.example.domain.interactor.UseCase

abstract class BaseViewModel(vararg useCases: UseCase<*, *>) : ViewModel() {
    private val useCases = listOf(*useCases)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }
}