package io.ducommun.code

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.Test

class numberFunctionTests {

    @Test
    fun `it transforms a binary into a decimal`() {
        assertThat(0.asDecimal).isEqualTo(0)
        assertThat(1.asDecimal).isEqualTo(1)
        assertThat(10.asDecimal).isEqualTo(2)
        assertThat(11.asDecimal).isEqualTo(3)
        assertThat(100.asDecimal).isEqualTo(4)
        assertThat(101.asDecimal).isEqualTo(5)
        assertThat(110.asDecimal).isEqualTo(6)
        assertThat(111.asDecimal).isEqualTo(7)
        assertThat(10010110.asDecimal).isEqualTo(150)
    }

    @Test
    fun `it transforms decimal into binary`() {
        assertThat(0.asBinary).isEqualTo(0)
        assertThat(1.asBinary).isEqualTo(1)
        assertThat(2.asBinary).isEqualTo(10)
        assertThat(3.asBinary).isEqualTo(11)
        assertThat(4.asBinary).isEqualTo(100)
        assertThat(5.asBinary).isEqualTo(101)
        assertThat(6.asBinary).isEqualTo(110)
        assertThat(7.asBinary).isEqualTo(111)
        assertThat(150.asBinary).isEqualTo(10010110)
    }

    @Test
    fun `power works`() {
        assertThat(1.power(0)).isEqualTo(1)
        assertThat(2.power(0)).isEqualTo(1)
        assertThat(2.power(1)).isEqualTo(2)
        assertThat(2.power(2)).isEqualTo(4)
        assertThat(2.power(3)).isEqualTo(8)
        assertThat(2.power(4)).isEqualTo(16)
    }
}