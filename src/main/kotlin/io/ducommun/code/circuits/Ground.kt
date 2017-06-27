package io.ducommun.code.circuits

class Ground : VoltageSink {

    override fun applyCurrent(appliedCurrent: Current?) {
        current = appliedCurrent
        appliedCurrent?.complete = true
        current?.source?.power()
    }

    override fun removeCurrent() {
        current?.complete = false
        current = null
    }

    private var current: Current? = null

    override fun powerOn() {}

    override fun powerOff() {}

    override val powered: Boolean get() = current?.complete ?: false
}