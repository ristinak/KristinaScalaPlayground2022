package com.github.ristinak

import java.sql.{DriverManager, PreparedStatement, ResultSet}
import scala.collection.mutable.ArrayBuffer

class NimDB(val dbPath: String) {

  val url =  s"jdbc:sqlite:$dbPath"

  val conn = DriverManager.getConnection(url) //TODO handle exceptions at connection time
  println(s"Opened Database at ${conn.getMetaData.getURL}")

  def dropAllTables():Unit = {
    val statement = conn.createStatement()
    val sql =
      """
        |DROP TABLE IF EXISTS results;
        |""".stripMargin
    val sql2 =
      """
        |DROP TABLE IF EXISTS scores;
        |""".stripMargin
    val sql3 =
      """
        |DROP TABLE IF EXISTS users;
        |""".stripMargin
    statement.addBatch(sql)
    statement.addBatch(sql2)
    statement.addBatch(sql3)
    statement.executeBatch()  //more efficient than 3 single queries
  }

  /**
   * Perform table migration in a new installation, does nothing otherwise
   */
  def migrate():Unit = {
    //migrate for db refers to table creation other setup needed to start work in a new enviroment
    //https://www.sqlitetutorial.net/sqlite-create-table/

    //TODO created multiple tables with users in one table
    //so when we do insert we will reference the users table in our results table

    val statement = conn.createStatement() //we create a statement object that will handl sending SQL statements to the DB

    //this query should do nothing if table already exists
    //this query should do nothing if table already exists
    val sql0 =
    """
      |CREATE TABLE IF NOT EXISTS users (
      |id INTEGER PRIMARY KEY,
      |name TEXT NOT NULL,
      |email TEXT,
      |created TEXT
      |);
      |""".stripMargin

    //    statement.executeQuery(sql) //so Query for selects
    //    statement.execute(sql)
    statement.addBatch(sql0)

    val sql =
      """
        |CREATE TABLE IF NOT EXISTS results (
        |id INTEGER PRIMARY KEY,
        |winner INTEGER NOT NULL,
        |loser INTEGER NOT NULL,
        |created TEXT,
        |    FOREIGN KEY (winner)
        |       REFERENCES users (id),
        |   FOREIGN KEY (loser)
        |       REFERENCES users (id)
        |);
        |""".stripMargin

    //    statement.executeQuery(sql) //so Query for selects
    //    statement.execute(sql)
    statement.addBatch(sql)
    //TODO add another sql statement that creates scores table if it does not exist
    //this table should have the following columns
    //id, game_id, turn, move, created
    //id is the primary key
    //it should have game_id column that will be referencing our results table - so called Foreign Key
    //also it should have turn column that will store game turn (starting from 1) for a specific game
    //finally we store move column
    //also lets store a created column as well -this will use autamtic timestamp later
    val sql2 =
    """
      |CREATE TABLE IF NOT EXISTS scores (
      |id INTEGER PRIMARY KEY,
      |game_id INTEGER NOT NULL,
      |turn INTEGER NOT NULL,
      |move INTEGER NOT NULL,
      |created TEXT,
      |    FOREIGN KEY (game_id)
      |       REFERENCES results (id)
      |);
      |""".stripMargin

    //    statement.execute(sql2)
    statement.addBatch(sql2)
    statement.executeBatch()

  }

  def insertResult(winner:String,loser:String):Unit = {
    //we want to avoid inserting unprepared values
    //https://xkcd.com/327/

    //https://alvinalexander.com/source-code/scala-jdbc-sql-select-insert-statement-resultset-preparedstatement-example/

    val insertSql = """
                      |INSERT INTO results (winner,loser,created)
                      |values (?,?,CURRENT_TIMESTAMP)
""".stripMargin
    //CURRENT_TIMESTAMP is in SQL standard: https://stackoverflow.com/questions/15473325/inserting-current-date-and-time-in-sqlite-database

    val winnerId = insertNewUser(winner)
    val loserId = insertNewUser(loser)
    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)


    preparedStmt.setInt (1, winnerId)
    preparedStmt.setInt (2, loserId)
    preparedStmt.execute

