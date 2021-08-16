package com.nordpass.task.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout), ErrorUi {

    override fun showError(message: ErrorMessage) {
        (activity as? ErrorUi)?.showError(message)
    }
}