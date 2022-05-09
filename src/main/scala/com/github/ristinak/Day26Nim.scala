package com.github.ristinak

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.readLine

//TODO exercise
//Create class Nim (or NimGame or NimState) - if you can come up with a better name please do
//you could use case class if you want
//Objects created from this class will hold ALL information particular to a single game of NIM
//
//create two at least two methods
//TODO one will be removeMatches which will perform a move of units allowed by the rules of the game

//TODO 2nd method would be to print game status let's call this method showStatus - how many matches are in the pile, whose turn it is,
//
//TODO bonus: objects created from this class should also have a ArrayBuffer of moves
//so each time removeMatches is called this buffer is updated, thus we have an exact log of game moves

class Nim(
           val startingCount:Int = 21,
           val gameEndCondition:Int = 0,
           val minMove: Int = 1,
           val maxMove: Int = 3,
           val playerA:String,
           val playerB: String,
           var isPlayerATurn:Boolean = true) {

  var currentState:Int = startingCount
  var movesArray: ArrayBuffer[Int] = ArrayBuffer()

  def removeMatches(safeMove:Int):Int = {
    currentState - safeMove
  }

  def showStatus(currentState:Int, currentPlayer:String):Unit = {
    if (currentState > 0) {
      println(s"There are $currentState matches left.")
      println(s"It is $currentPlayer's turn.")
    }
    else {
    println(s"The game has ended. $currentPlayer has won.")}
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
  val startingCount = 21
  val gameEndCondition = 0
  val minMove = 1
  val maxMove = 3
  var isPlayerATurn = true

  val playerA = readLine("Player A what is your name?")
  val playerB = readLine("Player B what is your name?")

  println(s"Player A - $playerA and Player B - $playerB let us play NIM!")

  //inevitably in most applications we will have some state that we want to keep track of
  //here it is simple enough state that we can use a few variables
  //at some point we will want to structure this game/app state into a separate object based on some class

  //TODO create a new object holding all the information necessary for a game nim from this class Nim
  val nimGame = new Nim(startingCount, gameEndCondition, minMove, maxMove, playerA, playerB, isPlayerATurn)

//  var currentState = nimGame.currentState

  def clampMove(move: Int, min:Int, max:Int, verbose: Boolean  = true): Int = {
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

  //main loop - while there are some matches play on
  //TODO implement PvP - player versus player - computer only checks the rules
  while (nimGame.currentState > nimGame.gameEndCondition) {
    val currentPlayer = if (nimGame.isPlayerATurn) playerA else playerB
    val move = readLine(s"How many matches do you want to take $currentPlayer? (1-3) ").toInt //TODO error checking
    val safeMove = clampMove(move, nimGame.minMove, nimGame.maxMove)
    nimGame.currentState = nimGame.removeMatches(safeMove)
    nimGame.movesArray.append(safeMove)
    nimGame.isPlayerATurn = !nimGame.isPlayerATurn //toggle trick to change a boolean to reverse version of present
    val nextPlayer = if (nimGame.isPlayerATurn) playerA else playerB
    nimGame.showStatus(nimGame.currentState, nextPlayer)
  }
  //TODO PvC - player versus computer you will need to add some logic to the computer

  //end cleanup here we just print some game state and congratulations
  //TODO add saving to Database, stats etc

  val winner = if (nimGame.isPlayerATurn) playerA else playerB
  val loser = if (!nimGame.isPlayerATurn) playerA else playerB
  println(s"Game ended. Congratulations $winner! Better luck next time $loser.")
  //print game status again
  nimGame.showStatus(nimGame.currentState, winner)
  println(s"The sequence of moves performed: ${nimGame.movesArray.mkString(", ")}.")
  //TODO implement multiple games



}
