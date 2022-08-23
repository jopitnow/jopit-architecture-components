package com.gperre.jopit.architecture.components.android.network.annotations

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Timeout(val value: Long = 20, val unit: TimeUnit = TimeUnit.SECONDS)

