package com.emamagic.mvi

import com.emamagic.mvi.base.BaseContract
import com.emamagic.mvi.base.BaseViewModel

class MainViewModel: BaseViewModel<MainContract.State, BaseContract.Effect>(), MainContract.Event {

    override fun createInitialState(): MainContract.State {
        return }
    }

    override fun tEvent() {
        TODO("Not yet implemented")
    }

    override fun getData() {
        TODO("Not yet implemented")
    }


}