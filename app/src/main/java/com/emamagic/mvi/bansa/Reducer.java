package com.emamagic.mvi.bansa;

public interface Reducer<S> {
    S reduce(S state, Action action);
}