    preparedStmt.close()
  }

  //TODO create insertScore method
  //parameters will be Array[Int] of moves
  //also we will want a reference to the game id

  //TODO we need to create a helper method to get the id of the last game played in results
  def getIdOfLastGame():Int = {
    val statement = conn.createStatement()
    //    val sql =
    //      """
    //        |SELECT id FROM results
    //        |WHERE id=(SELECT max(id) FROM results);
    //        |""".stripMargin
    val sql =
    """
      |SELECT MAX(id) id FROM results;
      |""".stripMargin
    val resultSet = statement.executeQuery(sql)
    val lastGameId = resultSet.getInt("id")
    lastGameId
  }
  //this assumes we save the game result first
  //https://stackoverflow.com/questions/5191503/how-to-select-the-last-record-of-a-table-in-sql
  //so we we will store moves for all games in a single table,
  //id, game_id, turn, move, created
  //1, 1, 1, 3, 2022-05-12etc
  //2, 1, 2, 2, 2022
  //...
  //8, 2, 1, 3, 2022
  //9, 2, 2, 1, 2022
  //10, 2, 3, 2, 2022
  //
  def insertScore(game_id: Int, turn: Int, moves: Int): Unit = {
    val insertSql = """
                      |INSERT INTO scores (game_id,turn,move,created)
                      |values (?,?,?,CURRENT_TIMESTAMP)
  """.stripMargin

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)

    preparedStmt.setInt(1, game_id)
    preparedStmt.setInt(2, turn)
    preparedStmt.setInt(3, moves)

    preparedStmt.execute

    preparedStmt.close()
  }

  def insertFullScore(moves:Array[Int]):Unit = {
    val id = getIdOfLastGame()
    for ((move, turn) <- moves.zipWithIndex) {
      insertScore(id, turn, move)
    }
  }

  /**
   * Returns user count by that name
   * @param name user name as string
   * @return 1 or 0
   */
  def getUserCount(name:String):Int = {
    val sql =
      """
        |SELECT COUNT(*) cnt FROM users u
        |WHERE name = ?;
        |""".stripMargin
    val preparedStmt: PreparedStatement = conn.prepareStatement(sql)

    preparedStmt.setString(1, name)

    val rs = preparedStmt.executeQuery

    val cnt = rs.getInt(1) //just the first column not worrying about the column name
    preparedStmt.close()
    cnt
  }

  /**
   * Returns user id if it exists
   * @param name - user  name
   * @return 0 or id
   */
  def getUserId(name:String):Int = {
    if (getUserCount(name) == 0) 0 else {
      val sql =
        """
          |SELECT id cnt FROM users u
          |WHERE name = ?
          |LIMIT 1;
          |""".stripMargin
      val preparedStmt: PreparedStatement = conn.prepareStatement(sql)

      preparedStmt.setString(1, name)

      val rs = preparedStmt.executeQuery

      val id = rs.getInt(1) //just the first column not worrying about the column name
      preparedStmt.close()
      id
    }

  }

  /**
   * inserts new user if it does not exists
   * returns id of new or existing user
   * @param name
   * @return
   */
  def insertNewUser(name: String):Int = {
    //if we have no user by this name only then we do anything
    if (getUserCount(name) == 0) {
      val sql =
        """
          |INSERT INTO users (name, created)
          |VALUES (?, CURRENT_TIMESTAMP);
          |""".stripMargin

      val preparedStmt: PreparedStatement = conn.prepareStatement(sql)

      preparedStmt.setString(1, name)

      preparedStmt.execute //not checking for success
      preparedStmt.close()
    } else {
      println(s"User $name already exists, nothing to do here!")
    }
    getUserId(name)

  }

  def getTopWinners():Array[Player] = {
    val sql =
      """
        |SELECT u.name, COUNT(winner) wins FROM results r
        |JOIN users u
        |ON u.id = r.winner
        |GROUP BY winner
        |ORDER BY wins DESC
        |;
        |""".stripMargin

    val playerBuffer = ArrayBuffer[Player]() //so we start with an empty buffer to store our rows
    val statement = conn.createStatement()
    val rs = statement.executeQuery(sql)
    while (rs.next()) {
      val player = Player(rs.getString("name"), wins = rs.getInt("wins"))
      playerBuffer += player
    }
    playerBuffer.toArray //better to return immutable values
  }

  def printTopPlayers():Unit = {
    println("Top Players with most wins are:")
    val topPlayers = getTopWinners()
    topPlayers.foreach(println)
  }

  //TODO
  //def getTopLosers() just like get TopWinners also return Array[Player] sorted by most losses
  def getTopLosers():Array[Player] = {
    val sql =
      """
        |SELECT u.name, COUNT(loser) losses FROM results r
        |JOIN users u
        |ON u.id = r.loser
        |GROUP BY loser
        |ORDER BY losses DESC
        |;
        |""".stripMargin

    val playerBuffer = ArrayBuffer[Player]() //so we start with an empty buffer to store our rows
    val statement = conn.createStatement()
    val rs = statement.executeQuery(sql)
    while (rs.next()) {
      val player = Player(rs.getString("name"), losses = rs.getInt("losses"))
      playerBuffer += player
    }
    playerBuffer.toArray //better/safer to return immutable values
  }

  def printBiggestLosers():Unit = {
    //it is surely tempting to use one function for both losers and winners
    println("Players with the most losses are:")
    val topLosers = getTopLosers()
    topLosers.foreach(println)
  }

  //  def getFullPlayerInfo:Array[Player]  = {
  //    //TODO
  //  }
  //return Array[Player] with all fields filled out, meaning name, id, wins, losses
  //You can use getTopWinners and getTopLosers and also getuserId
  //you can also skip using getUserId and perhaps add id inside getTopWinners or getTopLosers
  //also it is possible to write just a single SQL query but that is not required
  //again so simplest will be to merge TopWinners Array and TopLosers array

  def getFullPlayerInfo():Array[Player] = {
    val winners = getTopWinners()
    val losers = getTopLosers()
    //for efficiency we create two maps of name to actual Player
    //using Maps we avoid searching array of players for a specific name each time
    //much faster on a larger collection
    //here it does not really make a difference but it would on a larger data collection
    //it is very similar idea behind SQL indexing by some column
    val winnerMap = winners.map(winner => (winner.name, winner)).toMap
    val loserMap = losers.map(loser => (loser.name, loser)).toMap //so we have instant access by player name

    val playerBuffer = ArrayBuffer[Player]()
    for (winner <- winners) {
      //if we did not have map then our contains would need a loop to look up in the Array
      val losses = if (loserMap.contains(winner.name)) loserMap(winner.name).losses else 0
      val id = getUserId(winner.name)
      playerBuffer += Player(winner.name, id, winner.wins, losses)
    }
    for (loser <- losers) {
      if (!winnerMap.contains(loser.name)) {
        val id = getUserId(loser.name)
        playerBuffer += Player(loser.name, id, 0, loser.losses)
      }
    }
    playerBuffer.toArray
  }

  def printAllPlayers():Unit = {
    println("Full Player info")
    val allPlayers = getFullPlayerInfo()

    allPlayers.foreach(player => println(player.getPrettyString))
  }
}
