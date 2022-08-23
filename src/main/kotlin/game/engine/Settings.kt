package game.engine

class Settings(
    private val width: Int,
    private val height: Int,
    private val fps: Int,
    private val speed: Int
    ) {

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }

    fun getFPS(): Int {
        return fps
    }

    fun getSpeed(): Int {
        return speed
    }

}