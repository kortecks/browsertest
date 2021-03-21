package ca.antonious.browser.libraries.css

import ca.antonious.browser.libraries.graphics.core.toColor

class CssAttributeParser {

    fun parseInlineAttributes(inlineStyle: String): List<CssAttribute> {
        return inlineStyle.split(";")
            .flatMap { rawAttribute ->
                if (rawAttribute.isBlank()) {
                    return@flatMap emptyList<CssAttribute>()
                }

                val splitAttribute = rawAttribute.split(":").map { it.trim() }
                parse(attributeName = splitAttribute[0], attributeValue = splitAttribute[1])
            }
    }

    fun parse(attributeName: String, attributeValue: String): List<CssAttribute> {
        val attributes = mutableListOf<CssAttribute>()

        when (attributeName) {
            "width" -> {
                attributes += CssAttribute.Width(size = parseSize(attributeValue))
            }
            "height" -> {
                attributes += CssAttribute.Height(size = parseSize(attributeValue))
            }
            "margin" -> {
                val marginValues = attributeValue.trim().split(" ")
                    .map { it.trim() }
                    .map { parseSize(it) }

                when (marginValues.size) {
                    1 -> {
                        attributes += CssAttribute.MarginTop(size = marginValues[0])
                        attributes += CssAttribute.MarginBottom(size = marginValues[0])
                        attributes += CssAttribute.MarginStart(size = marginValues[0])
                        attributes += CssAttribute.MarginEnd(size = marginValues[0])
                    }
                    2 -> {
                        attributes += CssAttribute.MarginTop(size = marginValues[0])
                        attributes += CssAttribute.MarginBottom(size = marginValues[0])
                        attributes += CssAttribute.MarginStart(size = marginValues[1])
                        attributes += CssAttribute.MarginEnd(size = marginValues[1])
                    }
                    4 -> {
                        attributes += CssAttribute.MarginTop(size = marginValues[0])
                        attributes += CssAttribute.MarginBottom(size = marginValues[1])
                        attributes += CssAttribute.MarginStart(size = marginValues[2])
                        attributes += CssAttribute.MarginEnd(size = marginValues[3])
                    }
                }
            }
            "background-color" -> {
                attributes += CssAttribute.BackgroundColor(color = attributeValue.toColor())
            }
            "color" -> {
                attributes += CssAttribute.Color(color = attributeValue.toColor())
            }
            "text-align" -> {
                attributes += CssAttribute.TextAlignment(
                    alignment = when (attributeValue.trim()) {
                        "left" -> CssHorizontalAlignment.left
                        "center" -> CssHorizontalAlignment.center
                        "right" -> CssHorizontalAlignment.right
                        else -> CssHorizontalAlignment.left
                    }
                )
            }
            "vertical-align" -> {
                attributes += CssAttribute.VerticalAlignment(
                    alignment = when (attributeValue.trim()) {
                        "top" -> CssVerticalAlignment.top
                        "middle" -> CssVerticalAlignment.middle
                        "bottom" -> CssVerticalAlignment.bottom
                        else -> CssVerticalAlignment.top
                    }
                )
            }
            "display" -> {
                attributes += CssAttribute.Display(
                    displayType = when (attributeValue.trim()) {
                        "inline-block" -> CssDisplay.inlineBlock
                        "none" -> CssDisplay.none
                        else -> CssDisplay.block
                    }
                )
            }
            "position" -> {
                attributes += CssAttribute.Position(
                    positionType = when (attributeValue.trim()) {
                        "absolute" -> CssPosition.absolute
                        else -> CssPosition.static
                    }
                )
            }
            "font-size" -> {
                attributes += CssAttribute.FontSize(size = parseSize(attributeValue))
            }
            "left" -> {
                attributes += CssAttribute.Left(size = parseSize(attributeValue))
            }
            "right" -> {
                attributes += CssAttribute.Right(size = parseSize(attributeValue))
            }
            "top" -> {
                attributes += CssAttribute.Top(size = parseSize(attributeValue))
            }
            "bottom" -> {
                attributes += CssAttribute.Bottom(size = parseSize(attributeValue))
            }
        }

        return attributes
    }

    private fun parseSize(size: String): CssSize {
        if (size.endsWith("em")) {
            return CssSize.Em(size.replace("em", "").trim().toInt())
        } else if (size.endsWith("px")) {
            return CssSize.Pixel(size.replace("px", "").trim().toInt())
        } else if (size.endsWith("%")) {
            return CssSize.Percent(size.replace("%", "").trim().toFloat() / 100f)
        } else if (size.toIntOrNull() != null) {
            return CssSize.Pixel(size.toInt())
        }

        return CssSize.Auto
    }
}
