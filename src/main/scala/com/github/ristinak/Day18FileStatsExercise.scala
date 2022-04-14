package com.github.ristinak

object Day18FileStatsExercise extends App {
  //Download and save a text file of your choosing from gutenberg.org
  //Get Character Count (including whitespace)
  //Get Line Count
  //get Word Count
  //Get average Word Count in each text line

  val url = "https://www.gutenberg.org/cache/epub/35123/pg35123.txt"
  val dst = "src/resources/HartleyF_The_Ladies_Book_of_Etiquette.txt"
  val text = Util.getTextFromWebAndSave(url, dst)
//  println(text.take(500))

  val charCount = text.length
  println(s"The total number of characters: $charCount")

  val lines = text.split("\n")
  println(s"The number of lines is: ${lines.length}")

  val words = text.split(" ")
  println(s"The number of words is: ${words.length}")

//  val wordCountPerLine = Util.getWordCountPerLine(lines)

  val avgCount = words.length*1.0 / lines.length
  val roundedCount = Util.myRound(avgCount, 2)
  println(s"Average word count in each line is $roundedCount")
}

