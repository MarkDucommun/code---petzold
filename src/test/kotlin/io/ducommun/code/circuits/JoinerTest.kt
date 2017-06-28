package io.ducommun.code.circuits

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import org.junit.Test


class JoinerTest {

    val powerOne = Power()
    val powerTwo = Power()

    val switchOne = SimpleSwitch(closedInitially = false)
    val switchTwo = SimpleSwitch(closedInitially = false)

    val joiner = Joiner()

    val bulb = Bulb()

    val ground = Ground()

    @Before
    fun setUp() {
        powerOne.connect(switchOne)
        switchOne.connect(joiner.connectionOne)

        powerTwo.connect(switchTwo)
        switchTwo.connect(joiner.connectionTwo)
        joiner.connect(bulb)
        bulb.connect(ground)
    }

    @Test
    fun `it powers on when one side is powered`() {

        switchOne.toggle()

        assertThat(bulb.powered).isTrue()
    }

    @Test
    fun `it powers on when the other side is powered`() {

        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()
    }

    @Test
    fun `it powers on when both sides are powered`() {

        switchOne.toggle()
        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()
    }

    @Test
    fun `it doesnt power off when one of two sides is toggled off`() {

        switchOne.toggle()
        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()

        switchOne.toggle()

        assertThat(bulb.powered).isTrue()
    }


    @Test
    fun `it doesnt power off when the other of two sides is toggled off`() {

        switchOne.toggle()
        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()

        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()
    }

    @Test
    fun `it does power off`() {

        switchOne.toggle()
        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()

        switchTwo.toggle()
        switchOne.toggle()

        assertThat(bulb.powered).isFalse()
    }


    @Test
    fun `power does not transmit if the circuit is not complete`() {

        switchOne.toggle()
        switchTwo.toggle()

        assertThat(bulb.powered).isTrue()

        bulb.disconnect()

        assertThat(switchOne.powered).isFalse()
    }
}