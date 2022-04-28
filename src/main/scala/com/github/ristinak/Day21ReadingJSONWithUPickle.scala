package com.github.ristinak

import ujson.Value.Value

object Day21ReadingJSONWithUPickle extends App{
  val src = "src/resources/json/fruitFacts.json"

  val rawText: String = Util.getTextFromFile(src)
//  println(rawText.take(100))

  //let's use uPickle library to parse the raw Text into some structure
  val data: Value = ujson.read(rawText)
//  println(data)
  //in order for arr method to work I need to know that my top level of data is actually an array (not an object or just a string or number)
  val arrData: Array[Value] = data.arr.toArray
  println(s"arrData head: ${arrData.head}")
  println(s"arrData last: ${arrData.last}")

  //with o I indicate that it is an object
  val fruits: Array[Fruit] = for (o <- arrData) yield {
    Fruit(o("genus").str,
      o("name").str,
      o("id").num.toInt,
      o("family").str,
      o("order").str,
      o("nutritions")("carbohydrates").num, //so Double by default
      o("nutritions")("protein").num,
      o("nutritions")("fat").num,
      o("nutritions")("calories").num,
      o("nutritions")("sugar").num,
    )
  }

//  fruits.take(3).foreach(println)

  //TODO find most calorie dense fruit - it looks the data is per 100grams maybe someone can verify this?

  val highCalFruits = fruits.sortBy(_.calories).reverse
  println(s"The most caloric fruits are:\n${highCalFruits.take(5).mkString("\n")}")


  //TODO find top 5 fattiest fruits

  val highFatFruits = fruits.sortBy(_.fat).reverse
  println(s"The most fatty fruits are:\n${highFatFruits.take(5).mkString("\n")}")

  //TODO find top 5 protein sources for fruits

  val highProteinFruits = fruits.sortBy(_.protein).reverse
  println(s"Fruits with the highest amount of protein are:\n${highProteinFruits.take(5).mkString("\n")}")

  //TODO find 5 least sugary fruits

  val lowSugarFruits = fruits.sortBy(_.sugar)
  println(s"Fruits with the least amount of sugar are:\n${lowSugarFruits.take(5).mkString("\n")}")

  //TODO find 5 fruits with most carbohydrates that are not sugars (so difference between carbohydrates and sugar)

  val highNonSugarFruits = fruits.sortBy(_.nonSugarCarbs).reverse
  println(s"Fruits with the highest amount of non-sugar carbs are:\n${highNonSugarFruits.take(5).mkString("\n")}")

  //you can add some extra conclusions, statistics as well
}
