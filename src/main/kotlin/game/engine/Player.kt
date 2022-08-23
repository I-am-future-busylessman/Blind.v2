package game.engine

import game.engine.utils.Pos

class Player(private var x: Int, private var y: Int) {

    fun getPos(): Pos {
        return(Pos(x, y))
    }
    fun move(futurePos: Pos) {
        x = futurePos.x
        y = futurePos.y
    }

    fun getX() = x

    fun getY() = y

}