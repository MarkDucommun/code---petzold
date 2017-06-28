package io.ducommun.code.circuits

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Before
import org.junit.Test

class SimpleSplitTest {

    val power = Power()

    val switchOne = SimpleSwitch(closedInitially = false)
    val switchTwo = SimpleSwitch(closedInitially = false)
    val switchThree = SimpleSwitch(closedInitially = false)

    val split = SimpleSplit()

    val bulbOne = Bulb()
    val bulbTwo = Bulb()
    val bulbThree = Bulb()

    val groundOne = Ground()
    val groundTwo = Ground()

    @Before
    fun setUp() {
        power.connect(switchOne)
        switchOne.connect(bulbOne)
        bulbOne.connect(split)

        split.connectOne(switchTwo)
        switchTwo.connect(bulbTwo)
        bulbTwo.connect(groundOne)

        split.connectTwo(switchThree)
        switchThree.connect(bulbThree)
        bulbThree.connect(groundTwo)
    }

    @Test
    fun `it splits a current`() {

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isFalse()

        switchOne.toggle()
        switchTwo.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()
        assertThat(bulbThree.powered).isFalse()
    }

    @Test
    fun `it can turn  on all the currents`() {

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isFalse()

        switchOne.toggle()
        switchTwo.toggle()
        switchThree.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()
        assertThat(bulbThree.powered).isTrue()
    }

    @Test
    fun `it can turn  off targeted currents`() {

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isFalse()

        switchOne.toggle()
        switchTwo.toggle()
        switchThree.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()
        assertThat(bulbThree.powered).isTrue()

        switchTwo.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isTrue()
    }

    @Test
    fun `it can turn  off all the currents`() {

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isFalse()

        switchOne.toggle()
        switchTwo.toggle()
        switchThree.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()
        assertThat(bulbThree.powered).isTrue()

        switchOne.toggle()

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isFalse()
    }

    @Test
    fun `it can disconnect one of the currents`() {

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isFalse()

        switchOne.toggle()
        switchTwo.toggle()
        switchThree.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()
        assertThat(bulbThree.powered).isTrue()

        split.disconnectOne()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isTrue()
    }

    @Test
    fun `it can disconnect the other the currents`() {

        assertThat(bulbOne.powered).isFalse()
        assertThat(bulbTwo.powered).isFalse()
        assertThat(bulbThree.powered).isFalse()

        switchOne.toggle()
        switchTwo.toggle()
        switchThree.toggle()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()
        assertThat(bulbThree.powered).isTrue()

        split.disconnectTwo()

        assertThat(bulbOne.powered).isTrue()
        assertThat(bulbTwo.powered).isTrue()
        assertThat(bulbThree.powered).isFalse()
    }
}