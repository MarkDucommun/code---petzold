package io.ducommun.code.circuits

import io.ducommun.code.results.flatMap
import io.ducommun.code.succeeded
import org.junit.Before
import org.junit.Test

class SimpleSwitchTest : BulbTest() {

    val power = Power()

    val subject = SimpleSwitch(closedInitially = false)

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

        subject.toggle().succeeded()

        bulbIsOn()

        subject.toggle().succeeded()

        bulbIsOff()

        subject.toggle().succeeded()

        bulbIsOn()

        subject.toggle().succeeded()

        bulbIsOff()
    }
}