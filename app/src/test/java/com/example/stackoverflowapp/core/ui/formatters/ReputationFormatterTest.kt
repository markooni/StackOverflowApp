package com.example.stackoverflowapp.core.ui.formatters

import org.junit.Assert.assertEquals
import org.junit.Test

class ReputationFormatterTest {

    @Test
    fun `values under one thousand format as plain integer`() {
        assertEquals("0", formatReputation(0))
        assertEquals("1", formatReputation(1))
        assertEquals("999", formatReputation(999))
    }

    @Test
    fun `values in thousands format with k suffix`() {
        assertEquals("1.0k", formatReputation(1_000))
        assertEquals("1.5k", formatReputation(1_500))
        assertEquals("999.9k", formatReputation(999_900))
    }

    @Test
    fun `values in millions format with m suffix`() {
        assertEquals("1.0m", formatReputation(1_000_000))
        assertEquals("1.5m", formatReputation(1_454_978))
        assertEquals("12.3m", formatReputation(12_345_678))
    }

    @Test
    fun `formatting is locale independent`() {
        // Some locales render decimals with a comma; we always want a dot.
        val previous = java.util.Locale.getDefault()
        try {
            java.util.Locale.setDefault(java.util.Locale.GERMANY)
            assertEquals("1.5k", formatReputation(1_500))
        } finally {
            java.util.Locale.setDefault(previous)
        }
    }
}
