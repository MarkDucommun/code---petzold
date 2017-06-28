package io.ducommun.code.circuits

interface ImmutableSwitchToggler : Pluggable {

    val switch: MutableSwitch

    val pluggedIn: Pluggable
}