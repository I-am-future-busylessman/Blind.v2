package game.engine.manager

import java.io.File
import game.engine.Settings

object SettingsManager {
    var settings = Settings(0, 0, 0, 0)

    fun getFromFile(): Settings {
        val file = File("./src/main/resources/settings/settings.txt")
        println("Opening file ${file.name}")
        return getFromText(file.readText())
    }

    fun getFromText(text: String): Settings {
        var width = 0
        var height = 0
        var fps = 0
        var speed = 0
        val lines = text.split("\n")
        for (line in lines) {
            if (line.startsWith("WIDTH")) {
                width = Integer.parseInt(line.filter { it.isDigit() })
            }
            if (line.startsWith("HEIGHT")) {
                height = Integer.parseInt(line.filter { it.isDigit() })
            }
            if (line.startsWith("FPS")) {
                fps = Integer.parseInt(line.filter { it.isDigit() })
            }
            if (line.startsWith("SPEED")) {
                speed = Integer.parseInt(line.filter { it.isDigit() })
            }
        }

        settings = Settings(width, height, fps, speed)
        return Settings(width, height, fps, speed)
    }

}