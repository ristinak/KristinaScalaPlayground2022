//1. Song class
class Song(val title: String = "",
           val author: String = "",
           val lyrics: Seq[String] = Seq()) {

  println(s"New Song made: $title by $author.")

  def sing(maxLines: Int = -1):Unit = {
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
    def yell(maxLines: Int = -1):Unit = {
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


object Day13ExerciseSong extends App {
  println("Let's make some songs!")
  //create a couple of Songs
  //possibly some Rap songs as well :)

  val mySong1 = new Song("Mmm Bop", "Hanson",
    Seq("You have so many relationships in this life",
      "Only one or two will last",
      "You go through all the pain and strife",
      "Then you turn your back and they're gone so fast",
      "Oh yeah",
      "And they're gone so fast, yeah",
      "Oh, so hold on the ones who really care",
      "In the end they'll be the only ones there",
      "(...)"))

  val mySong2 = new Song("Don't Stop Believin'", "Journey",
    Seq("Just a small town girl",
      "Livin' in a lonely world",
      "She took the midnight train goin' anywhere",
      "Just a city boy",
      "Born and raised in south Detroit",
      "He took the midnight train goin' anywhere","..."))
  mySong1.yell(3)
  mySong2.yell(3)
  mySong1.sing(4)
  mySong2.sing(4)
}
