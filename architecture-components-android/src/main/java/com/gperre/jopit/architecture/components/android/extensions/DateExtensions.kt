package com.gperre.jopit.architecture.components.android.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.getFormattedDate(format: String): String {
    return SimpleDateFormat(
        format,
        Locale.getDefault(Locale.Category.FORMAT)
    ).format(this)
}
