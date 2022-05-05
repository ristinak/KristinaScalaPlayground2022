package com.github.ristinak

import java.sql.DriverManager
import scala.collection.mutable.ArrayBuffer

case class Genre(genreId: Int, Name: String)


object Day25DBConnection extends App {
  println("Testing Database connection")

  val dbPath = "src/resources/db/chinook.db"
  val url = s"jdbc:sqlite:$dbPath"

  println(s"Will connect SQlite at the following url: $url")

  val conn = DriverManager.getConnection(url)
  //https://docs.scala-lang.org/overviews/scala-book/try-catch-finally.html
  //Alvin has a nice example on how to connect to MySQL - different database but very similar
  //https://alvinalexander.com/scala/scala-jdbc-connection-mysql-sql-select-example/
  println(conn.getClientInfo())

  val statement = conn.createStatement() //the statement object  will handle sending SQL statements to DB

//  val sql =
//    """
//      |SELECT * FROM tracks
//      |LIMIT 20;
//      |""".stripMargin

  val sql =
    """
      |SELECT * FROM genres;
      |""".stripMargin

//  val sql =
//    """
//      |SELECT * FROM artists
//      |JOIN albums
//      |ON artists.ArtistId = albums.ArtistId;
//      |""".stripMargin

  val resultSet = statement.executeQuery(sql)
  val metaData = resultSet.getMetaData
  println(s"We have received ${metaData.getColumnCount} columns.")

  //column indexes start at 1, not 0
  for (i <- 1 to metaData.getColumnCount) {
    println(s"Column $i is named: ${metaData.getColumnName(i)}")
    println(s"Column $i comes from table: ${metaData.getTableName(i)}")
  }

  val genreBuffer = ArrayBuffer[Genre]() //we start with empty buffer ti store ur rows
  while (resultSet.next()) {
    //we loop over all rows until results are exhausted
//    println(resultSet.getInt(1), resultSet.getString(2))
    println(resultSet.getInt("GenreId"), resultSet.getString("Name"))
    // we could loop over all columns as well for that row
    for (i <- 1 to metaData.getColumnCount) {
      print(resultSet.getString(i) + " ") // getString works even on Integers
    }
    val genre = Genre(resultSet.getInt("genreId"), resultSet.getString("Name"))
    genreBuffer += genre
//    genreBuffer.append(genre) //same as above
    println()
  }
  //here our result set is exhausted // there might be a command to reset
  //you would have to make a new query

  //generally we want to close the database connection
  //it would close automatically but still it's safer to explicitly close it

  conn.close()
  val genreCollection = genreBuffer.toArray
  genreCollection.take(5).foreach(println)
}
