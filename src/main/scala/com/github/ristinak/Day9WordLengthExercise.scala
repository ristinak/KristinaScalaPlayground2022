package com.github.ristinak

import scala.io.StdIn.readLine

//you are allowed to use yield or map/filter
object Day9WordLengthExercise extends App {

  def betterSentence(sentence: String): String = {
    var betterSentence = sentence
    for (c <- sentence) {
      if (!c.isLetter && c != '\'') { // keeping apostrophes as part of words
        betterSentence = betterSentence.replace(c, ' ')
        // replaces all punctuation with a space
      }
    }
    betterSentence.trim.replaceAll(" +", " ")
    // removes duplicate spaces
  }

  val sentence = readLine("Please enter a sentence:\n")
  val aBetterSentence = betterSentence(sentence)
  println(s"Your sentence without punctuation: $aBetterSentence\n")
  val words = aBetterSentence.split(" ")
  val wordLengths = words.map(_.length)
  val wordsOverFive = words.filter(_.length > 5)

  println(s"Word lengths: ${wordLengths.mkString(", ")}")
  println(s"The long words: ${wordsOverFive.mkString(", ")}")

}
