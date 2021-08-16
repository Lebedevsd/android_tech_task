package com.nordpass.tt.usecase.utils

import org.threeten.bp.OffsetDateTime

fun String.toDate(): OffsetDateTime {
    return OffsetDateTime.parse(this)
}