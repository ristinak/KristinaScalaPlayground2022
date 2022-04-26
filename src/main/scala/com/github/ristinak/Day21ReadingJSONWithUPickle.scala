package com.github.ristinak

import ujson.Value.Value

object Day21ReadingJSONWithUPickle {
  val src = "src/resources/json/fruitFacts.json"

  val rawText: String = Util.getTextFromFile(src)
  println(rawText.take(100))

  //let's use uPickle library to parse the raw Text into some structure
  val data: Value = ujson.read(rawText)
  println(data)
  //in order for arr method to work I need to know that my top level of data is actually an array (not an object or just a string or number)
  val arrData: Array[Value] = data.arr.toArray
  println(arrData.head)
  println(arrData.last)

  //with o I indicate that it is an object
  val fruits: Array[Fruit] = for (o <- arrData) yield {
    Fruit(o("genus").str,
      o("name").str,
      o("id").num.toInt,
      o("family").str,
      o("order").str,
      o("nutritions")("carbohydrates").num //so Double by default
      //TODO add the rest of the fields proteins etc
    )
  }

  fruits.take(3).foreach(println)

  //TODO find most calorie dense fruit - it looks the data is per 100grams maybe someone can verify this?
  //TODO find top 5 fattiest fruits
  //TODO find top 5 protein sources for fruits
  //TODO find 5 least sugary fruits
  //TODO find 5 fruits with most carbohydrates that are not sugars (so difference between carbohydrates and sugar)

  //you can add some extra conclusions, statistics as well
}
