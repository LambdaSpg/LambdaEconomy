package at.lambdaspg.lambdaeconomy.commands

import org.bukkit.entity.Player

interface SubCommand {

    fun getName() : String
    fun perform(p: Player, args: Array<out String>)
    fun tab(args: Array<out String>) : MutableList<String>
    fun getSyntax() : String
    fun getDescription() : String
}
