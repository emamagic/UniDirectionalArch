package com.emamagic.mvi.bansa


class LoggingMiddleware : Middleware<ApplicationState> {
    override fun dispatch(store: Store<ApplicationState>, action: Action, next: NextDispatcher) {
        println("Before ${action.javaClass.canonicalName}: ${store.state}")
        next(action)
        println("After ${action.javaClass.canonicalName}:  ${store.state}")
    }
}