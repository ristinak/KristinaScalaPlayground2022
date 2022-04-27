package com.github.ristinak

case class Veggie(category:String, item:String, variety:String, date:String, price:Double, unit:String) {
  val year = date.split("-").head.toInt //of course we could add some error checking as well
}