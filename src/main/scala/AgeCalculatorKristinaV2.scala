import java.time.Year
import scala.io.StdIn.readLine

object AgeCalculatorKristinaV2 extends App {
  val currentYear = Year.now.getValue
  val name = readLine("What is you name?\n")
  val age = readLine(s"Hello, $name! How old are you?\n")
  var centeniary = currentYear + 100 - age.toInt
  val smiley = "\u263a"
  val yesORno = readLine("Have you celebrated your birthday this year? Type y or n\n")
    if(yesORno == "y"){
    println(s"Great news! You will be 100 years old in $centeniary! $smiley")
  } else if(yesORno == "n"){
      val newCenteniary = centeniary - 1
      println(s"Great news! You will be 100 years old in $newCenteniary! $smiley")
  } else{
    println("Sorry, don't understand that.")
  }
}
