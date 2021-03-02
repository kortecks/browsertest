package ca.antonious.browser.libraries.javascript.ast

import ca.antonious.browser.libraries.javascript.interpreter.JavascriptFunction
import ca.antonious.browser.libraries.javascript.interpreter.JavascriptObject

sealed class JavascriptNode {
    data class Program(val body: List<JavascriptNode>) : JavascriptNode()
    data class Function(val name: String, val parameterNames: List<String>, val body: List<JavascriptNode>) : JavascriptNode()
    data class Return(val expression: JavascriptExpression) : JavascriptNode()
    data class IfStatement(val condition: JavascriptExpression, val body: List<JavascriptNode>) : JavascriptNode()
    data class WhileLoop(val condition: JavascriptExpression, val body: List<JavascriptNode>) : JavascriptNode()
    data class LetAssignment(val name: String, val expression: JavascriptExpression) : JavascriptNode()
}

sealed class JavascriptExpression : JavascriptNode() {
    data class FunctionCall(val expression: JavascriptExpression, val parameters: List<JavascriptExpression>) : JavascriptExpression()
    data class DotAccess(val propertyName: String, val expression: JavascriptExpression) : JavascriptExpression()
    data class Reference(val name: String) : JavascriptExpression()
    data class Literal(val value: JavascriptValue) : JavascriptExpression()
    data class BooleanOperation(val operator: JavascriptBooleanOperator, val lhs: JavascriptExpression, val rhs: JavascriptExpression): JavascriptExpression()
}

sealed class JavascriptBooleanOperator {
    object Add : JavascriptBooleanOperator()
    object Subtract : JavascriptBooleanOperator()
    object Multiply : JavascriptBooleanOperator()
    object LessThan : JavascriptBooleanOperator()
}

sealed class JavascriptValue {
    abstract val isTruthy: kotlin.Boolean

    object Undefined : JavascriptValue() {
        override val isTruthy = false
        override fun toString() = "undefined"
    }

    data class Boolean(val value: kotlin.Boolean) : JavascriptValue() {
        override val isTruthy = value
        override fun toString() = value.toString()
    }

    data class Number(val value: Double) : JavascriptValue() {
        override val isTruthy = value != 0.0
        override fun toString() = value.toString()
    }

    data class String(val value: kotlin.String) : JavascriptValue() {
        override val isTruthy = value.isNotEmpty()
        override fun toString() = value
    }

    data class Object(val value: JavascriptObject) : JavascriptValue() {
        override val isTruthy = true
        override fun toString() = value.toString()
    }

    data class Function(val value: JavascriptFunction) : JavascriptValue() {
        override val isTruthy = true
        override fun toString() = value.toString()
    }
}