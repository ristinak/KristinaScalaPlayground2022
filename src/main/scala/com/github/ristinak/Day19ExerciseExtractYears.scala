package com.github.ristinak

object Day19ExerciseExtractYears extends App {
  //extract all years into a Array(or list or vector) of Integers
  //From the following src
  val src = "src/resources/ChristieA_Poirot_Investigates.txt" //you can choose another text if you wish
  //you can test your regex here first: https://regex101.com/ or somewhere else

  val text = Util.getTextFromFile(src)

  val yearRegEx = raw"\b\d{4}\b".r
  val years = yearRegEx.findAllIn(text).map(y => y.toInt).toArray
  println(years.mkString(", "))

  val phoneRegEx = raw"\(\d{3}\) \d{3}-\d{4}".r
  val phoneNumbers = phoneRegEx.findAllIn(text).toArray
  println(phoneNumbers.mkString(", "))

}
