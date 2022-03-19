package ca.antonious.browser.libraries.html

sealed class HtmlParserError(message: String? = null) : Exception(message) {
    class AbsenceOfDigitsInNumericCharacterReference() : HtmlParserError()
    class EofBeforeTagName : HtmlParserError()
    class UnexpectedQuestionMarkBeforeTagName : HtmlParserError()
    class InvalidFirstCharacterOfTagName : HtmlParserError()
    class UnexpectedEqualsSignBeforeAttributeName : HtmlParserError()
    class UnexpectedCharacterInAttributeNameError : HtmlParserError()
    class EofInTag : HtmlParserError()
    class MissingAttributeValue : HtmlParserError()
    class MissingWhitespaceBetweenAttributes : HtmlParserError()
    class UnexpectedCharacterInUnquotedAttributeValue : HtmlParserError()
    class UnexpectedSolidusInTag : HtmlParserError()
    class IncorrectlyOpenedComment : HtmlParserError()
    class EofInDoctype : HtmlParserError()
    class MissingWhitespaceBeforeDoctypeName : HtmlParserError()
    class MissingDoctypeName : HtmlParserError()
    class MissingSemiColonAfterCharacterReference : HtmlParserError()
    class UnknownNamedCharacterReference : HtmlParserError()
    class InvalidCharacterSequenceAfterDoctypeName : HtmlParserError()
    class AbruptClosingOfEmptyComment : HtmlParserError()
    class EofInComment : HtmlParserError()
    class NestedComment : HtmlParserError()
    class IncorrectlyClosedComment : HtmlParserError()
    class EofInScriptHtmlCommentLikeText : HtmlParserError()
    class NullCharacterReference : HtmlParserError()
    class CharacterReferenceOutOfUnicodeRange : HtmlParserError()
    class SurrogateCharacterReferenceError : HtmlParserError()
    class NonCharacterReferenceError : HtmlParserError()
    class ControlCharacterParseError : HtmlParserError()
}
