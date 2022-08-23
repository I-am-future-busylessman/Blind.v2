package game.engine

import java.awt.Color
import java.awt.image.BufferedImage

class Image (
    private val width: Int,
    private val height: Int,
    ) {

    fun render(level: Level): BufferedImage {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g = image.graphics
        val map = level.getMap()
        val brickHeight = height/map.size
        val brickWidth = width/map[0].size

        g.color = Color.BLACK
        g.fillRect(0,0, image.width, image.height)
        g.color = Color.RED
        for (y in 0 until map.size) {
            for (x in 0 until map[y].size){
                if (map[y][x] == "1") {
                    g.fillRect(brickWidth * x, brickHeight * y, brickWidth, brickHeight)
                }
            }
        }

        return image
    }

}