package com.github.ristinak

import java.time.Year
import scala.io.StdIn.readLine

object Day3AgeCalculatorKristina extends App {
  val currentYear = Year.now.getValue
  val name = readLine("What is you name?\n")
  val age = readLine(s"Hello, $name! How old are you?\n")
  val centeniary = currentYear + 100 - age.toInt
  val smiley = "\u263a"
  println(s"Great news! You will be 100 years old in $centeniary! $smiley")
}
