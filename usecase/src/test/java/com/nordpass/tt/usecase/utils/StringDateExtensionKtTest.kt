package com.nordpass.tt.usecase.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class StringDateExtensionKtTest {

    @Test
    fun toDate_apiDateIsParsed() {
        val dateString = "2021-01-13T03:50:03.794+05:30"
        val date = dateString.toDate()

        assertEquals(2021, date.year)
        assertEquals(1, date.monthValue)
        assertEquals(13, date.dayOfMonth)
        assertEquals(3, date.hour)
        assertEquals(50, date.minute)
        assertEquals(3, date.second)
        assertEquals(19_800, date.offset.totalSeconds)
    }

    @Test
    fun dateToString_returnsSameFormat() {
        val dateString = "2021-01-13T03:50:03.794+05:30"
        val date = dateString.toDate()

        val result = date.toString()

        assertEquals(dateString, result)
    }
}