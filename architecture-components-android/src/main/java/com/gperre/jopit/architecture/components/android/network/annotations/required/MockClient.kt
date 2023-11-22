package com.gperre.jopit.architecture.components.android.network.annotations.required

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MockClient(
    val url: String
)
