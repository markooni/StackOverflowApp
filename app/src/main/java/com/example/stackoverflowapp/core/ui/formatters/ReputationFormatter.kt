package com.example.stackoverflowapp.core.ui.formatters

import java.util.Locale

fun formatReputation(reputation: Int): String {
    return when {
        reputation >= 1_000_000 -> String.format(Locale.US, "%.1fm", reputation / 1_000_000.0)
        reputation >= 1_000 -> String.format(Locale.US, "%.1fk", reputation / 1_000.0)
        else -> reputation.toString()
    }
}
