package com.emamagic.mvi.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emamagic.mvi.bansa.Reducer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : BaseContract.State, EFFECT : BaseContract.Effect, EVENT : BaseContract.Event> :
    ViewModel(), BaseContract.Event {

    init {
        subscribeEvents()
    }

    // Create Initial State of View
    private val initialState: STATE by lazy { createInitialState() }
    abstract fun createInitialState(): STATE

    // Get Current State
    val currentState: STATE
        get() = uiState.value

    private val _uiState: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<EVENT> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiEffect: Channel<EFFECT> = Channel()
    val uiEffect = _uiEffect.receiveAsFlow()


    fun setEvent(event: EVENT) {
        val newEvent = event
        viewModelScope.launch { _uiEvent.emit(newEvent) }
    }

    protected fun setState(reduce: STATE.() -> STATE) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> EFFECT) {
        val effectValue = builder()
        viewModelScope.launch { _uiEffect.send(effectValue) }
    }


    private fun subscribeEvents() = viewModelScope.launch {
        uiEvent.collect {
            Reducer<STATE> { state, action ->
                reducer(it)
            }

        }
    }

    abstract fun reducer(event: EVENT): STATE

    override fun onError(throwable: Throwable) {

    }

}