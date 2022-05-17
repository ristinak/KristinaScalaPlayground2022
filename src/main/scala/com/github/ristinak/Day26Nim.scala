package com.github.ristinak

import java.io.FileNotFoundException
import java.nio.file.{Files, Paths}
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.{ZoneOffset, ZonedDateTime}
import java.util.Calendar
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.readLine

object Day26Nim extends App {
  //implement basic version of https://en.wikipedia.org/wiki/Nim
  //https://en.wikipedia.org/wiki/Nim#The_21_game

  //TODO move saving date into database
  //TODO add more computer opponents
  //TODO allow more than one game at once
  val saveDst = "src/resources/nim/scores.csv"
  val db = new NimDB("src/resources/nim/nim.db")
  val startingCount = 21
  val gameEndCondition = 0
  val minMove = 1
  val maxMove = 3



  val playerA = readLine("Player A what is your name?")
  var playerB = readLine("Player B what is your name? (press ENTER for computer) ")
  if (playerB == "") playerB = "COMPUTER" //TODO see if you can do the previous 2 lines at once

  //TODO more computer levels
  var computerLevel = 0
  if (playerB == "COMPUTER") {
    computerLevel = getIntegerInput("Please enter computer level (1-3)")
  }


  //so this function is only inside the outer loop
  def getIntegerInput(prompt:String="Please enter an integer: "): Int = {
    var needsInteger = true //we use this as a flag for our code
    var myInteger = 0
    //so we keep going until we get an input which we can cast to integer
    while (needsInteger) {
      val moveInput = readLine(prompt)
      //https://alvinalexander.com/scala/scala-try-catch-finally-syntax-examples-exceptions-wildcard/
      try {
        myInteger = moveInput.toInt //this type Casting will throw an exception on bad input
        needsInteger = false //IMPORTANT! this line will not execute if error is encountered
      } catch {
        //It is considered good practice to catch specific errors relevant to your code
        case e:NumberFormatException => println(s"That is not a number! + $e") //for users you would not print $e
        // handling any other exception that might come up
        case unknown => println("Got this unknown exception we need an integer!: " + unknown)
      }
    }
    myInteger
  }

  var isNewGameNeeded = true
  while(isNewGameNeeded) {
    println(s"Player A -  $playerA and Player B - $playerB let us play NIM!")

    val isPlayerAStarting = true //so A goes first

    val nimGame = new Nim(playerA, playerB, startingCount, gameEndCondition, minMove, maxMove, isPlayerAStarting)



    //main loop - while there are some matches play on
    while (nimGame.isGameActive) {
      //show the game state
      //    println(s"Currently there are $currentState matches on the table")
      nimGame.showStatus()

      val move = if (nimGame.isCurrentPlayerComputer) {
        NimAI.getComputerMove(nimGame.currentState, computerLevel)
      } else {
        getIntegerInput(s"${nimGame.currentPlayer} please enter how many matches you are taking (1-3)")
      }
      nimGame.removeMatches(move)
      nimGame.nextPlayer()
    }

    nimGame.showStatus()
    nimGame.printMoves()

    nimGame.saveGameResult(saveDst)
    db.insertResult(nimGame.getWinner, nimGame.getLoser)
    nimGame.saveGameScore()
    db.insertFullScore(nimGame.getMoves)
    db.printTopPlayers()
    db.printBiggestLosers()

    db.printAllPlayers()

    val nextGameInput = readLine("Do you want to play another game with same players ? (Y/N)")
    if (nextGameInput.toLowerCase.startsWith("y")) isNewGameNeeded = true
    else isNewGameNeeded = false

    //TODO add support for new player names
    //TODO add changing of computer level if playerb is computer


  }

  println("Thank you for playing! Hoping to see you again ;)")


}
