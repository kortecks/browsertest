package ca.antonious.browser.libraries.web

import ca.antonious.browser.libraries.css.*
import ca.antonious.browser.libraries.graphics.core.Color
import ca.antonious.browser.libraries.graphics.core.MeasuringTape

data class ResolvedStyle(
    var margins: CssInsets = CssInsets.zero(),
    var padding: CssInsets = CssInsets.zero(),
    var fontSize: CssSize = CssSize.Pixel(8),
    var width: CssSize = CssSize.Auto,
    var height: CssSize = CssSize.Auto,
    var backgroundColor: Color = Color.clear,
    var color: Color = Color.black,
    var textAlignment: CssHorizontalAlignment = CssHorizontalAlignment.left,
    var verticalAlignment: CssVerticalAlignment = CssVerticalAlignment.top,
    var displayType: CssDisplay = CssDisplay.block
)

data class CssInsets(
    var start: CssSize,
    var end: CssSize,
    var top: CssSize,
    var bottom: CssSize
) {
    companion object {
        fun zero() = CssInsets(
            start = CssSize.Pixel(0),
            top = CssSize.Pixel(0),
            end = CssSize.Pixel(0),
            bottom = CssSize.Pixel(0)
        )
    }
}

fun MeasuringTape.resolveSize(size: CssSize): Float? {
    return when (size) {
        is CssSize.Pixel -> size.size * 2f
        is CssSize.Em -> size.size * 20f
        is CssSize.Auto -> null
        is CssSize.Percent -> null
    }
}
