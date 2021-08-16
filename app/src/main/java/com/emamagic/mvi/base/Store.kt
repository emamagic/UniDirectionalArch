package com.emamagic.mvi.base

interface Store<S> {
    val state: S
    fun dispatch(event: BaseContract.Event): S
    fun subscribe(subscriber: Subscriber<S>): Subscription
}
