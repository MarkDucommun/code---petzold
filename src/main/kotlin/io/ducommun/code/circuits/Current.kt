package io.ducommun.code.circuits

interface Current {

    var complete: Boolean

    val source: VoltageSource
}