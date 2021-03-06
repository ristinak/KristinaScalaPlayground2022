package com.github.ristinak

object Day6FarenheitCalculator extends App {

  def celciusToFarenheit(tempInCelcius: Double = 36.6): Double = {
    32 + (tempInCelcius * 180.0).round / 100.0
  }

  println(s"36.6 degrees C is ${celciusToFarenheit()} degrees Farenheit.")
  println(s"37 degrees C is ${celciusToFarenheit(37)} degrees Farenheit.")
  println(s"48.5 degrees C is ${celciusToFarenheit(48.5)} degrees Farenheit.")

}
