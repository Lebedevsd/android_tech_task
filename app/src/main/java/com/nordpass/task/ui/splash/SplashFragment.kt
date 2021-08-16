package com.nordpass.task.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nordpass.task.R
import com.nordpass.task.databinding.FragmentSplashBinding
import com.nordpass.task.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.completed.observe(this, { showTodoList() })
        viewModel.error.observe(this, ::showError)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentSplashBinding.bind(view).viewModel = viewModel
    }

    private fun showTodoList() {
        findNavController().navigate(SplashFragmentDirections.actionTodoList())
    }
}