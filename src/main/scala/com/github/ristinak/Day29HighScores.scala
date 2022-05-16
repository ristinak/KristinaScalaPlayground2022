package com.github.ristinak

//TODO utilize all fields for Player
case class Player(name: String, id:Int=0, wins:Int=0, losses:Int=0)

object Day29HighScores extends App {
  val db = new NimDB("src/resources/nim/nim.db")

  println(db.getUserId("Valdis"))
  println(db.getUserId("Alice"))
  println(db.getUserId("Līga"))
  println(db.getUserId("Shrek"))

  db.insertNewUser("Shrek")
  println(db.getUserId("Shrek"))

  db.conn.close()
}

