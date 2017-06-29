package io.ducommun.code

import org.junit.Assert.fail

infix inline fun <reified type> Any.isTypeAnd(fn: (type) -> Unit) : Unit {
    if (this is type) {
        fn(this)
    } else {
        fail("Object was not of type ${type::class.simpleName} - was ${this::class.simpleName}")
    }
}

inline fun <reified type> Any.isType() : Unit {
    isTypeAnd<type> { Unit }
}