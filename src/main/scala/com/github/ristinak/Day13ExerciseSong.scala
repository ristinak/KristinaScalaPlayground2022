package com.github.ristinak

//1. com.github.ristinak.Song class
class Song1(val title: String = "",
            val author: String = "",
            val lyrics: Seq[String] = Seq()) {

  println(s"New com.github.ristinak.Song made: $title by $author.")

  def sing1(maxLines: Int = -1):Unit = {
    println("-"*20)
    if (author != "") print(s"$author")
    if (title != "") print(s" - $title\n")
    println("-"*20)

    if ((maxLines == -1) || (maxLines > lyrics.length)) {
      for (line <- lyrics) {
        println(line)
      }
    } else {
      for (line <- lyrics.take(maxLines)) {
        println(line)
      }
    }
  }
    def yell1(maxLines: Int = -1):Unit = {
      println("-"*20)
      if (author != "") print(s"$author")
      if (title != "") print(s" - $title\n")
      println("-"*20)

      if ((maxLines == -1) || (maxLines > lyrics.length)) {
        for (line <- lyrics) {
          println(line)
        }
      } else {
        for (line <- lyrics.take(maxLines)) {
          println(line.toUpperCase)
        }
      }
  }


}
//2. com.github.ristinak.Rap class
//class com.github.ristinak.Rap(title: String, author: String, lyrics: Seq[String]
//  extends com.github.ristinak.Song(title: String, author: String, lyrics: Seq[String] {
//
//  def breakIt(maxLines:Int = -1, drop:String = "yeah"):Unit = {
//    println("-"*20)
//    if (author != "") print(s"$author")
//    if (title != "") {
//      print(s" - $title\n")
//    }
//    println("-"*20)
//
//    if ((maxLines == -1) || (maxLines > lyrics.length)) {
//      for (line <- lyrics) {
//        println(line)
//      }
//    } else {
//      for (line <- lyrics.take(maxLines)) {
//        println(line)
//      }
//    }
//  }
//
//}


//For those feeling comfortable with class syntax, create a com.github.ristinak.Rap class that inherits from com.github.ristinak.Song
//
// add a new method breakIt with two default parameters max_lines=-1 and drop equal to "yeah",
// this breakIt which is similar to sing, but adds a drop after each word .
//
//Example:
//
//
//
//zrap = com.github.ristinak.Rap("Ziemeļmeita", "Jumprava", Array("Gāju meklēt ziemeļmeitu"," Garu, tālu ceļu veicu"))
//
//
//
//zrap.breakIt(1, "yah")
//
//Ziemeļmeita - Jumprava
//
//Gāju YAH meklēt YAH ziemeļmeitu YAH


object Day13ExerciseSong extends App {
  println("Let's make some songs!")
  //create a couple of Songs
  //possibly some com.github.ristinak.Rap songs as well :)

  val mySong1 = new Song1("Mmm Bop", "Hanson",
    Seq("You have so many relationships in this life",
      "Only one or two will last",
      "You go through all the pain and strife",
      "Then you turn your back and they're gone so fast",
      "Oh yeah",
      "And they're gone so fast, yeah",
      "Oh, so hold on the ones who really care",
      "In the end they'll be the only ones there",
      "(...)"))

  val mySong2 = new Song1("Don't Stop Believin'", "Journey",
    Seq("Just a small town girl",
      "Livin' in a lonely world",
      "She took the midnight train goin' anywhere",
      "Just a city boy",
      "Born and raised in south Detroit",
      "He took the midnight train goin' anywhere","..."))
  mySong1.yell1(3)
  mySong2.yell1(3)
  mySong1.sing1(4)
  mySong2.sing1(4)
}
