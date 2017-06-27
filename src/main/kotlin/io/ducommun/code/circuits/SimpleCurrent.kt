package io.ducommun.code.circuits

class SimpleCurrent(
        override val source: VoltageSource
) : Current {

    override var complete: Boolean = false
}