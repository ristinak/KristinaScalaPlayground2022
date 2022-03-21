import scala.io.StdIn.readLine

//TODO ask user for starting number
//TODO ask user for ending numbers
//Calculate cubes of these integers including start and end AND store results in a sequence
//Print the saved sequence on screen

//extra challenge save odd cubes and print them

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
