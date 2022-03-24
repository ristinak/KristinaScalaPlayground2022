import scala.io.StdIn.readLine

object Day10ExerciseCountLetters extends App {
  //TODO ask user for some sentence or word
  //Count the number of characters in this string
  //Store them in a mutable Map of type Char, Int
  //print the results
  //you can count whitespace as well

  //you could store results in some sort of Sequence and then convert to Map later, but that would just complicate things

  val myString = readLine("Type something:\n")
  val stringLength = myString.length

// doesn't work:
//  var myMuteMap: scala.collection.mutable.Map[Char, Int] = Map(
//    for (i <- myIndex) yield (myString(i-1), i))


//   A sequence
  val myMuteMap = for (i <- 1 to myString.length) yield (myString(i-1), i)
  println(myMuteMap.mkString)

}
