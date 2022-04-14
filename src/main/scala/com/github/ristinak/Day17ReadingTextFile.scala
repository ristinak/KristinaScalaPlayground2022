package com.github.ristinak
import scala.io.Source

object Day17ReadingTextFile extends App {
  println("Let's read some text files!")

  //let's check our current working directory because we need to know to have correct relative path

//  println(System.getProperty("user.dir"))

  //using Absolute Path first
  // Windows uses \ for separating folders - unfortunately \ is also used to escape some character \n \t
  // One solution would be to escape those \ -> \\
  val absoluteFilePath = "C:\\Users\\risti\\IdeaProjects\\KristinaScalaPlayground2022\\src\\main\\scala\\com\\github\\ristinak\\song_days_of_the_week.txt"
  val relativeFilePath = "src/resources/song_days_of_the_week.txt"

  def getTextFromFile(src: String):String = {
    val bufferedSource = Source.fromFile(src) //think of bufferedSource as a stream of bytes
    val text = bufferedSource.mkString //we convert this stream into actual string
    bufferedSource.close() //important to close the file
    text
  }

  def getLinesFromFile(src: String):Array[String] = {
    val bufferedSource = Source.fromFile(src)
    val lines = bufferedSource.getLines().toArray
    bufferedSource.close()
    lines
  }
  //print all characters one by one
  //  for (character <- Source.fromFile(filePath)) println(character) //turns out we just read a big string of characters...

  //print lines one by one
  //  for (line <- Source.fromFile(filePath).getLines) println(line) //we want to read lines of text not single characters

  //we could save the whole file into a String
//  val mySong = Source.fromFile(absoluteFilePath).mkString //so we turn a stream of characters into one big String
//  val mySong = Source.fromFile(relativeFilePath).mkString //so we turn a stream of characters into one big String
//  println(mySong)

//  val mySongLines = Source.fromFile(absoluteFilePath).getLines.toArray //toArray because iterator was on demand lazy
//  val mySongLines = Source.fromFile(relativeFilePath).getLines.toArray //toArray because iterator was on demand lazy

  println()
  println("*"*40)
  println()
  println("Printing Poem line by line \n")

//  for (line <- mySongLines) println(line)

  val myText = getTextFromFile(relativeFilePath)
  println(myText)

  val myLines = getLinesFromFile(relativeFilePath)

  val maxLines = 5
  println(s"FIRST $maxLines lines")
  myLines.slice(0,maxLines).foreach(println)
  //same result as previous operation but here we create the substring first, above we printed line by line
  println(myLines.slice(0,maxLines).mkString("\n")) //this should be slightly faster

  println("\nLines starting with And\n")
  val andLines = myLines.filter(_.startsWith("And"))
  andLines.foreach(println)

  println("\nLines containing no\n")
  val roadsLines = myLines.filter(_.contains("no")) //text can be anywhere in the line
  roadsLines.foreach(println)


}
