package com.emamagic.mvi

import com.emamagic.mvi.base.BaseContract

interface MainContract {

    interface State: BaseContract.State {
        fun tState()
    }

    interface Event: BaseContract.Event {
        fun tEvent()
    }
}