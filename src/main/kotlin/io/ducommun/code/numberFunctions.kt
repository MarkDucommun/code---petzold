package io.ducommun.code

val Int.asDecimal: Int get() = toString().reversed().foldIndexed(0) { index, acc, char ->
    acc + (2.power(index) * char.toString().toInt())
}

data class Acc(
        val binary: String = "",
        val remaining: Int
)

val Int.asBinary: Int get() {

    var index = 0
    var largestDivisibleExponent: Int? = null

    while (largestDivisibleExponent == null) {

        if (this / 2.power(index) > 0) index++ else largestDivisibleExponent = index - 1
    }

    return 0.rangeTo(largestDivisibleExponent).reversed().fold(Acc(remaining = this)) { acc, value ->

        acc.copy(
                remaining = acc.remaining % 2.power(value),
                binary = acc.binary + (acc.remaining / 2.power(value)).toString()
        )
    }.binary.let { binary -> if (binary.isEmpty()) 0 else binary.toInt() }
}

fun Int.power(exponent: Int): Int = List(exponent) { this }.reduceIfNotEmptyElseNull(Int::times) ?: 1

fun <T> List<T>.reduceIfNotEmptyElseNull(operation: (acc: T, T) -> T): T? = if (isEmpty()) null else reduce(operation)