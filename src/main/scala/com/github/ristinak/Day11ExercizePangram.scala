package com.github.ristinak

object Day11ExercisePangram extends App {

  //TODO write a pangram tester
  //https://en.wikipedia.org/wiki/Pangram
  //check if text contains every letter of an alphabet at least once
  //for this task consider upuper and lower case the same
  def isPangram(text:String, alpha:String="abcdefghijklmnopqrstuvwxyz"):Boolean = {
    val charSet = text.toLowerCase.toSet
    alpha.toSet.subsetOf(charSet)
  }

  val myTestText = "The five boxing wizards jump quickly."
  println(isPangram(myTestText)) //should be true

}
