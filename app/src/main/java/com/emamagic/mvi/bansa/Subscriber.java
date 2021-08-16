package com.emamagic.mvi.bansa;

public interface Subscriber<S> {
    void onStateChange(S state);
}
