package com.github.ristinak

object PunctuationTests extends App {
  val sentence = "Hello there,how are you? "
  val words = sentence.split(" ")
  println(words.mkString)
  println('?'.isLetter)
  //  val punctuation = "\\p{Punct}"
  //  println(punctuation.contains(','))

}
