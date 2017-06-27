package io.ducommun.code.circuits

interface Switch : Connectible {

    val closed: Boolean

    fun toggle(): Boolean
}