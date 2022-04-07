package com.github.ristinak

object Day15WeekdayExercise extends App {

  def getDay(day: Int): String = day match {
    case 1 => "Monday"
    case 2 => "Tuesday"
    case 3 => "Wednesday"
    case 4 => "Thursday"
    case 5 => "Friday"
    case 6 => "Saturday"
    case 7 => "Sunday"
    case default => "Unknown Weekday"
  }

  def getDayType(day: String): String = day match {
    case "Monday" | "Tuesday" | "Wednesday" | "Thursday" | "Friday" => "Weekday"
    case "Saturday" | "Sunday" => "Weekend"
    case default => "Groundhog Day"
  }

  println(getDay(8))
  println(getDay(1))
  println(getDay(5))

  println(getDayType("Tuesday"))
  println(getDayType(getDay(9)))

}
