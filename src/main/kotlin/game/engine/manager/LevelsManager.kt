package game.engine.manager

import game.engine.Level
import game.engine.utils.Pos
import java.io.File

object LevelsManager {
    val levels = ArrayList<Level>()

    fun getLevels(){
        File("./src/main/resources/levels").listFiles()?.forEach {
            println("Opening file ${it.name}")
            getFromText(it.readText())
            println("Level ${it.name} downloaded")
        }
    }

    fun getFromText(text: String){
        val lines = text.split("\n")
        val map = ArrayList<ArrayList<String>>()
        lines.indexOf("")
        for (line in lines.subList(0, lines.indexOf(""))) {
            map.add(ArrayList(line.split(" ")))
        }
        val level = Level()
        level.setMap(map)
        val pos = Pos(Integer.parseInt(lines[lines.indexOf("") + 1].split(" ")[0]),Integer.parseInt(lines[lines.indexOf("") + 1].split(" ")[1] ))
        level.setStart(pos)
        level.setBrickHeight(SettingsManager.settings.getHeight()/map.size)
        level.setBrickWidth(SettingsManager.settings.getWidth()/map[0].size)
        levels.add(level)
    }
}