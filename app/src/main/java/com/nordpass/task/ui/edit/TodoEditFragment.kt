package com.nordpass.task.ui.edit

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nordpass.task.R
import com.nordpass.task.databinding.FragmentEditBinding
import com.nordpass.task.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoEditFragment : BaseFragment(R.layout.fragment_edit) {
    private val viewModel: TodoEditViewModel by viewModels()
    private val args: TodoEditFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.init(args.todo)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentEditBinding.bind(view)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TodoEditFragment.viewModel
            titleEditText.addTextChangedListener { text ->
                if (text.isNullOrBlank()) {
                    titleInputLayout.error = getString(R.string.errorTitleEmpty)
                    saveBtn.isEnabled = false
                } else {
                    titleInputLayout.error = null
                    saveBtn.isEnabled = true
                }
            }
        }

        viewModel.saveFinished.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}
