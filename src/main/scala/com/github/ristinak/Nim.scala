package com.github.ristinak

import java.nio.file.{Files, Paths}
import java.text.SimpleDateFormat
import java.time.{ZoneOffset, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.util.Calendar
import scala.collection.mutable.ArrayBuffer

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

  def getMoves: Array[Int] = movesArray.toArray //again parameterless function


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
      println(s"The game has ended. $currentPlayer (same as $getWinner ) has won. ")
      println(s"Better luck next time $getLoser.")
      //we could calculate the loser's name as well of course
    }
  }

  def getWinner: String = {
    if (isGameActive) "N/A" // could be empty string
    else currentPlayer //since currentPlayer with no moves to make is the winner
  }

  def getLoser: String = {
    if (isGameActive) "N/A" // could be empty string
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

  def isCurrentPlayerComputer: Boolean = currentPlayer == "COMPUTER"

  def isGameActive: Boolean = currentState > gameEndCondition

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
      println(s"Need to save winner $getWinner and loser $getLoser")
      //TODO save above and also with the date
      //https://alvinalexander.com/scala/scala-get-current-date-time-hour-calendar-example/
      val now = Calendar.getInstance().getTime
      println(s"Today is $now")
      val utcNow = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)
      //      println(LocalDateTime.now().format( DateTimeFormatter.ISO_INSTANT ))
      //TODO get local time in ISO 8601 format
      val row = s"$getWinner, $getLoser, $utcNow"
      Util.saveText(dst, row, append = true) //crucial that we use append flag so we do not accidentally overwrite..
      //TODO explore logging solutions such as infamous log4j which make saving similar date more structured
    }
  }

  def saveGameScore(folder: String = "src/resources/nim", prefix: String = "game", suffix: String = ".csv"): Unit = {
    println("Saving game score!")
    val now = Calendar.getInstance().getTime
    val minuteFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss")
    val currentTimeAsString = minuteFormat.format(now)
    val dst = folder + "/" + prefix + "_" + currentTimeAsString + suffix
    println("Need to save the score!")
    //    for ((move, index) <- movesArray.zipWithIndex) {
    //      val playerName = if (index % 2 == 0) playerA else playerB
    //      val row = s"$playerName, $move"
    //      Util.saveText(dst, row, append = true)
    //    }
    //saving line by line is going to be slower than saving all the moves at once
    //better to create a mapping (or same thing using yield) of some sequence of results
    val moveRows = for ((move, index) <- movesArray.zipWithIndex) yield {
      val playerName = if (index % 2 == 0) playerA else playerB
      val row = s"$playerName, $move"
      row
    }
    Util.saveLines(dst, moveRows.toArray)

  }
}