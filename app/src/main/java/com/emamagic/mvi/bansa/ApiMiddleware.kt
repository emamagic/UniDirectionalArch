package com.emamagic.mvi.bansa

class GifMiddleware : Middleware<ApplicationState> {

    override fun dispatch(store: Store<ApplicationState>, action: Action, next: NextDispatcher) {
//        when (action) {
//            is FETCH_NEXT_PAGE -> {
//                currentRequest = fetchTrendingGifs(
//                    store.state.pagination.offset,
//                    store.state.pagination.count).subscribe({
//                    next(FETCH_NEXT_PAGE_COMPLETED(it))
//                }, {
//                    throw it
//                })
//
//                next(FETCH_NEXT_PAGE_STARTED)
//            }
//            is REFRESH -> {
//                currentRequest.unsubscribe()
//
//                currentRequest = fetchTrendingGifs().subscribe({
//                    next(REFRESH_COMPLETED(it))
//                }, {
//                    throw it
//                })
//
//                next(REFRESH_STARTED)
//            }
//            else -> next(action)
//        }
    }
}