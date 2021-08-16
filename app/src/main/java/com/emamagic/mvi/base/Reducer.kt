package com.emamagic.mvi.base

interface Reducer<S> {
    fun reduce(state: S, event: BaseContract.Event): S
}