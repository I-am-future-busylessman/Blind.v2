package game.engine

import game.engine.manager.LevelsManager
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame
import kotlin.concurrent.thread
import game.engine.manager.SettingsManager
import game.engine.utils.Pos
import java.awt.Color
import java.awt.image.BufferedImage

class Window: JFrame(), KeyListener  {

    lateinit var image: Image
    lateinit var settings: Settings
    lateinit var player: Player
    var keyBuffer = arrayListOf<Int>()
    var levelNum = 0
    var level = Level()
    var playerSize = 10
    lateinit var renderedImage: BufferedImage

    fun start() {
        settings = SettingsManager.getFromFile()
        LevelsManager.getLevels()
        level = LevelsManager.levels[levelNum]
        player = Player(level.getStart().x * level.getBrickWidth(), level.getStart().y * level.getBrickHeight())
        image = Image(settings.getWidth(), settings.getHeight())
        renderedImage = image.render(level)
        name = "blind"
        size = Dimension(settings.getWidth(), settings.getHeight())
        isUndecorated = true
        isVisible = true

        addKeyListener(this)

        startRender()
        startInput()

        



    }

    fun startRender() = thread {
        while (isVisible) {
            Thread.sleep((1000/settings.getFPS()).toLong())
            Toolkit.getDefaultToolkit().sync()

            graphics?.let { render(it) }
        }
    }

    fun render(g: Graphics) {
        renderedImage = image.render(level)
        renderPlayer(renderedImage.graphics)

        g.drawImage(renderedImage, 0, 0, settings.getWidth(), settings.getHeight(), null)
    }

    fun renderPlayer(g: Graphics) {
        g.color = Color.WHITE

        g.fillRect(player.getX(), player.getY(), 1, 1)
    }

    fun startInput() = thread {
        while (isVisible) {
            Thread.sleep((1000/settings.getFPS()).toLong())
            (keyBuffer.clone() as List<Int>).forEach {
                when (it) {
                    //UP
                    87 -> {
                        val futurePos = Pos(player.getX(), player.getY() - settings.getSpeed())
                        if(validateMove(futurePos)) {
                            player.move(futurePos)
                        }
                    }
                    //DOWN
                    83 -> {
                        val futurePos = Pos(player.getX(), player.getY() + settings.getSpeed())
                        if(validateMove(futurePos)) {
                            player.move(futurePos)
                        }
                    }
                    //LEFT
                    65 -> {
                        val futurePos = Pos(player.getX() - settings.getSpeed(), player.getY())
                        if(validateMove(futurePos)) {
                            player.move(futurePos)
                        }
                    }
                    //RIGHT
                    68 -> {
                        val futurePos = Pos(player.getX() + settings.getSpeed(), player.getY())
                        if(validateMove(futurePos)) {
                            player.move(futurePos)
                        }
                    }
                    //EXIT
                    27 -> {
                        isVisible = false
                    }
                    else -> {
                        println("Unknown key $it")
                        keyBuffer.remove(it)
                    }
                }
            }
        }

    }

    fun validateMove(futurePos: Pos): Boolean {
        if(futurePos.y in 0 until (settings.getHeight() - playerSize)
            && futurePos.x in 0 until (settings.getWidth() - playerSize)
            && Color(renderedImage.getRGB(futurePos.x , futurePos.y ) )!= Color.RED) {
            return true
        }
        return false
    }

    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent) {
        if(!keyBuffer.contains(e.keyCode))
            keyBuffer.add(e.keyCode)
    }

    override fun keyReleased(e: KeyEvent) {
        keyBuffer.remove(e.keyCode)
    }
}