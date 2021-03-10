package ca.antonious.browser.libraries.javascript.interpreter.builtins

import ca.antonious.browser.libraries.javascript.interpreter.JavascriptObject

class JavascriptRegex(val regex: String, val flags: String) : JavascriptObject() {
    override fun toString(): String {
        return "/$regex/$flags"
    }
}