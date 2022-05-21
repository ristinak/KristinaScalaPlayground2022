package com.github.ristinak

object Bulls_and_Cows extends App {
  val BullsAndCowsGame = new BullsAndCows

  BullsAndCowsGame.Play
  BullsAndCowsGame.printGuesses

//  val db = new BullsAndCowsDatabase("src/resources/db/bullsandcows.db")
//  db.Migrate()
//  db.insertPlayer("Marija")
//  db.insertPlayer("Nikita")
}
