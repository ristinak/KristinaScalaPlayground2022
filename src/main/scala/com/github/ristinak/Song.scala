package com.github.ristinak

class Song(title: String = "", author: String= "", lyrics:Seq[String]){

  println(s"New song $title made by $author")
  println(s"The song has ${lyrics.length} lines")

  //internal utility function
  def printLyrics(lines:Seq[String], maxLines: Int = -1): Unit = {
    val numLines = if (maxLines == -1) lines.length else maxLines
    for (line <- lines.take(numLines)) println(line)
  }

  //private unless we want to allow outside use of this function
  def printTitle(): Unit = {
    println("-"*20)
    if (author != "") print(s"$author")
    if (title != "") print(s" - $title\n")
    println("-"*20)
  }

  def sing(maxLines: Int = -1):Song= {
    printTitle()
    printLyrics(lyrics, maxLines) //sometimes these pass along chains can get too long 3 or 4 functions deep
    this //so we return a com.github.ristinak.Song itself so then we can chain the next command
  }

  def yell(maxLines: Int = -1):Song = {
    printTitle()
    val capitalLyrics = lyrics.map(_.toUpperCase)
    printLyrics(capitalLyrics, maxLines)
    this
  }

  //it is now trivial to add a similar whisper function
  def whisper(maxLines: Int = -1):Song = {
    printTitle()
    val capitalLyrics = lyrics.map(_.toLowerCase)
    printLyrics(capitalLyrics, maxLines)
    this
  }

}

class Rap(title:String, author:String, lyrics: Seq[String])
  extends Song(title:String, author:String, lyrics: Seq[String]) {

  //now any com.github.ristinak.Rap will have access to ALL com.github.ristinak.Song methods and values /fields
  def dropIt(drop:String, maxLines:Int = -1): Unit = {
    println(s"Dropping some beats with $drop on $title")
    val linesOfWords = for (line <- lyrics) yield line.split(" ")

    var newLyrics = scala.collection.mutable.ArrayBuffer[String]()
    for (line <- linesOfWords) {
      newLyrics += line.mkString(s" $drop ") + s" $drop"
    }
    printTitle()
    printLyrics(newLyrics.toSeq, maxLines)
    }
  }