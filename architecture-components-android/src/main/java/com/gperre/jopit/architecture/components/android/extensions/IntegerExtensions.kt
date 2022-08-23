package com.gperre.jopit.architecture.components.android.extensions

fun Int?.isValue(value: Int): Boolean = this?.let { it == value } ?: false

inline val Int.Companion.ZERO_VALUE: Int get() = 0