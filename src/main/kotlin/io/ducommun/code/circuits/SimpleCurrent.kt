package io.ducommun.code.circuits

import io.ducommun.code.results.Result

class SimpleCurrent(
        override val power: () -> Result<Current.PowerFailure, Unit>
) : Current {

    override var complete: Boolean = false
}