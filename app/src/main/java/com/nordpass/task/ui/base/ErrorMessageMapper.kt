package com.nordpass.task.ui.base

import com.nordpass.task.R

object ErrorMessageMapper {
    fun map(error: Throwable): ErrorMessage {
        return ErrorMessage(R.string.error_generic)
    }
}