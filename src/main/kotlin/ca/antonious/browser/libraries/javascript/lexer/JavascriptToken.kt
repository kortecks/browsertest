package ca.antonious.browser.libraries.javascript.lexer

sealed class JavascriptTokenType {
    data class Identifier(val name: kotlin.String) : JavascriptTokenType()
    object Dot : JavascriptTokenType()
    object Comma : JavascriptTokenType()
    object Colon : JavascriptTokenType()
    object SemiColon : JavascriptTokenType()
    object QuestionMark : JavascriptTokenType()
    object Arrow : JavascriptTokenType()
    object OpenParentheses : JavascriptTokenType()
    object CloseParentheses : JavascriptTokenType()
    object OpenCurlyBracket : JavascriptTokenType()
    object CloseCurlyBracket : JavascriptTokenType()
    object OpenBracket : JavascriptTokenType()
    object CloseBracket : JavascriptTokenType()

    object Function : JavascriptTokenType()
    object Do : JavascriptTokenType()
    object While : JavascriptTokenType()
    object For : JavascriptTokenType()
    object If : JavascriptTokenType()
    object Else : JavascriptTokenType()
    object Return : JavascriptTokenType()
    object Let : JavascriptTokenType()
    object Const : JavascriptTokenType()
    object Var : JavascriptTokenType()
    object PlusPlus : JavascriptTokenType()
    object MinusMinus : JavascriptTokenType()
    object New : JavascriptTokenType()
    object Try : JavascriptTokenType()
    object Catch : JavascriptTokenType()
    object Finally : JavascriptTokenType()
    object Throw : JavascriptTokenType()
    object In : JavascriptTokenType()
    object TypeOf : JavascriptTokenType()
    object Void : JavascriptTokenType()
    object Delete : JavascriptTokenType()
    object InstanceOf : JavascriptTokenType()
    object Break : JavascriptTokenType()
    object Continue : JavascriptTokenType()

    data class RegularExpression(val regex: kotlin.String, val flags: kotlin.String) : JavascriptTokenType()
    data class String(val value: kotlin.String) : JavascriptTokenType()
    data class Number(val value: Double) : JavascriptTokenType()
    data class Boolean(val value: kotlin.Boolean) : JavascriptTokenType()
    object Undefined : JavascriptTokenType()
    object Null : JavascriptTokenType()

    sealed class Operator : JavascriptTokenType() {
        object Plus : Operator()
        object Minus : Operator()
        object Multiply : Operator()
        object Divide : Operator()
        object Or : Operator()
        object And : Operator()
        object AndAnd : Operator()
        object OrOr : Operator()
        object Mod : Operator()
        object Xor : Operator()
        object LessThan : Operator()
        object LessThanOrEqual : Operator()
        object GreaterThan : Operator()
        object GreaterThanOrEqual : Operator()
        object OrAssign : Operator()
        object AndAssign : Operator()
        object XorAssign : Operator()
        object ModAssign : Operator()
        object PlusAssign : Operator()
        object MinusAssign : Operator()
        object MultiplyAssign : Operator()
        object DivideAssign : Operator()
        object Assignment : Operator()
        object Equals : Operator()
        object NotEquals : Operator()
        object StrictEquals : Operator()
        object StrictNotEquals : Operator()
        object BitNot : Operator()
        object Not : Operator()
        object LeftShift : Operator()
        object RightShift : Operator()
    }
}

data class JavascriptToken(
    val type: JavascriptTokenType,
    val sourceInfo: SourceInfo
)

data class SourceInfo(
    val line: Int,
    val column: Int,
    val filename: String = "unknown",
    val source: String = ""
)
