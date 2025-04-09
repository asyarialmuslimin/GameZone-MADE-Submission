package com.saifur.gamezone.core.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilTest {

    @Test
    fun `convertDate should return formatted date when input is valid`() {
        val input = "2024-04-08T15:30:00"
        val expected = "April, 08 2024"
        val result = StringUtil.convertDate(input)
        assertEquals(expected, result)
    }

    @Test
    fun `convertDate should return dash when input is invalid`() {
        val input = "invalid-date"
        val result = StringUtil.convertDate(input)
        assertEquals("-", result)
    }

    @Test
    fun `convertDate should return formatted date with different day`() {
        val input = "2025-12-25T00:00:00"
        val expected = "December, 25 2025"
        val result = StringUtil.convertDate(input)
        assertEquals(expected, result)
    }

    @Test
    fun `convertDate should return dash when date is empty`() {
        val input = ""
        val result = StringUtil.convertDate(input)
        assertEquals("-", result)
    }
}
