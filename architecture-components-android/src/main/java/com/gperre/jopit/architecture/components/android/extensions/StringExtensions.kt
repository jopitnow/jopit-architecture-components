package com.gperre.jopit.architecture.components.android.extensions

fun String.Companion.empty(): String = ""

fun String.Companion.space(): String = " "

fun String.Companion.comma(): String = ","

fun String?.valueOrDefault(default: String = String.empty()) = this?.let { it } ?: default