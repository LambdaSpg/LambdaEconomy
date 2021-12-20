package at.lambdaspg.lambdaeconomy

import org.bukkit.Bukkit.getServer
import org.bukkit.entity.Player

object MessageManager {
    const val server = "§9Server"
    const val prefix = "$server |§7"

    private const val good = "$prefix§2 "
    private const val info = "$prefix§e "
    private const val error = "$prefix§4 "

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
        sendPlayerError("Du darfst diesen Command nicht ausführen", player);
    }
}