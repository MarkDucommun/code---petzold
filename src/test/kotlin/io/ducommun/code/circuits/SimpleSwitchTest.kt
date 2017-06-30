package io.ducommun.code.circuits

import io.ducommun.code.results.flatMap
import io.ducommun.code.succeeded
import io.ducommun.code.succeedsAnd
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import org.junit.Test

class SimpleSwitchTest {

    val power = Power()

    val subject = SimpleSwitch(closedInitially = false)

    val bulb = Bulb()

    val ground = Ground()

    @Before
    fun setUp() {
        power
                .connect(subject)
                .flatMap { subject.connect(bulb) }
                .flatMap { bulb.connect(ground) }
                .succeeded()
    }

    @Test
    fun `the switch turns on and off repeatedly`() {

        bulbIsOff()

        subject.toggle().succeeded()

        bulbIsOn()

        subject.toggle().succeeded()

        bulbIsOff()

        subject.toggle().succeeded()

        bulbIsOn()

        subject.toggle().succeeded()

        bulbIsOff()
    }

    private fun bulbIsOff() { assertThat(bulb.powered).isFalse() }
    private fun bulbIsOn() { assertThat(bulb.powered).isTrue() }
}