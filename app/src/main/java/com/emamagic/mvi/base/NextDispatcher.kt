package com.emamagic.mvi.base

interface NextDispatcher {
    fun dispatch(event: BaseContract.Event)
}