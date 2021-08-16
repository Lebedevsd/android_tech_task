package com.nordpass.task.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), ErrorUi {

    override fun showError(message: ErrorMessage) {
        Toast.makeText(this, message.messageId, Toast.LENGTH_LONG).show()
    }
}