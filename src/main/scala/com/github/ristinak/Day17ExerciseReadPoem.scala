package com.github.ristinak

import scala.io.Source

object Day17ExerciseReadPoem extends App {

  //read stopping by poem from src/resources/stopping_by.txt
  //get poem name - it is the first line
  //get poet - it is in the 2nd line but we want just the poet

  //Get all lines which contain "woods" somewhere in the text
  //so instead of startsWith we use contains

  def getTextFromFile(src: String):String = {
    val bufferedSource = Source.fromFile(src)
    val text = bufferedSource.mkString
    bufferedSource.close()
    text
  }

  def getLinesFromFile(src: String):Array[String] = {
    val bufferedSource = Source.fromFile(src)
    val lines = bufferedSource.getLines().toArray
    bufferedSource.close()
    lines
  }

  val relativeFilePath = "src/resources/stopping_by.txt"
  val poemLines = getLinesFromFile(relativeFilePath)
  val poemName = poemLines(0)
  println(s"The poem is titled \"$poemName\".\n")

  val secondLineWords = poemLines(1).split(" ")
  val poetFirstName = secondLineWords(1)
  println(s"The poet's first name is $poetFirstName.\n")

  val woodsLines = poemLines.filter(_.contains("woods"))
  println(s"These lines contain the word \"woods\":\n\n${woodsLines.mkString("\n")}")

}
