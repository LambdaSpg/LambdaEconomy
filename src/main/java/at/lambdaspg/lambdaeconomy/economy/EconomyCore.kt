package at.lambdaspg.lambdaeconomy.economy

import at.lambdaspg.lambdaeconomy.LambdaEconomy.Companion.ecoHandler
import at.lambdaspg.lambdaeconomy.economy.EconomyHandler.Companion.startMoney
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.OfflinePlayer
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class EconomyCore : Economy {


    override fun isEnabled(): Boolean {
        return true;
    }

    override fun getName(): String {
        return "LambdaEconomy"
    }

    override fun hasBankSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun fractionalDigits(): Int {
        TODO("Not yet implemented")
    }

    override fun format(amount: Double): String {
        TODO("Not yet implemented")
    }

    override fun currencyNamePlural(): String {
        return EconomyHandler.currencyP
    }

    override fun currencyNameSingular(): String {
        return EconomyHandler.currencyS
    }

    fun currencySign(): String{
        return EconomyHandler.currencySign
    }

    override fun hasAccount(playerName: String?): Boolean {
        val uuid = nameToUUID(playerName)
        if(uuid != null){
            return ecoHandler.containsPlayer(uuid)
        }
        return false;
    }

    override fun hasAccount(player: OfflinePlayer?): Boolean {
        if(player != null){
            return ecoHandler.containsPlayer(player.uniqueId)
        }
        return false
    }

    override fun hasAccount(playerName: String?, worldName: String?): Boolean {
        return hasAccount(playerName)
    }

    override fun hasAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return hasAccount(player)
    }

    override fun getBalance(playerName: String?): Double {
        val uuid = nameToUUID(playerName)
        if(uuid != null){
            return ecoHandler.getMoney(uuid)
        }
        return 0.0
    }

    override fun getBalance(player: OfflinePlayer?): Double {
        if(player != null){
            return ecoHandler.getMoney(player.uniqueId)
        }

        return 0.0
    }

    override fun getBalance(playerName: String?, world: String?): Double {
        return getBalance(playerName)
    }

    override fun getBalance(player: OfflinePlayer?, world: String?): Double {
        return getBalance(player)
    }

    override fun has(playerName: String?, amount: Double): Boolean {
        return getBalance(playerName) >= amount
    }

    override fun has(player: OfflinePlayer?, amount: Double): Boolean {
        return getBalance(player) >= amount
    }

    override fun has(playerName: String?, worldName: String?, amount: Double): Boolean {
        return has(playerName, amount)
    }

    override fun has(player: OfflinePlayer?, worldName: String?, amount: Double): Boolean {
        return has(player, amount)
    }

    override fun withdrawPlayer(playerName: String?, amount: Double): EconomyResponse {
        val uuid = nameToUUID(playerName)
        if(uuid != null){
            if(has(playerName, amount)){
                ecoHandler.setMoney(uuid, ecoHandler.getMoney(uuid) - amount)
                return EconomyResponse(amount, ecoHandler.getMoney(uuid), EconomyResponse.ResponseType.SUCCESS, null)
            }
        }

        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player does not exist or does not have enough money")
    }

    override fun withdrawPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        if(player != null){
            if(has(player, amount)) {
                ecoHandler.setMoney(player.uniqueId, ecoHandler.getMoney(player.uniqueId) - amount)
                return EconomyResponse(amount, ecoHandler.getMoney(player.uniqueId), EconomyResponse.ResponseType.SUCCESS, null)
            }
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player does not exist or does not have enough money")
    }

    override fun withdrawPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return withdrawPlayer(playerName, amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        return withdrawPlayer(player, amount)
    }

    override fun depositPlayer(playerName: String?, amount: Double): EconomyResponse {
        val uuid = nameToUUID(playerName)
        if(uuid != null){
            ecoHandler.setMoney(uuid, getBalance(playerName) + amount)
            return EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, null)
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player does not exist")
    }

    override fun depositPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        if(player != null){
            ecoHandler.setMoney(player.uniqueId, getBalance(player) + amount)
            return EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null)
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Player does not exist")
    }

    override fun depositPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return depositPlayer(playerName, amount)
    }

    override fun depositPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        return depositPlayer(player, amount)
    }

    fun setPlayer(player: OfflinePlayer?, amount: Double) : Boolean{
        if(player != null){
            return ecoHandler.setMoney(player.uniqueId, amount)
        }

        return false;
    }

    override fun createBank(name: String?, player: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun createBank(name: String?, player: OfflinePlayer?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun deleteBank(name: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankBalance(name: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankHas(name: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankWithdraw(name: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun bankDeposit(name: String?, amount: Double): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankOwner(name: String?, playerName: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankOwner(name: String?, player: OfflinePlayer?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun isBankMember(name: String?, player: OfflinePlayer?): EconomyResponse {
        TODO("Not yet implemented")
    }

    override fun getBanks(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun createPlayerAccount(playerName: String?): Boolean {
        val uuid: UUID? = nameToUUID(playerName)
        if(uuid != null) {
            return ecoHandler.addPlayer(uuid, startMoney)
        }

        return false;
    }

    override fun createPlayerAccount(player: OfflinePlayer?): Boolean {
        if(player != null){
            return ecoHandler.addPlayer(player.uniqueId, startMoney)
        }
        return false
    }

    override fun createPlayerAccount(playerName: String?, worldName: String?): Boolean {
        return createPlayerAccount(playerName)
    }

    override fun createPlayerAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return createPlayerAccount(player)
    }

    private fun  nameToUUID(name: String?) : UUID? {
        val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")

        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.connect()

        val responseCode = conn.responseCode

        if(responseCode != 200){
            if(responseCode == 204) return null;
            throw RuntimeException("responseCode: $responseCode")
        }

        var s = ""
        val scanner: Scanner = Scanner(url.openStream())

        while(scanner.hasNext()) s += scanner.nextLine()
        scanner.close()

        val parser = JSONParser()
        var jsonObj = parser.parse(s) as JSONObject

        if(jsonObj.get("error") != null) return null;

        val uuidString: String = jsonObj["id"].toString()

        val f8 = uuidString.substring(0, 8)
        val a4 = uuidString.substring(8, 12)
        val b4 = uuidString.substring(12, 16)
        val c4 = uuidString.substring(16, 20)
        val fend = uuidString.substring(20)
        return UUID.fromString("$f8-$a4-$b4-$c4-$fend")
    }
}