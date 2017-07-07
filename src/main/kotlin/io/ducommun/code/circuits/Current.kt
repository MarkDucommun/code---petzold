package io.ducommun.code.circuits

import io.ducommun.code.results.Result

interface Current {

    var complete: Boolean

    val power: () -> Result<PowerFailure, Unit>

    sealed class PowerFailure
}