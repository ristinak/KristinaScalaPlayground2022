package com.github.ristinak

object Day5FizzBuzz_Kristina extends App {
  //TODO
  //TODO create a FizzBuzz program based on children's game I think from Britain
  //for numbers 1 to 100 (included!)
  //print FizzBuzz if number divides evenly by both 5 and 7
  //print Fizz if number only divides by 5
  //print Buzz if number divides by 7
  //print number if number does not divide by 5 or 7
  //so something like this 1,2,3,4,Fizz,6,Buzz,..... 34,FizzBuzz,36,...., 99,Fizz (because 100 divides evenly by 5
  //printing can be done with number on new line like we have been doing

  for (number <- 1 to 100) {
    if (number % 35 == 0) println(s"FizzBuzz")
    else if (number % 5 == 0) println(s"Fizz")
    else if (number % 7 == 0) println(s"Buzz")
    else println(number)
  }
}
