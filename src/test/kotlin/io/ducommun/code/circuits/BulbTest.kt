package io.ducommun.code.circuits

import org.assertj.core.api.KotlinAssertions

abstract class BulbTest {

    protected val bulb = Bulb()

    protected fun bulbIsOff() { KotlinAssertions.assertThat(bulb.powered).isFalse() }
    protected fun bulbIsOn() { KotlinAssertions.assertThat(bulb.powered).isTrue() }
}