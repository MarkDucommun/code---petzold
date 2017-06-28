package io.ducommun.code.circuits

interface Switch {

    val closed: Boolean

    fun toggle(): Boolean
}