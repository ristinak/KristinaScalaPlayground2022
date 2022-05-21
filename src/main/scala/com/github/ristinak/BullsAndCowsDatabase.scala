package com.github.ristinak

import org.sqlite.SQLiteException

import java.sql.{Connection, DriverManager, PreparedStatement}
import scala.collection.mutable.ArrayBuffer

class  BullsAndCowsDatabase(val dbPath: String) {
  val url = s"jdbc:sqlite:$dbPath"

  val dbConnection: Connection = DriverManager.getConnection(url)
  println(s"Opened Database at ${dbConnection.getMetaData.getURL}")

  def dropAllTables(): Unit = {
    val query = dbConnection.createStatement()
    val sql1 =
      """
        |DROP TABLE IF EXISTS players
        |""".stripMargin
    val sql2 =
      """
        |DROP TABLE IF EXISTS history
        |""".stripMargin
    val sql3 =
      """
        |DROP TABLE IF EXISTS results
        |""".stripMargin

    query.addBatch(sql1)
    query.addBatch(sql2)
    query.addBatch(sql3)
    query.executeBatch()
  }

  def migrate: Unit = {
    val query = dbConnection.createStatement()

    val sql1 =
      """
        |CREATE TABLE IF NOT EXISTS players (
        |id INTEGER PRIMARY KEY,
        |player TEXT NOT NULL UNIQUE,
        |created TEXT
        |);
        |""".stripMargin
    val sql2 =
      """
        |CREATE TABLE IF NOT EXISTS results (
        |id INTEGER PRIMARY KEY,
        |winner INTEGER NOT NULL,
        |loser INTEGER NOT NULL,
        |created TEXT,
        |FOREIGN KEY (winner) REFERENCES players (id),
        |FOREIGN KEY (loser) REFERENCES players (id)
        |);
        |""".stripMargin
    val sql3 =
      """
        |CREATE TABLE IF NOT EXISTS history (
        |id INTEGER PRIMARY KEY,
        |game_id INTEGER NOT NULL,
        |turn INTEGER NOT NULL,
        |guess TEXT NOT NULL,
        |outcome TEXT NOT NULL,
        |created TEXT,
        | FOREIGN KEY (game_id) REFERENCES results (id)
        |);
        |""".stripMargin

    query.addBatch(sql1)
    query.addBatch(sql2)
    query.addBatch(sql3)

    query.executeBatch()
  }

  /**
   * Returns player count by that name
   * @param player player name as string
   * @return 1 or 0
   */
  def getPlayerCount(player:String):Int = {
    val sql =
      """
        |SELECT COUNT(*) cnt FROM players p
        |WHERE player = ?;
        |""".stripMargin
    val preparedStmt: PreparedStatement = dbConnection.prepareStatement(sql)

    preparedStmt.setString(1, player)

    val rs = preparedStmt.executeQuery

    val cnt = rs.getInt(1) //just the first column not worrying about the column name
    preparedStmt.close()
    cnt
  }

  // Function to get last id in the specific table
  private def getLastId(table: String): Int = {
    val query = dbConnection.createStatement()
    val sql =
      s"""
         |SELECT MAX(id) id FROM $table;
         |""".stripMargin
    val resultSet = query.executeQuery(sql)
    resultSet.getInt("id")
  }

    /**
     * Returns user id if it exists
     *
     * @param player - user  name
     * @return 0 or id
     */
    def getPlayerId(player: String): Int = {
      if (getPlayerCount(player) == 0) 0 else {
        val sql =
          """
            |SELECT id cnt FROM players p
            |WHERE player = ?
            |LIMIT 1;
            |""".stripMargin
        val preparedStmt: PreparedStatement = dbConnection.prepareStatement(sql)

        preparedStmt.setString(1, player)

        val rs = preparedStmt.executeQuery

        val id = rs.getInt(1) //just the first column not worrying about the column name
        preparedStmt.close()
        id
      }
    }

  def getScoreboard(): Unit = {
    val sql =
      """
        |SELECT p.player, COUNT(winner) wins FROM results r
        |JOIN players p
        |ON p.id == r.winner
        |GROUP BY winner
        |ORDER BY wins DESC;
        |""".stripMargin

    val query = dbConnection.createStatement()
    val result = query.executeQuery(sql)

    println("=" * 10 + " Scoreboard " + "=" * 10)
    while (result.next()) {
      println(result.getString("player") + ": " + result.getInt("wins"))
    }
    print("=" * 32)
  }

  // Function to insert a player into the database
  def insertNewPlayer(player: String): Int = {
    //if we have no player by this name only then we do anything
    if (getPlayerCount(player) == 0) {
      val sql =
        """
          |INSERT INTO players (player, created)
          |VALUES (?, CURRENT_TIMESTAMP);
          |""".stripMargin

      val preparedStmt: PreparedStatement = dbConnection.prepareStatement(sql)

      preparedStmt.setString(1, player)

      preparedStmt.execute //not checking for success
      preparedStmt.close()
    }
    getPlayerId(player)
  }

  def insertResult(winner: String, loser: String): Unit = {
    val winnerId = getPlayerId(winner)
    val loserId = getPlayerId(loser)
    val lastId = getLastId("results")

    val sql =
      """
        |INSERT INTO results (id, winner, loser, created)
        |values (?,?,?,CURRENT_TIMESTAMP)
        |""".stripMargin

    val query =  dbConnection.prepareStatement(sql)

    query.setInt(1, lastId+1)
    query.setInt(2, winnerId)
    query.setInt(3, loserId)

    query.execute()

    query.close()
  }

  def insertHistory(gameTurns: ArrayBuffer[(String, String)]): Unit = {
    val lastGameId = getLastId("results")
    val sql =
      """
        |INSERT INTO history (id, game_id, turn, guess, outcome, created)
        |values (?,?,?,?,?,CURRENT_TIMESTAMP)
        |""".stripMargin

    val query = dbConnection.prepareStatement(sql)

    for (((guess, result), index) <- gameTurns.zipWithIndex) {
      val lastHistoryId = getLastId("history")

      query.setInt(1, lastHistoryId+1)
      query.setInt(2, lastGameId)
      query.setInt(3, index+1)
      query.setString(4, guess)
      query.setString(5, result)

      query.execute()
    }

    query.close()
  }
}