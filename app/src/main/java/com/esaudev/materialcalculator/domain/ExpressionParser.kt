package com.esaudev.materialcalculator.domain

class ExpressionParser(
    private val calculation: String
) {

    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()

        var i = 0
        while (i <calculation.length) {
            val cuChar = calculation[i]
            when {
                cuChar in operationSymbols -> {
                    result.add(ExpressionPart.Op(operationFromSymbol(cuChar)))
                }
                cuChar.isDigit() -> {
                    i = parseNumber(i, result)
                    continue
                }
                cuChar in "()" -> {
                    parseParentheses(cuChar, result)
                }
            }
            i++
        }
        return result
    }

    private fun parseNumber(startingIndex: Int, result: MutableList<ExpressionPart>): Int {
        var i = startingIndex
        val numberAsString = buildString {
            while (i < calculation.length && calculation[i] in "0123456789.") {
                append(calculation[i])
                i++
            }
        }
        result.add(ExpressionPart.Number(numberAsString.toDouble()))
        return i
    }

    private fun parseParentheses(cuChar: Char, result: MutableList<ExpressionPart>) {
        result.add(
            ExpressionPart.Parentheses(
                type = when(cuChar) {
                    '(' -> ParenthesesType.Opening
                    ')' -> ParenthesesType.Closing
                    else -> throw IllegalArgumentException("Invalid parentheses type")
                }
            )
        )
    }

}