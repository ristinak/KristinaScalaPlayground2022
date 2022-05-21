package com.github.ristinak.Final

import scala.io.StdIn.readLine

object Bulls_and_Cows extends App {
  var isNewGameNeeded = true

  while (isNewGameNeeded) {
    val BullsAndCowsGame = new BullsAndCows
    BullsAndCowsGame.play
    BullsAndCowsGame.printGuesses
    val nextGameInput = readLine("Do you want to play another game? (Y/N) ")
    if (!nextGameInput.toLowerCase.startsWith("y")) {
      isNewGameNeeded = false
    }
  }
  System.exit(0)

  //  BullsAndCowsGame.db.dropAllTables()

}
