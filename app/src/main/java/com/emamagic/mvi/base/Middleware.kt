package com.emamagic.mvi.base

interface Middleware<S> {
    fun dispatch(store: Store<S>, event: BaseContract.Event, next: NextDispatcher?)
}