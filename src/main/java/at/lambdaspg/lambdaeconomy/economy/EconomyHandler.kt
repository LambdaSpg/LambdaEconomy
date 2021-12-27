package at.lambdaspg.lambdaeconomy.economy

import at.lambdaspg.lambdaeconomy.LambdaEconomy
import at.lambdaspg.lambdaeconomy.MessageManager
import java.sql.*
import java.util.*
import kotlin.collections.ArrayList

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

    fun addPlayer(uuid: UUID, money: Double): Boolean {
        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("insert into MONEY values(?, ?)")
        statement.queryTimeout = 30

        statement.setString(1, uuid.toString())
        statement.setDouble(2, money)

        statement.executeUpdate()
        connection.close()

        ecoList.add(uuid)
        return true
    }

    fun addPlayer(uuid: UUID): Boolean {
        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("insert into MONEY values(?, ?)")
        statement.queryTimeout = 30

        statement.setString(1, uuid.toString())
        statement.setDouble(2, startMoney)

        statement.executeUpdate()
        connection.close()

        ecoList.add(uuid)
        return true
    }

    fun removePlayer(uuid: UUID) : Boolean{
        return false

        //dont forget to remove from ecolist
    }

    fun setMoney(uuid: UUID, money: Double) : Boolean{
        if(!containsPlayer(uuid)) return false

        val connection: Connection = DriverManager.getConnection(connectionString)
        val statement: PreparedStatement = connection.prepareStatement("UPDATE MONEY SET money = ? WHERE id = ?")
        statement.queryTimeout = 30

        statement.setDouble(1, money)
        statement.setString(2, uuid.toString())

        statement.executeUpdate()

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
        connection.close()

        return resultD
    }
}