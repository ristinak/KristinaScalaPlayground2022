import scala.io.StdIn.readLine
//Ask user to enter a sentence
//Split sentence into words using split, you will will have a sequence of words, we did this on Day 8
//Generate word length sequence (can use map or yield)
//Filter only words of length over 5
//print word lengths for every word
//print the long words

//you are allowed to use yield or map/filter
object Day9WordLengthExercise extends App {

  val sentence = readLine("Please enter a sentence: ").trim
  val betterSentence = sentence.replaceAll("\\p{Punct}","") // from Java, found online
  // it only works if there are spaces between words, not only punctuation
  val words = betterSentence.split(" ")
  val wordLengths = words.map(_.length)
  val wordsOverFive = words.filter(_.length > 5)

  println(s"Word lengths: ${wordLengths.mkString(", ")}")
  println(s"The long words: ${wordsOverFive.mkString(", ")}")

}
