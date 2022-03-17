import java.time.Year
import scala.io.StdIn.readLine

object AgeCalculatorKristinaV2 extends App {
  val currentYear = Year.now.getValue
  val targetAge = 100
  val name = readLine("What is you name?\n")
  val age = readLine(s"Hello, $name! How old are you?\n")
  var centenary = currentYear + targetAge - age.toInt
  val smiley = '\u263a'
  val yesORno = readLine("Have you celebrated your birthday this year? Type y or n:\n")
    if(yesORno == "y"){
    println(s"Great news! You will be $targetAge years old in $centenary! $smiley")
  } else if(yesORno == "n"){
      val newCentenary = centenary - 1
      println(s"Great news! You will be $targetAge years old in $newCentenary! $smiley")
  } else{
    println("Sorry, don't understand that.")
  }
}
