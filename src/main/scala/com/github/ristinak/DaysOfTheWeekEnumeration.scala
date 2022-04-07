package com.github.ristinak

object Weekdays extends Enumeration {
  type Weekday = Value
  val Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday = Value
}

object DaysOfTheWeekEnumeration extends App {

  def isWeekend(d: Weekdays.Weekday): Boolean = {
    d == Weekdays.Saturday || d == Weekdays.Sunday
  }

  val day1 = Weekdays.Monday
  val day2 = Weekdays.Saturday
  print(s"Is $day1 the weekend? ")
  println(isWeekend(day1))
  print(s"Is $day2 the weekend? ")
  println(isWeekend(day2))

  println(s"Is $day1 equal to ${day1.toString}? ${day1 == day1.toString}")


}
