package com.emamagic.mvi.base

interface Subscriber<S> {
    fun onStateChange(state: S)
}