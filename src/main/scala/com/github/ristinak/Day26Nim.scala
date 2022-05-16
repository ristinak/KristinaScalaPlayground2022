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

  //TODO move saveing date into database
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
  if (playerB == "") playerB = "COMPUTER" //TODO see if you can do the previos 2 lines at once


  println(s"Player A -  $playerA and Player B - $playerB let us play NIM!")

  //inevitably in most applications we will have some state that we want to keep track of
  //here it is simple enough state that we can use a few variables
  //at some point we will want to structure this game/app state into a separate object based on some class
  //  var currentState = startingCount
  val isPlayerAStarting = true //so A goes first

  //TODO create a new object holding all the information necessary for a game nim from this class Nim
  val nimGame = new Nim(playerA, playerB, startingCount, gameEndCondition, minMove, maxMove, isPlayerAStarting)

  def getComputerMove(): Int = 2 //TODO add more complex logic later
  //computer can be made to play perfectly
  //or we could add some randomness

  def getHumanMove(): Int = {
    //TODO move this to method
    var needsInteger = true //we use this as a flag for our code
    var myInteger = 0
    //so we keep going until we get an input which we can cast to integer
    while (needsInteger) {
      val moveInput = readLine(s"How many matches do you want to take ${nimGame.currentPlayer}? (1-3) ")
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

  //main loop - while there are some matches play on
  //TODO implement PvP - player versus player - computer only checks the rules
  while (nimGame.isGameActive) {
    //show the game state
    //    println(s"Currently there are $currentState matches on the table")
    nimGame.showStatus()

    val move = if (nimGame.isCurrentPlayerComputer) {
      getComputerMove()
    } else {
      getHumanMove()
    }
    nimGame.removeMatches(move)
    nimGame.nextPlayer()
  }
  //TODO PvC - player versus computer you will need to add some logic to the computer, add more levels

  //end cleanup here we just print some game state and congratulations


  //  val winner = if (isPlayerATurn) playerA else playerB
  //  val loser = if (!isPlayerATurn) playerA else playerB
  //  println(s"Game ended. Congratulations $winner! Better luck next time $loser.")
  nimGame.showStatus()
  nimGame.printMoves()

  //  Day27Persistence.saveGameResult(saveDst, nimGame.getWinner(), nimGame.getLoser())
  nimGame.saveGameResult(saveDst)
  db.insertResult(nimGame.getWinner, nimGame.getLoser)
  nimGame.saveGameScore()
  db.insertFullScore(nimGame.getMoves)
  db.printTopPlayers()
  db.printBottomPlayers()
  db.printAllPlayers()
  //print game status again
  //TODO implement multiple games

}
