package at.lambdaspg.lambdaeconomy

import org.bukkit.Bukkit.getServer
import org.bukkit.entity.Player

object MessageManager {
    val server = "§9${LambdaEconomy.getConfig().get("Server.name")}"
    val prefix = "$server |§7"

    private val good = "$prefix§2 "
    private val info = "$prefix§e "
    private val error = "$prefix§4 "

    fun sendConsoleGood(text: String){
        getServer().consoleSender.sendMessage(good + text)
    }

    fun sendConsoleInfo(text: String){
        getServer().consoleSender.sendMessage(info + text)
    }

    fun sendConsoleError(text: String){
        getServer().consoleSender.sendMessage(error + text)
    }

    fun sendConsoleEco(text: String){
        getServer().consoleSender.sendMessage("$info[ECO] | $text")
    }

    fun sendPlayerGood(text: String, player: Player){
        player.sendMessage(good + text)
    }

    fun sendPlayerInfo(text: String, player: Player){
        player.sendMessage(info + text)
    }

    fun sendPlayerError(text: String, player: Player){
        player.sendMessage(error + text)
    }

    fun noPermission(player: Player){
        sendPlayerError("You are not allowed to execute this command", player);
    }
}