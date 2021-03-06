package com.emamagic.mvi.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect

abstract class BaseFragment<VB : ViewBinding, STATE : BaseContract.State, EVENT : BaseContract.Event, VM : BaseViewModel<STATE, EVENT>> :
    Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private lateinit var loading: FrameLayout
    private var callback: OnBackPressedCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getFragmentBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideLoading()
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                renderViewState(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiEffect.collect { renderViewEffect(it) }
        }
    }

    protected fun showLoading(isDim: Boolean = false) {
        if (loading.visibility != View.VISIBLE) {
            if (isDim) loading.setBackgroundColor(Color.parseColor("#cc000000"))
            loading.visibility = View.VISIBLE
        }
    }

    protected fun hideLoading() {
        if (loading.visibility != View.GONE) {
            loading.visibility = View.GONE
        }
    }


    abstract val viewModel: VM

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun renderViewState(viewState: STATE)

    protected open fun renderViewEffect(viewEffect: BaseContract.Effect) {
        when(viewEffect) {
            is BaseContract.Effect.Loading -> {
                if (viewEffect.isLoading) showLoading() else hideLoading()
            }
            is BaseContract.Effect.ShowToast -> {
                Toast.makeText(requireContext(), viewEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun onFragmentBackPressed(owner: LifecycleOwner, call: () -> Unit) {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                call()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(owner, callback!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (callback != null) {
            callback?.isEnabled = false
            callback?.remove()
        }
    }

}

