package at.lambdaspg.lambdaeconomy

import net.md_5.bungee.api.ChatColor
import java.awt.Color

object ColorManager {
    fun good() : ChatColor {
        return ChatColor.of(Color(51, 255, 130, 255))
    }

    fun info() : ChatColor {
        return ChatColor.of(Color(255,251,0, 255))
    }

    fun bad() : ChatColor {
        return ChatColor.of(Color(255, 51, 51, 255))
    }

    fun main() : ChatColor{
        return ChatColor.of(Color(27, 255, 255, 255))
    }
}