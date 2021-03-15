package ca.antonious.browser.libraries.layout.libgdx

import ca.antonious.browser.libraries.graphics.core.Point
import ca.antonious.browser.libraries.graphics.libgdx.LibgdxCanvas
import ca.antonious.browser.libraries.graphics.libgdx.LibgdxDrawCall
import ca.antonious.browser.libraries.graphics.libgdx.LibgdxFontProvider
import ca.antonious.browser.libraries.graphics.libgdx.LibgdxMeasuringTape
import ca.antonious.browser.libraries.layout.core.InputEvent
import ca.antonious.browser.libraries.layout.core.LayoutNode
import ca.antonious.browser.libraries.layout.core.LayoutRunner
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align

class LibgdxLayoutRunner : LayoutRunner {
    override fun runLayout(layoutNode: LayoutNode) {
        val config = LwjglApplicationConfiguration().apply {
            title = "Browser"
            useHDPI = true
        }

        LwjglApplication(LibgdxLayoutRunnerApplication(layoutNode), config)
    }
}

private class LibgdxLayoutRunnerApplication(val rootNode: LayoutNode) : ApplicationAdapter(), InputProcessor {
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var camera: OrthographicCamera
    private val fontProvider = LibgdxFontProvider()

    private val inputEventsToProcess = mutableListOf<InputEvent>()

    override fun create() {
        Gdx.input.inputProcessor = this
        camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.width.toFloat())
        camera.setToOrtho(true, Gdx.graphics.width.toFloat(), Gdx.graphics.width.toFloat())
        spriteBatch = SpriteBatch()
        shapeRenderer = ShapeRenderer()
        shapeRenderer.setAutoShapeType(true)
    }

    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.setToOrtho(true, width.toFloat(), height.toFloat())
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        camera.update()
        spriteBatch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined

        val measureTape = LibgdxMeasuringTape(fontProvider)

        for (inputEvent in inputEventsToProcess) {
            rootNode.handleInputEvent(inputEvent)
        }

        inputEventsToProcess.clear()

        rootNode.measure(
            measuringTape = measureTape,
            widthConstraint = camera.viewportWidth,
            heightConstraint = camera.viewportHeight
        )

        val canvas = LibgdxCanvas()
        rootNode.drawTo(canvas)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        canvas.drawCalls.filterIsInstance<LibgdxDrawCall.DrawRect>().forEach { drawRectCall ->
            val paint = drawRectCall.paint
            val rect = drawRectCall.rect
            shapeRenderer.color = Color(paint.color.r, paint.color.g, paint.color.b, paint.color.a)
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height)
        }
        shapeRenderer.end()

        spriteBatch.begin()
        canvas.drawCalls.filterIsInstance<LibgdxDrawCall.DrawText>().forEach { drawTextCall ->
            val font = fontProvider.getFont(drawTextCall.font)
            val color = drawTextCall.paint.color
            font.color = Color(color.r, color.g, color.b, color.a)
            font.draw(
                spriteBatch,
                drawTextCall.text,
                drawTextCall.x,
                drawTextCall.y,
                drawTextCall.width,
                Align.left,
                true
            )
        }
        spriteBatch.end()
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        inputEventsToProcess += InputEvent.TouchUp(Point(screenX.toFloat(), screenY.toFloat()))
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        inputEventsToProcess += InputEvent.OnScrolled(dy = amountY)
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        inputEventsToProcess += InputEvent.KeyDown(key = keycode.key)
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }
}
