package com.github.ristinak


import java.nio.file.{Files, Paths}
import java.time.{LocalDateTime, ZoneOffset, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.util.Calendar
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.readLine

//TODO exercise
//Create class Nim (or NimGame or NimState) - if you can come up with a better name please do
//you could use case class if you want
//Objects created from this class will hold ALL information particular to a single game of NIM
//
//create two at least two methods
//SO this is called refactoring - keeping functionality but changing code

//TODO one will be removeMatches which will perform a move of units allowed by the rules of the game

//TODO 2nd method would be to print game status let's call this method showStatus - how many matches are in the pile, whose turn it is,
//
//TODO bonus: objects created from this class should also have a ArrayBuffer of moves
//so each time removeMatches is called this buffer is updated, thus we have an exact log of game moves
class Nim(
           val playerA:String,
           val playerB: String,
           val startingCount:Int = 21,
           val gameEndCondition:Int = 0,
           val minMove: Int = 1,
           val maxMove: Int = 3,

           var isPlayerATurn:Boolean = true) {

  //so the next  lines will be run upon initialization
  println("Created a new game object of NIM")
  var currentState: Int = startingCount
  var currentPlayer: String = if (isPlayerATurn) playerA else playerB
  var movesArray: ArrayBuffer[Int] = ArrayBuffer()


  def removeMatches(unsafeMove: Int): Int = {
    val safeMove = clampMove(unsafeMove, minMove, maxMove)
    currentState -= safeMove //so we mutate/modify internal currentState = currentState - safeMove
    movesArray += safeMove //save the move to move log
    currentState
  }

  //I am removing parameters since showStatus only cares about properties local to this object
  def showStatus(): Unit = {
    if (currentState > gameEndCondition) {
      println(s"There are $currentState matches left.")
      println(s"It is $currentPlayer's turn.")
    }
    else {
      println(s"The game has ended. $currentPlayer (same as ${getWinner()} ) has won. ")
      println(s"Better luck next time ${getLoser()}.")
      //we could calculate the loser's name as well of course
    }
  }

  def getWinner(): String = {
    if (isGameActive()) "N/A" // could be empty string
    else currentPlayer //since currentPlayer with no moves to make is the winner
  }

  def getLoser(): String = {
    if (isGameActive()) "N/A" // could be empty string
    else { //game is finished
      if (isPlayerATurn) playerB else playerA
    }
  }

  def clampMove(move: Int, min: Int, max: Int, verbose: Boolean = true): Int = {
    if (move > max) {
      if (verbose) println(s"$move was too much, you will have to settle for $max")
      max //return since this is the last line of the function
    } else if (move < min) {
      if (verbose) println(s"$move is too little, you will have to settle for $min")
      min //return
    } else {
      move //return
    }
  }

  /**
   * Toggles to the next player
   *
   * @return
   */
  def nextPlayer(): String = {
    isPlayerATurn = !isPlayerATurn
    currentPlayer = if (isPlayerATurn) playerA else playerB
    currentPlayer
  }

  def isCurrentPlayerComputer(): Boolean = currentPlayer == "COMPUTER"

  def isGameActive(): Boolean = currentState > gameEndCondition

  def printMoves(): Array[Int] = {
    for ((move, index) <- movesArray.zipWithIndex) {
      val playerName = if (index % 2 == 0) playerA else playerB
      println(s"Move ${index + 1}. $playerName took $move matches")
    }
    movesArray.toArray
  }

  def saveGameResult(dst: String): Unit = {
    if (!Files.exists(Paths.get(dst))) {
      println("Saving header since no file exists")
      val header = "winner, loser, date" //we do not add \n since append will add \n
      Util.saveText(dst, header)
    } else {
      println(s"Need to save winner ${getWinner()} and loser ${getLoser()}")
      //TODO save above and also with the date
      //https://alvinalexander.com/scala/scala-get-current-date-time-hour-calendar-example/
      val now = Calendar.getInstance().getTime()
      val utcNow = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)
      //      println(LocalDateTime.now().format( DateTimeFormatter.ISO_INSTANT ))
      //TODO get local time in ISO 8601 format
      val row = s"${getWinner()}, ${getLoser()}, $utcNow"
      Util.saveText(dst, row, append=true) //crucial that we use append flag so we do not accidentally overwrite..
      //TODO explore logging solutions such as infamous log4j which make saving similar date more structured
    }
  }

  def saveGameScore(folder:String = "src/resources/nim", prefix:String = "game", suffix:String = ".csv"):Unit = {
    val dst = folder + "/" + prefix + "_" + getTimeStamp + suffix
    val header = "player, move"
    for ((move, index) <- movesArray.zipWithIndex) {
      val playerName = if (index % 2 == 0) playerA else playerB
      val row = s"$playerName, $move"
      Util.saveText(dst, row, append=true)
    }
  }

  def getTimeStamp: String = {
    val now = LocalDateTime.now()
    val formattedTimeStamp = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss").format(now)
    formattedTimeStamp
  }
}



object Day26Nim extends App {
  //TODO implement basic version of https://en.wikipedia.org/wiki/Nim
  //https://en.wikipedia.org/wiki/Nim#The_21_game


  //TODO setup/config - set data/state what is needed for the application
  //TODO main application/game loop - it could be a loopless - if you process data only once
  //TODO cleanup - close database connections, files etc
  //No plan survives first contact with the enemy - who said it first?
  //It is normal (especially Agile development) to adjust as you development

  //NIM specific TODO
  //setup
  //we will start with 21 matches/tokens
  val saveDst = "src/resources/nim/scores.csv"
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
  val nimGame = new Nim(playerA, playerB,startingCount, gameEndCondition, minMove, maxMove, isPlayerAStarting)

  def getComputerMove():Int = 2 //TODO add more complex logic later
  //computer can be made to play perfectly
  //or we could add some randomness

  //main loop - while there are some matches play on
  //TODO implement PvP - player versus player - computer only checks the rules
  while (nimGame.isGameActive()) {
    //show the game state
    //    println(s"Currently there are $currentState matches on the table")
    nimGame.showStatus()

    val move = if (nimGame.isCurrentPlayerComputer()) {
      getComputerMove()
    } else {
      readLine(s"How many matches do you want to take ${nimGame.currentPlayer}? (1-3) ").toInt
    } //TODO error checking
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

  nimGame.saveGameResult(saveDst)
  nimGame.saveGameScore()
  //print game status again
  //TODO implement multiple games



}