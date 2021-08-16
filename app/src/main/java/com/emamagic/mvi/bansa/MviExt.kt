package com.emamagic.mvi.bansa

operator fun <S> Reducer<S>.invoke(state: S, action: Action): S {
    return reduce(state, action)
}

operator fun <S> Middleware<S>.invoke(store: Store<S>, action: Action, next: NextDispatcher) {
    dispatch(store, action, next)
}

operator fun NextDispatcher.invoke(action: Action) {
    dispatch(action)
}