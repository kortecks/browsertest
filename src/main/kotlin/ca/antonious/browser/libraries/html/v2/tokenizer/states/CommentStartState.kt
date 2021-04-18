package ca.antonious.browser.libraries.html.v2.tokenizer.states

import ca.antonious.browser.libraries.html.v2.HtmlParserError
import ca.antonious.browser.libraries.html.v2.tokenizer.HtmlTokenizer
import ca.antonious.browser.libraries.html.v2.tokenizer.HtmlTokenizerState

object CommentStartState : HtmlTokenizerState {
    override fun tickState(tokenizer: HtmlTokenizer) {
        val nextChar = tokenizer.consumeNextChar()

        when {
            nextChar == '-' -> tokenizer.switchStateTo(CommentStartDashState)
            nextChar == '>' -> {
                tokenizer.emitError(HtmlParserError.AbruptClosingOfEmptyComment())
                tokenizer.switchStateTo(DataState)
                tokenizer.emitCurrentToken()
            }
            else -> {
                tokenizer.reconsumeIn(CommentState)
            }
        }
    }
}
