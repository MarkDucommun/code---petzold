package io.ducommun.code.circuits

interface Pluggable {

    fun applyCurrent(appliedCurrent: Current?)

    fun removeCurrent()

    fun powerOn()

    fun powerOff()

    val powered: Boolean
}