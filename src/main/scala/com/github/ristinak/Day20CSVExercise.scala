package com.github.ristinak

import java.nio.file.{Files, Paths}

object Day20CSVExercise extends App {

  //saves a csv file from web to src/resources/csv/customFileName.csv and returns the text
//  def saveTextFromWeb(url: String, dstFolder: String = "src/resources/csv"): String = {
//    Files.createDirectories(Paths.get(dstFolder))
//    val fileName = url.split("/").last
//    val dst = s"$dstFolder/$fileName"
//    val text = Util.getTextFromWebAndSave(url, dst)
//    text
//  }
  val src = "src/resources/csv/fruitvegprices-19apr22.csv"
  val lines = Util.getLinesFromFile(src)
  val splitLines = lines.map(_.split(","))

  def arrayToVeggie(arr:Array[String]):Veggie = {
    //in real life scenarios we would want a library to handle bad cases
    //we would want to check if we have some bad data
    Veggie(arr(0), arr(1), arr(2), arr(3),arr(4).toDouble, arr(5))
  }

  val veggies = splitLines.tail.map(arrayToVeggie)
//  veggies.take(5).foreach(println)

  val prices = veggies.map(_.price)
//  println(s"Max price is ${prices.max}")

  val maxPriceItems = veggies.filter(_.price == prices.max)
//  maxPriceItems.take(5).foreach(println)

  val expensiveItems = veggies.filter(_.price >= prices.max - 1.60) //1.60 should really be a val constant
//  expensiveItems.take(5).foreach(println)

  val sortedVeggies = veggies.sortBy(_.price)
//  sortedVeggies.take(10).foreach(println) //so it was sorted ascending so we get the cheap flowers here
//  sortedVeggies.reverse.take(10).foreach(println) //our top ten most expensive items

  //TODO get the top 5 most expensive apple entries
  val apples = veggies.filter(_.item == "apples")
  val fiveExpensiveApples = apples.sortBy(_.price).reverse.take(5)
  println(s"The top 5 most expensive apple entries:\n${fiveExpensiveApples.mkString("\n")}")

  //TODO get the least expensive 5 apple entries
  val fiveCheapApples = apples.sortBy(_.price).take(5)
  println(s"The bottom 5 cheapest apple entries:\n${fiveCheapApples.mkString("\n")}")

  //TODO get average price for lettuce
  val lettuce = veggies.filter(_.item == "lettuce")
  val lettucePrices = lettuce.map(_.price)
  val totalPriceLettuce = lettucePrices.sum
  val avgLettucePrice = Util.myRound((totalPriceLettuce / lettuce.length), 2)
  println(s"Average price of lettuce is $avgLettucePrice")

  //TODO get cherry tomatoes pricing min, max, mean for year 2021
  val tomatoes = veggies.filter(_.item == "tomatoes")
  println(s"There are ${tomatoes.length} entries of tomatoes.")
  val cherryTomatoes = tomatoes.filter(_.variety  == "cherry")
  println(s"There are ${cherryTomatoes.length} entries of cherry tomatoes.")
  val cherryTomatoes2021 = cherryTomatoes.filter(_.date.startsWith("2021"))
  println(s"There are ${cherryTomatoes2021.length} entries of cherry tomatoes in 2021.")
//  println(s"Cherry tomatoes 2021:\n${cherryTomatoes2021.mkString("\n")}")
  val cherryTomatoPrices2021 = cherryTomatoes2021.map(_.price)
  val minCherryTomatoPrice2021 = cherryTomatoPrices2021.min
  val maxCherryTomatoPrice2021 = cherryTomatoPrices2021.max
  val cherryTomatoPriceAvg2021 = Util.myRound(cherryTomatoPrices2021.sum / cherryTomatoPrices2021.length, 2)
  println(s"Average price of cherry tomatoes in 2021 is: ${cherryTomatoPrices2021.sum} divided by " +
    s"${cherryTomatoPrices2021.length} = $cherryTomatoPriceAvg2021")

  //TODO extra credit challenge get average price for lettuce by year

  def avgPriceThatYear(veggies: Array[Veggie], item: String = "", year: Int = 0, precision: Int = 2): Double = {
    val itemsThatYear = veggies.filter(_.item == item).filter(_.year == year)
    val pricesThatYear = itemsThatYear.map(_.price)
    val avgPriceThatYear = Util.myRound(pricesThatYear.sum / pricesThatYear.length, precision)
    println(s"The average price of $item in $year was $avgPriceThatYear.")
    avgPriceThatYear
  }

  val lettuceYears = lettuce.groupBy(_.year)
  for (year <- lettuceYears.keys) {
    avgPriceThatYear(veggies, "lettuce", year, 3)
  }

  //two approaches - one is simply hardcode starting and ending years and filter by those
  //you might not even need to extract year simply lexicographical filering should work

  //even better approach use groupBy
  // hint: use groupBy but first you would need to modify case class with Year field(value)
  //alternative to creating a Year entry would be to split date field while grouping and group by first portion
  // https://alvinalexander.com/scala/how-to-split-sequences-subsets-groupby-partition-scala-cookbook/
}

