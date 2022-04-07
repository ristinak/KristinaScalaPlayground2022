package com.github.ristinak

import scala.io.StdIn.readLine

object Day4HealthExercise_Kristina extends App {

  println("Temperature exercise")
  //Ask person for name
  //Ask for their temperature
  //if temperature is below 35 print "That is a bit too cold"
  //if temperature is between 35 and 37 (both sides inclusive) then print "You are all right!"
  //finally if the temperature is over 37 then print "You have a fever! Consider contacting a doctor"

  //consider what would be the best way to handle this logic

  val name = readLine("What is your name?\n")
  val temperature = readLine("What is your temperature today?\n").toDouble
  val minTemp = 35
  val maxTemp = 37

  if (temperature >= minTemp && temperature <= maxTemp) println(s"You are alright!, $name \n")
  else if (temperature < minTemp) println(s"Aren't you a bit cold, $name?\n")
  else println("You have a fever! Consider contacting your doctor.\n")

}
