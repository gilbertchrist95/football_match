package com.treblig.footballmatch.util

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat

class DateTimeTest{
    @Test
    fun testToSimpleString(){
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("02/28/2018")
        assertEquals("Wed, 28 02 2018", DateTime.toSimpleString(date))
    }
}