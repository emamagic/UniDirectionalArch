package com.emamagic.mvi.base;

public interface BaseContract {

    interface State {
        default void init() {

        }
        void LoadData();
    }

    interface Event {
        void getData();
    }
    interface Effect {
        void showMessage();
    }

}
