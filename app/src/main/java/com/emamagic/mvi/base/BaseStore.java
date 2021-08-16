package com.emamagic.mvi.base;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BaseStore<S> implements Store<S> {
    private S currentState;
    public final Reducer<S> reducer;
    private final List<Subscriber<S>> subscribers = new ArrayList<>();
    public List<NextDispatcher> dispatchers;
    private final Middleware<S> dispatcher = new Middleware<S>() {
        @Override
        public void dispatch(Store<S> store, @NonNull BaseContract.Event event, NextDispatcher next) {
            synchronized (this) {
                currentState = reducer.reduce(store.getState(), event);
            }
            for (int i = 0; i < subscribers.size(); i++) {
                subscribers.get(i).onStateChange(currentState);
            }
        }
    };

    @SafeVarargs
    public BaseStore(S initialState, Reducer<S> reducer, Middleware<S>... middlewares) {
        this(initialState, reducer, Arrays.asList(middlewares));
    }

    public BaseStore(S initialState, Reducer<S> reducer, List<Middleware<S>> middlewares) {
        this.reducer = reducer;
        this.currentState = initialState;
        this.dispatchers = populateDispatchers(middlewares);
    }

    @Override
    public synchronized S getState() {
        return currentState;
    }

    @Override
    public S dispatch(@NonNull BaseContract.Event event) {
        this.dispatchers.get(0).dispatch(event);
        return currentState;
    }

    @NonNull
    @Override
    public Subscription subscribe(@NonNull final Subscriber<S> subscriber) {
        this.subscribers.add(subscriber);
        return new Subscription() {
            @Override
            public void unsubscribe() {
                subscribers.remove(subscriber);
            }
        };
    }

    private List<NextDispatcher> populateDispatchers(List<Middleware<S>> middlewares) {
        final Store<S> store = this;
        LinkedList<NextDispatcher> dispatchers = new LinkedList<>();

        dispatchers.add(new NextDispatcher() {
            public void dispatch(@NonNull BaseContract.Event event) {
                dispatcher.dispatch(store, event, null);
            }
        });

        for (int i = middlewares.size() - 1; i >= 0; i--) {
            final Middleware<S> middleware = middlewares.get(i);
            final NextDispatcher next = dispatchers.get(0);
            dispatchers.add(0, new NextDispatcher() {
                @Override
                public void dispatch(@NonNull BaseContract.Event event) {
                    middleware.dispatch(store, event, next);
                }
            });
        }

        return dispatchers;
    }
}
