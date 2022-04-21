package com.github.ristinak

object Day19ExerciseExtractYears extends App {
  //TODO extract all years into a Array(or list or vector) of Integers
  //From the following src
  val src = "src/resources/ChristieA_Poirot_Investigates.txt" //you can choose another text if you wish
  //you can test your regex here first: https://regex101.com/ or somewhere else

  val text = Util.getTextFromFile(src)

  val yearRegEx = raw"\d{4}".r
  val years = yearRegEx.findAllIn(text).toArray.mkString(", ")
  println(years)

}
