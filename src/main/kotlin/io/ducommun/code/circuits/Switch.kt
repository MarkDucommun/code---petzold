package io.ducommun.code.circuits

import io.ducommun.code.results.Result

interface Switch {

    val closed: Boolean

    fun toggle(): Result<ToggleError, Unit>
}