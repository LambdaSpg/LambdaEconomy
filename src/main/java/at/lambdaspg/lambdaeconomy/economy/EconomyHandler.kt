package at.lambdaspg.lambdaeconomy.economy

import at.lambdaspg.lambdaeconomy.LambdaEconomy
import at.lambdaspg.lambdaeconomy.MessageManager
import java.sql.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EconomyHandler() {
    init {
        loadAllUUIDs()
    }

    companion object{
        var ecoList = ArrayList<UUID>()
        var connectionString: String = LambdaEconomy.getDatabaseConnectionString()
        const val startMoney: Double = 500.0
        val currencyP = LambdaEconomy.configuration.getString("Server.currency.plural")!!
        val currencyS = LambdaEconomy.configuration.getString("Server.currency.singular")!!
        val currencySign = LambdaEconomy.configuration.getString("Server.currency.sign")!!
    }


    private fun loadAllUUIDs(){
        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: Statement = connection.createStatement()
        statement.queryTimeout = 30

        val result: ResultSet = statement.executeQuery("SELECT id FROM MONEY")
        while(result.next()){
            ecoList.add(UUID.fromString(result.getString("id")))
        }

        statement.close()
        connection.close()
    }

    fun containsPlayer(uuid: UUID) : Boolean{
        return ecoList.contains(uuid)
    }

    fun getName(uuid: UUID) : String?{
        if(!containsPlayer(uuid)) return null

        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("SELECT name FROM MONEY WHERE id = ?")
        statement.queryTimeout = 30

        statement.setString(1, uuid.toString())
        val result: ResultSet = statement.executeQuery()
        val resultString = result.getString("name")

        statement.close()
        connection.close()

        return resultString
    }

    fun addPlayer(uuid: UUID, money: Double, name: String): Boolean {
        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("insert into MONEY values(?, ?, ?)")
        statement.queryTimeout = 30

        statement.setString(1, uuid.toString())
        statement.setDouble(2, money)
        statement.setString(3, name)

        statement.executeUpdate()
        statement.close()
        connection.close()

        ecoList.add(uuid)
        return true
    }

    fun addPlayer(uuid: UUID, name: String): Boolean {
        return addPlayer(uuid, startMoney, name)
    }

    fun removePlayer(uuid: UUID) : Boolean{
        return false

        //dont forget to remove from ecolist
    }

    fun setMoney(uuid: UUID, money: Double) : Boolean{

        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("UPDATE MONEY SET money = ? WHERE id = ?")
        statement.queryTimeout = 30

        statement.setDouble(1, money)
        statement.setString(2, uuid.toString())

        statement.executeUpdate()
        statement.close()
        connection.close()

        return true
    }

    fun setMoney(name: String, money: Double) : Boolean{

        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("UPDATE MONEY SET money = ? WHERE name = ?")
        statement.queryTimeout = 30

        statement.setDouble(1, money)
        statement.setString(2, name)

        statement.executeUpdate()
        statement.close()
        connection.close()

        return true
    }

    fun getMoney(uuid: UUID) : Double{
        if(!containsPlayer(uuid)) return 0.0
        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("SELECT money FROM MONEY WHERE id = ?")
        statement.queryTimeout = 30

        statement.setString(1, uuid.toString())

        val result: ResultSet = statement.executeQuery()
        val resultD: Double = result.getDouble("money")
        statement.close()
        connection.close()

        return resultD
    }

    fun getMoney(name: String) : Double{

        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("SELECT money FROM MONEY WHERE name = ?")
        statement.queryTimeout = 30

        statement.setString(1, name)

        val result: ResultSet = statement.executeQuery()
        val resultD: Double = result.getDouble("money")
        statement.close()
        connection.close()

        return resultD
    }

    fun getMoneyMap() : LinkedHashMap<UUID, Double>{
        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: Statement = connection.createStatement()
        statement.queryTimeout = 30

        val resultMap: ResultSet = statement.executeQuery("SELECT id, money FROM MONEY")
        val map: LinkedHashMap<UUID, Double> = LinkedHashMap()

        while(resultMap.next()){
            map.put(UUID.fromString(resultMap.getString("id")), resultMap.getDouble("money"))
        }
        connection.close()

        return map
    }
}