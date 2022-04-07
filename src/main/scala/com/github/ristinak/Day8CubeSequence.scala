package com.github.ristinak

import scala.io.StdIn.readLine

object Day8CubeSequence extends App {

  val startNumber = readLine("Enter starting number: ").toInt
  val endNumber = readLine("Enter ending number: ").toInt

  val cubeSequence = for (number <- startNumber to endNumber) yield Math.pow(number, 3).toInt
  val cubeSequenceString = cubeSequence.mkString(", ")
  println(s"The cubes of all numbers between $startNumber and $endNumber are: $cubeSequenceString")

  val oddCubes = for (cube <- cubeSequence if cube % 2 != 0) yield cube
  val oddCubesString = oddCubes.mkString(", ")
  println(s"Among them, the odd cubes: $oddCubesString")

}
