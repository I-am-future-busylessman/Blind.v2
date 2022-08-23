package game.engine

class Player(private var x: Int, private var y: Int, private var speed: Int) {

    fun moveUp() {
        y -= 1*speed
    }

    fun moveUp(step: Int) {
        y -= step
    }

    fun moveDown() {
        y += 1*speed
    }

    fun moveDown(step: Int) {
        y += step
    }

    fun moveLeft() {
        x -= 1*speed
    }

    fun moveLeft(step: Int) {
        x -= step
    }

    fun moveRight() {
        x += 1*speed
    }

    fun moveRight(step: Int) {
        x += step
    }

    fun getX() = x

    fun getY() = y

}