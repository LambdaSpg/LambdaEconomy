package at.lambdaspg.lambdaeconomy

import org.bukkit.Bukkit.getServer
import org.bukkit.entity.Player

object MessageManager {
    val server = "${ColorManager.main()}${LambdaEconomy.getConfig().get("Server.name")}"
    val prefix = "$server §8»§7"

    private val good = "$prefix "
    private val info = "$prefix "
    private val error = "$prefix${ColorManager.bad()} "

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
        val s = "$info [ECO] | $text"
        getServer().consoleSender.sendMessage(s)
        FileManager.logEcoChange(text)
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

    fun sendEcoChange(target: Player, targeter: Player, money: Double){
        sendConsoleEco("The balance of ${target.name} has been set to ${money}${LambdaEconomy.ecoCore.currencySign()} (by: ${targeter.name})")
    }
}