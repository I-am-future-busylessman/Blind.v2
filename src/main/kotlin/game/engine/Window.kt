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
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.abs

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
        player = Player(level.getStart().x * level.getBrickWidth(), level.getStart().y * level.getBrickHeight(), settings.getSpeed())
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

        g.fillRect(player.getX(), player.getY(), playerSize, playerSize)
    }

    fun startInput() = thread {
        while (isVisible) {
            Thread.sleep((1000/settings.getFPS()).toLong())
            (keyBuffer.clone() as List<Int>).forEach {
                when (it) {
                    //UP
                    87 -> {
                        if (player.getY() - 1*settings.getSpeed() > 0 && Color(renderedImage.getRGB(player.getX(), player.getY() - 1*settings.getSpeed())) != Color.RED) {
                            player.moveUp()
                        } else if (Color(renderedImage.getRGB(player.getX(), player.getY() - 1*settings.getSpeed())) == Color.RED) {
                            if (Color(renderedImage.getRGB(player.getX(), player.getY() - 2)) != Color.RED) {
                                player.moveUp(1)
                            }
                        }
                        else {
                            player.moveUp(player.getY())
                        }
                    }
                    //DOWN
                    83 -> {
                        if (player.getY() + 1*settings.getSpeed() < settings.getHeight()) {
                            player.moveDown()
                        }else {
                            player.moveDown(abs(settings.getHeight() - player.getY() - playerSize))
                        }
                    }
                    //LEFT
                    65 -> {
                        if (player.getX() - 1*settings.getSpeed() > 0) {
                            player.moveLeft()
                        } else {
                            player.moveLeft(player.getX())
                        }
                    }
                    //RIGHT
                    68 -> {
                        if (player.getX() + 1*settings.getSpeed() < settings.getWidth()) {
                            player.moveRight()
                        } else {
                            player.moveRight(abs(settings.getWidth() - player.getX() - playerSize))
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