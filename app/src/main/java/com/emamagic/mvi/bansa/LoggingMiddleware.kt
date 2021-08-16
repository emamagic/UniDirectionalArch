package com.emamagic.mvi.bansa

import com.emamagic.mvi.base.Middleware
import com.emamagic.mvi.base.NextDispatcher
import com.emamagic.mvi.base.Store


class LoggingMiddleware : Middleware<ApplicationState> {
    override fun dispatch(store: Store<ApplicationState>, action: Action, next: NextDispatcher) {
        println("Before ${action.javaClass.canonicalName}: ${store.state}")
        next(action)
        println("After ${action.javaClass.canonicalName}:  ${store.state}")
    }
}