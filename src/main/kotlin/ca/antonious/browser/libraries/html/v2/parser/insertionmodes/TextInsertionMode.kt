package ca.antonious.browser.libraries.html.v2.parser.insertionmodes

import ca.antonious.browser.libraries.html.v2.parser.HtmlParser
import ca.antonious.browser.libraries.html.v2.parser.HtmlParserInsertionMode
import ca.antonious.browser.libraries.html.v2.tokenizer.HtmlToken

object TextInsertionMode : HtmlParserInsertionMode {
    override fun process(token: HtmlToken, parser: HtmlParser) {
        when {
            token is HtmlToken.Character -> parser.insertCharacter(token.char)
            token is HtmlToken.EndOfFile -> {
                parser.switchInsertionModeTo(parser.originalInsertionMode)
                parser.popCurrentNode()
                parser.originalInsertionMode.process(token, parser)
            }
            token is HtmlToken.EndTag && token.name == "script" -> {
                // Todo run script instantly
                parser.popCurrentNode()
                parser.switchToOriginalInsertionMode()
            }
            token is HtmlToken.EndTag -> {
                parser.popCurrentNode()
                parser.switchToOriginalInsertionMode()
            }
        }
    }
}
