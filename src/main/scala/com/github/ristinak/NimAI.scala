package com.github.ristinak

import scala.util.Random

object  NimAI {
  //in more complex games/application currentState would be some stat object such as Nim object
  def getComputerMove(currentState:Int, computerLevel:Int = 0): Int = {
    computerLevel match {
      case 1 => getMinimalStrategy
      case 2 => getRandomStrategy
      case 3 => getSmartStrategy(currentState)
      case _ => getMinimalStrategy //in an application we do not want to print error but we could add logging
      //so you would want to check and clamp allowed values earlier
    }
  }
  //computer can be made to play perfectly
  //or we could add some randomness
  def getMinimalStrategy:Int = 1
  def getRandomStrategy:Int = Random.nextInt(3)+1 //nextInt returns 0 to 2 but we need 1 to 3

  //TODO FIXME create smart strategy
  def getSmartStrategy(currentState:Int):Int = {
    //some match case would work in combination with modulo/reminder operation
    //plus what should computer do when it has a losing position - one idea play randomly then
    2 //FIXME figure out a smart strategy
  }
}

