package com.group.libraryapp.calculator

import java.lang.IllegalArgumentException

class Calculator(
    var number: Int
) {
    //    // TODO: 이런것도 방법인데, 그냥 public으로 열어두고 setter를 의도적으로 사용하지 않는 것이 더 좋다.
//    val number : Int
//        get() = this._number
    fun add(operand: Int) {
        this.number += operand
    }

    fun minus(operand: Int) {
        this.number -= operand
    }

    fun multiply(operand: Int) {
        this.number *= operand
    }

    fun division(operand: Int) {
        if (operand == 0) {
            throw IllegalArgumentException("0으로 나눌수 없다")
        }
        this.number /= operand
    }


}