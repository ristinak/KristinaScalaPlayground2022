package com.github.ristinak

import org.sqlite.SQLiteException

import java.sql.DriverManager

class  BullsAndCowsDatabase(val dbPath: String) {
  val url = s"jdbc:sqlite:$dbPath"
  val dbConnection = DriverManager.getConnection(url)
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

  def Migrate(): Unit = {
    val query = dbConnection.createStatement()

    val sql1 =
      """
        |CREATE TABLE IF NOT EXISTS players (
        |id INTEGER PRIMARY KEY,
        |player TEXT NOT NULL,
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

  def getLastId(table: String): Int = {
    val query = dbConnection.createStatement()
    val sql =
      s"""
         |SELECT MAX(id) id FROM $table;
         |""".stripMargin
    val resultSet = query.executeQuery(sql)
    resultSet.getInt("id")
  }

  def getPlayerId(player: String): Int = {
    val query = dbConnection.createStatement()
    val sql =
      s"""
         |SELECT id FROM players
         |WHERE player == $player
         |""".stripMargin
    val resultSet = query.executeQuery(sql)
    resultSet.getInt("id")
  }

  def insertPlayer(player: String): Unit = {
    val lastId = getLastId("players")
    val sql =
      """
        |INSERT INTO players (id, player, created)
        |values (?,?,CURRENT_TIMESTAMP)
        |""".stripMargin

    val query = dbConnection.prepareStatement(sql)

    query.setInt(1, lastId+1)
    query.setString(2, player)

    try {
      query.execute()
    } catch {
      case e => println("Duplicate name inserted")
    }
    query.close()
  }
}
