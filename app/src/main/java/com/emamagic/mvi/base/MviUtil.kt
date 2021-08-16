package com.emamagic.mvi.base

operator fun <S> Reducer<S>.invoke(state: S, event: BaseContract.Event): S {
    return reduce(state, event)
}

operator fun <S> Middleware<S>.invoke(store: Store<S>, event: BaseContract.Event, next: NextDispatcher) {
    dispatch(store, event, next)
}

operator fun NextDispatcher.invoke(event: BaseContract.Event) {
    dispatch(event)
}

@SafeVarargs
fun <S> combineReducers(vararg reducers: Reducer<S>): Reducer<S> {
    return object : Reducer<S> {
        override fun reduce(state: S, event: BaseContract.Event): S {
            var mState = state
            for (reducer in reducers) {
                mState = reducer.reduce(state, event)
            }
            return mState
        }
    }
}