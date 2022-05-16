package com.github.ristinak

object Day28DatabaseMigration extends App {

  val db = new NimDB("src/resources/nim/nim.db")
  //  db.dropAllTables() //dangerous :)

  db.migrate()
  //
  //  db.insertResult("Alice", "Bob")
  //  db.insertResult("Carol", "Dave")
  //
  //  val lastID = db.getIdOfLastGame()
  //  db.insertScore(lastID, 1, 3)
  //  db.insertScore(lastID, 2, 1)
  //
  //  println(lastID)


  //cleanup
  db.conn.close()

}

