package com.gperre.jopit.architecture.components

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}