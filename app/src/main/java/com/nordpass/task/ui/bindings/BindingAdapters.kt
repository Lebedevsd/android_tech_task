package com.nordpass.task.ui.bindings

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter

@SuppressLint("RestrictedApi")
@BindingAdapter("android:text") fun setText(
    view: EditText,
    oldText: String?,
    text: String?
) {
    TextViewBindingAdapter.setText(view, text)
    if (text == null) return
    if (text == oldText || oldText == null) {
        view.setSelection(text.length)
        view.requestFocus()
    }
}
