package game.engine

import game.engine.utils.Pos

class Level {
    private var map: ArrayList<ArrayList<String>> = ArrayList()
    private var start = Pos(0,0)
    private var brickHeight = 0
    private var brickWidth = 0

    fun setMap( map: ArrayList<ArrayList<String>>){
        this.map = map
    }

    fun getMap():ArrayList<ArrayList<String>> {
        return map
    }

    fun setStart(pos: Pos) {
        start = pos
    }

    fun getStart(): Pos{
        return start
    }

    fun setBrickHeight(brickHeight: Int) {
        this.brickHeight = brickHeight
    }

    fun getBrickHeight(): Int {
        return brickHeight
    }

    fun setBrickWidth(brickWidth: Int) {
        this.brickWidth = brickWidth
    }

    fun getBrickWidth(): Int {
        return brickWidth
    }
}