package com.emamagic.mvi.base

import androidx.annotation.StringRes

interface BaseContract {

    interface Event {
        fun onError(throwable: Throwable)
    }

    interface State {
        fun showLoading(@StringRes resId: Int)

        fun hideLoading()

        fun showMessage(@StringRes titleRes: Int, @StringRes messageRes: Int)

        fun showMessage(title: String, message: String)

        fun showErrorMessage(@StringRes messageRes: Int)

        fun showErrorMessage(message: String)
    }

    interface Effect {
        fun showToast(message: String)
        fun loading(isLoading: Boolean)
    }
}