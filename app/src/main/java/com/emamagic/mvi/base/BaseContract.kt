package com.emamagic.mvi.base

import androidx.annotation.StringRes

interface BaseContract {

    interface Event {
        fun onError(throwable: Throwable)
    }

    sealed class State {
        data class ShowLoading(@StringRes val resId: Int): State()

        object HideLoading: State()

        data class ShowMessageWithId(@StringRes val titleRes: Int, @StringRes val messageRes: Int)

        data class ShowMessage(val title: String, val message: String): State()

        data class ShowErrorMessageWithId(@StringRes val messageRes: Int)

        data class ShowErrorMessage(val message: String)
    }

    sealed class Effect {
        data class ShowToast(val message: String): Effect()

        data class Loading(val isLoading: Boolean): Effect()
    }
}