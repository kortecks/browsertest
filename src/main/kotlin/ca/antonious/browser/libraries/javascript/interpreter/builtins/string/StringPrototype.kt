package ca.antonious.browser.libraries.javascript.interpreter.builtins.string

import ca.antonious.browser.libraries.javascript.ast.JavascriptValue
import ca.antonious.browser.libraries.javascript.interpreter.JavascriptObject
import ca.antonious.browser.libraries.javascript.interpreter.builtins.array.JavascriptArray
import ca.antonious.browser.libraries.javascript.interpreter.builtins.regex.JavascriptRegex
import ca.antonious.browser.libraries.javascript.interpreter.setNonEnumerableNativeFunction

object StringPrototype : JavascriptObject() {
    init {
        setNonEnumerableNativeFunction("match") { executionContext ->
            val stringObject = executionContext.thisBinding as? StringObject
                ?: return@setNonEnumerableNativeFunction JavascriptValue.Undefined

            val regex = executionContext.arguments.first().valueAs<JavascriptValue.Object>()?.value as JavascriptRegex
            JavascriptValue.Object(
                JavascriptArray(
                    Regex(regex.regex).findAll(stringObject.value).map { JavascriptValue.String(it.value) }.toList()
                )
            )
        }

        setNonEnumerableNativeFunction("toLowerCase") { executionContext ->
            val stringObject = executionContext.thisBinding as? StringObject
                ?: return@setNonEnumerableNativeFunction JavascriptValue.Undefined

            JavascriptValue.String(stringObject.value.toLowerCase())
        }

        setNonEnumerableNativeFunction("toUpperCase") { executionContext ->
            val stringObject = executionContext.thisBinding as? StringObject
                ?: return@setNonEnumerableNativeFunction JavascriptValue.Undefined

            JavascriptValue.String(stringObject.value.toUpperCase())
        }

        setNonEnumerableNativeFunction("charAt") { executionContext ->
            val stringObject = executionContext.thisBinding as? StringObject
                ?: return@setNonEnumerableNativeFunction JavascriptValue.Undefined

            val index = executionContext.arguments.firstOrNull()?.coerceToNumber()?.toInt() ?: 0

            if (index >= 0 && index < stringObject.value.length) {
                JavascriptValue.String(stringObject.value[index].toString())
            } else {
                JavascriptValue.String("")
            }
        }
    }
}