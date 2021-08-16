package com.emamagic.mvi

sealed class CommonEffect: BaseEffect {
    data class ShowToast(val message: String): CommonEffect()
    data class Loading(val isLoading: Boolean, val isDim: Boolean = true): CommonEffect()
}
